import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GuestBlockDetailComponent } from './guest-block-detail.component';

describe('GuestBlock Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestBlockDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GuestBlockDetailComponent,
              resolve: { guestBlock: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GuestBlockDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load guestBlock on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GuestBlockDetailComponent);

      // THEN
      expect(instance.guestBlock).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
