export interface IRestOfTheDay {
  id: string;
  willStay?: number | null;
  num?: number | null;
  isDeparture?: boolean | null;
}

export type NewRestOfTheDay = Omit<IRestOfTheDay, 'id'> & { id: null };
