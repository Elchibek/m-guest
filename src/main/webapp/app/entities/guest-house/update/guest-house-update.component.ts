import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFloor } from 'app/entities/guest/model/floor.model';
import { FloorService } from 'app/entities/guest/service/floor.service';
import { IGuestHouse } from '../guest-house.model';
import { GuestHouseService } from '../service/guest-house.service';
import { GuestHouseFormService, GuestHouseFormGroup } from './guest-house-form.service';

@Component({
  standalone: true,
  selector: 'jhi-guest-house-update',
  templateUrl: './guest-house-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GuestHouseUpdateComponent implements OnInit {
  isSaving = false;
  guestHouse: IGuestHouse | null = null;

  floorsSharedCollection: IFloor[] = [];

  editForm: GuestHouseFormGroup = this.guestHouseFormService.createGuestHouseFormGroup();

  constructor(
    protected guestHouseService: GuestHouseService,
    protected guestHouseFormService: GuestHouseFormService,
    protected floorService: FloorService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareFloor = (o1: IFloor | null, o2: IFloor | null): boolean => this.floorService.compareFloor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guestHouse }) => {
      this.guestHouse = guestHouse;
      if (guestHouse) {
        this.updateForm(guestHouse);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guestHouse = this.guestHouseFormService.getGuestHouse(this.editForm);
    if (guestHouse.id !== null) {
      this.subscribeToSaveResponse(this.guestHouseService.update(guestHouse));
    } else {
      this.subscribeToSaveResponse(this.guestHouseService.create(guestHouse));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuestHouse>>): void {
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

  protected updateForm(guestHouse: IGuestHouse): void {
    this.guestHouse = guestHouse;
    this.guestHouseFormService.resetForm(this.editForm, guestHouse);

    this.floorsSharedCollection = this.floorService.addFloorToCollectionIfMissing<IFloor>(this.floorsSharedCollection, guestHouse.floor);
  }

  protected loadRelationshipsOptions(): void {
    this.floorService
      .query()
      .pipe(map((res: HttpResponse<IFloor[]>) => res.body ?? []))
      .pipe(map((floors: IFloor[]) => this.floorService.addFloorToCollectionIfMissing<IFloor>(floors, this.guestHouse?.floor)))
      .subscribe((floors: IFloor[]) => (this.floorsSharedCollection = floors));
  }
}
