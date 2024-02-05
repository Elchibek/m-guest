import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGuestHouse, NewGuestHouse } from '../guest-house.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGuestHouse for edit and NewGuestHouseFormGroupInput for create.
 */
type GuestHouseFormGroupInput = IGuestHouse | PartialWithRequiredKeyOf<NewGuestHouse>;

type GuestHouseFormDefaults = Pick<NewGuestHouse, 'id' | 'isEmpty'>;

type GuestHouseFormGroupContent = {
  id: FormControl<IGuestHouse['id'] | NewGuestHouse['id']>;
  name: FormControl<IGuestHouse['name']>;
  numHouse: FormControl<IGuestHouse['numHouse']>;
  isEmpty: FormControl<IGuestHouse['isEmpty']>;
  countPerson: FormControl<IGuestHouse['countPerson']>;
  backgroundColor: FormControl<IGuestHouse['backgroundColor']>;
  floor: FormControl<IGuestHouse['floor']>;
};

export type GuestHouseFormGroup = FormGroup<GuestHouseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestHouseFormService {
  createGuestHouseFormGroup(guestHouse: GuestHouseFormGroupInput = { id: null }): GuestHouseFormGroup {
    const guestHouseRawValue = {
      ...this.getFormDefaults(),
      ...guestHouse,
    };
    return new FormGroup<GuestHouseFormGroupContent>({
      id: new FormControl(
        { value: guestHouseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(guestHouseRawValue.name, {
        validators: [Validators.required],
      }),
      numHouse: new FormControl(guestHouseRawValue.numHouse, {
        validators: [Validators.required],
      }),
      isEmpty: new FormControl(guestHouseRawValue.isEmpty),
      countPerson: new FormControl(guestHouseRawValue.countPerson),
      backgroundColor: new FormControl(guestHouseRawValue.backgroundColor),
      floor: new FormControl(guestHouseRawValue.floor, {
        validators: [Validators.required],
      }),
    });
  }

  getGuestHouse(form: GuestHouseFormGroup): IGuestHouse | NewGuestHouse {
    return form.getRawValue() as IGuestHouse | NewGuestHouse;
  }

  resetForm(form: GuestHouseFormGroup, guestHouse: GuestHouseFormGroupInput): void {
    const guestHouseRawValue = { ...this.getFormDefaults(), ...guestHouse };
    form.reset(
      {
        ...guestHouseRawValue,
        id: { value: guestHouseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GuestHouseFormDefaults {
    return {
      id: null,
      isEmpty: false,
    };
  }
}
