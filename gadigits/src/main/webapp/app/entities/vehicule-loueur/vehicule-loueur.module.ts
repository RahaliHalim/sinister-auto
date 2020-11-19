import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    VehiculeLoueurService,
    VehiculeLoueurPopupService,
    VehiculeLoueurComponent,
    VehiculeLoueurDetailComponent,
    VehiculeLoueurDialogComponent,
    VehiculeLoueurPopupComponent,
    VehiculeLoueurDeletePopupComponent,
    VehiculeLoueurDeleteDialogComponent,
    vehiculeLoueurRoute,
    vehiculeLoueurPopupRoute,
} from './';

const ENTITY_STATES = [
    ...vehiculeLoueurRoute,
    ...vehiculeLoueurPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VehiculeLoueurComponent,
        VehiculeLoueurDetailComponent,
        VehiculeLoueurDialogComponent,
        VehiculeLoueurDeleteDialogComponent,
        VehiculeLoueurPopupComponent,
        VehiculeLoueurDeletePopupComponent,
    ],
    entryComponents: [
        VehiculeLoueurComponent,
        VehiculeLoueurDialogComponent,
        VehiculeLoueurPopupComponent,
        VehiculeLoueurDeleteDialogComponent,
        VehiculeLoueurDeletePopupComponent,
    ],
    providers: [
        VehiculeLoueurService,
        VehiculeLoueurPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVehiculeLoueurModule {}
