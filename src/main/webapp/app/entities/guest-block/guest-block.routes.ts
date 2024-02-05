import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GuestBlockComponent } from './list/guest-block.component';
import { GuestBlockDetailComponent } from './detail/guest-block-detail.component';
import { GuestBlockUpdateComponent } from './update/guest-block-update.component';
import GuestBlockResolve from './route/guest-block-routing-resolve.service';

const guestBlockRoute: Routes = [
  {
    path: '',
    component: GuestBlockComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GuestBlockDetailComponent,
    resolve: {
      guestBlock: GuestBlockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GuestBlockUpdateComponent,
    resolve: {
      guestBlock: GuestBlockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GuestBlockUpdateComponent,
    resolve: {
      guestBlock: GuestBlockResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default guestBlockRoute;
