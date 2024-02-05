import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuestFrom, NewGuestFrom } from '../guest-from.model';

export type PartialUpdateGuestFrom = Partial<IGuestFrom> & Pick<IGuestFrom, 'id'>;

export type EntityResponseType = HttpResponse<IGuestFrom>;
export type EntityArrayResponseType = HttpResponse<IGuestFrom[]>;

@Injectable({ providedIn: 'root' })
export class GuestFromService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guest-froms');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(guestFrom: NewGuestFrom): Observable<EntityResponseType> {
    return this.http.post<IGuestFrom>(this.resourceUrl, guestFrom, { observe: 'response' });
  }

  update(guestFrom: IGuestFrom): Observable<EntityResponseType> {
    return this.http.put<IGuestFrom>(`${this.resourceUrl}/${this.getGuestFromIdentifier(guestFrom)}`, guestFrom, { observe: 'response' });
  }

  partialUpdate(guestFrom: PartialUpdateGuestFrom): Observable<EntityResponseType> {
    return this.http.patch<IGuestFrom>(`${this.resourceUrl}/${this.getGuestFromIdentifier(guestFrom)}`, guestFrom, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGuestFrom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestFrom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuestFromIdentifier(guestFrom: Pick<IGuestFrom, 'id'>): string {
    return guestFrom.id;
  }

  compareGuestFrom(o1: Pick<IGuestFrom, 'id'> | null, o2: Pick<IGuestFrom, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestFromIdentifier(o1) === this.getGuestFromIdentifier(o2) : o1 === o2;
  }

  addGuestFromToCollectionIfMissing<Type extends Pick<IGuestFrom, 'id'>>(
    guestFromCollection: Type[],
    ...guestFromsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guestFroms: Type[] = guestFromsToCheck.filter(isPresent);
    console.log('start');
    console.log(guestFromCollection);
    console.log('-----------------------');
    console.log(guestFroms);
    console.log('-----------------------');
    console.log(guestFromsToCheck);
    console.log('end');
    if (guestFroms.length > 0) {
      const guestFromCollectionIdentifiers = guestFromCollection.map(guestFromItem => this.getGuestFromIdentifier(guestFromItem)!);
      const guestFromsToAdd = guestFroms.filter(guestFromItem => {
        const guestFromIdentifier = this.getGuestFromIdentifier(guestFromItem);
        if (guestFromCollectionIdentifiers.includes(guestFromIdentifier)) {
          return false;
        }
        guestFromCollectionIdentifiers.push(guestFromIdentifier);
        return true;
      });
      return [...guestFromsToAdd, ...guestFromCollection];
    }
    return guestFromCollection;
  }
}
