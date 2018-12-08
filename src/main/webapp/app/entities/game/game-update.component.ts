import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGame } from 'app/shared/model/game.model';
import { GameService } from './game.service';

@Component({
    selector: 'jhi-game-update',
    templateUrl: './game-update.component.html'
})
export class GameUpdateComponent implements OnInit {
    game: IGame;
    isSaving: boolean;

    constructor(private gameService: GameService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ game }) => {
            this.game = game;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.game.id !== undefined) {
            this.subscribeToSaveResponse(this.gameService.update(this.game));
        } else {
            this.subscribeToSaveResponse(this.gameService.create(this.game));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>) {
        result.subscribe((res: HttpResponse<IGame>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
