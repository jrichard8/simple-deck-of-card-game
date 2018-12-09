import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDeck } from 'app/shared/model/deck.model';
import { Principal } from 'app/core';
import { DeckService } from './deck.service';

@Component({
    selector: 'jhi-deck',
    templateUrl: './deck.component.html'
})
export class DeckComponent implements OnInit, OnDestroy {
    decks: IDeck[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deckService: DeckService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.deckService.query().subscribe(
            (res: HttpResponse<IDeck[]>) => {
                this.decks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDecks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDeck) {
        return item.id;
    }

    registerChangeInDecks() {
        this.eventSubscriber = this.eventManager.subscribe('deckListModification', response => this.loadAll());
    }



    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
