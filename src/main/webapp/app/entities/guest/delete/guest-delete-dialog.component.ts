import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGuest } from '../model/guest.model';
import { GuestService } from '../service/guest.service';

@Component({
  standalone: true,
  templateUrl: './guest-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GuestDeleteDialogComponent {
  guest?: IGuest;

  constructor(
    protected guestService: GuestService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDeleteOrArchive(id: string, isArchive: boolean, isRestore: boolean): void {
    if (isArchive) {
      this.guestService.archive(id).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    } else {
      this.guestService.delete(id, isRestore).subscribe(() => {
        this.activeModal.close(ITEM_DELETED_EVENT);
      });
    }
  }
}
