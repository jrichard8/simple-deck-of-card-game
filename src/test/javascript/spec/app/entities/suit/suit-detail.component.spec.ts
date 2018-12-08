/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimpleDeckOfCardGameTestModule } from '../../../test.module';
import { SuitDetailComponent } from 'app/entities/suit/suit-detail.component';
import { Suit } from 'app/shared/model/suit.model';

describe('Component Tests', () => {
    describe('Suit Management Detail Component', () => {
        let comp: SuitDetailComponent;
        let fixture: ComponentFixture<SuitDetailComponent>;
        const route = ({ data: of({ suit: new Suit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SimpleDeckOfCardGameTestModule],
                declarations: [SuitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SuitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SuitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.suit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
