import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuest } from '../model/guest.model';
import { GuestService } from '../service/guest.service';

export const guestResolve = (route: ActivatedRouteSnapshot): Observable<null | IGuest> => {
  const id = route.params['id'];
  if (id) {
    return inject(GuestService)
      .find(id)
      .pipe(
        mergeMap((guest: HttpResponse<IGuest>) => {
          if (guest.body) {
            return of(guest.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default guestResolve;
