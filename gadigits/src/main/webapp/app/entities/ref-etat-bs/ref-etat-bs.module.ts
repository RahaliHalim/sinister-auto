import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefEtatBsService,
    RefEtatBsPopupService,
    RefEtatBsComponent,
    RefEtatBsDetailComponent,
    RefEtatBsDialogComponent,
    RefEtatBsPopupComponent,
    RefEtatBsDeletePopupComponent,
    RefEtatBsDeleteDialogComponent,
    refEtatBsRoute,
    refEtatBsPopupRoute,
    RefEtatBsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refEtatBsRoute,
    ...refEtatBsPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefEtatBsComponent,
        RefEtatBsDetailComponent,
        RefEtatBsDialogComponent,
        RefEtatBsDeleteDialogComponent,
        RefEtatBsPopupComponent,
        RefEtatBsDeletePopupComponent,
    ],
    entryComponents: [
        RefEtatBsComponent,
        RefEtatBsDialogComponent,
        RefEtatBsPopupComponent,
        RefEtatBsDeleteDialogComponent,
        RefEtatBsDeletePopupComponent,
    ],
    providers: [
        RefEtatBsService,
        RefEtatBsPopupService,
        RefEtatBsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefEtatBsModule {}
