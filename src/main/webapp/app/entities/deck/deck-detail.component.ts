import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeck } from 'app/shared/model/deck.model';

@Component({
    selector: 'jhi-deck-detail',
    templateUrl: './deck-detail.component.html'
})
export class DeckDetailComponent implements OnInit {
    deck: IDeck;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deck }) => {
            this.deck = deck;
        });
    }

    previousState() {
        window.history.back();
    }
}
