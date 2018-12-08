/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SimpleDeckOfCardGameTestModule } from '../../../test.module';
import { DeckComponent } from 'app/entities/deck/deck.component';
import { DeckService } from 'app/entities/deck/deck.service';
import { Deck } from 'app/shared/model/deck.model';

describe('Component Tests', () => {
    describe('Deck Management Component', () => {
        let comp: DeckComponent;
        let fixture: ComponentFixture<DeckComponent>;
        let service: DeckService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SimpleDeckOfCardGameTestModule],
                declarations: [DeckComponent],
                providers: []
            })
                .overrideTemplate(DeckComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeckComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeckService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Deck(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.decks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
