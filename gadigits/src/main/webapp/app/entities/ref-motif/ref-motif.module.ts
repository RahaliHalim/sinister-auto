import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';
import {
    RefMotifService,
    RefMotifPopupService,
    RefMotifComponent,
    RefMotifDetailComponent,
    RefMotifDialogComponent,
    RefMotifPopupComponent,
    RefMotifDeletePopupComponent,
    RefMotifDeleteDialogComponent,
    refMotifRoute,
    refMotifPopupRoute,
    RefMotifResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refMotifRoute,
    ...refMotifPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ], 
    declarations: [
        RefMotifComponent,
        RefMotifDetailComponent,
        RefMotifDialogComponent,
        RefMotifDeleteDialogComponent,
        RefMotifPopupComponent,
        RefMotifDeletePopupComponent,
    ],
    entryComponents: [
        RefMotifComponent,
        RefMotifDialogComponent,
        RefMotifPopupComponent,
        RefMotifDeleteDialogComponent,
        RefMotifDeletePopupComponent,
    ],
    providers: [
        RefMotifService,
        RefMotifPopupService,
        RefMotifResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefMotifModule {}
