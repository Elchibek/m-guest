import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GuestDetailComponent } from './guest-detail.component';

describe('Guest Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GuestDetailComponent,
              resolve: { guest: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GuestDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load guest on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GuestDetailComponent);

      // THEN
      expect(instance.guest).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
