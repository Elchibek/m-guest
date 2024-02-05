import { IFloor } from 'app/entities/guest/model/floor.model';

export interface IGuestHouse {
  id: string;
  name?: string | null;
  numHouse?: string | null;
  isEmpty?: boolean | null;
  countPerson?: number | null;
  backgroundColor?: string | null;
  floor?: Pick<IFloor, 'id'> | null;
}

export type NewGuestHouse = Omit<IGuestHouse, 'id'> & { id: null };
