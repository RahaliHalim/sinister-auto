import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    AmendmentTypeService,
    AmendmentTypePopupService,
    AmendmentTypeComponent,
    AmendmentTypeDetailComponent,
    AmendmentTypeDialogComponent,
    AmendmentTypePopupComponent,
    AmendmentTypeDeletePopupComponent,
    AmendmentTypeDeleteDialogComponent,
    amendmentTypeRoute,
    amendmentTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...amendmentTypeRoute,
    ...amendmentTypePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AmendmentTypeComponent,
        AmendmentTypeDetailComponent,
        AmendmentTypeDialogComponent,
        AmendmentTypeDeleteDialogComponent,
        AmendmentTypePopupComponent,
        AmendmentTypeDeletePopupComponent,
    ],
    entryComponents: [
        AmendmentTypeComponent,
        AmendmentTypeDialogComponent,
        AmendmentTypePopupComponent,
        AmendmentTypeDeleteDialogComponent,
        AmendmentTypeDeletePopupComponent,
    ],
    providers: [
        AmendmentTypeService,
        AmendmentTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumAmendmentTypeModule {}
