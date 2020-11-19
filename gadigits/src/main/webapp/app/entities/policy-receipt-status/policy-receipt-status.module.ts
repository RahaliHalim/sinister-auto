import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PolicyReceiptStatusService,
    PolicyReceiptStatusPopupService,
    PolicyReceiptStatusComponent,
    PolicyReceiptStatusDetailComponent,
    PolicyReceiptStatusDialogComponent,
    PolicyReceiptStatusPopupComponent,
    PolicyReceiptStatusDeletePopupComponent,
    PolicyReceiptStatusDeleteDialogComponent,
    policyReceiptStatusRoute,
    policyReceiptStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...policyReceiptStatusRoute,
    ...policyReceiptStatusPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PolicyReceiptStatusComponent,
        PolicyReceiptStatusDetailComponent,
        PolicyReceiptStatusDialogComponent,
        PolicyReceiptStatusDeleteDialogComponent,
        PolicyReceiptStatusPopupComponent,
        PolicyReceiptStatusDeletePopupComponent,
    ],
    entryComponents: [
        PolicyReceiptStatusComponent,
        PolicyReceiptStatusDialogComponent,
        PolicyReceiptStatusPopupComponent,
        PolicyReceiptStatusDeleteDialogComponent,
        PolicyReceiptStatusDeletePopupComponent,
    ],
    providers: [
        PolicyReceiptStatusService,
        PolicyReceiptStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPolicyReceiptStatusModule {}
