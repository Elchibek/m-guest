export interface IGuestFrom {
  id: string;
  name?: string | null;
}

export type NewGuestFrom = Omit<IGuestFrom, 'id'> & { id: null };
