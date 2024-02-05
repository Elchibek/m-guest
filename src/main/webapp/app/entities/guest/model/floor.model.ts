import { IEntrance } from 'app/entities/guest/model/entrance.model';
import { IGuestHouse } from '../../guest-house/guest-house.model';

export interface IFloor {
  id: string;
  name?: string | null;
  numFloor?: number | null;
  backgroundColor?: string | null;
  gDtos?: IGuestHouse[] | null;
  entrance?: Pick<IEntrance, 'id'> | null;
}

export type NewFloor = Omit<IFloor, 'id'> & { id: null };
