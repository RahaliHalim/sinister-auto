import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ProfileAccessService,
    ProfileAccessPopupService,
    ProfileAccessComponent,
    ProfileAccessDetailComponent,
    ProfileAccessDialogComponent,
    ProfileAccessPopupComponent,
    ProfileAccessDeletePopupComponent,
    ProfileAccessDeleteDialogComponent,
    profileAccessRoute,
    profileAccessPopupRoute,
} from './';

const ENTITY_STATES = [
    ...profileAccessRoute,
    ...profileAccessPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProfileAccessComponent,
        ProfileAccessDetailComponent,
        ProfileAccessDialogComponent,
        ProfileAccessDeleteDialogComponent,
        ProfileAccessPopupComponent,
        ProfileAccessDeletePopupComponent,
    ],
    entryComponents: [
        ProfileAccessComponent,
        ProfileAccessDialogComponent,
        ProfileAccessPopupComponent,
        ProfileAccessDeleteDialogComponent,
        ProfileAccessDeletePopupComponent,
    ],
    providers: [
        ProfileAccessService,
        ProfileAccessPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumProfileAccessModule {}
