import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DeckService } from './deck.service';
import { IDeck } from 'app/shared/model/deck.model';

@Component({
    selector: 'jhi-deck-detail',
    templateUrl: './deck-detail.component.html'
})
export class DeckDetailComponent implements OnInit {
    deck: IDeck;
    suitData: Object;

    constructor(private activatedRoute: ActivatedRoute,
                private deckService: DeckService,) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deck }) => {
            this.deck = deck;
        });
        this.cardLeftBySuit(this.deck.id);
    }

    previousState() {
        window.history.back();
    }

    cardLeftBySuit(id: number) {
        this.suitData =  this.deckService.cardLeftBySuit(id);
    }
}
