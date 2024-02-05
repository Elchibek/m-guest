import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGuest } from '../model/guest.model';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { IGuestContact } from '../model/guest-contact.model';
import { GuestContactService } from '../service/guest-contact.service';
import { map } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { GuestService } from '../service/guest.service';

@Component({
  standalone: true,
  selector: 'jhi-guest-detail',
  templateUrl: './guest-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, NgbNavModule],
})
export class GuestDetailComponent implements OnInit {
  guest: IGuest | null = null;
  guestContacts?: IGuestContact[];

  links: { [key: string]: number } = {
    last: 0,
  };
  page = 1;
  active = 1;

  constructor(
    protected guestService: GuestService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guest }) => {
      this.guest = guest;
      this.loadGuestContact(guest.id);
    });
  }

  previousState(): void {
    window.history.back();
  }

  trackId = (_index: number, item: IGuestContact): string => this.guestService.getGuestContactIdentifier(item);

  protected loadGuestContact(guestId: string): void {
    if (guestId != null) {
      this.guestService
        .guestContactsQery({ guestId })
        .pipe(map((res: HttpResponse<IGuestContact[]>) => res.body ?? []))
        .subscribe((guestFroms: IGuestContact[]) => (this.guestContacts = guestFroms));
    }
  }
}
