import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHand } from 'app/shared/model/hand.model';
import { Principal } from 'app/core';
import { HandService } from './hand.service';

@Component({
    selector: 'jhi-hand',
    templateUrl: './hand.component.html'
})
export class HandComponent implements OnInit, OnDestroy {
    hands: IHand[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private handService: HandService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.handService.query().subscribe(
            (res: HttpResponse<IHand[]>) => {
                this.hands = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHands();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHand) {
        return item.id;
    }

    registerChangeInHands() {
        this.eventSubscriber = this.eventManager.subscribe('handListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
