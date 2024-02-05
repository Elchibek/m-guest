import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GuestBlockService } from '../service/guest-block.service';
import { IGuestBlock } from '../guest-block.model';
import { GuestBlockFormService } from './guest-block-form.service';

import { GuestBlockUpdateComponent } from './guest-block-update.component';

describe('GuestBlock Management Update Component', () => {
  let comp: GuestBlockUpdateComponent;
  let fixture: ComponentFixture<GuestBlockUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let guestBlockFormService: GuestBlockFormService;
  let guestBlockService: GuestBlockService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), GuestBlockUpdateComponent],
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
      .overrideTemplate(GuestBlockUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GuestBlockUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    guestBlockFormService = TestBed.inject(GuestBlockFormService);
    guestBlockService = TestBed.inject(GuestBlockService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const guestBlock: IGuestBlock = { id: 'CBA' };

      activatedRoute.data = of({ guestBlock });
      comp.ngOnInit();

      expect(comp.guestBlock).toEqual(guestBlock);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestBlock>>();
      const guestBlock = { id: 'ABC' };
      jest.spyOn(guestBlockFormService, 'getGuestBlock').mockReturnValue(guestBlock);
      jest.spyOn(guestBlockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestBlock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestBlock }));
      saveSubject.complete();

      // THEN
      expect(guestBlockFormService.getGuestBlock).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(guestBlockService.update).toHaveBeenCalledWith(expect.objectContaining(guestBlock));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestBlock>>();
      const guestBlock = { id: 'ABC' };
      jest.spyOn(guestBlockFormService, 'getGuestBlock').mockReturnValue({ id: null });
      jest.spyOn(guestBlockService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestBlock: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestBlock }));
      saveSubject.complete();

      // THEN
      expect(guestBlockFormService.getGuestBlock).toHaveBeenCalled();
      expect(guestBlockService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestBlock>>();
      const guestBlock = { id: 'ABC' };
      jest.spyOn(guestBlockService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestBlock });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(guestBlockService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
