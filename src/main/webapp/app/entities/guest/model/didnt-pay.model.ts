export interface IDidntPay {
  id: string;
  countPerson?: number | null;
  paid?: number | null;
  explanation?: string | null;
  isDelete?: boolean | null;
}

export type NewDidntPay = Omit<IDidntPay, 'id'> & { id: null };
