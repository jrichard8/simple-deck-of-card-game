import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SimpleDeckOfCardGameGameModule } from './game/game.module';
import { SimpleDeckOfCardGameDeckModule } from './deck/deck.module';
import { SimpleDeckOfCardGameSuitModule } from './suit/suit.module';
import { SimpleDeckOfCardGameCardModule } from './card/card.module';
import { SimpleDeckOfCardGamePlayerModule } from './player/player.module';
import { SimpleDeckOfCardGameHandModule } from './hand/hand.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SimpleDeckOfCardGameGameModule,
        SimpleDeckOfCardGameDeckModule,
        SimpleDeckOfCardGameSuitModule,
        SimpleDeckOfCardGameCardModule,
        SimpleDeckOfCardGamePlayerModule,
        SimpleDeckOfCardGameHandModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SimpleDeckOfCardGameEntityModule {}
