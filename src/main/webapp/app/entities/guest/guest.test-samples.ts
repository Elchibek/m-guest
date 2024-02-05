import dayjs from 'dayjs/esm';

import { IGuest, NewGuest } from './model/guest.model';

export const sampleWithRequiredData: IGuest = {
  id: 'f1d6ac46-bb3e-43ae-a41d-b6623962bc98',
  name: 'beggar redial',
  startDate: dayjs('2023-12-05T04:01'),
  endDate: dayjs('2023-12-04T08:28'),
  countPerson: 29419,
  price: 21415,
};

export const sampleWithPartialData: IGuest = {
  id: '12e4fa3d-12b5-4d75-af3e-89784dbe1e5a',
  name: 'properly',
  guestInstitution: 'duh tulip egg',
  isArchive: true,
  startDate: dayjs('2023-12-05T04:21'),
  endDate: dayjs('2023-12-04T22:58'),
  countDidntPay: 15155,
  countPerson: 4215,
  explanation: 'yippee daintily',
  price: 11206,
};

export const sampleWithFullData: IGuest = {
  id: 'a2528d53-9453-4db8-b642-846814929de7',
  name: 'yuck yuck',
  guestInstitution: 'definitive quiet',
  responsible: 'somber officially',
  isArchive: false,
  startDate: dayjs('2023-12-04T17:52'),
  endDate: dayjs('2023-12-05T01:38'),
  countDidntPay: 7795,
  paid: false,
  countPerson: 31166,
  explanation: 'unwind concrete',
  price: 3480,
  totalPrice: 13334,
};

export const sampleWithNewData: NewGuest = {
  name: 'honestly rash aha',
  startDate: dayjs('2023-12-04T19:10'),
  endDate: dayjs('2023-12-04T14:13'),
  countPerson: 20290,
  price: 1575,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
