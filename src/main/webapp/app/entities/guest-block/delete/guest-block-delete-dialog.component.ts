import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGuestBlock } from '../guest-block.model';
import { GuestBlockService } from '../service/guest-block.service';

@Component({
  standalone: true,
  templateUrl: './guest-block-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GuestBlockDeleteDialogComponent {
  guestBlock?: IGuestBlock;

  constructor(
    protected guestBlockService: GuestBlockService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.guestBlockService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
