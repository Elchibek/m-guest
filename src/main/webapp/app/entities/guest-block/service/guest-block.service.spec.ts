import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGuestBlock } from '../guest-block.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../guest-block.test-samples';

import { GuestBlockService } from './guest-block.service';

const requireRestSample: IGuestBlock = {
  ...sampleWithRequiredData,
};

describe('GuestBlock Service', () => {
  let service: GuestBlockService;
  let httpMock: HttpTestingController;
  let expectedResult: IGuestBlock | IGuestBlock[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GuestBlockService);
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

    it('should create a GuestBlock', () => {
      const guestBlock = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(guestBlock).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GuestBlock', () => {
      const guestBlock = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(guestBlock).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GuestBlock', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GuestBlock', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GuestBlock', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGuestBlockToCollectionIfMissing', () => {
      it('should add a GuestBlock to an empty array', () => {
        const guestBlock: IGuestBlock = sampleWithRequiredData;
        expectedResult = service.addGuestBlockToCollectionIfMissing([], guestBlock);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestBlock);
      });

      it('should not add a GuestBlock to an array that contains it', () => {
        const guestBlock: IGuestBlock = sampleWithRequiredData;
        const guestBlockCollection: IGuestBlock[] = [
          {
            ...guestBlock,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGuestBlockToCollectionIfMissing(guestBlockCollection, guestBlock);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GuestBlock to an array that doesn't contain it", () => {
        const guestBlock: IGuestBlock = sampleWithRequiredData;
        const guestBlockCollection: IGuestBlock[] = [sampleWithPartialData];
        expectedResult = service.addGuestBlockToCollectionIfMissing(guestBlockCollection, guestBlock);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestBlock);
      });

      it('should add only unique GuestBlock to an array', () => {
        const guestBlockArray: IGuestBlock[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const guestBlockCollection: IGuestBlock[] = [sampleWithRequiredData];
        expectedResult = service.addGuestBlockToCollectionIfMissing(guestBlockCollection, ...guestBlockArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const guestBlock: IGuestBlock = sampleWithRequiredData;
        const guestBlock2: IGuestBlock = sampleWithPartialData;
        expectedResult = service.addGuestBlockToCollectionIfMissing([], guestBlock, guestBlock2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(guestBlock);
        expect(expectedResult).toContain(guestBlock2);
      });

      it('should accept null and undefined values', () => {
        const guestBlock: IGuestBlock = sampleWithRequiredData;
        expectedResult = service.addGuestBlockToCollectionIfMissing([], null, guestBlock, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(guestBlock);
      });

      it('should return initial array if no GuestBlock is added', () => {
        const guestBlockCollection: IGuestBlock[] = [sampleWithRequiredData];
        expectedResult = service.addGuestBlockToCollectionIfMissing(guestBlockCollection, undefined, null);
        expect(expectedResult).toEqual(guestBlockCollection);
      });
    });

    describe('compareGuestBlock', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGuestBlock(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareGuestBlock(entity1, entity2);
        const compareResult2 = service.compareGuestBlock(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareGuestBlock(entity1, entity2);
        const compareResult2 = service.compareGuestBlock(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareGuestBlock(entity1, entity2);
        const compareResult2 = service.compareGuestBlock(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
