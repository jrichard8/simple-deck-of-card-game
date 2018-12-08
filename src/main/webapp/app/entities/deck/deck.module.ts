import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleDeckOfCardGameSharedModule } from 'app/shared';
import {
    DeckComponent,
    DeckDetailComponent,
    DeckUpdateComponent,
    DeckDeletePopupComponent,
    DeckDeleteDialogComponent,
    deckRoute,
    deckPopupRoute
} from './';

const ENTITY_STATES = [...deckRoute, ...deckPopupRoute];

@NgModule({
    imports: [SimpleDeckOfCardGameSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DeckComponent, DeckDetailComponent, DeckUpdateComponent, DeckDeleteDialogComponent, DeckDeletePopupComponent],
    entryComponents: [DeckComponent, DeckUpdateComponent, DeckDeleteDialogComponent, DeckDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SimpleDeckOfCardGameDeckModule {}
