import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormArray, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGuestBlock } from 'app/entities/guest-block/guest-block.model';
import { GuestBlockService } from 'app/entities/guest-block/service/guest-block.service';
import { IEntrance } from 'app/entities/guest/model/entrance.model';
import { EntranceService } from 'app/entities/guest/service/entrance.service';
import { IFloor } from 'app/entities/guest/model/floor.model';
import { FloorService } from 'app/entities/guest/service/floor.service';
import { IGuestHouse } from 'app/entities/guest-house/guest-house.model';
import { GuestHouseService } from 'app/entities/guest-house/service/guest-house.service';
import { IGuestFrom } from 'app/entities/guest-from/guest-from.model';
import { GuestFromService } from 'app/entities/guest-from/service/guest-from.service';
import { GuestService } from '../service/guest.service';
import { IGuest } from '../model/guest.model';
import { GuestFormService, GuestFormGroup } from './guest-form.service';
import { IRestOfTheDay } from '../model/rest-of-the-day.model';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  standalone: true,
  selector: 'jhi-guest-update',
  templateUrl: './guest-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GuestUpdateComponent implements OnInit {
  isSaving = false;
  guest: IGuest | null = null;

  restOfTheDaysSharedCollection: IRestOfTheDay[] = [];
  guestBlocksSharedCollection: IGuestBlock[] = [];
  entrancesSharedCollection: IEntrance[] = [];
  floorsSharedCollection: IFloor[] = [];
  guestHousesSharedCollection: IGuestHouse[] = [];
  guestFromsSharedCollection: IGuestFrom[] = [];

  editForm: GuestFormGroup = this.guestFormService.createGuestFormGroup();

  constructor(
    protected guestService: GuestService,
    protected guestFormService: GuestFormService,
    protected guestBlockService: GuestBlockService,
    protected entranceService: EntranceService,
    protected floorService: FloorService,
    protected guestHouseService: GuestHouseService,
    protected guestFromService: GuestFromService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) {}

  compareGuestBlock = (o1: IGuestBlock | null, o2: IGuestBlock | null): boolean => this.guestBlockService.compareGuestBlock(o1, o2);

  compareEntrance = (o1: IEntrance | null, o2: IEntrance | null): boolean => this.entranceService.compareEntrance(o1, o2);

  compareFloor = (o1: IFloor | null, o2: IFloor | null): boolean => this.floorService.compareFloor(o1, o2);

  compareGuestHouse = (o1: IGuestHouse | null, o2: IGuestHouse | null): boolean => this.guestHouseService.compareGuestHouse(o1, o2);

  compareGuestFrom = (o1: IGuestFrom | null, o2: IGuestFrom | null): boolean => this.guestFromService.compareGuestFrom(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guest }) => {
      this.guest = guest;
      if (guest) {
        this.updateForm(guest);
        this.getAllByEntranceGuestBlockId(guest.guestBlock.id);
        this.getAllByFloorEntranceId(guest.entrance.id);
        this.getAllByHouseFloorId(guest.floor.id);
      }
      this.addFormArray(-1, true);
      this.addFormArray(-1, false);
      this.loadRelationshipsOptions();
    });
    this.fieldsIsPaid({ currentTarget: { checked: true } });
  }

  previousState(): void {
    window.history.back();
  }

  get contacts() {
    return this.editForm.get('contacts') as FormArray;
  }

  // get putAways() {
  //   return this.editForm.get('putAways') as FormArray;
  // }

  getFormArray(name: string) {
    return this.editForm.get(name) as FormArray;
  }

  removeFormArray(index: number, name: string) {
    const f = this.getFormArray(name);
    if (f.at(index).get('id')?.value) f.at(index).patchValue({ isDelete: true });
    else f.removeAt(index);

    if (index < 1) this.addFormArray(-1, name === 'contacts' ? true : false);
  }

  addFormArray(index: number, isContact: boolean) {
    if (isContact) {
      (this.editForm.get('contacts') as FormArray).insert(index + 1, this.guestFormService.getContact());
    }
    // else if (!isContact) {
    //   (this.editForm.get('putAways') as FormArray).insert(index + 1, this.guestFormService.getPutAway());
    // }
  }

  getGuestBlockId(guestBlock: IGuestBlock) {
    this.entrancesSharedCollection = [];
    this.floorsSharedCollection = [];
    this.guestHousesSharedCollection = [];
    this.editForm.get('entrance')?.reset();
    this.editForm.get('floor')?.reset();
    this.editForm.get('guestHouse')?.reset();
    if (guestBlock != null) this.getAllByEntranceGuestBlockId(guestBlock.id);
  }

  getEntranceId(entrance: IEntrance) {
    this.floorsSharedCollection = [];
    this.guestHousesSharedCollection = [];
    this.editForm.get('floor')?.reset();
    this.editForm.get('guestHouse')?.reset();
    if (entrance != null) this.getAllByFloorEntranceId(entrance?.id);
  }

  getFloorId(floor: IFloor) {
    this.guestHousesSharedCollection = [];
    this.editForm.get('guestHouse')?.reset();
    if (floor != null) this.getAllByHouseFloorId(floor?.id);
  }

  save(): void {
    this.isSaving = true;
    const guest = this.guestFormService.getGuest(this.editForm);
    // console.log(guest);
    if (guest.id !== null) {
      this.subscribeToSaveResponse(this.guestService.update(guest));
    } else {
      this.subscribeToSaveResponse(this.guestService.create(guest));
    }
  }

  formatInput(input: HTMLInputElement) {
    input.value = input.value.replace(FILTER_PAG_REGEX, '');
  }

  fieldsIsPaid(value: any): void {
    ['countDidntPay', 'didntPay'].forEach(f => {
      if (value.currentTarget.checked) {
        this.editForm.get(f)?.reset();
        this.editForm.get(f)?.disable();
      } else this.editForm.get(f)?.enable();
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(guest: IGuest): void {
    this.guest = guest;
    this.guestFormService.resetForm(this.editForm, guest);

    this.guestBlocksSharedCollection = this.guestBlockService.addGuestBlockToCollectionIfMissing<IGuestBlock>(
      this.guestBlocksSharedCollection,
      guest.guestBlock,
    );
    this.entrancesSharedCollection = this.entranceService.addEntranceToCollectionIfMissing<IEntrance>(
      this.entrancesSharedCollection,
      guest.entrance,
    );
    this.floorsSharedCollection = this.floorService.addFloorToCollectionIfMissing<IFloor>(this.floorsSharedCollection, guest.floor);
    this.guestHousesSharedCollection = this.guestHouseService.addGuestHouseToCollectionIfMissing<IGuestHouse>(
      this.guestHousesSharedCollection,
      guest.guestHouse,
    );
    this.guestFromsSharedCollection = this.guestFromService.addGuestFromToCollectionIfMissing<IGuestFrom>(
      this.guestFromsSharedCollection,
      guest.guestFrom,
    );
  }

  protected getAllByEntranceGuestBlockId(guestBlockId: string) {
    this.entranceService
      .query({ guestBlockId })
      .pipe(map((res: HttpResponse<IEntrance[]>) => res.body ?? []))
      .pipe(
        map((entrances: IEntrance[]) => this.entranceService.addEntranceToCollectionIfMissing<IEntrance>(entrances, this.guest?.entrance)),
      )
      .subscribe((entrances: IEntrance[]) => (this.entrancesSharedCollection = entrances));
  }

  protected getAllByFloorEntranceId(entranceId: string) {
    this.floorService
      .query({ entranceId })
      .pipe(map((res: HttpResponse<IFloor[]>) => res.body ?? []))
      // .pipe(map((floors: IFloor[]) => this.floorService.addFloorToCollectionIfMissing<IFloor>(floors, this.guest?.floor)))
      .subscribe((floors: IFloor[]) => (this.floorsSharedCollection = floors));
  }

  protected getAllByHouseFloorId(floorId: string) {
    this.guestHouseService
      .query({ floorId })
      .pipe(map((res: HttpResponse<IGuestHouse[]>) => res.body ?? []))
      // .pipe(
      //   map((guestHouses: IGuestHouse[]) =>
      //     this.guestHouseService.addGuestHouseToCollectionIfMissing<IGuestHouse>(guestHouses, this.guest?.guestHouse),
      //   ),
      // )
      .subscribe((guestHouses: IGuestHouse[]) => (this.guestHousesSharedCollection = guestHouses));
  }

  protected loadRelationshipsOptions(): void {
    this.guestFromService
      .query()
      .pipe(map((res: HttpResponse<IGuestFrom[]>) => res.body ?? []))
      // .pipe(
      //   map((guestFroms: IGuestFrom[]) =>
      //     this.guestFromService.addGuestFromToCollectionIfMissing<IGuestFrom>(guestFroms, this.guest?.guestFrom),
      //   ),
      // )
      .subscribe((guestFroms: IGuestFrom[]) => (this.guestFromsSharedCollection = guestFroms));

    this.guestBlockService
      .query()
      .pipe(map((res: HttpResponse<IGuestBlock[]>) => res.body ?? []))
      // .pipe(
      //   map((guestBlocks: IGuestBlock[]) =>
      //     this.guestBlockService.addGuestBlockToCollectionIfMissing<IGuestBlock>(guestBlocks, this.guest?.guestBlock),
      //   ),
      // )
      .subscribe((guestBlocks: IGuestBlock[]) => (this.guestBlocksSharedCollection = guestBlocks));

    // this.restOfTheDayService
    //   .query()
    //   .pipe(map((res: HttpResponse<IRestOfTheDay[]>) => res.body ?? []))
    //   .pipe(
    //     map((restOfTheDays: IRestOfTheDay[]) =>
    //       this.restOfTheDayService.addRestOfTheDayToCollectionIfMissing<IRestOfTheDay>(restOfTheDays, this.guest?.restOfTheDay),
    //     ),
    //   )
    //   .subscribe((restOfTheDays: IRestOfTheDay[]) => (this.restOfTheDaysSharedCollection = restOfTheDays));
  }
}
