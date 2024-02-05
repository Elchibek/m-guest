import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        data: { pageTitle: 'guestApp.guest.home.title' },
        loadChildren: () => import('./guest/guest.routes'),
      },
      {
        path: 'guest-block',
        data: { pageTitle: 'guestApp.guestBlock.home.title' },
        loadChildren: () => import('./guest-block/guest-block.routes'),
      },
      {
        path: 'guest-house',
        data: { pageTitle: 'guestApp.guestHouse.home.title' },
        loadChildren: () => import('./guest-house/guest-house.routes'),
      },
      {
        path: 'guest-from',
        data: { pageTitle: 'guestApp.guestFrom.home.title' },
        loadChildren: () => import('./guest-from/guest-from.routes'),
      },
      {
        path: 'guest-static',
        data: { pageTitle: 'Guest Static' },
        loadChildren: () => import('./gues-chart/guest-chart.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
