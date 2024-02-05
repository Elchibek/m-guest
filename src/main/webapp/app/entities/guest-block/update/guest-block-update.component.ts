import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IGuestBlock } from '../guest-block.model';
import { GuestBlockService } from '../service/guest-block.service';
import { GuestBlockFormService, GuestBlockFormGroup } from './guest-block-form.service';

@Component({
  standalone: true,
  selector: 'jhi-guest-block-update',
  templateUrl: './guest-block-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class GuestBlockUpdateComponent implements OnInit {
  isSaving = false;
  guestBlock: IGuestBlock | null = null;

  editForm: GuestBlockFormGroup = this.guestBlockFormService.createGuestBlockFormGroup();

  constructor(
    protected guestBlockService: GuestBlockService,
    protected guestBlockFormService: GuestBlockFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guestBlock }) => {
      this.guestBlock = guestBlock;
      if (guestBlock) {
        this.updateForm(guestBlock);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const guestBlock = this.guestBlockFormService.getGuestBlock(this.editForm);
    if (guestBlock.id !== null) {
      this.subscribeToSaveResponse(this.guestBlockService.update(guestBlock));
    } else {
      this.subscribeToSaveResponse(this.guestBlockService.create(guestBlock));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuestBlock>>): void {
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

  protected updateForm(guestBlock: IGuestBlock): void {
    this.guestBlock = guestBlock;
    this.guestBlockFormService.resetForm(this.editForm, guestBlock);
  }
}
