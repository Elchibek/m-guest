import { IGuestBlock, NewGuestBlock } from './guest-block.model';

export const sampleWithRequiredData: IGuestBlock = {
  id: '6e6369ff-53a9-48e1-9125-50626f922019',
  name: 'spear head calibrate',
  numEntrance: 23201,
  numFloor: 9494,
  numHouse: 897,
};

export const sampleWithPartialData: IGuestBlock = {
  id: 'a2ff7428-f87f-4ad6-a7e5-cc2a7d71c1dc',
  name: 'reckless crowd',
  numEntrance: 7378,
  numFloor: 9254,
  numHouse: 27993,
};

export const sampleWithFullData: IGuestBlock = {
  id: '2ee49fb6-1f8b-4ede-869c-db0cd4c50dba',
  name: 'since circa reef',
  numEntrance: 12730,
  numFloor: 29329,
  numHouse: 3730,
};

export const sampleWithNewData: NewGuestBlock = {
  name: 'hourly absent where',
  numEntrance: 24315,
  numFloor: 27826,
  numHouse: 5640,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
