import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, asapScheduler, scheduled } from 'rxjs';

import { catchError, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuest, NewGuest } from '../model/guest.model';
import { IGuestContact } from '../model/guest-contact.model';
import { IGuestDoc } from '../model/guest-doc.model';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IPutAway } from '../model/put-away.model';

export type PartialUpdateGuest = Partial<IGuest> & Pick<IGuest, 'id'>;

type RestOf<T extends IGuest | NewGuest> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

export type RestGuest = RestOf<IGuest>;

export type NewRestGuest = RestOf<NewGuest>;

export type PartialUpdateRestGuest = RestOf<PartialUpdateGuest>;

export type EntityResponseType = HttpResponse<IGuest>;
export type EntityArrayResponseType = HttpResponse<IGuest[]>;

@Injectable({ providedIn: 'root' })
export class GuestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guests');
  protected resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/guests/_search');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(guest: NewGuest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guest);
    return this.http.post<RestGuest>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(guest: IGuest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guest);
    console.log(copy);
    return this.http
      .put<RestGuest>(`${this.resourceUrl}/${this.getGuestIdentifier(guest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(guest: PartialUpdateGuest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guest);
    return this.http
      .patch<RestGuest>(`${this.resourceUrl}/${this.getGuestIdentifier(guest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestGuest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGuest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  guestContactsQery(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestContact[]>(this.resourceUrl.concat('/contacts'), { params: options, observe: 'response' });
  }

  archive(id: string): Observable<HttpResponse<{}>> {
    return this.http.get(`${this.resourceUrl}/archive/${id}`, { observe: 'response' });
  }

  delete(id: string, isRestore: boolean): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}` + '?isRestore=' + isRestore, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGuest[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(catchError(() => scheduled([new HttpResponse<IGuest[]>()], asapScheduler)));
  }

  saveToDoc(guestDoc: IGuestDoc): Observable<EntityResponseType> {
    return this.http
      .post<RestGuest>(this.resourceUrl + '/saveToDoc', guestDoc, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  getGuestIdentifier(guest: Pick<IGuest, 'id'>): string {
    return guest.id;
  }

  getGuestContactIdentifier(guestContact: Pick<IGuestContact, 'id'>): string {
    return guestContact.id;
  }

  compareGuest(o1: Pick<IGuest, 'id'> | null, o2: Pick<IGuest, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestIdentifier(o1) === this.getGuestIdentifier(o2) : o1 === o2;
  }

  addGuestToCollectionIfMissing<Type extends Pick<IGuest, 'id'>>(
    guestCollection: Type[],
    ...guestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guests: Type[] = guestsToCheck.filter(isPresent);
    if (guests.length > 0) {
      const guestCollectionIdentifiers = guestCollection.map(guestItem => this.getGuestIdentifier(guestItem)!);
      const guestsToAdd = guests.filter(guestItem => {
        const guestIdentifier = this.getGuestIdentifier(guestItem);
        if (guestCollectionIdentifiers.includes(guestIdentifier)) {
          return false;
        }
        guestCollectionIdentifiers.push(guestIdentifier);
        return true;
      });
      return [...guestsToAdd, ...guestCollection];
    }
    return guestCollection;
  }

  protected convertDateFromClient<T extends IGuest | NewGuest | PartialUpdateGuest>(guest: T): RestOf<T> {
    return {
      ...guest,
      startDate: guest.startDate?.toJSON() ?? null,
      endDate: guest.endDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGuest: RestGuest): IGuest {
    return {
      ...restGuest,
      startDate: restGuest.startDate ? dayjs(restGuest.startDate) : undefined,
      endDate: restGuest.endDate ? dayjs(restGuest.endDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGuest>): HttpResponse<IGuest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGuest[]>): HttpResponse<IGuest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
