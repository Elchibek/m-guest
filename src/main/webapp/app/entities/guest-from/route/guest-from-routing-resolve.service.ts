import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuestFrom } from '../guest-from.model';
import { GuestFromService } from '../service/guest-from.service';

export const guestFromResolve = (route: ActivatedRouteSnapshot): Observable<null | IGuestFrom> => {
  const id = route.params['id'];
  if (id) {
    return inject(GuestFromService)
      .find(id)
      .pipe(
        mergeMap((guestFrom: HttpResponse<IGuestFrom>) => {
          if (guestFrom.body) {
            return of(guestFrom.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default guestFromResolve;
