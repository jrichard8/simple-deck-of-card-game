/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SimpleDeckOfCardGameTestModule } from '../../../test.module';
import { SuitUpdateComponent } from 'app/entities/suit/suit-update.component';
import { SuitService } from 'app/entities/suit/suit.service';
import { Suit } from 'app/shared/model/suit.model';

describe('Component Tests', () => {
    describe('Suit Management Update Component', () => {
        let comp: SuitUpdateComponent;
        let fixture: ComponentFixture<SuitUpdateComponent>;
        let service: SuitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SimpleDeckOfCardGameTestModule],
                declarations: [SuitUpdateComponent]
            })
                .overrideTemplate(SuitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SuitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SuitService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Suit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.suit = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Suit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.suit = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
