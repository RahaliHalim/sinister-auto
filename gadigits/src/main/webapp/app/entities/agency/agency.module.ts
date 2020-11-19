import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuxiliumSharedModule } from '../../shared';
import {
    AgencyService,
    AgencyPopupService,
    AgencyComponent,
    AgenceConcessionnaireComponent,
    AgencyDetailComponent,
    AgenceConcessionnaireDetailComponent,
    AgencyDialogComponent,
    AgenceConcessionnaireDialogComponent,
    AgencyPopupComponent,
    AgenceConcessionnairePopupComponent,
    AgencyDeletePopupComponent,
    AgenceConcessionnaireDeletePopupComponent,
    AgencyDeleteDialogComponent,
    AgenceConcessionnaireDeleteDialogComponent,
    agencyRoute,
    agencyPopupRoute,
    AgencyResolvePagingParams,
    AgenceConcessPopupDetail,
    AgenceConcessPopupComponent
} from './';

const ENTITY_STATES = [
    ...agencyRoute,
    ...agencyPopupRoute,
];
import { DataTablesModule } from 'angular-datatables';

@NgModule({
    imports: [
        AuxiliumSharedModule,
        NgMultiSelectDropDownModule,
        DataTablesModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AgencyComponent,
        AgenceConcessionnaireComponent,
        AgencyDetailComponent,
        AgenceConcessionnaireDetailComponent,
        AgencyDialogComponent,
        AgenceConcessionnaireDialogComponent,
        AgencyDeleteDialogComponent,
        AgenceConcessionnaireDeleteDialogComponent,
        AgencyPopupComponent,
        AgenceConcessionnairePopupComponent,
        AgencyDeletePopupComponent,
        AgenceConcessionnaireDeletePopupComponent,
        AgenceConcessPopupDetail,
        AgenceConcessPopupComponent
    ],
    entryComponents: [
        AgencyComponent,
        AgenceConcessionnaireComponent,
        AgencyDialogComponent,
        AgenceConcessionnaireDialogComponent,
        AgencyPopupComponent,
        AgenceConcessionnairePopupComponent,
        AgencyDeleteDialogComponent,
        AgenceConcessionnaireDeleteDialogComponent,
        AgencyDeletePopupComponent,
        AgenceConcessionnaireDeletePopupComponent,
        AgenceConcessPopupDetail,
        AgenceConcessPopupComponent
    ],
    providers: [
        AgencyService,
        AgencyPopupService,
        AgencyResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumAgencyModule {}
