import { NgModule } from '@angular/core';

import { SimpleDeckOfCardGameSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SimpleDeckOfCardGameSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SimpleDeckOfCardGameSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SimpleDeckOfCardGameSharedCommonModule {}
