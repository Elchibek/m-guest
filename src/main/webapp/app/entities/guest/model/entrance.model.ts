import { IGuestBlock } from 'app/entities/guest-block/guest-block.model';
import { IFloor } from './floor.model';

export interface IEntrance {
  id: string;
  name?: string | null;
  numEntrance?: number | null;
  fDtos?: IFloor[] | null;
  guestBlock?: Pick<IGuestBlock, 'id' | 'name'> | null;
}

export type NewEntrance = Omit<IEntrance, 'id'> & { id: null };
