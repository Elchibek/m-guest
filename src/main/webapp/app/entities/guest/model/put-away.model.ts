import dayjs from 'dayjs/esm';

export interface IPutAway {
  id: string;
  countPerson?: number | null;
  whenLeft?: dayjs.Dayjs | null;
  explanation?: string | null;
  isDelete?: string | null;
}

export type NewPutAway = Omit<IPutAway, 'id'> & { id: null };
