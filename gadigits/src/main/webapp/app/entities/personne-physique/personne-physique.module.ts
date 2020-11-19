import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumAdminModule } from '../../admin/admin.module';
import {
    PersonnePhysiqueService,
    PersonnePhysiquePopupService,
    PersonnePhysiqueComponent,
    PersonnePhysiqueDetailComponent,
    PersonnePhysiqueDialogComponent,
    PersonnePhysiqueDialogForDetailComponent,
    PersonnePhysiquePopupComponent,
    PersonnePhysiqueDeletePopupComponent,
    PersonnePhysiqueDeleteDialogComponent,
    personnePhysiqueRoute,
    personnePhysiquePopupRoute,
    PersonnePhysiqueResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personnePhysiqueRoute,
    ...personnePhysiquePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonnePhysiqueComponent,
        PersonnePhysiqueDetailComponent,
        PersonnePhysiqueDialogComponent,
        PersonnePhysiqueDialogForDetailComponent,
        PersonnePhysiqueDeleteDialogComponent,
        PersonnePhysiquePopupComponent,
        PersonnePhysiqueDeletePopupComponent,
    ],
    entryComponents: [
        PersonnePhysiqueComponent,
        PersonnePhysiqueDialogComponent,
        PersonnePhysiqueDialogForDetailComponent,
        PersonnePhysiquePopupComponent,
        PersonnePhysiqueDeleteDialogComponent,
        PersonnePhysiqueDeletePopupComponent,
    ],
    exports: [
        PersonnePhysiqueDialogComponent,
        PersonnePhysiqueDialogForDetailComponent
    ],
    providers: [
        PersonnePhysiqueService,
        PersonnePhysiquePopupService,
        PersonnePhysiqueResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPersonnePhysiqueModule {}
