import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    FournisseurService,
    FournisseurPopupService,
    FournisseurComponent,
    FournisseurDetailComponent,
    FournisseurDialogComponent,
    FournisseurPopupComponent,
    FournisseurDeletePopupComponent,
    FournisseurDeleteDialogComponent,
    fournisseurRoute,
    fournisseurPopupRoute,
    FournisseurResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fournisseurRoute,
    ...fournisseurPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FournisseurComponent,
        FournisseurDetailComponent,
        FournisseurDialogComponent,
        FournisseurDeleteDialogComponent,
        FournisseurPopupComponent,
        FournisseurDeletePopupComponent,
    ],
    entryComponents: [
        FournisseurComponent,
        FournisseurDialogComponent,
        FournisseurPopupComponent,
        FournisseurDeleteDialogComponent,
        FournisseurDeletePopupComponent,
    ],
    providers: [
        FournisseurService,
        FournisseurPopupService,
        FournisseurResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumFournisseurModule {}
