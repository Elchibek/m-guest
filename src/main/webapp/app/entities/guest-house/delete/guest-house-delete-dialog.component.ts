import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IGuestHouse } from '../guest-house.model';
import { GuestHouseService } from '../service/guest-house.service';

@Component({
  standalone: true,
  templateUrl: './guest-house-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class GuestHouseDeleteDialogComponent {
  guestHouse?: IGuestHouse;

  constructor(
    protected guestHouseService: GuestHouseService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.guestHouseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
