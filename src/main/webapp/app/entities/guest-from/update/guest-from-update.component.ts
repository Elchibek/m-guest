import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGuestFrom } from '../guest-from.model';
import { GuestFromService } from '../service/guest-from.service';
import { GuestFromFormService, GuestFromFormGroup } from './guest-from-form.service';

@Component({
  standalone: true,
  selector: 'jhi-guest-from-update',
  templateUrl: './guest-from-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GuestFromUpdateComponent implements OnInit {
  isSaving = false;
  guestFrom: IGuestFrom | null = null;

  editForm: GuestFromFormGroup = this.guestFromFormService.createGuestFromFormGroup();

  constructor(
    protected guestFromService: GuestFromService,
    protected guestFromFormService: GuestFromFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guestFrom }) => {
      this.guestFrom = guestFrom;
      if (guestFrom) {
        this.updateForm(guestFrom);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guestFrom = this.guestFromFormService.getGuestFrom(this.editForm);
    if (guestFrom.id !== null) {
      this.subscribeToSaveResponse(this.guestFromService.update(guestFrom));
    } else {
      this.subscribeToSaveResponse(this.guestFromService.create(guestFrom));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuestFrom>>): void {
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

  protected updateForm(guestFrom: IGuestFrom): void {
    this.guestFrom = guestFrom;
    this.guestFromFormService.resetForm(this.editForm, guestFrom);
  }
}
