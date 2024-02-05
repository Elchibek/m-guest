import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGuestFrom } from '../guest-from.model';
import { GuestFromService } from '../service/guest-from.service';

@Component({
  standalone: true,
  templateUrl: './guest-from-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GuestFromDeleteDialogComponent {
  guestFrom?: IGuestFrom;

  constructor(
    protected guestFromService: GuestFromService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.guestFromService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
