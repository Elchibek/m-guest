import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuestHouse, NewGuestHouse } from '../guest-house.model';

export type PartialUpdateGuestHouse = Partial<IGuestHouse> & Pick<IGuestHouse, 'id'>;

export type EntityResponseType = HttpResponse<IGuestHouse>;
export type EntityArrayResponseType = HttpResponse<IGuestHouse[]>;

@Injectable({ providedIn: 'root' })
export class GuestHouseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guest-houses');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(guestHouse: NewGuestHouse): Observable<EntityResponseType> {
    return this.http.post<IGuestHouse>(this.resourceUrl, guestHouse, { observe: 'response' });
  }

  update(guestHouse: IGuestHouse): Observable<EntityResponseType> {
    return this.http.put<IGuestHouse>(`${this.resourceUrl}/${this.getGuestHouseIdentifier(guestHouse)}`, guestHouse, {
      observe: 'response',
    });
  }

  partialUpdate(guestHouse: PartialUpdateGuestHouse): Observable<EntityResponseType> {
    return this.http.patch<IGuestHouse>(`${this.resourceUrl}/${this.getGuestHouseIdentifier(guestHouse)}`, guestHouse, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGuestHouse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestHouse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuestHouseIdentifier(guestHouse: Pick<IGuestHouse, 'id'>): string {
    return guestHouse.id;
  }

  compareGuestHouse(o1: Pick<IGuestHouse, 'id'> | null, o2: Pick<IGuestHouse, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestHouseIdentifier(o1) === this.getGuestHouseIdentifier(o2) : o1 === o2;
  }

  addGuestHouseToCollectionIfMissing<Type extends Pick<IGuestHouse, 'id'>>(
    guestHouseCollection: Type[],
    ...guestHousesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guestHouses: Type[] = guestHousesToCheck.filter(isPresent);
    if (guestHouses.length > 0) {
      const guestHouseCollectionIdentifiers = guestHouseCollection.map(guestHouseItem => this.getGuestHouseIdentifier(guestHouseItem)!);
      const guestHousesToAdd = guestHouses.filter(guestHouseItem => {
        const guestHouseIdentifier = this.getGuestHouseIdentifier(guestHouseItem);
        if (guestHouseCollectionIdentifiers.includes(guestHouseIdentifier)) {
          return false;
        }
        guestHouseCollectionIdentifiers.push(guestHouseIdentifier);
        return true;
      });
      return [...guestHousesToAdd, ...guestHouseCollection];
    }
    return guestHouseCollection;
  }
}
