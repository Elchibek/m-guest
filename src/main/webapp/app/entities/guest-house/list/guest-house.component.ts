import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, map, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortDirective, SortByDirective } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IGuestHouse } from '../guest-house.model';
import { EntityArrayResponseType, GuestHouseService } from '../service/guest-house.service';
import { GuestHouseDeleteDialogComponent } from '../delete/guest-house-delete-dialog.component';
import { EntranceService } from 'app/entities/guest/service/entrance.service';
import { IEntrance } from 'app/entities/guest/model/entrance.model';
import { GuestBlockService } from 'app/entities/guest-block/service/guest-block.service';
import { IGuestBlock } from 'app/entities/guest-block/guest-block.model';

@Component({
  standalone: true,
  selector: 'jhi-guest-house',
  templateUrl: './guest-house.component.html',
  styleUrls: ['./guest-house.component.scss'],
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ItemCountComponent,
  ],
})
export class GuestHouseComponent implements OnInit {
  guestHouses?: IGuestHouse[];
  entrancesSharedCollection: IEntrance[] = [];
  guestBlocksSharedCollection: IGuestBlock[] = [];
  guestBlock?: IGuestBlock;
  entrance?: IEntrance;
  numberHouses?: Array<number> | null;
  isLoading = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  entranceColspan = 0;

  constructor(
    protected guestHouseService: GuestHouseService,
    protected entranceService: EntranceService,
    protected guestBlockService: GuestBlockService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal,
  ) {}

  trackId = (_index: number, item: IGuestHouse): string => this.guestHouseService.getGuestHouseIdentifier(item);

  ngOnInit(): void {
    this.load();
    this.loadRelationshipsOptions();
  }

  getHouse(index: number) {
    var chunkSize = 12;
    index *= chunkSize;
    if (index > 0) chunkSize = chunkSize + index;
    return this.guestHouses!.slice(index, chunkSize);
  }

  getGuestBlockId() {
    if (this.guestBlock?.id != null) {
      this.getAllByEntranceGuestBlockId(this.guestBlock?.id);
      this.numberHouses = Array(this.guestBlock?.numFloor)
        .fill(0)
        .map((x, i) => i);
    }
  }

  delete(guestHouse: IGuestHouse): void {
    const modalRef = this.modalService.open(GuestHouseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.guestHouse = guestHouse;
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

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending)),
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.guestHouses = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IGuestHouse[] | null): IGuestHouse[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.guestHouseService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
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

  protected getAllByEntranceGuestBlockId(guestBlockId: string) {
    this.entranceService
      .query({ guestBlockId })
      .pipe(map((res: HttpResponse<IEntrance[]>) => res.body ?? []))
      .subscribe((entrances: IEntrance[]) => (this.entrancesSharedCollection = entrances));
  }

  protected loadRelationshipsOptions(): void {
    this.guestBlockService
      .query()
      .pipe(map((res: HttpResponse<IGuestBlock[]>) => res.body ?? []))
      .subscribe((guestBlocks: IGuestBlock[]) => {
        this.guestBlocksSharedCollection = guestBlocks;
        this.guestBlock = guestBlocks[0];
        this.getAllByEntranceGuestBlockId(guestBlocks[0].id);
        this.numberHouses = Array(guestBlocks[0].numFloor)
          .fill(0)
          .map((x, i) => i);
      });
  }
}
