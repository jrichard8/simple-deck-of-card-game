import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuit } from 'app/shared/model/suit.model';

@Component({
    selector: 'jhi-suit-detail',
    templateUrl: './suit-detail.component.html'
})
export class SuitDetailComponent implements OnInit {
    suit: ISuit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ suit }) => {
            this.suit = suit;
        });
    }

    previousState() {
        window.history.back();
    }
}
