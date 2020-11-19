import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefNatureExpertiseService,
    RefNatureExpertisePopupService,
    RefNatureExpertiseComponent,
    RefNatureExpertiseDetailComponent,
    RefNatureExpertiseDialogComponent,
    RefNatureExpertisePopupComponent,
    RefNatureExpertiseDeletePopupComponent,
    RefNatureExpertiseDeleteDialogComponent,
    refNatureExpertiseRoute,
    refNatureExpertisePopupRoute,
    RefNatureExpertiseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refNatureExpertiseRoute,
    ...refNatureExpertisePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefNatureExpertiseComponent,
        RefNatureExpertiseDetailComponent,
        RefNatureExpertiseDialogComponent,
        RefNatureExpertiseDeleteDialogComponent,
        RefNatureExpertisePopupComponent,
        RefNatureExpertiseDeletePopupComponent,
    ],
    entryComponents: [
        RefNatureExpertiseComponent,
        RefNatureExpertiseDialogComponent,
        RefNatureExpertisePopupComponent,
        RefNatureExpertiseDeleteDialogComponent,
        RefNatureExpertiseDeletePopupComponent,
    ],
    providers: [
        RefNatureExpertiseService,
        RefNatureExpertisePopupService,
        RefNatureExpertiseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefNatureExpertiseModule {}
