import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGuest, NewGuest } from '../model/guest.model';
import { IGuestContact } from 'app/entities/guest/model/guest-contact.model';
import { IDidntPay } from '../model/didnt-pay.model';
import { IPutAway } from '../model/put-away.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGuest for edit and NewGuestFormGroupInput for create.
 */
type GuestFormGroupInput = IGuest | PartialWithRequiredKeyOf<NewGuest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGuest | NewGuest> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type PutAwayFormValueOf<T extends IPutAway> = Omit<T, 'whenLeft'> & {
  whenLeft?: string | null;
};

type GuestFormRawValue = FormValueOf<IGuest>;
type PutAwayFormRawValue = PutAwayFormValueOf<IPutAway>;

type NewGuestFormRawValue = FormValueOf<NewGuest>;

type GuestFormDefaults = Pick<NewGuest, 'id' | 'isArchive' | 'startDate' | 'endDate' | 'isPaid' | 'countPerson'>;

type GuestFormGroupContent = {
  id: FormControl<GuestFormRawValue['id'] | NewGuest['id']>;
  name: FormControl<GuestFormRawValue['name']>;
  guestInstitution: FormControl<GuestFormRawValue['guestInstitution']>;
  responsible: FormControl<GuestFormRawValue['responsible']>;
  isArchive: FormControl<GuestFormRawValue['isArchive']>;
  isDeleted: FormControl<GuestFormRawValue['isDeleted']>;
  isUpdate: FormControl<GuestFormRawValue['isUpdate']>;
  isChange: FormControl<GuestFormRawValue['isChange']>;
  startDate: FormControl<GuestFormRawValue['startDate']>;
  endDate: FormControl<GuestFormRawValue['endDate']>;
  lastModifiedDate: FormControl<GuestFormRawValue['lastModifiedDate']>;
  willStay: FormControl<GuestFormRawValue['willStay']>;
  num: FormControl<GuestFormRawValue['num']>;
  countDidntPay: FormControl<GuestFormRawValue['countDidntPay']>;
  didntPay: FormControl<GuestFormRawValue['didntPay']>;
  isPaid: FormControl<GuestFormRawValue['isPaid']>;
  countPerson: FormControl<GuestFormRawValue['countPerson']>;
  explanation: FormControl<GuestFormRawValue['explanation']>;
  price: FormControl<GuestFormRawValue['price']>;
  totalPrice: FormControl<GuestFormRawValue['totalPrice']>;
  restOfTheDay: FormControl<GuestFormRawValue['restOfTheDay']>;
  user: FormControl<GuestFormRawValue['user']>;
  guestBlock: FormControl<GuestFormRawValue['guestBlock']>;
  entrance: FormControl<GuestFormRawValue['entrance']>;
  floor: FormControl<GuestFormRawValue['floor']>;
  guestHouse: FormControl<GuestFormRawValue['guestHouse']>;
  guestFrom: FormControl<GuestFormRawValue['guestFrom']>;
  contacts: FormArray<FormGroup<GuestContactFormGroupContent>>;
  // putAways: FormArray<FormGroup<PutAwayFormGroupContent>>;
  // contacts: FormArray<any>;
  // pays: FormArray<any>;
};

type GuestContactFormGroupContent = {
  id: FormControl<IGuestContact['id']>;
  name: FormControl<IGuestContact['name']>;
  phone: FormControl<IGuestContact['phone']>;
  isDelete: FormControl<IGuestContact['isDelete']>;
};

type PutAwayFormGroupContent = {
  id: FormControl<IPutAway['id']>;
  countPerson: FormControl<IPutAway['countPerson']>;
  whenLeft: FormControl<IPutAway['whenLeft']>;
  explanation: FormControl<IPutAway['explanation']>;
  isDelete: FormControl<IPutAway['isDelete']>;
};

export type GuestFormGroup = FormGroup<GuestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestFormService {
  createGuestFormGroup(guest: GuestFormGroupInput = { id: null }): GuestFormGroup {
    const guestRawValue = this.convertGuestToGuestRawValue({
      ...this.getFormDefaults(),
      ...guest,
    });
    return new FormGroup<GuestFormGroupContent>({
      id: new FormControl(
        { value: guestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(guestRawValue.name, {
        validators: [Validators.required],
      }),
      guestInstitution: new FormControl(guestRawValue.guestInstitution),
      responsible: new FormControl(guestRawValue.responsible),
      isArchive: new FormControl(guestRawValue.isArchive),
      isDeleted: new FormControl(guestRawValue.isDeleted),
      isUpdate: new FormControl(guestRawValue.isUpdate),
      isChange: new FormControl(guestRawValue.isChange),
      startDate: new FormControl(guestRawValue.startDate, {
        validators: [Validators.required],
      }),
      endDate: new FormControl(guestRawValue.endDate, {
        validators: [Validators.required],
      }),
      lastModifiedDate: new FormControl(guestRawValue.lastModifiedDate),
      willStay: new FormControl(guestRawValue.willStay),
      num: new FormControl(guestRawValue.num),
      countDidntPay: new FormControl(guestRawValue.countDidntPay),
      didntPay: new FormControl(guestRawValue.didntPay),
      isPaid: new FormControl(guestRawValue.isPaid),
      countPerson: new FormControl(guestRawValue.countPerson, {
        validators: [Validators.required],
      }),
      explanation: new FormControl(guestRawValue.explanation),
      price: new FormControl(guestRawValue.price, {
        validators: [Validators.required],
      }),
      totalPrice: new FormControl(guestRawValue.totalPrice),
      restOfTheDay: new FormControl(guestRawValue.restOfTheDay),
      user: new FormControl(guestRawValue.user),
      guestBlock: new FormControl(guestRawValue.guestBlock, {
        validators: [Validators.required],
      }),
      entrance: new FormControl(guestRawValue.entrance, {
        validators: [Validators.required],
      }),
      floor: new FormControl(guestRawValue.floor, {
        validators: [Validators.required],
      }),
      guestHouse: new FormControl(guestRawValue.guestHouse, {
        validators: [Validators.required],
      }),
      guestFrom: new FormControl(guestRawValue.guestFrom, {
        validators: [Validators.required],
      }),
      contacts: new FormArray<FormGroup<GuestContactFormGroupContent>>([]),
      // putAways: new FormArray<FormGroup<PutAwayFormGroupContent>>([]),
    });
  }

  public getContact() {
    return new FormGroup({
      id: new FormControl(null),
      name: new FormControl(null),
      phone: new FormControl(null),
      isDelete: new FormControl(false),
    });
  }

  public getPutAway() {
    return new FormGroup({
      id: new FormControl(null),
      countPerson: new FormControl(null),
      whenLeft: new FormControl(dayjs().format(DATE_TIME_FORMAT)),
      explanation: new FormControl(null),
      isDelete: new FormControl(false),
    });
  }

  getGuest(form: GuestFormGroup): IGuest | NewGuest {
    return this.convertGuestRawValueToGuest(form.getRawValue() as GuestFormRawValue | NewGuestFormRawValue);
  }

  resetForm(form: GuestFormGroup, guest: GuestFormGroupInput): void {
    const guestRawValue = this.convertGuestToGuestRawValue({ ...this.getFormDefaults(), ...guest });
    for (let i = 0; i < guestRawValue.contacts!.length; i++) {
      (form.get('contacts') as FormArray).push(this.getContact());
    }

    // for (let i = 0; i < guestRawValue.putAways!.length; i++) {
    //   (form.get('putAways') as FormArray).push(this.getPutAway());
    // }
    form.reset(
      {
        ...guestRawValue,
        id: { value: guestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GuestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isArchive: false,
      countPerson: 1,
      startDate: currentTime,
      endDate: currentTime,
      isPaid: true,
    };
  }

  private convertGuestRawValueToGuest(rawGuest: GuestFormRawValue | NewGuestFormRawValue): IGuest | NewGuest {
    rawGuest.putAways?.forEach(v => {
      v.whenLeft = dayjs(v.whenLeft, DATE_TIME_FORMAT);
    });
    return {
      ...rawGuest,
      startDate: dayjs(rawGuest.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawGuest.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertGuestToGuestRawValue(
    guest: IGuest | (Partial<NewGuest> & GuestFormDefaults),
  ): GuestFormRawValue | PartialWithRequiredKeyOf<NewGuestFormRawValue> {
    return {
      ...guest,
      startDate: guest.startDate ? guest.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: guest.endDate ? guest.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }

  // private convertPutAwayToPutAwayRawValue(putAway: IPutAway): PutAwayFormRawValue {
  //   return {
  //     ...putAway,
  //     whenLeft: putAway.whenLeft ? putAway.whenLeft.format(DATE_TIME_FORMAT) : undefined;
  //   }
  // }
}
