import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ObservationService,
    ObservationPopupService,
    ObservationComponent,
    ObservationDetailComponent,
    ObservationDialogComponent,
    ObservationPopupComponent,
    ObservationDeletePopupComponent,
    ObservationDeleteDialogComponent,
    observationRoute,
    observationPopupRoute,
    ObservationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...observationRoute,
    ...observationPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ObservationComponent,
        ObservationDetailComponent,
        ObservationDialogComponent,
        ObservationDeleteDialogComponent,
        ObservationPopupComponent,
        ObservationDeletePopupComponent,
    ],
    entryComponents: [
        ObservationComponent,
        ObservationDialogComponent,
        ObservationPopupComponent,
        ObservationDeleteDialogComponent,
        ObservationDeletePopupComponent,
    ],
    providers: [
        ObservationService,
        ObservationPopupService,
        ObservationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumObservationModule {}
