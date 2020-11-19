import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuxiliumSharedModule } from '../../shared';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import {
    RefRemorqueurService,
    RefRemorqueurPopupService, 
    RefRemorqueurComponent,
    RefRemorqueurDetailComponent,
    RefRemorqueurDialogComponent,
    RefRemorqueurPopupComponent,
    RefRemorqueurDeletePopupComponent,
    RefRemorqueurDeleteDialogComponent,
    refRemorqueurRoute,
    refRemorqueurPopupRoute,
    RefRemorqueurResolvePagingParams,
    RefRemorqueurBloquePopupComponent,
    RefRemorqueurBloqueDialogComponent,
    
} from './';
import { AuxiliumPersonneMoraleModule } from '../personne-morale/personne-morale.module';
import { DataTablesModule } from 'angular-datatables';

const ENTITY_STATES = [
    ...refRemorqueurRoute,
    ...refRemorqueurPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumPersonneMoraleModule,
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule,
        NgMultiSelectDropDownModule.forRoot()
    ],
    declarations: [
        RefRemorqueurComponent,
        RefRemorqueurDetailComponent,
        RefRemorqueurDialogComponent,
        RefRemorqueurDeleteDialogComponent,
        RefRemorqueurPopupComponent,
        RefRemorqueurDeletePopupComponent,
        RefRemorqueurBloquePopupComponent,
        RefRemorqueurBloqueDialogComponent,
    ],
    entryComponents: [
        RefRemorqueurComponent,
        RefRemorqueurDialogComponent,
        RefRemorqueurPopupComponent,
        RefRemorqueurDeleteDialogComponent,
        RefRemorqueurDeletePopupComponent,
        RefRemorqueurBloquePopupComponent,
        RefRemorqueurBloqueDialogComponent,
    ],
    providers: [
        RefRemorqueurService,
        RefRemorqueurPopupService,
        RefRemorqueurResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefRemorqueurModule {}
