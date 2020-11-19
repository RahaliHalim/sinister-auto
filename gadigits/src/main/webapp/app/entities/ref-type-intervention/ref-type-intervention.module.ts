import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefTypeInterventionService,
    RefTypeInterventionPopupService,
    RefTypeInterventionComponent,
    RefTypeInterventionDetailComponent,
    RefTypeInterventionDialogComponent,
    RefTypeInterventionPopupComponent,
    RefTypeInterventionDeletePopupComponent,
    RefTypeInterventionDeleteDialogComponent,
    refTypeInterventionRoute,
    refTypeInterventionPopupRoute,
    RefTypeInterventionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refTypeInterventionRoute,
    ...refTypeInterventionPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefTypeInterventionComponent,
        RefTypeInterventionDetailComponent,
        RefTypeInterventionDialogComponent,
        RefTypeInterventionDeleteDialogComponent,
        RefTypeInterventionPopupComponent,
        RefTypeInterventionDeletePopupComponent,
    ],
    entryComponents: [
        RefTypeInterventionComponent,
        RefTypeInterventionDialogComponent,
        RefTypeInterventionPopupComponent,
        RefTypeInterventionDeleteDialogComponent,
        RefTypeInterventionDeletePopupComponent,
    ],
    providers: [
        RefTypeInterventionService,
        RefTypeInterventionPopupService,
        RefTypeInterventionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefTypeInterventionModule {}
