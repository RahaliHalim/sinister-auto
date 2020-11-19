import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    HistoryService,HistoryPopupComponent,HistoryPopupService,HistoryPopupDetail,historyPopupRoute,historyRoute

} from '.';

import { DataTablesModule } from 'angular-datatables';
const ENTITY_STATES = [
   ...historyPopupRoute,
   ...historyRoute
 
];

@NgModule({
    imports: [
        DataTablesModule,
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HistoryPopupComponent,
        HistoryPopupDetail
    ],
    entryComponents: [
        HistoryPopupComponent,
        HistoryPopupDetail
    ],
    providers: [
        HistoryService,HistoryPopupService
      
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumHistoryModule {}
