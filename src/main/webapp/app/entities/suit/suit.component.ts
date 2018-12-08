import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISuit } from 'app/shared/model/suit.model';
import { Principal } from 'app/core';
import { SuitService } from './suit.service';

@Component({
    selector: 'jhi-suit',
    templateUrl: './suit.component.html'
})
export class SuitComponent implements OnInit, OnDestroy {
    suits: ISuit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private suitService: SuitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.suitService.query().subscribe(
            (res: HttpResponse<ISuit[]>) => {
                this.suits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSuits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISuit) {
        return item.id;
    }

    registerChangeInSuits() {
        this.eventSubscriber = this.eventManager.subscribe('suitListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
