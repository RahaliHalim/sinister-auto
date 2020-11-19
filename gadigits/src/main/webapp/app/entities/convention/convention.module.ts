import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AuxiliumSharedModule } from '../../shared';
import {
    ConventionService,
    ConventionAmendmentService,
    ConventionComponent,
    ConventionDialogComponent,
    ConventionAmendmentComponent,
    ConventionDetailComponent,
    ConventionPackPopupService,
    RefPackPopupDetail,
    RefPackPopupComponent,
    conventionRoute
} from './';

const ENTITY_STATES = [
    ...conventionRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule,
        NgMultiSelectDropDownModule
    ],
    declarations: [
        ConventionComponent,
        ConventionDialogComponent,
        ConventionAmendmentComponent,
        ConventionDetailComponent,
        RefPackPopupDetail,
        RefPackPopupComponent
    ],
    entryComponents: [
        ConventionComponent,
        ConventionDialogComponent,
        ConventionAmendmentComponent,
        ConventionDetailComponent,
        RefPackPopupDetail,
        RefPackPopupComponent
    ],
    providers: [
        ConventionService,
        ConventionAmendmentService,
        ConventionPackPopupService,
    
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumConventionModule {}
