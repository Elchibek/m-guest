import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IFloor } from 'app/entities/guest/model/floor.model';
import { FloorService } from 'app/entities/guest/service/floor.service';
import { GuestHouseService } from '../service/guest-house.service';
import { IGuestHouse } from '../guest-house.model';
import { GuestHouseFormService } from './guest-house-form.service';

import { GuestHouseUpdateComponent } from './guest-house-update.component';

describe('GuestHouse Management Update Component', () => {
  let comp: GuestHouseUpdateComponent;
  let fixture: ComponentFixture<GuestHouseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let guestHouseFormService: GuestHouseFormService;
  let guestHouseService: GuestHouseService;
  let floorService: FloorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), GuestHouseUpdateComponent],
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
      .overrideTemplate(GuestHouseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GuestHouseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    guestHouseFormService = TestBed.inject(GuestHouseFormService);
    guestHouseService = TestBed.inject(GuestHouseService);
    floorService = TestBed.inject(FloorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Floor query and add missing value', () => {
      const guestHouse: IGuestHouse = { id: 'CBA' };
      const floor: IFloor = { id: '7a884d52-bb71-41cb-b5c5-f5e4f23ffeea' };
      guestHouse.floor = floor;

      const floorCollection: IFloor[] = [{ id: '54a9ba05-8d15-4d8b-baee-297e9741be1c' }];
      jest.spyOn(floorService, 'query').mockReturnValue(of(new HttpResponse({ body: floorCollection })));
      const additionalFloors = [floor];
      const expectedCollection: IFloor[] = [...additionalFloors, ...floorCollection];
      jest.spyOn(floorService, 'addFloorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guestHouse });
      comp.ngOnInit();

      expect(floorService.query).toHaveBeenCalled();
      expect(floorService.addFloorToCollectionIfMissing).toHaveBeenCalledWith(
        floorCollection,
        ...additionalFloors.map(expect.objectContaining),
      );
      expect(comp.floorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const guestHouse: IGuestHouse = { id: 'CBA' };
      const floor: IFloor = { id: '4bf644bb-eaef-4c29-b75c-c86ef6ec5228' };
      guestHouse.floor = floor;

      activatedRoute.data = of({ guestHouse });
      comp.ngOnInit();

      expect(comp.floorsSharedCollection).toContain(floor);
      expect(comp.guestHouse).toEqual(guestHouse);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestHouse>>();
      const guestHouse = { id: 'ABC' };
      jest.spyOn(guestHouseFormService, 'getGuestHouse').mockReturnValue(guestHouse);
      jest.spyOn(guestHouseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestHouse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestHouse }));
      saveSubject.complete();

      // THEN
      expect(guestHouseFormService.getGuestHouse).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(guestHouseService.update).toHaveBeenCalledWith(expect.objectContaining(guestHouse));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestHouse>>();
      const guestHouse = { id: 'ABC' };
      jest.spyOn(guestHouseFormService, 'getGuestHouse').mockReturnValue({ id: null });
      jest.spyOn(guestHouseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestHouse: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestHouse }));
      saveSubject.complete();

      // THEN
      expect(guestHouseFormService.getGuestHouse).toHaveBeenCalled();
      expect(guestHouseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestHouse>>();
      const guestHouse = { id: 'ABC' };
      jest.spyOn(guestHouseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestHouse });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(guestHouseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFloor', () => {
      it('Should forward to floorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(floorService, 'compareFloor');
        comp.compareFloor(entity, entity2);
        expect(floorService.compareFloor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
