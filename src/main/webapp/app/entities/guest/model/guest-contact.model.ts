export interface IGuestContact {
  id: string;
  name?: string | null;
  phone?: string | null;
  isDelete?: string | null;
}

export type NewGuestContact = Omit<IGuestContact, 'id'> & { id: null };
