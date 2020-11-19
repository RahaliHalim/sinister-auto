import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumAdminModule } from '../../admin/admin.module';
import {
    UserCelluleService,
    UserCellulePopupService,
    UserCelluleComponent,
    UserCelluleDetailComponent,
    UserCelluleDialogComponent,
    UserCellulePopupComponent,
    UserCelluleDeletePopupComponent,
    UserCelluleDeleteDialogComponent,
    userCelluleRoute,
    userCellulePopupRoute,
    UserCelluleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...userCelluleRoute,
    ...userCellulePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserCelluleComponent,
        UserCelluleDetailComponent,
        UserCelluleDialogComponent,
        UserCelluleDeleteDialogComponent,
        UserCellulePopupComponent,
        UserCelluleDeletePopupComponent,
    ],
    entryComponents: [
        UserCelluleComponent,
        UserCelluleDialogComponent,
        UserCellulePopupComponent,
        UserCelluleDeleteDialogComponent,
        UserCelluleDeletePopupComponent,
    ],
    providers: [
        UserCelluleService,
        UserCellulePopupService,
        UserCelluleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumUserCelluleModule {}
