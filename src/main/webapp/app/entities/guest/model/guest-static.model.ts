export interface IGuestStatic {
  id: string;
  isArchive?: boolean | null;
  countPerson?: number | null;
  toDayDeparture?: number | null;
  toMorrowDeparture?: number | null;
  totalPrice?: number | null;
  totalDidntPay?: number | null;
  numOfApartments?: number | null;
  affordableApartments?: number | null;
}

export type NewGuestStatic = Omit<IGuestStatic, 'id'> & { id: null };
