import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GuestFromService } from '../service/guest-from.service';
import { IGuestFrom } from '../guest-from.model';
import { GuestFromFormService } from './guest-from-form.service';

import { GuestFromUpdateComponent } from './guest-from-update.component';

describe('GuestFrom Management Update Component', () => {
  let comp: GuestFromUpdateComponent;
  let fixture: ComponentFixture<GuestFromUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let guestFromFormService: GuestFromFormService;
  let guestFromService: GuestFromService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), GuestFromUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(GuestFromUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GuestFromUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    guestFromFormService = TestBed.inject(GuestFromFormService);
    guestFromService = TestBed.inject(GuestFromService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const guestFrom: IGuestFrom = { id: 'CBA' };

      activatedRoute.data = of({ guestFrom });
      comp.ngOnInit();

      expect(comp.guestFrom).toEqual(guestFrom);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestFrom>>();
      const guestFrom = { id: 'ABC' };
      jest.spyOn(guestFromFormService, 'getGuestFrom').mockReturnValue(guestFrom);
      jest.spyOn(guestFromService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestFrom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestFrom }));
      saveSubject.complete();

      // THEN
      expect(guestFromFormService.getGuestFrom).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(guestFromService.update).toHaveBeenCalledWith(expect.objectContaining(guestFrom));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestFrom>>();
      const guestFrom = { id: 'ABC' };
      jest.spyOn(guestFromFormService, 'getGuestFrom').mockReturnValue({ id: null });
      jest.spyOn(guestFromService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestFrom: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestFrom }));
      saveSubject.complete();

      // THEN
      expect(guestFromFormService.getGuestFrom).toHaveBeenCalled();
      expect(guestFromService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestFrom>>();
      const guestFrom = { id: 'ABC' };
      jest.spyOn(guestFromService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestFrom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(guestFromService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
