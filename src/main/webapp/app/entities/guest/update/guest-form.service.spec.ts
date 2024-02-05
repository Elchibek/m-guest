import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../guest.test-samples';

import { GuestFormService } from './guest-form.service';

describe('Guest Form Service', () => {
  let service: GuestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuestFormService);
  });

  describe('Service methods', () => {
    describe('createGuestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGuestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            guestInstitution: expect.any(Object),
            responsible: expect.any(Object),
            isArchive: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            countDidntPay: expect.any(Object),
            paid: expect.any(Object),
            countPerson: expect.any(Object),
            explanation: expect.any(Object),
            price: expect.any(Object),
            totalPrice: expect.any(Object),
            restOfTheDay: expect.any(Object),
            guestBlock: expect.any(Object),
            entrance: expect.any(Object),
            floor: expect.any(Object),
            guestHouse: expect.any(Object),
            guestFrom: expect.any(Object),
          }),
        );
      });

      it('passing IGuest should create a new form with FormGroup', () => {
        const formGroup = service.createGuestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            guestInstitution: expect.any(Object),
            responsible: expect.any(Object),
            isArchive: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            countDidntPay: expect.any(Object),
            paid: expect.any(Object),
            countPerson: expect.any(Object),
            explanation: expect.any(Object),
            price: expect.any(Object),
            totalPrice: expect.any(Object),
            restOfTheDay: expect.any(Object),
            guestBlock: expect.any(Object),
            entrance: expect.any(Object),
            floor: expect.any(Object),
            guestHouse: expect.any(Object),
            guestFrom: expect.any(Object),
          }),
        );
      });
    });

    describe('getGuest', () => {
      it('should return NewGuest for default Guest initial value', () => {
        const formGroup = service.createGuestFormGroup(sampleWithNewData);

        const guest = service.getGuest(formGroup) as any;

        expect(guest).toMatchObject(sampleWithNewData);
      });

      it('should return NewGuest for empty Guest initial value', () => {
        const formGroup = service.createGuestFormGroup();

        const guest = service.getGuest(formGroup) as any;

        expect(guest).toMatchObject({});
      });

      it('should return IGuest', () => {
        const formGroup = service.createGuestFormGroup(sampleWithRequiredData);

        const guest = service.getGuest(formGroup) as any;

        expect(guest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGuest should not enable id FormControl', () => {
        const formGroup = service.createGuestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGuest should disable id FormControl', () => {
        const formGroup = service.createGuestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
