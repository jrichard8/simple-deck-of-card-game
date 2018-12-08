import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IHand } from 'app/shared/model/hand.model';
import { HandService } from './hand.service';

@Component({
    selector: 'jhi-hand-update',
    templateUrl: './hand-update.component.html'
})
export class HandUpdateComponent implements OnInit {
    hand: IHand;
    isSaving: boolean;

    constructor(private handService: HandService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hand }) => {
            this.hand = hand;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hand.id !== undefined) {
            this.subscribeToSaveResponse(this.handService.update(this.hand));
        } else {
            this.subscribeToSaveResponse(this.handService.create(this.hand));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHand>>) {
        result.subscribe((res: HttpResponse<IHand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
