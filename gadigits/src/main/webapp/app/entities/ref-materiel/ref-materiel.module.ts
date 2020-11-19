import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefMaterielService,
    RefMaterielPopupService,
    RefMaterielComponent,
    RefMaterielDetailComponent,
    RefMaterielDialogComponent,
    RefMaterielPopupComponent,
    RefMaterielDeletePopupComponent,
    RefMaterielDeleteDialogComponent,
    refMaterielRoute,
    refMaterielPopupRoute,
    RefMaterielResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refMaterielRoute,
    ...refMaterielPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefMaterielComponent,
        RefMaterielDetailComponent,
        RefMaterielDialogComponent,
        RefMaterielDeleteDialogComponent,
        RefMaterielPopupComponent,
        RefMaterielDeletePopupComponent,
    ],
    entryComponents: [
        RefMaterielComponent,
        RefMaterielDialogComponent,
        RefMaterielPopupComponent,
        RefMaterielDeleteDialogComponent,
        RefMaterielDeletePopupComponent,
    ],
    providers: [
        RefMaterielService,
        RefMaterielPopupService,
        RefMaterielResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefMaterielModule {}
