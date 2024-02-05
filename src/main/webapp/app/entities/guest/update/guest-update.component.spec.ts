import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IRestOfTheDay } from 'app/entities/rest-of-the-day/rest-of-the-day.model';
import { RestOfTheDayService } from 'app/entities/rest-of-the-day/service/rest-of-the-day.service';
import { IGuestBlock } from 'app/entities/guest-block/guest-block.model';
import { GuestBlockService } from 'app/entities/guest-block/service/guest-block.service';
import { IEntrance } from 'app/entities/guest/model/entrance.model';
import { EntranceService } from 'app/entities/guest/service/entrance.service';
import { IFloor } from 'app/entities/guest/model/floor.model';
import { FloorService } from 'app/entities/guest/service/floor.service';
import { IGuestHouse } from 'app/entities/guest-house/guest-house.model';
import { GuestHouseService } from 'app/entities/guest-house/service/guest-house.service';
import { IGuestFrom } from 'app/entities/guest-from/guest-from.model';
import { GuestFromService } from 'app/entities/guest-from/service/guest-from.service';
import { IGuest } from '../model/guest.model';
import { GuestService } from '../service/guest.service';
import { GuestFormService } from './guest-form.service';

import { GuestUpdateComponent } from './guest-update.component';

describe('Guest Management Update Component', () => {
  let comp: GuestUpdateComponent;
  let fixture: ComponentFixture<GuestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let guestFormService: GuestFormService;
  let guestService: GuestService;
  let restOfTheDayService: RestOfTheDayService;
  let guestBlockService: GuestBlockService;
  let entranceService: EntranceService;
  let floorService: FloorService;
  let guestHouseService: GuestHouseService;
  let guestFromService: GuestFromService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), GuestUpdateComponent],
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
      .overrideTemplate(GuestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GuestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    guestFormService = TestBed.inject(GuestFormService);
    guestService = TestBed.inject(GuestService);
    restOfTheDayService = TestBed.inject(RestOfTheDayService);
    guestBlockService = TestBed.inject(GuestBlockService);
    entranceService = TestBed.inject(EntranceService);
    floorService = TestBed.inject(FloorService);
    guestHouseService = TestBed.inject(GuestHouseService);
    guestFromService = TestBed.inject(GuestFromService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RestOfTheDay query and add missing value', () => {
      const guest: IGuest = { id: 'CBA' };
      const restOfTheDay: IRestOfTheDay = { id: '0fd34df0-f51e-4430-a810-f891033a5668' };
      guest.restOfTheDay = restOfTheDay;

      const restOfTheDayCollection: IRestOfTheDay[] = [{ id: '5f0ca840-3985-4cd1-b0c9-9cc6523a1fee' }];
      jest.spyOn(restOfTheDayService, 'query').mockReturnValue(of(new HttpResponse({ body: restOfTheDayCollection })));
      const additionalRestOfTheDays = [restOfTheDay];
      const expectedCollection: IRestOfTheDay[] = [...additionalRestOfTheDays, ...restOfTheDayCollection];
      jest.spyOn(restOfTheDayService, 'addRestOfTheDayToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(restOfTheDayService.query).toHaveBeenCalled();
      expect(restOfTheDayService.addRestOfTheDayToCollectionIfMissing).toHaveBeenCalledWith(
        restOfTheDayCollection,
        ...additionalRestOfTheDays.map(expect.objectContaining),
      );
      expect(comp.restOfTheDaysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GuestBlock query and add missing value', () => {
      const guest: IGuest = { id: 'CBA' };
      const guestBlock: IGuestBlock = { id: '8bb2493c-3f2c-4b26-8405-45fe2a2191fd' };
      guest.guestBlock = guestBlock;

      const guestBlockCollection: IGuestBlock[] = [{ id: 'a7302da7-dea6-443e-a8aa-f43e2aa6d195' }];
      jest.spyOn(guestBlockService, 'query').mockReturnValue(of(new HttpResponse({ body: guestBlockCollection })));
      const additionalGuestBlocks = [guestBlock];
      const expectedCollection: IGuestBlock[] = [...additionalGuestBlocks, ...guestBlockCollection];
      jest.spyOn(guestBlockService, 'addGuestBlockToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(guestBlockService.query).toHaveBeenCalled();
      expect(guestBlockService.addGuestBlockToCollectionIfMissing).toHaveBeenCalledWith(
        guestBlockCollection,
        ...additionalGuestBlocks.map(expect.objectContaining),
      );
      expect(comp.guestBlocksSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Entrance query and add missing value', () => {
      const guest: IGuest = { id: 'CBA' };
      const entrance: IEntrance = { id: '306b8144-0548-47f0-9be5-3cae2d137bb9' };
      guest.entrance = entrance;

      const entranceCollection: IEntrance[] = [{ id: 'f33d9bee-2d80-42e8-bd2b-a9e9f7571bbc' }];
      jest.spyOn(entranceService, 'query').mockReturnValue(of(new HttpResponse({ body: entranceCollection })));
      const additionalEntrances = [entrance];
      const expectedCollection: IEntrance[] = [...additionalEntrances, ...entranceCollection];
      jest.spyOn(entranceService, 'addEntranceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(entranceService.query).toHaveBeenCalled();
      expect(entranceService.addEntranceToCollectionIfMissing).toHaveBeenCalledWith(
        entranceCollection,
        ...additionalEntrances.map(expect.objectContaining),
      );
      expect(comp.entrancesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Floor query and add missing value', () => {
      const guest: IGuest = { id: 'CBA' };
      const floor: IFloor = { id: 'd7467032-4dcc-47a8-aea8-fffdff81527b' };
      guest.floor = floor;

      const floorCollection: IFloor[] = [{ id: '24f780c6-3c89-433f-8172-1bd3ca7cbadc' }];
      jest.spyOn(floorService, 'query').mockReturnValue(of(new HttpResponse({ body: floorCollection })));
      const additionalFloors = [floor];
      const expectedCollection: IFloor[] = [...additionalFloors, ...floorCollection];
      jest.spyOn(floorService, 'addFloorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(floorService.query).toHaveBeenCalled();
      expect(floorService.addFloorToCollectionIfMissing).toHaveBeenCalledWith(
        floorCollection,
        ...additionalFloors.map(expect.objectContaining),
      );
      expect(comp.floorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GuestHouse query and add missing value', () => {
      const guest: IGuest = { id: 'CBA' };
      const guestHouse: IGuestHouse = { id: 'dad3e3b2-6133-4ada-ae4e-56036f35a1ef' };
      guest.guestHouse = guestHouse;

      const guestHouseCollection: IGuestHouse[] = [{ id: 'e1fd0d63-2d22-4822-a747-933d7887e7e4' }];
      jest.spyOn(guestHouseService, 'query').mockReturnValue(of(new HttpResponse({ body: guestHouseCollection })));
      const additionalGuestHouses = [guestHouse];
      const expectedCollection: IGuestHouse[] = [...additionalGuestHouses, ...guestHouseCollection];
      jest.spyOn(guestHouseService, 'addGuestHouseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(guestHouseService.query).toHaveBeenCalled();
      expect(guestHouseService.addGuestHouseToCollectionIfMissing).toHaveBeenCalledWith(
        guestHouseCollection,
        ...additionalGuestHouses.map(expect.objectContaining),
      );
      expect(comp.guestHousesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call GuestFrom query and add missing value', () => {
      const guest: IGuest = { id: 'CBA' };
      const guestFrom: IGuestFrom = { id: 'dde0abc1-e68b-4e84-83e4-71e706d4ebdc' };
      guest.guestFrom = guestFrom;

      const guestFromCollection: IGuestFrom[] = [{ id: 'c89a0cef-9a8e-4a7e-ad4d-4ce39a0e5679' }];
      jest.spyOn(guestFromService, 'query').mockReturnValue(of(new HttpResponse({ body: guestFromCollection })));
      const additionalGuestFroms = [guestFrom];
      const expectedCollection: IGuestFrom[] = [...additionalGuestFroms, ...guestFromCollection];
      jest.spyOn(guestFromService, 'addGuestFromToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(guestFromService.query).toHaveBeenCalled();
      expect(guestFromService.addGuestFromToCollectionIfMissing).toHaveBeenCalledWith(
        guestFromCollection,
        ...additionalGuestFroms.map(expect.objectContaining),
      );
      expect(comp.guestFromsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const guest: IGuest = { id: 'CBA' };
      const restOfTheDay: IRestOfTheDay = { id: '5f231957-7bf5-4b84-b029-100366416421' };
      guest.restOfTheDay = restOfTheDay;
      const guestBlock: IGuestBlock = { id: '8548768d-8b59-4f88-ac95-e6ae865c3db0' };
      guest.guestBlock = guestBlock;
      const entrance: IEntrance = { id: '98efcda1-a7d1-4680-b8e2-0da3868fe89e' };
      guest.entrance = entrance;
      const floor: IFloor = { id: 'bf1fd47d-76ba-4ca4-8a49-fc217e175e2a' };
      guest.floor = floor;
      const guestHouse: IGuestHouse = { id: '9e193b3d-587b-4ef0-ba2e-a4f900a93041' };
      guest.guestHouse = guestHouse;
      const guestFrom: IGuestFrom = { id: 'e73bad6d-5df9-47c5-950e-a73381f48961' };
      guest.guestFrom = guestFrom;

      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      expect(comp.restOfTheDaysSharedCollection).toContain(restOfTheDay);
      expect(comp.guestBlocksSharedCollection).toContain(guestBlock);
      expect(comp.entrancesSharedCollection).toContain(entrance);
      expect(comp.floorsSharedCollection).toContain(floor);
      expect(comp.guestHousesSharedCollection).toContain(guestHouse);
      expect(comp.guestFromsSharedCollection).toContain(guestFrom);
      expect(comp.guest).toEqual(guest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuest>>();
      const guest = { id: 'ABC' };
      jest.spyOn(guestFormService, 'getGuest').mockReturnValue(guest);
      jest.spyOn(guestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guest }));
      saveSubject.complete();

      // THEN
      expect(guestFormService.getGuest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(guestService.update).toHaveBeenCalledWith(expect.objectContaining(guest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuest>>();
      const guest = { id: 'ABC' };
      jest.spyOn(guestFormService, 'getGuest').mockReturnValue({ id: null });
      jest.spyOn(guestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guest }));
      saveSubject.complete();

      // THEN
      expect(guestFormService.getGuest).toHaveBeenCalled();
      expect(guestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuest>>();
      const guest = { id: 'ABC' };
      jest.spyOn(guestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(guestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRestOfTheDay', () => {
      it('Should forward to restOfTheDayService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(restOfTheDayService, 'compareRestOfTheDay');
        comp.compareRestOfTheDay(entity, entity2);
        expect(restOfTheDayService.compareRestOfTheDay).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGuestBlock', () => {
      it('Should forward to guestBlockService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(guestBlockService, 'compareGuestBlock');
        comp.compareGuestBlock(entity, entity2);
        expect(guestBlockService.compareGuestBlock).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEntrance', () => {
      it('Should forward to entranceService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(entranceService, 'compareEntrance');
        comp.compareEntrance(entity, entity2);
        expect(entranceService.compareEntrance).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFloor', () => {
      it('Should forward to floorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(floorService, 'compareFloor');
        comp.compareFloor(entity, entity2);
        expect(floorService.compareFloor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGuestHouse', () => {
      it('Should forward to guestHouseService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(guestHouseService, 'compareGuestHouse');
        comp.compareGuestHouse(entity, entity2);
        expect(guestHouseService.compareGuestHouse).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareGuestFrom', () => {
      it('Should forward to guestFromService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(guestFromService, 'compareGuestFrom');
        comp.compareGuestFrom(entity, entity2);
        expect(guestFromService.compareGuestFrom).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
