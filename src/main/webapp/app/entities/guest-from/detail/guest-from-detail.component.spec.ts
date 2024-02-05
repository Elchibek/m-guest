import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GuestFromDetailComponent } from './guest-from-detail.component';

describe('GuestFrom Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestFromDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GuestFromDetailComponent,
              resolve: { guestFrom: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GuestFromDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load guestFrom on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GuestFromDetailComponent);

      // THEN
      expect(instance.guestFrom).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
