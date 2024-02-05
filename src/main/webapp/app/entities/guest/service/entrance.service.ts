import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntrance, NewEntrance } from '../model/entrance.model';

export type PartialUpdateEntrance = Partial<IEntrance> & Pick<IEntrance, 'id'>;

export type EntityResponseType = HttpResponse<IEntrance>;
export type EntityArrayResponseType = HttpResponse<IEntrance[]>;

@Injectable({ providedIn: 'root' })
export class EntranceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entrances');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(entrance: NewEntrance): Observable<EntityResponseType> {
    return this.http.post<IEntrance>(this.resourceUrl, entrance, { observe: 'response' });
  }

  update(entrance: IEntrance): Observable<EntityResponseType> {
    return this.http.put<IEntrance>(`${this.resourceUrl}/${this.getEntranceIdentifier(entrance)}`, entrance, { observe: 'response' });
  }

  partialUpdate(entrance: PartialUpdateEntrance): Observable<EntityResponseType> {
    return this.http.patch<IEntrance>(`${this.resourceUrl}/${this.getEntranceIdentifier(entrance)}`, entrance, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEntrance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEntrance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEntranceIdentifier(entrance: Pick<IEntrance, 'id'>): string {
    return entrance.id;
  }

  compareEntrance(o1: Pick<IEntrance, 'id'> | null, o2: Pick<IEntrance, 'id'> | null): boolean {
    return o1 && o2 ? this.getEntranceIdentifier(o1) === this.getEntranceIdentifier(o2) : o1 === o2;
  }

  addEntranceToCollectionIfMissing<Type extends Pick<IEntrance, 'id'>>(
    entranceCollection: Type[],
    ...entrancesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const entrances: Type[] = entrancesToCheck.filter(isPresent);
    if (entrances.length > 0) {
      const entranceCollectionIdentifiers = entranceCollection.map(entranceItem => this.getEntranceIdentifier(entranceItem)!);
      const entrancesToAdd = entrances.filter(entranceItem => {
        const entranceIdentifier = this.getEntranceIdentifier(entranceItem);
        if (entranceCollectionIdentifiers.includes(entranceIdentifier)) {
          return false;
        }
        entranceCollectionIdentifiers.push(entranceIdentifier);
        return true;
      });
      return [...entrancesToAdd, ...entranceCollection];
    }
    return entranceCollection;
  }
}
