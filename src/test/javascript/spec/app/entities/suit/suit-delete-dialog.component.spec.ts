/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SimpleDeckOfCardGameTestModule } from '../../../test.module';
import { SuitDeleteDialogComponent } from 'app/entities/suit/suit-delete-dialog.component';
import { SuitService } from 'app/entities/suit/suit.service';

describe('Component Tests', () => {
    describe('Suit Management Delete Component', () => {
        let comp: SuitDeleteDialogComponent;
        let fixture: ComponentFixture<SuitDeleteDialogComponent>;
        let service: SuitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SimpleDeckOfCardGameTestModule],
                declarations: [SuitDeleteDialogComponent]
            })
                .overrideTemplate(SuitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SuitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SuitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
