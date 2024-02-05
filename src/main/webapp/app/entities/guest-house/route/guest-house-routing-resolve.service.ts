import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuestHouse } from '../guest-house.model';
import { GuestHouseService } from '../service/guest-house.service';

export const guestHouseResolve = (route: ActivatedRouteSnapshot): Observable<null | IGuestHouse> => {
  const id = route.params['id'];
  if (id) {
    return inject(GuestHouseService)
      .find(id)
      .pipe(
        mergeMap((guestHouse: HttpResponse<IGuestHouse>) => {
          if (guestHouse.body) {
            return of(guestHouse.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default guestHouseResolve;
