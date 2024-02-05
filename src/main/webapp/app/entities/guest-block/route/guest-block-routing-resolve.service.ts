import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGuestBlock } from '../guest-block.model';
import { GuestBlockService } from '../service/guest-block.service';

export const guestBlockResolve = (route: ActivatedRouteSnapshot): Observable<null | IGuestBlock> => {
  const id = route.params['id'];
  if (id) {
    return inject(GuestBlockService)
      .find(id)
      .pipe(
        mergeMap((guestBlock: HttpResponse<IGuestBlock>) => {
          if (guestBlock.body) {
            return of(guestBlock.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default guestBlockResolve;
