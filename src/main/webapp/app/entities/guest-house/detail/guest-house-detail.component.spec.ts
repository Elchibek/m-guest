import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GuestHouseDetailComponent } from './guest-house-detail.component';

describe('GuestHouse Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestHouseDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GuestHouseDetailComponent,
              resolve: { guestHouse: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GuestHouseDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load guestHouse on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GuestHouseDetailComponent);

      // THEN
      expect(instance.guestHouse).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
