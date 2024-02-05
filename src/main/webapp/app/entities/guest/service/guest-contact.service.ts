import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuestContact, NewGuestContact } from '../model/guest-contact.model';

export type PartialUpdateGuestContact = Partial<IGuestContact> & Pick<IGuestContact, 'id'>;

export type EntityResponseType = HttpResponse<IGuestContact>;
export type EntityArrayResponseType = HttpResponse<IGuestContact[]>;

@Injectable({ providedIn: 'root' })
export class GuestContactService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guest-contacts');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(guestContact: NewGuestContact): Observable<EntityResponseType> {
    return this.http.post<IGuestContact>(this.resourceUrl, guestContact, { observe: 'response' });
  }

  update(guestContact: IGuestContact): Observable<EntityResponseType> {
    return this.http.put<IGuestContact>(`${this.resourceUrl}/${this.getGuestContactIdentifier(guestContact)}`, guestContact, {
      observe: 'response',
    });
  }

  partialUpdate(guestContact: PartialUpdateGuestContact): Observable<EntityResponseType> {
    return this.http.patch<IGuestContact>(`${this.resourceUrl}/${this.getGuestContactIdentifier(guestContact)}`, guestContact, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGuestContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestContact[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuestContactIdentifier(guestContact: Pick<IGuestContact, 'id'>): string {
    return guestContact.id;
  }

  compareGuestContact(o1: Pick<IGuestContact, 'id'> | null, o2: Pick<IGuestContact, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestContactIdentifier(o1) === this.getGuestContactIdentifier(o2) : o1 === o2;
  }

  addGuestContactToCollectionIfMissing<Type extends Pick<IGuestContact, 'id'>>(
    guestContactCollection: Type[],
    ...guestContactsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guestContacts: Type[] = guestContactsToCheck.filter(isPresent);
    if (guestContacts.length > 0) {
      const guestContactCollectionIdentifiers = guestContactCollection.map(
        guestContactItem => this.getGuestContactIdentifier(guestContactItem)!,
      );
      const guestContactsToAdd = guestContacts.filter(guestContactItem => {
        const guestContactIdentifier = this.getGuestContactIdentifier(guestContactItem);
        if (guestContactCollectionIdentifiers.includes(guestContactIdentifier)) {
          return false;
        }
        guestContactCollectionIdentifiers.push(guestContactIdentifier);
        return true;
      });
      return [...guestContactsToAdd, ...guestContactCollection];
    }
    return guestContactCollection;
  }
}
