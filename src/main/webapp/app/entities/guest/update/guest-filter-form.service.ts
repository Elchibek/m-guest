import { Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { IGuestFilter } from '../model/guest-filter.model';

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGuestFilter> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type GuestFilterFormRawValue = FormValueOf<IGuestFilter>;

type GuestFilterFormDefaults = Pick<IGuestFilter, 'isArchive' | 'isDeleted' | 'isChange'>;

type GuestFilterFormGroupContent = {
  userId: FormControl<IGuestFilter['userId']>;
  searchText: FormControl<IGuestFilter['searchText']>;
  searchType: FormControl<IGuestFilter['searchType']>;
  guestFromId: FormControl<IGuestFilter['guestFromId']>;
  guestBlockId: FormControl<IGuestFilter['guestBlockId']>;
  entranceId: FormControl<IGuestFilter['entranceId']>;
  floorId: FormControl<IGuestFilter['floorId']>;
  houseId: FormControl<IGuestFilter['houseId']>;
  typeBetween: FormControl<IGuestFilter['typeBetween']>;
  from: FormControl<IGuestFilter['from']>;
  before: FormControl<IGuestFilter['before']>;
  startDate: FormControl<IGuestFilter['startDate']>;
  endDate: FormControl<IGuestFilter['endDate']>;
  typeDate: FormControl<IGuestFilter['typeDate']>;
  isPaid: FormControl<IGuestFilter['isPaid']>;
  isUpdate: FormControl<IGuestFilter['isUpdate']>;
  isDeleted: FormControl<IGuestFilter['isDeleted']>;
  isChange: FormControl<IGuestFilter['isChange']>;
  isArchive: FormControl<IGuestFilter['isArchive']>;
  parentId: FormControl<IGuestFilter['parentId']>;
};

export type GuestFilterFormGroup = FormGroup<GuestFilterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GuestFilterFormService {
  createGuestFilterFormGroup(guestFilter: IGuestFilter = {}): GuestFilterFormGroup {
    const guestFilterRawValue = {
      ...this.getFormDefaults(),
      ...guestFilter,
    };
    return new FormGroup<GuestFilterFormGroupContent>({
      userId: new FormControl(guestFilterRawValue.userId),
      searchText: new FormControl(guestFilterRawValue.searchText),
      searchType: new FormControl(guestFilterRawValue.searchType),
      guestFromId: new FormControl(guestFilterRawValue.guestFromId),
      guestBlockId: new FormControl(guestFilterRawValue.guestBlockId),
      entranceId: new FormControl(guestFilterRawValue.entranceId),
      floorId: new FormControl(guestFilterRawValue.floorId),
      houseId: new FormControl(guestFilterRawValue.houseId),
      typeBetween: new FormControl(guestFilterRawValue.typeBetween),
      from: new FormControl(guestFilterRawValue.from),
      before: new FormControl(guestFilterRawValue.before),
      startDate: new FormControl(guestFilterRawValue.startDate),
      endDate: new FormControl(guestFilterRawValue.endDate),
      typeDate: new FormControl(guestFilterRawValue.typeDate),
      isPaid: new FormControl(guestFilterRawValue.isPaid),
      isUpdate: new FormControl(guestFilterRawValue.isUpdate),
      isDeleted: new FormControl(guestFilterRawValue.isDeleted),
      isChange: new FormControl(guestFilterRawValue.isChange),
      isArchive: new FormControl(guestFilterRawValue.isArchive),
      parentId: new FormControl(guestFilterRawValue.parentId),
    });
  }

  getGuest(form: GuestFilterFormGroup): IGuestFilter {
    return this.convertGuestRawValueToGuest(form.getRawValue() as GuestFilterFormRawValue);
  }

  private getFormDefaults(): GuestFilterFormDefaults {
    return {
      isArchive: false,
      isChange: false,
      isDeleted: false,
    };
  }

  private convertGuestRawValueToGuest(rawGuest: GuestFilterFormRawValue): IGuestFilter {
    var startDate = null;
    var endDate = null;
    if (rawGuest.startDate != null) {
      startDate = rawGuest.startDate?.concat('T' + '00:00');
    }
    if (rawGuest.endDate != null) endDate = rawGuest.endDate?.concat('T' + '00:00');

    return {
      ...rawGuest,
      startDate,
      endDate,
    };
  }
}
