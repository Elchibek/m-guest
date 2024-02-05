import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GuestChartComponent } from './guest-chart.component';

const guestChartRoute: Routes = [
  {
    path: '',
    component: GuestChartComponent,
    canActivate: [UserRouteAccessService],
  },
];

export default guestChartRoute;
