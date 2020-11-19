import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumAdminModule } from '../../admin/admin.module';
import {
    PolicyHolderService,
    PolicyHolderPopupService,
    PolicyHolderComponent,
    PolicyHolderDetailComponent,
    PolicyHolderDialogComponent,
    PolicyHolderPopupComponent,
    PolicyHolderDeletePopupComponent,
    PolicyHolderDeleteDialogComponent,
    policyHolderRoute,
    policyHolderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...policyHolderRoute,
    ...policyHolderPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PolicyHolderComponent,
        PolicyHolderDetailComponent,
        PolicyHolderDialogComponent,
        PolicyHolderDeleteDialogComponent,
        PolicyHolderPopupComponent,
        PolicyHolderDeletePopupComponent,
    ],
    entryComponents: [
        PolicyHolderComponent,
        PolicyHolderDialogComponent,
        PolicyHolderPopupComponent,
        PolicyHolderDeleteDialogComponent,
        PolicyHolderDeletePopupComponent,
    ],
    providers: [
        PolicyHolderService,
        PolicyHolderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPolicyHolderModule {}
