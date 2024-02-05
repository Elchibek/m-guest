import dayjs from 'dayjs/esm';

export interface IArrivalDepartureStatic {
  id: string;
  isArrival?: boolean | null;
  date?: dayjs.Dayjs | null;
  countPerson?: number | null;
}

export type NewArrivalDepartureStatic = Omit<IArrivalDepartureStatic, 'id'> & { id: null };
