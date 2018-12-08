import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleDeckOfCardGameSharedModule } from 'app/shared';
import {
    SuitComponent,
    SuitDetailComponent,
    SuitUpdateComponent,
    SuitDeletePopupComponent,
    SuitDeleteDialogComponent,
    suitRoute,
    suitPopupRoute
} from './';

const ENTITY_STATES = [...suitRoute, ...suitPopupRoute];

@NgModule({
    imports: [SimpleDeckOfCardGameSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SuitComponent, SuitDetailComponent, SuitUpdateComponent, SuitDeleteDialogComponent, SuitDeletePopupComponent],
    entryComponents: [SuitComponent, SuitUpdateComponent, SuitDeleteDialogComponent, SuitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SimpleDeckOfCardGameSuitModule {}
