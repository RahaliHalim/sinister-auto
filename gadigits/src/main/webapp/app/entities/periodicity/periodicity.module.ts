import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PeriodicityService,
    PeriodicityPopupService,
    PeriodicityComponent,
    PeriodicityDetailComponent,
    PeriodicityDialogComponent,
    PeriodicityPopupComponent,
    PeriodicityDeletePopupComponent,
    PeriodicityDeleteDialogComponent,
    periodicityRoute,
    periodicityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...periodicityRoute,
    ...periodicityPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PeriodicityComponent,
        PeriodicityDetailComponent,
        PeriodicityDialogComponent,
        PeriodicityDeleteDialogComponent,
        PeriodicityPopupComponent,
        PeriodicityDeletePopupComponent,
    ],
    entryComponents: [
        PeriodicityComponent,
        PeriodicityDialogComponent,
        PeriodicityPopupComponent,
        PeriodicityDeleteDialogComponent,
        PeriodicityDeletePopupComponent,
    ],
    providers: [
        PeriodicityService,
        PeriodicityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPeriodicityModule {}
