import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumPersonneMoraleModule } from '../personne-morale/personne-morale.module';
import {
    ReparateurService,
    ReparateurPopupService,
    ReparateurComponent,
    ReparateurDetailComponent,
    ReparateurDialogComponent,
    ReparateurPopupComponent,
    ReparateurDeletePopupComponent,
    ReparateurDeleteDialogComponent,
    reparateurRoute,
    reparateurPopupRoute,
    ReparateurResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...reparateurRoute,
    ...reparateurPopupRoute,
];
import { DataTablesModule } from 'angular-datatables';
@NgModule({
    imports: [
        DataTablesModule,
        AuxiliumSharedModule,
        AuxiliumPersonneMoraleModule,
        NgMultiSelectDropDownModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReparateurComponent,
        ReparateurDetailComponent,
        ReparateurDialogComponent,
        ReparateurDeleteDialogComponent,
        ReparateurPopupComponent,
        ReparateurDeletePopupComponent,
    ],
    entryComponents: [
        ReparateurComponent,
        ReparateurDialogComponent,
        ReparateurPopupComponent,
        ReparateurDeleteDialogComponent,
        ReparateurDeletePopupComponent,
    ],
    providers: [
        ReparateurService,
        ReparateurPopupService,
        ReparateurResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumReparateurModule {}
