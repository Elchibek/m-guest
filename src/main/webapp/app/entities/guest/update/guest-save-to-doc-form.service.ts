import { Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { IGuestDoc } from '../model/guest-doc.model';

type FormValueOf<T extends IGuestDoc> = Omit<T, 'startDate' | 'endDate'>;

type GuestSaveToDocFormDefaults = Pick<IGuestDoc, 'docType'>;
type GuestSaveToDocFormRawValue = FormValueOf<IGuestDoc>;

type GuestSaveToDocFormGroupContent = {
  docType: FormControl<IGuestDoc['docType']>;
  tabKeys: FormControl<IGuestDoc['tabKeys']>;
  guestFilter: FormControl<IGuestDoc['guestFilter']>;
};

export type GuestSaveToDocFormGroup = FormGroup<GuestSaveToDocFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestSaveToDocFormService {
  createGuestSaveToDocFormGroup(guestFilter: IGuestDoc = {}): GuestSaveToDocFormGroup {
    const guestFilterRawValue = {
      ...this.getFormDefaults(),
      ...guestFilter,
    };
    return new FormGroup<GuestSaveToDocFormGroupContent>({
      docType: new FormControl(guestFilterRawValue.docType),
      tabKeys: new FormControl(guestFilterRawValue.tabKeys),
      guestFilter: new FormControl(guestFilterRawValue.guestFilter),
    });
  }

  getGuestDoc(form: GuestSaveToDocFormGroup): IGuestDoc {
    return this.convertGuestDocRawValueToGuestDoc(form.getRawValue() as GuestSaveToDocFormRawValue);
  }

  private getFormDefaults(): GuestSaveToDocFormDefaults {
    return {
      docType: 'docx',
    };
  }

  private convertGuestDocRawValueToGuestDoc(rawGuest: GuestSaveToDocFormRawValue): IGuestDoc {
    return { ...rawGuest };
  }
}
