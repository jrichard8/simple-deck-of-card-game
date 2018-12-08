import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICard } from 'app/shared/model/card.model';
import { CardService } from './card.service';
import { ISuit } from 'app/shared/model/suit.model';
import { SuitService } from 'app/entities/suit';
import { IHand } from 'app/shared/model/hand.model';
import { HandService } from 'app/entities/hand';

@Component({
    selector: 'jhi-card-update',
    templateUrl: './card-update.component.html'
})
export class CardUpdateComponent implements OnInit {
    card: ICard;
    isSaving: boolean;

    suits: ISuit[];

    hands: IHand[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cardService: CardService,
        private suitService: SuitService,
        private handService: HandService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ card }) => {
            this.card = card;
        });
        this.suitService.query().subscribe(
            (res: HttpResponse<ISuit[]>) => {
                this.suits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.handService.query().subscribe(
            (res: HttpResponse<IHand[]>) => {
                this.hands = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.card.id !== undefined) {
            this.subscribeToSaveResponse(this.cardService.update(this.card));
        } else {
            this.subscribeToSaveResponse(this.cardService.create(this.card));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICard>>) {
        result.subscribe((res: HttpResponse<ICard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSuitById(index: number, item: ISuit) {
        return item.id;
    }

    trackHandById(index: number, item: IHand) {
        return item.id;
    }
}
