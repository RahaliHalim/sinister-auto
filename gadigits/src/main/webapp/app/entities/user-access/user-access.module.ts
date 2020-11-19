import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumAdminModule } from '../../admin/admin.module';
import {
    UserAccessService,
    UserAccessPopupService,
    UserAccessComponent,
    UserAccessDetailComponent,
    UserAccessDialogComponent,
    UserAccessPopupComponent,
    UserAccessDeletePopupComponent,
    UserAccessDeleteDialogComponent,
    userAccessRoute,
    userAccessPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userAccessRoute,
    ...userAccessPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserAccessComponent,
        UserAccessDetailComponent,
        UserAccessDialogComponent,
        UserAccessDeleteDialogComponent,
        UserAccessPopupComponent,
        UserAccessDeletePopupComponent,
    ],
    entryComponents: [
        UserAccessComponent,
        UserAccessDialogComponent,
        UserAccessPopupComponent,
        UserAccessDeleteDialogComponent,
        UserAccessDeletePopupComponent,
    ],
    providers: [
        UserAccessService,
        UserAccessPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumUserAccessModule {}
