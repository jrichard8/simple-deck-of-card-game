import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from './player.service';
import { IGame } from 'app/shared/model/game.model';
import { GameService } from 'app/entities/game';
import { IHand } from 'app/shared/model/hand.model';
import { HandService } from 'app/entities/hand';

@Component({
    selector: 'jhi-player-update',
    templateUrl: './player-update.component.html'
})
export class PlayerUpdateComponent implements OnInit {
    player: IPlayer;
    isSaving: boolean;

    games: IGame[];

    hands: IHand[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private playerService: PlayerService,
        private gameService: GameService,
        private handService: HandService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ player }) => {
            this.player = player;
        });
        this.gameService.query().subscribe(
            (res: HttpResponse<IGame[]>) => {
                this.games = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.handService.query({ filter: 'player-is-null' }).subscribe(
            (res: HttpResponse<IHand[]>) => {
                if (!this.player.hand || !this.player.hand.id) {
                    this.hands = res.body;
                } else {
                    this.handService.find(this.player.hand.id).subscribe(
                        (subRes: HttpResponse<IHand>) => {
                            this.hands = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.player.id !== undefined) {
            this.subscribeToSaveResponse(this.playerService.update(this.player));
        } else {
            this.subscribeToSaveResponse(this.playerService.create(this.player));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlayer>>) {
        result.subscribe((res: HttpResponse<IPlayer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHandById(index: number, item: IHand) {
        return item.id;
    }
}
