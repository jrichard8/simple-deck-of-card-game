import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDeck } from 'app/shared/model/deck.model';
import { DeckService } from './deck.service';
import { IGame } from 'app/shared/model/game.model';
import { GameService } from 'app/entities/game';

@Component({
    selector: 'jhi-deck-update',
    templateUrl: './deck-update.component.html'
})
export class DeckUpdateComponent implements OnInit {
    deck: IDeck;
    isSaving: boolean;

    games: IGame[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private deckService: DeckService,
        private gameService: GameService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deck }) => {
            this.deck = deck;
        });
        this.gameService.query().subscribe(
            (res: HttpResponse<IGame[]>) => {
                this.games = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deck.id !== undefined) {
            this.subscribeToSaveResponse(this.deckService.update(this.deck));
        } else {
            this.subscribeToSaveResponse(this.deckService.create(this.deck));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeck>>) {
        result.subscribe((res: HttpResponse<IDeck>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGameById(index: number, item: IGame) {
        return item.id;
    }
}
