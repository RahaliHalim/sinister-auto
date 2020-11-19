import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ServiceAssuranceService,
    ServiceAssurancePopupService,
    ServiceAssuranceComponent,
    ServiceAssuranceDetailComponent,
    ServiceAssuranceDialogComponent,
    ServiceAssurancePopupComponent,
    ServiceAssuranceDeletePopupComponent,
    ServiceAssuranceDeleteDialogComponent,
    serviceAssuranceRoute,
    serviceAssurancePopupRoute,
    ServiceAssuranceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...serviceAssuranceRoute,
    ...serviceAssurancePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServiceAssuranceComponent,
        ServiceAssuranceDetailComponent,
        ServiceAssuranceDialogComponent,
        ServiceAssuranceDeleteDialogComponent,
        ServiceAssurancePopupComponent,
        ServiceAssuranceDeletePopupComponent,
    ],
    entryComponents: [
        ServiceAssuranceComponent,
        ServiceAssuranceDialogComponent,
        ServiceAssurancePopupComponent,
        ServiceAssuranceDeleteDialogComponent,
        ServiceAssuranceDeletePopupComponent,
    ],
    providers: [
        ServiceAssuranceService,
        ServiceAssurancePopupService,
        ServiceAssuranceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumServiceAssuranceModule {}
