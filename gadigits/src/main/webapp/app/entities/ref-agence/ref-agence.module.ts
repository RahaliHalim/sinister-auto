import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefAgenceService,
    RefAgencePopupService,
    RefAgenceComponent,
    RefAgenceDetailComponent,
    RefAgenceDialogComponent,
    RefAgencePopupComponent,
    RefAgenceDeletePopupComponent,
    RefAgenceDeleteDialogComponent,
    refAgenceRoute,
    refAgencePopupRoute,
    RefAgenceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refAgenceRoute,
    ...refAgencePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefAgenceComponent,
        RefAgenceDetailComponent,
        RefAgenceDialogComponent,
        RefAgenceDeleteDialogComponent,
        RefAgencePopupComponent,
        RefAgenceDeletePopupComponent,
    ],
    entryComponents: [
        RefAgenceComponent,
        RefAgenceDialogComponent,
        RefAgencePopupComponent,
        RefAgenceDeleteDialogComponent,
        RefAgenceDeletePopupComponent,
    ],
    providers: [
        RefAgenceService,
        RefAgencePopupService,
        RefAgenceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefAgenceModule {}
