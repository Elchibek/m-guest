export interface IGuestBlock {
  id: string;
  name?: string | null;
  nameEntrance?: string | null;
  numEntrance?: number | null;
  nameFloor?: string | null;
  numFloor?: number | null;
  nameHouse?: string | null;
  numHouse?: number | null;
}

export type NewGuestBlock = Omit<IGuestBlock, 'id'> & { id: null };
