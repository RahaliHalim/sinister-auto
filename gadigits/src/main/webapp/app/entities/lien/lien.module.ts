import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    LienService,
    LienPopupService,
    LienComponent,
    LienDetailComponent,
    LienDialogComponent,
    LienPopupComponent,
    LienDeletePopupComponent,
    LienDeleteDialogComponent,
    lienRoute,
    lienPopupRoute,
    LienResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lienRoute,
    ...lienPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LienComponent,
        LienDetailComponent,
        LienDialogComponent,
        LienDeleteDialogComponent,
        LienPopupComponent,
        LienDeletePopupComponent,
    ],
    entryComponents: [
        LienComponent,
        LienDialogComponent,
        LienPopupComponent,
        LienDeleteDialogComponent,
        LienDeletePopupComponent,
    ],
    providers: [
        LienService,
        LienPopupService,
        LienResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumLienModule {}
