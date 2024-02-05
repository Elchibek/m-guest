import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGuest } from '../model/guest.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../guest.test-samples';

import { GuestService, RestGuest } from './guest.service';

const requireRestSample: RestGuest = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
};

describe('Guest Service', () => {
  let service: GuestService;
  let httpMock: HttpTestingController;
  let expectedResult: IGuest | IGuest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GuestService);
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

    it('should create a Guest', () => {
      const guest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(guest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Guest', () => {
      const guest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(guest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Guest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Guest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Guest', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGuestToCollectionIfMissing', () => {
      it('should add a Guest to an empty array', () => {
        const guest: IGuest = sampleWithRequiredData;
        expectedResult = service.addGuestToCollectionIfMissing([], guest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guest);
      });

      it('should not add a Guest to an array that contains it', () => {
        const guest: IGuest = sampleWithRequiredData;
        const guestCollection: IGuest[] = [
          {
            ...guest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGuestToCollectionIfMissing(guestCollection, guest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Guest to an array that doesn't contain it", () => {
        const guest: IGuest = sampleWithRequiredData;
        const guestCollection: IGuest[] = [sampleWithPartialData];
        expectedResult = service.addGuestToCollectionIfMissing(guestCollection, guest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guest);
      });

      it('should add only unique Guest to an array', () => {
        const guestArray: IGuest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const guestCollection: IGuest[] = [sampleWithRequiredData];
        expectedResult = service.addGuestToCollectionIfMissing(guestCollection, ...guestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const guest: IGuest = sampleWithRequiredData;
        const guest2: IGuest = sampleWithPartialData;
        expectedResult = service.addGuestToCollectionIfMissing([], guest, guest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guest);
        expect(expectedResult).toContain(guest2);
      });

      it('should accept null and undefined values', () => {
        const guest: IGuest = sampleWithRequiredData;
        expectedResult = service.addGuestToCollectionIfMissing([], null, guest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guest);
      });

      it('should return initial array if no Guest is added', () => {
        const guestCollection: IGuest[] = [sampleWithRequiredData];
        expectedResult = service.addGuestToCollectionIfMissing(guestCollection, undefined, null);
        expect(expectedResult).toEqual(guestCollection);
      });
    });

    describe('compareGuest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGuest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareGuest(entity1, entity2);
        const compareResult2 = service.compareGuest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareGuest(entity1, entity2);
        const compareResult2 = service.compareGuest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareGuest(entity1, entity2);
        const compareResult2 = service.compareGuest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
