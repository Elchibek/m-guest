import { IGuestFrom, NewGuestFrom } from './guest-from.model';

export const sampleWithRequiredData: IGuestFrom = {
  id: '844ca6f4-c877-4b9f-b536-98a8529e0f28',
  name: 'rot',
};

export const sampleWithPartialData: IGuestFrom = {
  id: 'a653ef1b-6036-44cc-bfab-825522e574b6',
  name: 'trepan',
};

export const sampleWithFullData: IGuestFrom = {
  id: '2a416271-f209-4b96-994e-b9b28c6ce461',
  name: 'mysteriously hmph gee',
};

export const sampleWithNewData: NewGuestFrom = {
  name: 'aw electric wherever',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
