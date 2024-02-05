import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { GuestHouseComponent } from './list/guest-house.component';
import { GuestHouseDetailComponent } from './detail/guest-house-detail.component';
import { GuestHouseUpdateComponent } from './update/guest-house-update.component';
import GuestHouseResolve from './route/guest-house-routing-resolve.service';

const guestHouseRoute: Routes = [
  {
    path: '',
    component: GuestHouseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GuestHouseDetailComponent,
    resolve: {
      guestHouse: GuestHouseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GuestHouseUpdateComponent,
    resolve: {
      guestHouse: GuestHouseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GuestHouseUpdateComponent,
    resolve: {
      guestHouse: GuestHouseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default guestHouseRoute;
