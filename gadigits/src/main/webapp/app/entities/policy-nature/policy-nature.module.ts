import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PolicyNatureService,
    PolicyNaturePopupService,
    PolicyNatureComponent,
    PolicyNatureDetailComponent,
    PolicyNatureDialogComponent,
    PolicyNaturePopupComponent,
    PolicyNatureDeletePopupComponent,
    PolicyNatureDeleteDialogComponent,
    policyNatureRoute,
    policyNaturePopupRoute,
} from './';

const ENTITY_STATES = [
    ...policyNatureRoute,
    ...policyNaturePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PolicyNatureComponent,
        PolicyNatureDetailComponent,
        PolicyNatureDialogComponent,
        PolicyNatureDeleteDialogComponent,
        PolicyNaturePopupComponent,
        PolicyNatureDeletePopupComponent,
    ],
    entryComponents: [
        PolicyNatureComponent,
        PolicyNatureDialogComponent,
        PolicyNaturePopupComponent,
        PolicyNatureDeleteDialogComponent,
        PolicyNatureDeletePopupComponent,
    ],
    providers: [
        PolicyNatureService,
        PolicyNaturePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPolicyNatureModule {}
