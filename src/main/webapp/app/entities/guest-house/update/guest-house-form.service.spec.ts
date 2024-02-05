import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../guest-house.test-samples';

import { GuestHouseFormService } from './guest-house-form.service';

describe('GuestHouse Form Service', () => {
  let service: GuestHouseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuestHouseFormService);
  });

  describe('Service methods', () => {
    describe('createGuestHouseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGuestHouseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            isEmpty: expect.any(Object),
            countPerson: expect.any(Object),
            backgroundColor: expect.any(Object),
            floor: expect.any(Object),
          }),
        );
      });

      it('passing IGuestHouse should create a new form with FormGroup', () => {
        const formGroup = service.createGuestHouseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            isEmpty: expect.any(Object),
            countPerson: expect.any(Object),
            backgroundColor: expect.any(Object),
            floor: expect.any(Object),
          }),
        );
      });
    });

    describe('getGuestHouse', () => {
      it('should return NewGuestHouse for default GuestHouse initial value', () => {
        const formGroup = service.createGuestHouseFormGroup(sampleWithNewData);

        const guestHouse = service.getGuestHouse(formGroup) as any;

        expect(guestHouse).toMatchObject(sampleWithNewData);
      });

      it('should return NewGuestHouse for empty GuestHouse initial value', () => {
        const formGroup = service.createGuestHouseFormGroup();

        const guestHouse = service.getGuestHouse(formGroup) as any;

        expect(guestHouse).toMatchObject({});
      });

      it('should return IGuestHouse', () => {
        const formGroup = service.createGuestHouseFormGroup(sampleWithRequiredData);

        const guestHouse = service.getGuestHouse(formGroup) as any;

        expect(guestHouse).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGuestHouse should not enable id FormControl', () => {
        const formGroup = service.createGuestHouseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGuestHouse should disable id FormControl', () => {
        const formGroup = service.createGuestHouseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
