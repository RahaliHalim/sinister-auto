import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PolicyTypeService,
    PolicyTypePopupService,
    PolicyTypeComponent,
    PolicyTypeDetailComponent,
    PolicyTypeDialogComponent,
    PolicyTypePopupComponent,
    PolicyTypeDeletePopupComponent,
    PolicyTypeDeleteDialogComponent,
    policyTypeRoute,
    policyTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...policyTypeRoute,
    ...policyTypePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PolicyTypeComponent,
        PolicyTypeDetailComponent,
        PolicyTypeDialogComponent,
        PolicyTypeDeleteDialogComponent,
        PolicyTypePopupComponent,
        PolicyTypeDeletePopupComponent,
    ],
    entryComponents: [
        PolicyTypeComponent,
        PolicyTypeDialogComponent,
        PolicyTypePopupComponent,
        PolicyTypeDeleteDialogComponent,
        PolicyTypeDeletePopupComponent,
    ],
    providers: [
        PolicyTypeService,
        PolicyTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPolicyTypeModule {}
