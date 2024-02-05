import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GuestFromComponent } from './list/guest-from.component';
import { GuestFromDetailComponent } from './detail/guest-from-detail.component';
import { GuestFromUpdateComponent } from './update/guest-from-update.component';
import GuestFromResolve from './route/guest-from-routing-resolve.service';

const guestFromRoute: Routes = [
  {
    path: '',
    component: GuestFromComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GuestFromDetailComponent,
    resolve: {
      guestFrom: GuestFromResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GuestFromUpdateComponent,
    resolve: {
      guestFrom: GuestFromResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GuestFromUpdateComponent,
    resolve: {
      guestFrom: GuestFromResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default guestFromRoute;
