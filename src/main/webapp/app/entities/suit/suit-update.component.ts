import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISuit } from 'app/shared/model/suit.model';
import { SuitService } from './suit.service';
import { IDeck } from 'app/shared/model/deck.model';
import { DeckService } from 'app/entities/deck';

@Component({
    selector: 'jhi-suit-update',
    templateUrl: './suit-update.component.html'
})
export class SuitUpdateComponent implements OnInit {
    suit: ISuit;
    isSaving: boolean;

    decks: IDeck[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private suitService: SuitService,
        private deckService: DeckService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ suit }) => {
            this.suit = suit;
        });
        this.deckService.query().subscribe(
            (res: HttpResponse<IDeck[]>) => {
                this.decks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.suit.id !== undefined) {
            this.subscribeToSaveResponse(this.suitService.update(this.suit));
        } else {
            this.subscribeToSaveResponse(this.suitService.create(this.suit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISuit>>) {
        result.subscribe((res: HttpResponse<ISuit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDeckById(index: number, item: IDeck) {
        return item.id;
    }
}
