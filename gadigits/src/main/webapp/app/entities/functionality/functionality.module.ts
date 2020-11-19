import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    FunctionalityService,
    FunctionalityPopupService,
    FunctionalityComponent,
    FunctionalityDetailComponent,
    FunctionalityDialogComponent,
    FunctionalityPopupComponent,
    FunctionalityDeletePopupComponent,
    FunctionalityDeleteDialogComponent,
    functionalityRoute,
    functionalityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...functionalityRoute,
    ...functionalityPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FunctionalityComponent,
        FunctionalityDetailComponent,
        FunctionalityDialogComponent,
        FunctionalityDeleteDialogComponent,
        FunctionalityPopupComponent,
        FunctionalityDeletePopupComponent,
    ],
    entryComponents: [
        FunctionalityComponent,
        FunctionalityDialogComponent,
        FunctionalityPopupComponent,
        FunctionalityDeleteDialogComponent,
        FunctionalityDeletePopupComponent,
    ],
    providers: [
        FunctionalityService,
        FunctionalityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumFunctionalityModule {}
