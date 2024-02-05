import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGuestFrom, NewGuestFrom } from '../guest-from.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGuestFrom for edit and NewGuestFromFormGroupInput for create.
 */
type GuestFromFormGroupInput = IGuestFrom | PartialWithRequiredKeyOf<NewGuestFrom>;

type GuestFromFormDefaults = Pick<NewGuestFrom, 'id'>;

type GuestFromFormGroupContent = {
  id: FormControl<IGuestFrom['id'] | NewGuestFrom['id']>;
  name: FormControl<IGuestFrom['name']>;
};

export type GuestFromFormGroup = FormGroup<GuestFromFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestFromFormService {
  createGuestFromFormGroup(guestFrom: GuestFromFormGroupInput = { id: null }): GuestFromFormGroup {
    const guestFromRawValue = {
      ...this.getFormDefaults(),
      ...guestFrom,
    };
    return new FormGroup<GuestFromFormGroupContent>({
      id: new FormControl(
        { value: guestFromRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(guestFromRawValue.name, {
        validators: [Validators.required],
      }),
    });
  }

  getGuestFrom(form: GuestFromFormGroup): IGuestFrom | NewGuestFrom {
    return form.getRawValue() as IGuestFrom | NewGuestFrom;
  }

  resetForm(form: GuestFromFormGroup, guestFrom: GuestFromFormGroupInput): void {
    const guestFromRawValue = { ...this.getFormDefaults(), ...guestFrom };
    form.reset(
      {
        ...guestFromRawValue,
        id: { value: guestFromRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GuestFromFormDefaults {
    return {
      id: null,
    };
  }
}
