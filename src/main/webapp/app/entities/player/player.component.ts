import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlayer } from 'app/shared/model/player.model';
import { Principal } from 'app/core';
import { PlayerService } from './player.service';

@Component({
    selector: 'jhi-player',
    templateUrl: './player.component.html'
})
export class PlayerComponent implements OnInit, OnDestroy {
    players: IPlayer[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private playerService: PlayerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.playerService.query().subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                this.players = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPlayers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPlayer) {
        return item.id;
    }

    registerChangeInPlayers() {
        this.eventSubscriber = this.eventManager.subscribe('playerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
