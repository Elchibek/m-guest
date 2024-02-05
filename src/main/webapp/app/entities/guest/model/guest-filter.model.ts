export interface IGuestFilter {
  userId?: string | null;
  // ----------------------------
  searchText?: string | null;
  searchType?: string | null;
  // ----------------------------
  guestFromId?: string | null;
  // ----------------------------
  guestBlockId?: string | null;
  entranceId?: string | null;
  floorId?: string | null;
  houseId?: string | null;
  // ----------------------------
  typeBetween?: string | null;
  from?: number | null;
  before?: number | null;
  // ----------------------------
  typeDate?: string | null;
  startDate?: string | null;
  startTime?: string | null;
  endDate?: string | null;
  endTime?: string | null;
  // ----------------------------
  isPaid?: boolean | null;
  // ----------------------------
  isArchive?: boolean | null;
  // ----------------------------
  isUpdate?: boolean | null;
  isDeleted?: boolean | null;
  isChange?: boolean | null;
  parentId?: string | null;
}
