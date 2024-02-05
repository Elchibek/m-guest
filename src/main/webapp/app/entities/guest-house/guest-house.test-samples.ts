import { IGuestHouse, NewGuestHouse } from './guest-house.model';

export const sampleWithRequiredData: IGuestHouse = {
  id: 'c570c6e4-94fb-46fe-b1d7-0b30f4836f16',
  name: 'inwardly',
};

export const sampleWithPartialData: IGuestHouse = {
  id: 'd2ca6d0d-81e8-4107-b096-5c29a2b73802',
  name: 'innocent regarding',
  countPerson: 11917,
  backgroundColor: 'even limb emerge',
};

export const sampleWithFullData: IGuestHouse = {
  id: '3ad4344f-f1e7-4b44-bc1a-a7a490a08414',
  name: 'adored times',
  isEmpty: false,
  countPerson: 17450,
  backgroundColor: 'apropos',
};

export const sampleWithNewData: NewGuestHouse = {
  name: 'resize',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
