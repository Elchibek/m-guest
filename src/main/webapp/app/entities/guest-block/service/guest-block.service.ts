import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuestBlock, NewGuestBlock } from '../guest-block.model';

export type PartialUpdateGuestBlock = Partial<IGuestBlock> & Pick<IGuestBlock, 'id'>;

export type EntityResponseType = HttpResponse<IGuestBlock>;
export type EntityArrayResponseType = HttpResponse<IGuestBlock[]>;

@Injectable({ providedIn: 'root' })
export class GuestBlockService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guest-blocks');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(guestBlock: NewGuestBlock): Observable<EntityResponseType> {
    return this.http.post<IGuestBlock>(this.resourceUrl, guestBlock, { observe: 'response' });
  }

  update(guestBlock: IGuestBlock): Observable<EntityResponseType> {
    return this.http.put<IGuestBlock>(`${this.resourceUrl}/${this.getGuestBlockIdentifier(guestBlock)}`, guestBlock, {
      observe: 'response',
    });
  }

  partialUpdate(guestBlock: PartialUpdateGuestBlock): Observable<EntityResponseType> {
    return this.http.patch<IGuestBlock>(`${this.resourceUrl}/${this.getGuestBlockIdentifier(guestBlock)}`, guestBlock, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGuestBlock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGuestBlock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuestBlockIdentifier(guestBlock: Pick<IGuestBlock, 'id'>): string {
    return guestBlock.id;
  }

  compareGuestBlock(o1: Pick<IGuestBlock, 'id'> | null, o2: Pick<IGuestBlock, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuestBlockIdentifier(o1) === this.getGuestBlockIdentifier(o2) : o1 === o2;
  }

  addGuestBlockToCollectionIfMissing<Type extends Pick<IGuestBlock, 'id'>>(
    guestBlockCollection: Type[],
    ...guestBlocksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guestBlocks: Type[] = guestBlocksToCheck.filter(isPresent);
    if (guestBlocks.length > 0) {
      const guestBlockCollectionIdentifiers = guestBlockCollection.map(guestBlockItem => this.getGuestBlockIdentifier(guestBlockItem)!);
      const guestBlocksToAdd = guestBlocks.filter(guestBlockItem => {
        const guestBlockIdentifier = this.getGuestBlockIdentifier(guestBlockItem);
        if (guestBlockCollectionIdentifiers.includes(guestBlockIdentifier)) {
          return false;
        }
        guestBlockCollectionIdentifiers.push(guestBlockIdentifier);
        return true;
      });
      return [...guestBlocksToAdd, ...guestBlockCollection];
    }
    return guestBlockCollection;
  }
}
