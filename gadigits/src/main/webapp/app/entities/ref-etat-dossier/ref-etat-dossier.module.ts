import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefEtatDossierService,
    RefEtatDossierPopupService,
    RefEtatDossierComponent,
    RefEtatDossierDetailComponent,
    RefEtatDossierDialogComponent,
    RefEtatDossierPopupComponent,
    RefEtatDossierDeletePopupComponent,
    RefEtatDossierDeleteDialogComponent,
    refEtatDossierRoute,
    refEtatDossierPopupRoute,
    RefEtatDossierResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refEtatDossierRoute,
    ...refEtatDossierPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefEtatDossierComponent,
        RefEtatDossierDetailComponent,
        RefEtatDossierDialogComponent,
        RefEtatDossierDeleteDialogComponent,
        RefEtatDossierPopupComponent,
        RefEtatDossierDeletePopupComponent,
    ],
    entryComponents: [
        RefEtatDossierComponent,
        RefEtatDossierDialogComponent,
        RefEtatDossierPopupComponent,
        RefEtatDossierDeleteDialogComponent,
        RefEtatDossierDeletePopupComponent,
    ],
    providers: [
        RefEtatDossierService,
        RefEtatDossierPopupService,
        RefEtatDossierResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefEtatDossierModule {}
