import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArrivalDepartureStatic, NewArrivalDepartureStatic } from '../model/arrival-departure-static.model';

export type PartialUpdateArrivalDepartureStatic = Partial<IArrivalDepartureStatic> & Pick<IArrivalDepartureStatic, 'id'>;

type RestOf<T extends IArrivalDepartureStatic | NewArrivalDepartureStatic> = Omit<T, 'date'> & {
  date?: string | null;
};

export type RestArrivalDepartureStatic = RestOf<IArrivalDepartureStatic>;

export type NewRestArrivalDepartureStatic = RestOf<NewArrivalDepartureStatic>;

export type PartialUpdateRestArrivalDepartureStatic = RestOf<PartialUpdateArrivalDepartureStatic>;

export type EntityResponseType = HttpResponse<IArrivalDepartureStatic>;
export type EntityArrayResponseType = HttpResponse<IArrivalDepartureStatic[]>;

@Injectable({ providedIn: 'root' })
export class ArrivalDepartureStaticService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/arrival-departure-statics');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(arrivalDepartureStatic: NewArrivalDepartureStatic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivalDepartureStatic);
    return this.http
      .post<RestArrivalDepartureStatic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(arrivalDepartureStatic: IArrivalDepartureStatic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivalDepartureStatic);
    return this.http
      .put<RestArrivalDepartureStatic>(`${this.resourceUrl}/${this.getArrivalDepartureStaticIdentifier(arrivalDepartureStatic)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(arrivalDepartureStatic: PartialUpdateArrivalDepartureStatic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrivalDepartureStatic);
    return this.http
      .patch<RestArrivalDepartureStatic>(`${this.resourceUrl}/${this.getArrivalDepartureStaticIdentifier(arrivalDepartureStatic)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestArrivalDepartureStatic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestArrivalDepartureStatic[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArrivalDepartureStaticIdentifier(arrivalDepartureStatic: Pick<IArrivalDepartureStatic, 'id'>): string {
    return arrivalDepartureStatic.id;
  }

  compareArrivalDepartureStatic(o1: Pick<IArrivalDepartureStatic, 'id'> | null, o2: Pick<IArrivalDepartureStatic, 'id'> | null): boolean {
    return o1 && o2 ? this.getArrivalDepartureStaticIdentifier(o1) === this.getArrivalDepartureStaticIdentifier(o2) : o1 === o2;
  }

  addArrivalDepartureStaticToCollectionIfMissing<Type extends Pick<IArrivalDepartureStatic, 'id'>>(
    arrivalDepartureStaticCollection: Type[],
    ...arrivalDepartureStaticsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const arrivalDepartureStatics: Type[] = arrivalDepartureStaticsToCheck.filter(isPresent);
    if (arrivalDepartureStatics.length > 0) {
      const arrivalDepartureStaticCollectionIdentifiers = arrivalDepartureStaticCollection.map(
        arrivalDepartureStaticItem => this.getArrivalDepartureStaticIdentifier(arrivalDepartureStaticItem)!,
      );
      const arrivalDepartureStaticsToAdd = arrivalDepartureStatics.filter(arrivalDepartureStaticItem => {
        const arrivalDepartureStaticIdentifier = this.getArrivalDepartureStaticIdentifier(arrivalDepartureStaticItem);
        if (arrivalDepartureStaticCollectionIdentifiers.includes(arrivalDepartureStaticIdentifier)) {
          return false;
        }
        arrivalDepartureStaticCollectionIdentifiers.push(arrivalDepartureStaticIdentifier);
        return true;
      });
      return [...arrivalDepartureStaticsToAdd, ...arrivalDepartureStaticCollection];
    }
    return arrivalDepartureStaticCollection;
  }

  protected convertDateFromClient<T extends IArrivalDepartureStatic | NewArrivalDepartureStatic | PartialUpdateArrivalDepartureStatic>(
    arrivalDepartureStatic: T,
  ): RestOf<T> {
    return {
      ...arrivalDepartureStatic,
      date: arrivalDepartureStatic.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restArrivalDepartureStatic: RestArrivalDepartureStatic): IArrivalDepartureStatic {
    return {
      ...restArrivalDepartureStatic,
      date: restArrivalDepartureStatic.date ? dayjs(restArrivalDepartureStatic.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestArrivalDepartureStatic>): HttpResponse<IArrivalDepartureStatic> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestArrivalDepartureStatic[]>): HttpResponse<IArrivalDepartureStatic[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
