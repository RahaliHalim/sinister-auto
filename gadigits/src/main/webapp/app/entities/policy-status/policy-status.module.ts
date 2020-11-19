import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PolicyStatusService,
    PolicyStatusPopupService,
    PolicyStatusComponent,
    PolicyStatusDetailComponent,
    PolicyStatusDialogComponent,
    PolicyStatusPopupComponent,
    PolicyStatusDeletePopupComponent,
    PolicyStatusDeleteDialogComponent,
    policyStatusRoute,
    policyStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...policyStatusRoute,
    ...policyStatusPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PolicyStatusComponent,
        PolicyStatusDetailComponent,
        PolicyStatusDialogComponent,
        PolicyStatusDeleteDialogComponent,
        PolicyStatusPopupComponent,
        PolicyStatusDeletePopupComponent,
    ],
    entryComponents: [
        PolicyStatusComponent,
        PolicyStatusDialogComponent,
        PolicyStatusPopupComponent,
        PolicyStatusDeleteDialogComponent,
        PolicyStatusDeletePopupComponent,
    ],
    providers: [
        PolicyStatusService,
        PolicyStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPolicyStatusModule {}
