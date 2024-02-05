import { IGuestFilter } from './guest-filter.model';
import { ITabkey } from './tab-key.model';

export interface IGuestDoc {
  docType?: string | null;
  tabKeys?: ITabkey[] | null;
  guestFilter?: IGuestFilter | null;
}
