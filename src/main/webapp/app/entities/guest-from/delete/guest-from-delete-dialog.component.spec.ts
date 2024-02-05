jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GuestFromService } from '../service/guest-from.service';

import { GuestFromDeleteDialogComponent } from './guest-from-delete-dialog.component';

describe('GuestFrom Management Delete Component', () => {
  let comp: GuestFromDeleteDialogComponent;
  let fixture: ComponentFixture<GuestFromDeleteDialogComponent>;
  let service: GuestFromService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, GuestFromDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(GuestFromDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GuestFromDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GuestFromService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
