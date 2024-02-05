import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IGuestBlock } from '../guest-block.model';

@Component({
  standalone: true,
  selector: 'jhi-guest-block-detail',
  templateUrl: './guest-block-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class GuestBlockDetailComponent implements OnInit {
  guestBlock: IGuestBlock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guestBlock }) => {
      this.guestBlock = guestBlock;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
