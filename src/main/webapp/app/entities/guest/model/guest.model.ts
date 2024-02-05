import dayjs from 'dayjs/esm';
import { IRestOfTheDay } from './rest-of-the-day.model';
import { IGuestBlock } from 'app/entities/guest-block/guest-block.model';
import { IEntrance } from 'app/entities/guest/model/entrance.model';
import { IFloor } from 'app/entities/guest/model/floor.model';
import { IGuestHouse } from 'app/entities/guest-house/guest-house.model';
import { IGuestFrom } from 'app/entities/guest-from/guest-from.model';
import { IGuestContact } from './guest-contact.model';
import { IUser } from 'app/admin/user-management/user-management.model';
import { IPutAway } from './put-away.model';

export interface IGuest {
  id: string;
  name?: string | null;
  guestInstitution?: string | null;
  responsible?: string | null;
  isArchive?: boolean | null;
  isDeleted?: boolean | null;
  isUpdate?: boolean | null;
  isChange?: boolean | null;
  isRestore?: boolean | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  willStay?: number | null;
  num?: number | null;
  countDidntPay?: number | null;
  didntPay?: number | null;
  isPaid?: boolean | null;
  countPerson?: number | null;
  explanation?: string | null;
  price?: number | null;
  totalPrice?: number | null;
  contacts?: IGuestContact[] | null;
  putAways?: IPutAway[] | null;
  user?: Pick<IUser, 'id' | 'firstName' | 'lastName'> | null;
  restOfTheDay?: Pick<IRestOfTheDay, 'id' | 'willStay' | 'num'> | null;
  guestBlock?: Pick<IGuestBlock, 'id' | 'name'> | null;
  entrance?: Pick<IEntrance, 'id' | 'name' | 'numEntrance'> | null;
  floor?: Pick<IFloor, 'id' | 'numFloor'> | null;
  guestHouse?: Pick<IGuestHouse, 'id' | 'name'> | null;
  guestFrom?: Pick<IGuestFrom, 'id' | 'name'> | null;
}

export type NewGuest = Omit<IGuest, 'id'> & { id: null };
