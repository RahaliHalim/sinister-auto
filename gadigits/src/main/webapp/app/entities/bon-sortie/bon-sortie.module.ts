import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    BonSortieService,
    BonSortiePopupService,
    BonSortieComponent,
    BonSortieDetailComponent,
    BonSortieDialogComponent,
    BonSortiePopupComponent,
    BonSortieDeletePopupComponent,
    BonSortieDeleteDialogComponent,
    bonSortieRoute,
    bonSortiePopupRoute,
    BonSortieResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bonSortieRoute,
    ...bonSortiePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BonSortieComponent,
        BonSortieDetailComponent,
        BonSortieDialogComponent,
        BonSortieDeleteDialogComponent,
        BonSortiePopupComponent,
        BonSortieDeletePopupComponent,
    ],
    entryComponents: [
        BonSortieComponent,
        BonSortieDialogComponent,
        BonSortiePopupComponent,
        BonSortieDeleteDialogComponent,
        BonSortieDeletePopupComponent,
    ],
    providers: [
        BonSortieService,
        BonSortiePopupService,
        BonSortieResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumBonSortieModule {}
