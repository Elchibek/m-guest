import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGuestFrom } from '../guest-from.model';

@Component({
  standalone: true,
  selector: 'jhi-guest-from-detail',
  templateUrl: './guest-from-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GuestFromDetailComponent implements OnInit {
  guestFrom: IGuestFrom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guestFrom }) => {
      this.guestFrom = guestFrom;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
