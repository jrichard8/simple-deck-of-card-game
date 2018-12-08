import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleDeckOfCardGameSharedModule } from 'app/shared';
import {
    HandComponent,
    HandDetailComponent,
    HandUpdateComponent,
    HandDeletePopupComponent,
    HandDeleteDialogComponent,
    handRoute,
    handPopupRoute
} from './';

const ENTITY_STATES = [...handRoute, ...handPopupRoute];

@NgModule({
    imports: [SimpleDeckOfCardGameSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [HandComponent, HandDetailComponent, HandUpdateComponent, HandDeleteDialogComponent, HandDeletePopupComponent],
    entryComponents: [HandComponent, HandUpdateComponent, HandDeleteDialogComponent, HandDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SimpleDeckOfCardGameHandModule {}
