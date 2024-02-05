import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../guest-from.test-samples';

import { GuestFromFormService } from './guest-from-form.service';

describe('GuestFrom Form Service', () => {
  let service: GuestFromFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuestFromFormService);
  });

  describe('Service methods', () => {
    describe('createGuestFromFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGuestFromFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IGuestFrom should create a new form with FormGroup', () => {
        const formGroup = service.createGuestFromFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getGuestFrom', () => {
      it('should return NewGuestFrom for default GuestFrom initial value', () => {
        const formGroup = service.createGuestFromFormGroup(sampleWithNewData);

        const guestFrom = service.getGuestFrom(formGroup) as any;

        expect(guestFrom).toMatchObject(sampleWithNewData);
      });

      it('should return NewGuestFrom for empty GuestFrom initial value', () => {
        const formGroup = service.createGuestFromFormGroup();

        const guestFrom = service.getGuestFrom(formGroup) as any;

        expect(guestFrom).toMatchObject({});
      });

      it('should return IGuestFrom', () => {
        const formGroup = service.createGuestFromFormGroup(sampleWithRequiredData);

        const guestFrom = service.getGuestFrom(formGroup) as any;

        expect(guestFrom).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGuestFrom should not enable id FormControl', () => {
        const formGroup = service.createGuestFromFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGuestFrom should disable id FormControl', () => {
        const formGroup = service.createGuestFromFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
