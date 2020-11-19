import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AuxiliumSharedModule } from '../../shared';
import {
    ExpertService,
    ExpertPopupService,
    ExpertComponent,
    ExpertDetailComponent,
    ExpertDialogComponent,
    ExpertPopupComponent,
    ExpertDeletePopupComponent,
    ExpertDeleteDialogComponent,
    expertRoute,
    expertPopupRoute,
    ExpertResolvePagingParams,
} from './';
import { AuxiliumPersonneMoraleModule } from '../personne-morale/personne-morale.module';
import { DataTablesModule } from 'angular-datatables';

const ENTITY_STATES = [
    ...expertRoute,
    ...expertPopupRoute,
];

@NgModule({
    imports: [
        NgMultiSelectDropDownModule,
        DataTablesModule,
        AuxiliumSharedModule,
        AuxiliumPersonneMoraleModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ExpertComponent,
        ExpertDetailComponent,
        ExpertDialogComponent,
        ExpertDeleteDialogComponent,
        ExpertPopupComponent,
        ExpertDeletePopupComponent,
    ],
    entryComponents: [
        ExpertComponent,
        ExpertDialogComponent,
        ExpertPopupComponent,
        ExpertDeleteDialogComponent,
        ExpertDeletePopupComponent,
    ],
    providers: [
        ExpertService,
        ExpertPopupService,
        ExpertResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumExpertModule {}
