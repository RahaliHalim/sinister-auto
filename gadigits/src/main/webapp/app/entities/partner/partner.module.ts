import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AuxiliumSharedModule } from '../../shared';
import {
    PartnerService,
    PartnerPopupService,
    PartnerComponent,
    DealerComponent,
    PartnerDetailComponent,
    DealerDetailComponent,
    PartnerDialogComponent,
    DealerDialogComponent,
    PartnerPopupComponent,
    DealerPopupComponent,
    PartnerDeletePopupComponent,
    DealerDeletePopupComponent,
    PartnerDeleteDialogComponent,
    DealerDeleteDialogComponent,
    partnerRoute,
    partnerPopupRoute,
    PartnerResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...partnerRoute,
    ...partnerPopupRoute,
];
import { DataTablesModule } from 'angular-datatables';


@NgModule({
    imports: [
        DataTablesModule,
        AuxiliumSharedModule,
        NgMultiSelectDropDownModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PartnerComponent,
        DealerComponent,
        PartnerDetailComponent,
        DealerDetailComponent,
        PartnerDialogComponent,
        DealerDialogComponent,
        PartnerDeleteDialogComponent,
        DealerDeleteDialogComponent,
        PartnerPopupComponent,
        DealerPopupComponent,
        PartnerDeletePopupComponent,
        DealerDeletePopupComponent
    ],
    entryComponents: [
        PartnerComponent,
        DealerComponent,
        PartnerDialogComponent,
        DealerDialogComponent,
        PartnerPopupComponent,
        DealerPopupComponent,
        PartnerDeleteDialogComponent,
        DealerDeleteDialogComponent,
        PartnerDeletePopupComponent,
        DealerDeletePopupComponent
    ],
    providers: [
        PartnerService,
        PartnerPopupService,
        PartnerResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPartnerModule {}
