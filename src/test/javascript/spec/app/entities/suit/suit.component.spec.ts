/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SimpleDeckOfCardGameTestModule } from '../../../test.module';
import { SuitComponent } from 'app/entities/suit/suit.component';
import { SuitService } from 'app/entities/suit/suit.service';
import { Suit } from 'app/shared/model/suit.model';

describe('Component Tests', () => {
    describe('Suit Management Component', () => {
        let comp: SuitComponent;
        let fixture: ComponentFixture<SuitComponent>;
        let service: SuitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SimpleDeckOfCardGameTestModule],
                declarations: [SuitComponent],
                providers: []
            })
                .overrideTemplate(SuitComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SuitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SuitService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Suit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.suits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
