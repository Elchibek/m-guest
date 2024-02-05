import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGuestFrom } from '../guest-from.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../guest-from.test-samples';

import { GuestFromService } from './guest-from.service';

const requireRestSample: IGuestFrom = {
  ...sampleWithRequiredData,
};

describe('GuestFrom Service', () => {
  let service: GuestFromService;
  let httpMock: HttpTestingController;
  let expectedResult: IGuestFrom | IGuestFrom[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GuestFromService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a GuestFrom', () => {
      const guestFrom = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(guestFrom).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GuestFrom', () => {
      const guestFrom = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(guestFrom).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GuestFrom', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GuestFrom', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GuestFrom', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGuestFromToCollectionIfMissing', () => {
      it('should add a GuestFrom to an empty array', () => {
        const guestFrom: IGuestFrom = sampleWithRequiredData;
        expectedResult = service.addGuestFromToCollectionIfMissing([], guestFrom);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestFrom);
      });

      it('should not add a GuestFrom to an array that contains it', () => {
        const guestFrom: IGuestFrom = sampleWithRequiredData;
        const guestFromCollection: IGuestFrom[] = [
          {
            ...guestFrom,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGuestFromToCollectionIfMissing(guestFromCollection, guestFrom);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GuestFrom to an array that doesn't contain it", () => {
        const guestFrom: IGuestFrom = sampleWithRequiredData;
        const guestFromCollection: IGuestFrom[] = [sampleWithPartialData];
        expectedResult = service.addGuestFromToCollectionIfMissing(guestFromCollection, guestFrom);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestFrom);
      });

      it('should add only unique GuestFrom to an array', () => {
        const guestFromArray: IGuestFrom[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const guestFromCollection: IGuestFrom[] = [sampleWithRequiredData];
        expectedResult = service.addGuestFromToCollectionIfMissing(guestFromCollection, ...guestFromArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const guestFrom: IGuestFrom = sampleWithRequiredData;
        const guestFrom2: IGuestFrom = sampleWithPartialData;
        expectedResult = service.addGuestFromToCollectionIfMissing([], guestFrom, guestFrom2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestFrom);
        expect(expectedResult).toContain(guestFrom2);
      });

      it('should accept null and undefined values', () => {
        const guestFrom: IGuestFrom = sampleWithRequiredData;
        expectedResult = service.addGuestFromToCollectionIfMissing([], null, guestFrom, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestFrom);
      });

      it('should return initial array if no GuestFrom is added', () => {
        const guestFromCollection: IGuestFrom[] = [sampleWithRequiredData];
        expectedResult = service.addGuestFromToCollectionIfMissing(guestFromCollection, undefined, null);
        expect(expectedResult).toEqual(guestFromCollection);
      });
    });

    describe('compareGuestFrom', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGuestFrom(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareGuestFrom(entity1, entity2);
        const compareResult2 = service.compareGuestFrom(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareGuestFrom(entity1, entity2);
        const compareResult2 = service.compareGuestFrom(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareGuestFrom(entity1, entity2);
        const compareResult2 = service.compareGuestFrom(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
