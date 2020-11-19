import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    SysActionUtilisateurService,
    SysActionUtilisateurPopupService,
    SysActionUtilisateurComponent,
    SysActionUtilisateurDetailComponent,
    SysActionUtilisateurDialogComponent,
    SysActionUtilisateurPopupComponent,
    SysActionUtilisateurDeletePopupComponent,
    SysActionUtilisateurDeleteDialogComponent,
    sysActionUtilisateurRoute,
    sysActionUtilisateurPopupRoute,
    SysActionUtilisateurResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...sysActionUtilisateurRoute,
    ...sysActionUtilisateurPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SysActionUtilisateurComponent,
        SysActionUtilisateurDetailComponent,
        SysActionUtilisateurDialogComponent,
        SysActionUtilisateurDeleteDialogComponent,
        SysActionUtilisateurPopupComponent,
        SysActionUtilisateurDeletePopupComponent,
    ],
    entryComponents: [
        SysActionUtilisateurComponent,
        SysActionUtilisateurDialogComponent,
        SysActionUtilisateurPopupComponent,
        SysActionUtilisateurDeleteDialogComponent,
        SysActionUtilisateurDeletePopupComponent,
    ],
    providers: [
        SysActionUtilisateurService,
        SysActionUtilisateurPopupService,
        SysActionUtilisateurResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumSysActionUtilisateurModule {}
