import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../guest-block.test-samples';

import { GuestBlockFormService } from './guest-block-form.service';

describe('GuestBlock Form Service', () => {
  let service: GuestBlockFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuestBlockFormService);
  });

  describe('Service methods', () => {
    describe('createGuestBlockFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGuestBlockFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            numEntrance: expect.any(Object),
            numFloor: expect.any(Object),
            numHouse: expect.any(Object),
          }),
        );
      });

      it('passing IGuestBlock should create a new form with FormGroup', () => {
        const formGroup = service.createGuestBlockFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            numEntrance: expect.any(Object),
            numFloor: expect.any(Object),
            numHouse: expect.any(Object),
          }),
        );
      });
    });

    describe('getGuestBlock', () => {
      it('should return NewGuestBlock for default GuestBlock initial value', () => {
        const formGroup = service.createGuestBlockFormGroup(sampleWithNewData);

        const guestBlock = service.getGuestBlock(formGroup) as any;

        expect(guestBlock).toMatchObject(sampleWithNewData);
      });

      it('should return NewGuestBlock for empty GuestBlock initial value', () => {
        const formGroup = service.createGuestBlockFormGroup();

        const guestBlock = service.getGuestBlock(formGroup) as any;

        expect(guestBlock).toMatchObject({});
      });

      it('should return IGuestBlock', () => {
        const formGroup = service.createGuestBlockFormGroup(sampleWithRequiredData);

        const guestBlock = service.getGuestBlock(formGroup) as any;

        expect(guestBlock).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGuestBlock should not enable id FormControl', () => {
        const formGroup = service.createGuestBlockFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGuestBlock should disable id FormControl', () => {
        const formGroup = service.createGuestBlockFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
