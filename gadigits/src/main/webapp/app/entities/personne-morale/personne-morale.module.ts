import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import { ModuleWithProviders } from '@angular/core';
import {
    PersonneMoraleService,
    PersonneMoralePopupService,
    PersonneMoraleComponent,
    PersonneMoraleDetailComponent,
    PersonneMoraleDialogForDetailComponent,
    ContactPersonneMoralePopupComponent,
    PersonneMoraleDeletePopupComponent,
    PersonneMoraleDeleteDialogComponent,
    personneMoraleRoute,
    personneMoralePopupRoute,
    PersonneMoraleResolvePagingParams,
    ContactPersonneMoraleDialogComponent
} from './';
import { AuxiliumPersonnePhysiqueModule } from '../personne-physique/personne-physique.module';
import { DataTablesModule } from 'angular-datatables';
const ENTITY_STATES = [
    ...personneMoraleRoute,
    ...personneMoralePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumPersonnePhysiqueModule,
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        PersonneMoraleComponent,
        PersonneMoraleDetailComponent,
        PersonneMoraleDialogForDetailComponent,
        PersonneMoraleDeleteDialogComponent,
        PersonneMoraleDeletePopupComponent,
        ContactPersonneMoralePopupComponent,
        ContactPersonneMoraleDialogComponent
    ],
    entryComponents: [
        PersonneMoraleComponent,
        PersonneMoraleDialogForDetailComponent,
        ContactPersonneMoralePopupComponent,
        PersonneMoraleDeleteDialogComponent,
        PersonneMoraleDeletePopupComponent,
        ContactPersonneMoraleDialogComponent
    ],
     exports: [
        PersonneMoraleDialogForDetailComponent
    ],
    providers: [
        PersonneMoraleService,
        PersonneMoralePopupService,
        PersonneMoraleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPersonneMoraleModule {}
