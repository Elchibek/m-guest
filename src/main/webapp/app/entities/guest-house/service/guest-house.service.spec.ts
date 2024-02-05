import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGuestHouse } from '../guest-house.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../guest-house.test-samples';

import { GuestHouseService } from './guest-house.service';

const requireRestSample: IGuestHouse = {
  ...sampleWithRequiredData,
};

describe('GuestHouse Service', () => {
  let service: GuestHouseService;
  let httpMock: HttpTestingController;
  let expectedResult: IGuestHouse | IGuestHouse[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GuestHouseService);
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

    it('should create a GuestHouse', () => {
      const guestHouse = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(guestHouse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GuestHouse', () => {
      const guestHouse = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(guestHouse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GuestHouse', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GuestHouse', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GuestHouse', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGuestHouseToCollectionIfMissing', () => {
      it('should add a GuestHouse to an empty array', () => {
        const guestHouse: IGuestHouse = sampleWithRequiredData;
        expectedResult = service.addGuestHouseToCollectionIfMissing([], guestHouse);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestHouse);
      });

      it('should not add a GuestHouse to an array that contains it', () => {
        const guestHouse: IGuestHouse = sampleWithRequiredData;
        const guestHouseCollection: IGuestHouse[] = [
          {
            ...guestHouse,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGuestHouseToCollectionIfMissing(guestHouseCollection, guestHouse);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GuestHouse to an array that doesn't contain it", () => {
        const guestHouse: IGuestHouse = sampleWithRequiredData;
        const guestHouseCollection: IGuestHouse[] = [sampleWithPartialData];
        expectedResult = service.addGuestHouseToCollectionIfMissing(guestHouseCollection, guestHouse);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestHouse);
      });

      it('should add only unique GuestHouse to an array', () => {
        const guestHouseArray: IGuestHouse[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const guestHouseCollection: IGuestHouse[] = [sampleWithRequiredData];
        expectedResult = service.addGuestHouseToCollectionIfMissing(guestHouseCollection, ...guestHouseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const guestHouse: IGuestHouse = sampleWithRequiredData;
        const guestHouse2: IGuestHouse = sampleWithPartialData;
        expectedResult = service.addGuestHouseToCollectionIfMissing([], guestHouse, guestHouse2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestHouse);
        expect(expectedResult).toContain(guestHouse2);
      });

      it('should accept null and undefined values', () => {
        const guestHouse: IGuestHouse = sampleWithRequiredData;
        expectedResult = service.addGuestHouseToCollectionIfMissing([], null, guestHouse, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestHouse);
      });

      it('should return initial array if no GuestHouse is added', () => {
        const guestHouseCollection: IGuestHouse[] = [sampleWithRequiredData];
        expectedResult = service.addGuestHouseToCollectionIfMissing(guestHouseCollection, undefined, null);
        expect(expectedResult).toEqual(guestHouseCollection);
      });
    });

    describe('compareGuestHouse', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGuestHouse(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareGuestHouse(entity1, entity2);
        const compareResult2 = service.compareGuestHouse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareGuestHouse(entity1, entity2);
        const compareResult2 = service.compareGuestHouse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareGuestHouse(entity1, entity2);
        const compareResult2 = service.compareGuestHouse(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
