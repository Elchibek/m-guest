import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGuestBlock, NewGuestBlock } from '../guest-block.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGuestBlock for edit and NewGuestBlockFormGroupInput for create.
 */
type GuestBlockFormGroupInput = IGuestBlock | PartialWithRequiredKeyOf<NewGuestBlock>;

type GuestBlockFormDefaults = Pick<NewGuestBlock, 'id'>;

type GuestBlockFormGroupContent = {
  id: FormControl<IGuestBlock['id'] | NewGuestBlock['id']>;
  name: FormControl<IGuestBlock['name']>;
  nameEntrance: FormControl<IGuestBlock['nameEntrance']>;
  numEntrance: FormControl<IGuestBlock['numEntrance']>;
  nameFloor: FormControl<IGuestBlock['nameFloor']>;
  numFloor: FormControl<IGuestBlock['numFloor']>;
  nameHouse: FormControl<IGuestBlock['nameHouse']>;
  numHouse: FormControl<IGuestBlock['numHouse']>;
};

export type GuestBlockFormGroup = FormGroup<GuestBlockFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestBlockFormService {
  createGuestBlockFormGroup(guestBlock: GuestBlockFormGroupInput = { id: null }): GuestBlockFormGroup {
    const guestBlockRawValue = {
      ...this.getFormDefaults(),
      ...guestBlock,
    };
    return new FormGroup<GuestBlockFormGroupContent>({
      id: new FormControl(
        { value: guestBlockRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(guestBlockRawValue.name, {
        validators: [Validators.required],
      }),
      nameEntrance: new FormControl(guestBlockRawValue.nameEntrance),
      numEntrance: new FormControl(guestBlockRawValue.numEntrance, {
        validators: [Validators.required],
      }),
      nameFloor: new FormControl(guestBlockRawValue.nameFloor),
      numFloor: new FormControl(guestBlockRawValue.numFloor, {
        validators: [Validators.required],
      }),
      nameHouse: new FormControl(guestBlockRawValue.nameHouse),
      numHouse: new FormControl(guestBlockRawValue.numHouse, {
        validators: [Validators.required],
      }),
    });
  }

  getGuestBlock(form: GuestBlockFormGroup): IGuestBlock | NewGuestBlock {
    return form.getRawValue() as IGuestBlock | NewGuestBlock;
  }

  resetForm(form: GuestBlockFormGroup, guestBlock: GuestBlockFormGroupInput): void {
    const guestBlockRawValue = { ...this.getFormDefaults(), ...guestBlock };
    form.reset(
      {
        ...guestBlockRawValue,
        id: { value: guestBlockRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GuestBlockFormDefaults {
    return {
      id: null,
    };
  }
}
