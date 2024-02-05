import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, EMPTY, filter, finalize, map, mergeMap, Observable, of, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IGuest } from '../model/guest.model';
import { EntityArrayResponseType, GuestService } from '../service/guest.service';
import { GuestDeleteDialogComponent } from '../delete/guest-delete-dialog.component';
import { IGuestBlock } from 'app/entities/guest-block/guest-block.model';
import { IEntrance } from 'app/entities/guest/model/entrance.model';
import { IFloor } from 'app/entities/guest/model/floor.model';
import { IGuestHouse } from 'app/entities/guest-house/guest-house.model';
import { IGuestFrom } from 'app/entities/guest-from/guest-from.model';
import { GuestBlockService } from 'app/entities/guest-block/service/guest-block.service';
import { EntranceService } from 'app/entities/guest/service/entrance.service';
import { FloorService } from 'app/entities/guest/service/floor.service';
import { GuestHouseService } from 'app/entities/guest-house/service/guest-house.service';
import { GuestFromService } from 'app/entities/guest-from/service/guest-from.service';
import { GuestFilterFormGroup, GuestFilterFormService } from '../update/guest-filter-form.service';
import { IGuestStatic } from '../model/guest-static.model';
import { GuestStaticService } from '../service/guest-static.service';
import { IGuestFilter } from '../model/guest-filter.model';
import { NgStyle } from '@angular/common';
import { User } from 'app/admin/user-management/user-management.model';
import { UserManagementService } from 'app/admin/user-management/service/user-management.service';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import { GuestChartComponent } from 'app/entities/gues-chart/guest-chart.component';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/config/authority.constants';
import { ITabkey, tabKeys } from '../model/tab-key.model';
import { TranslateService } from '@ngx-translate/core';
import { GuestSaveToDocFormGroup, GuestSaveToDocFormService } from '../update/guest-save-to-doc-form.service';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  standalone: true,
  selector: 'jhi-guest',
  templateUrl: './guest.component.html',
  styleUrls: ['./guest.component.scss'],
  imports: [
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ItemCountComponent,
    GuestChartComponent,
    NgStyle,
  ],
})
export class GuestComponent implements OnInit {
  private static readonly NOT_SORTABLE_FIELDS_AFTER_SEARCH = ['id'];

  users: User[] | null = null;
  guests?: IGuest[];
  guestBlocksSharedCollection: IGuestBlock[] = [];
  entrancesSharedCollection: IEntrance[] = [];
  floorsSharedCollection: IFloor[] = [];
  guestHousesSharedCollection: IGuestHouse[] = [];
  guestFromsSharedCollection: IGuestFrom[] = [];
  gStatic: IGuestStatic | null = null;

  fileterdTabKeys: ITabkey[] = tabKeys;
  tabKeys: ITabkey[] = tabKeys;
  selectedTabKey: any;
  currentLang = 'en';
  isLoading = false;
  isFilter = false;
  isArchive = false;
  isSavingDoc = false;
  currentSearch = '';

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  // -------------------------------

  filterForm: GuestFilterFormGroup = this.guestFilterFormService.createGuestFilterFormGroup();
  saveToDocForm: GuestSaveToDocFormGroup = this.guestSaveToDocFormService.createGuestSaveToDocFormGroup();

  constructor(
    private userService: UserManagementService,
    private accountService: AccountService,
    protected guestService: GuestService,
    protected guestBlockService: GuestBlockService,
    protected entranceService: EntranceService,
    protected floorService: FloorService,
    protected guestHouseService: GuestHouseService,
    protected guestFromService: GuestFromService,
    protected guestStaticService: GuestStaticService,
    protected activatedRoute: ActivatedRoute,
    protected guestFilterFormService: GuestFilterFormService,
    protected guestSaveToDocFormService: GuestSaveToDocFormService,
    private translateService: TranslateService,
    public router: Router,
    protected modalService: NgbModal,
  ) {}

  trackId = (_index: number, item: IGuest): string => this.guestService.getGuestIdentifier(item);

  ngOnInit(): void {
    this.resetForm(['entranceId', 'floorId', 'houseId'], true);
    if (!this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      this.filterForm.get('userId')?.disable();
    }
    this.load();
    this.loadRelationshipsOptions();
  }

  search(query: string): void {
    if (query && GuestComponent.NOT_SORTABLE_FIELDS_AFTER_SEARCH.includes(this.predicate)) {
      this.predicate = '';
    }
    this.page = 1;
    this.currentSearch = query;
    this.navigateToWithComponentValues();
  }

  getTableTitle() {
    if (this.isArchive) {
      return 'Архивдер';
    }
    return 'Коноктор';
  }

  getArchive() {
    this.filterForm.patchValue({ isArchive: true });
    this.searchFilter();
  }

  onchange(e: any, index: number, isSelectAll: boolean) {
    /*     const currentLang = this.translateService.currentLang;
        this.tabKeys.forEach(v => {
          const translatedMessage = this.translateService.instant(v.translate!);
          console.log(translatedMessage)
        })
     */
    const checked = e.target.checked;
    const classNames = isSelectAll ? 'cl' : 'cl' + index;
    const cl = document.getElementsByClassName(classNames);
    if (checked) {
      for (let i = 0; i < cl.length; i++) cl[i].classList.remove('tabCol');
    } else {
      for (let i = 0; i < cl.length; i++) cl[i].classList.add('tabCol');
    }

    if (isSelectAll) {
      this.tabKeys.forEach(item => (item.selected = checked));
    } else {
      checked ? (this.tabKeys[index].selected = true) : (this.tabKeys[index].selected = false);
    }
  }

  saveToDoc(type: string) {
    this.isSavingDoc = true;
    for (let i = 0; i < this.tabKeys.length; i++) {
      this.tabKeys[i].name = this.translateService.instant(this.tabKeys[i].translate!);
    }
    const guestFilter = this.guestFilterFormService.getGuest(this.filterForm);
    this.saveToDocForm.patchValue({
      docType: type,
      tabKeys: this.tabKeys,
      guestFilter: guestFilter,
    });
    const guestDoc = this.guestSaveToDocFormService.getGuestDoc(this.saveToDocForm);
    this.subscribeToSaveResponse(this.guestService.saveToDoc(guestDoc));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuest>>): void {
    result.pipe(finalize(() => (this.isSavingDoc = false))).subscribe();
  }

  filterTabKeys() {
    if (this.currentLang != this.translateService.currentLang) {
      this.currentLang = this.translateService.currentLang;
      for (let i = 0; i < this.tabKeys.length; i++) {
        this.tabKeys[i].name = this.translateService.instant(this.tabKeys[i].translate!);
      }
    }

    this.fileterdTabKeys = this.tabKeys.filter(item => item.name?.toLowerCase().includes(this.selectedTabKey.toLowerCase()));
  }

  searchFilter(parentId?: string, isDeleted?: boolean): void {
    var isChange = false;
    if (parentId != null) {
      isChange = true;
      this.clearFilter(false);
      this.filterForm.patchValue({ isDeleted, isChange: true, parentId });
    }
    var guest = this.guestFilterFormService.getGuest(this.filterForm);
    if (isChange) {
      ['parentId', 'isChange', 'isDeleted'].forEach(v => {
        this.filterForm.get(v)?.reset();
      });
    }
    this.loadFromBackendWithRouteInformations(guest).subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  clearFilter(isLoad: boolean) {
    this.resetForm(
      ['userId', 'guestFromId', 'guestBlockId', 'entranceId', 'startDate', 'endDate', 'isPaid', 'from', 'before', 'isDeleted', 'isChange'],
      false,
    );
    if (isLoad) {
      this.load();
    }
  }

  formatInput(input: HTMLInputElement) {
    input.value = input.value.replace(FILTER_PAG_REGEX, '');
  }

  getGuestBlockId(guestBlockId: string) {
    this.entrancesSharedCollection = [];
    this.floorsSharedCollection = [];
    this.guestHousesSharedCollection = [];
    this.resetForm(['entranceId', 'floorId', 'houseId'], true);
    if (guestBlockId != null) {
      this.entranceService
        .query({ guestBlockId })
        .pipe(map((res: HttpResponse<IEntrance[]>) => res.body ?? []))
        .subscribe((entrances: IEntrance[]) => {
          this.filterForm.get('entranceId')?.enable();
          this.entrancesSharedCollection = entrances;
        });
    }
  }

  getEntranceId(entranceId: string) {
    this.floorsSharedCollection = [];
    this.guestHousesSharedCollection = [];
    this.resetForm(['floorId', 'houseId'], true);
    if (entranceId != null) {
      this.floorService
        .query({ entranceId })
        .pipe(map((res: HttpResponse<IFloor[]>) => res.body ?? []))
        .subscribe((floors: IFloor[]) => {
          this.filterForm.get('floorId')?.enable();
          this.floorsSharedCollection = floors;
        });
    }
  }

  getFloorId(floorId: string) {
    this.guestHousesSharedCollection = [];
    this.resetForm(['houseId'], true);
    if (floorId != null) {
      this.guestHouseService
        .query({ floorId })
        .pipe(map((res: HttpResponse<IGuestHouse[]>) => res.body ?? []))
        .subscribe((guestHouses: IGuestHouse[]) => {
          this.filterForm.get('houseId')?.enable();
          this.guestHousesSharedCollection = guestHouses;
        });
    }
  }

  delete(guest: IGuest, isArchive: boolean, isRestore: boolean): void {
    const modalRef = this.modalService.open(GuestDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    guest.isArchive = isArchive;
    guest.isRestore = isRestore;
    modalRef.componentInstance.guest = guest;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations()),
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  showFilter() {
    this.isFilter = !this.isFilter;
  }

  load(): void {
    this.loadFromBackendWithRouteInformations(null).subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });

    this.loadGuestStatic();
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.currentSearch);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(fil?: IGuestFilter | null): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(fil, this.page, this.predicate, this.ascending, this.currentSearch)),
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    if (params.has('search') && params.get('search') !== '') {
      this.currentSearch = params.get('search') as string;
      if (GuestComponent.NOT_SORTABLE_FIELDS_AFTER_SEARCH.includes(this.predicate)) {
        this.predicate = '';
      }
    }
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.guests = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IGuest[] | null): IGuest[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(
    fil?: IGuestFilter | null,
    page?: number,
    predicate?: string,
    ascending?: boolean,
    currentSearch?: string,
  ): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      query: currentSearch,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    if (fil?.isArchive != null) {
      queryObject.isArchive = fil.isArchive;
    }
    if (fil?.userId != null) {
      queryObject.userId = fil.userId;
    }
    if (fil?.guestFromId != null) {
      queryObject.guestFromId = fil.guestFromId;
    }
    if (fil?.guestBlockId != null) {
      queryObject.guestBlockId = fil.guestBlockId;
    }
    if (fil?.entranceId != null) {
      queryObject.entranceId = fil.entranceId;
    }
    if (fil?.floorId != null) {
      queryObject.floorId = fil.floorId;
    }
    if (fil?.houseId != null) {
      queryObject.houseId = fil.houseId;
    }
    if (fil?.typeDate != null) {
      queryObject.typeDate = fil.typeDate;
    }
    if (fil?.startDate != null) {
      if (fil?.typeDate == null) queryObject.typeDate = 'between';
      queryObject.startDate = fil.startDate;
    }
    if (fil?.endDate != null) {
      if (fil?.typeDate == null) queryObject.typeDate = 'between';
      queryObject.endDate = fil.endDate;
    }
    if (fil?.typeBetween != null) {
      queryObject.typeBetween = fil.typeBetween;
    }
    if (fil?.from != null) {
      if (fil?.typeBetween == null) queryObject.typeBetween = 'betweenPesrson';
      queryObject.from = fil.from;
    }
    if (fil?.before != null) {
      if (fil?.typeBetween == null) queryObject.typeBetween = 'betweenPesrson';
      queryObject.before = fil.before;
    }
    if (fil?.isPaid != null) {
      queryObject.isPaid = fil.isPaid;
    }
    if (fil?.isUpdate != null) {
      queryObject.isUpdate = fil.isUpdate;
    }
    if (fil?.isDeleted != null) {
      queryObject.isDeleted = fil.isDeleted;
    }
    if (fil?.isChange != null) {
      queryObject.isChange = fil.isChange;
    }
    if (fil?.parentId != null) {
      queryObject.parentId = fil.parentId;
    }
    if (this.currentSearch && this.currentSearch !== '') {
      return this.guestService.search(queryObject).pipe(tap(() => (this.isLoading = false)));
    } else {
      return this.guestService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
    }
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean, currentSearch?: string): void {
    const queryParamsObj = {
      page,
      search: currentSearch,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  protected loadGuestStatic(): void {
    this.guestStaticService
      .find({ isArhive: false })
      .pipe(
        mergeMap((res: HttpResponse<IGuestStatic>) => {
          return res.body ? of(res.body) : EMPTY;
        }),
      )
      .subscribe((guestStatic: IGuestStatic) => (this.gStatic = guestStatic));
  }

  protected loadRelationshipsOptions(): void {
    if (this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      this.userService.query().subscribe({
        next: (res: HttpResponse<User[]>) => (this.users = res.body),
      });
    }

    this.guestFromService
      .query()
      .pipe(map((res: HttpResponse<IGuestFrom[]>) => res.body ?? []))
      .subscribe((guestFroms: IGuestFrom[]) => (this.guestFromsSharedCollection = guestFroms));

    this.guestBlockService
      .query()
      .pipe(map((res: HttpResponse<IGuestBlock[]>) => res.body ?? []))
      .subscribe((guestBlocks: IGuestBlock[]) => (this.guestBlocksSharedCollection = guestBlocks));
  }

  protected resetForm(values: string[], disable: boolean) {
    values.forEach(v => {
      this.filterForm.get(v)?.reset();
      if (disable) this.filterForm.get(v)?.disable();
    });
  }
}
