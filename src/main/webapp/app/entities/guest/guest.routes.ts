import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GuestComponent } from './list/guest.component';
import { GuestDetailComponent } from './detail/guest-detail.component';
import { GuestUpdateComponent } from './update/guest-update.component';
import GuestResolve from './route/guest-routing-resolve.service';

const guestRoute: Routes = [
  {
    path: '',
    component: GuestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'guest/:id/view',
    component: GuestDetailComponent,
    resolve: {
      guest: GuestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'guest/new',
    component: GuestUpdateComponent,
    resolve: {
      guest: GuestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'guest/:id/edit',
    component: GuestUpdateComponent,
    resolve: {
      guest: GuestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default guestRoute;
