import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { IGame } from 'app/shared/model/game.model';
import { GameService } from './game.service';
import {PlayerService} from "app/entities/player";
import {IPlayer, Player} from "app/shared/model/player.model";

@Component({
    selector: 'jhi-game-detail',
    templateUrl: './game-detail.component.html'
})
export class GameDetailComponent implements OnInit {
    game: IGame;
    players: IPlayer[];

    constructor(
        private activatedRoute: ActivatedRoute,
        private gameService: GameService,
        private playerService: PlayerService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.loadPlayer();
        this.activatedRoute.data.subscribe(({ game }) => {
            this.game = game;
        });
    }

    previousState() {
        window.history.back();
    }


    loadPlayer() {
        this.playerService.query().subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                this.players = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
