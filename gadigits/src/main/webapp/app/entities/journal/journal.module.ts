import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    JournalService,
    JournalPopupService,
    JournalComponent,
    JournalDetailComponent,
    JournalDialogComponent,
    JournalPopupComponent,
    JournalDeletePopupComponent,
    JournalDeleteDialogComponent,
    journalRoute,
    journalPopupRoute,
    JournalResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...journalRoute,
    ...journalPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JournalComponent,
        JournalDetailComponent,
        JournalDialogComponent,
        JournalDeleteDialogComponent,
        JournalPopupComponent,
        JournalDeletePopupComponent,
    ],
    entryComponents: [
        JournalComponent,
        JournalDialogComponent,
        JournalPopupComponent,
        JournalDeleteDialogComponent,
        JournalDeletePopupComponent,
    ],
    providers: [
        JournalService,
        JournalPopupService,
        JournalResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumJournalModule {}
