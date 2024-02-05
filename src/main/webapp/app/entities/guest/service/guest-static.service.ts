import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuestStatic, NewGuestStatic } from '../model/guest-static.model';

export type PartialUpdateGuestStatic = Partial<IGuestStatic> & Pick<IGuestStatic, 'id'>;

export type EntityResponseType = HttpResponse<IGuestStatic>;
export type EntityArrayResponseType = HttpResponse<IGuestStatic[]>;

@Injectable({ providedIn: 'root' })
export class GuestStaticService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guest-statics');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(guestStatic: NewGuestStatic): Observable<EntityResponseType> {
    return this.http.post<IGuestStatic>(this.resourceUrl, guestStatic, { observe: 'response' });
  }

  update(guestStatic: IGuestStatic): Observable<EntityResponseType> {
    return this.http.put<IGuestStatic>(`${this.resourceUrl}/${this.getGuestStaticIdentifier(guestStatic)}`, guestStatic, {
      observe: 'response',
    });
  }

  partialUpdate(guestStatic: PartialUpdateGuestStatic): Observable<EntityResponseType> {
    return this.http.patch<IGuestStatic>(`${this.resourceUrl}/${this.getGuestStaticIdentifier(guestStatic)}`, guestStatic, {
      observe: 'response',
    });
  }

  find(req?: any): Observable<EntityResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestStatic>(this.resourceUrl.concat('/get'), { params: options, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestStatic[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuestStaticIdentifier(guestStatic: Pick<IGuestStatic, 'id'>): string {
    return guestStatic.id;
  }

  compareGuestStatic(o1: Pick<IGuestStatic, 'id'> | null, o2: Pick<IGuestStatic, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestStaticIdentifier(o1) === this.getGuestStaticIdentifier(o2) : o1 === o2;
  }

  addGuestStaticToCollectionIfMissing<Type extends Pick<IGuestStatic, 'id'>>(
    guestStaticCollection: Type[],
    ...guestStaticsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guestStatics: Type[] = guestStaticsToCheck.filter(isPresent);
    if (guestStatics.length > 0) {
      const guestStaticCollectionIdentifiers = guestStaticCollection.map(
        guestStaticItem => this.getGuestStaticIdentifier(guestStaticItem)!,
      );
      const guestStaticsToAdd = guestStatics.filter(guestStaticItem => {
        const guestStaticIdentifier = this.getGuestStaticIdentifier(guestStaticItem);
        if (guestStaticCollectionIdentifiers.includes(guestStaticIdentifier)) {
          return false;
        }
        guestStaticCollectionIdentifiers.push(guestStaticIdentifier);
        return true;
      });
      return [...guestStaticsToAdd, ...guestStaticCollection];
    }
    return guestStaticCollection;
  }
}
