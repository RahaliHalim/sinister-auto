import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    BusinessEntityService,
    BusinessEntityPopupService,
    BusinessEntityComponent,
    BusinessEntityDetailComponent,
    BusinessEntityDialogComponent,
    BusinessEntityPopupComponent,
    BusinessEntityDeletePopupComponent,
    BusinessEntityDeleteDialogComponent,
    businessEntityRoute,
    businessEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...businessEntityRoute,
    ...businessEntityPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BusinessEntityComponent,
        BusinessEntityDetailComponent,
        BusinessEntityDialogComponent,
        BusinessEntityDeleteDialogComponent,
        BusinessEntityPopupComponent,
        BusinessEntityDeletePopupComponent,
    ],
    entryComponents: [
        BusinessEntityComponent,
        BusinessEntityDialogComponent,
        BusinessEntityPopupComponent,
        BusinessEntityDeleteDialogComponent,
        BusinessEntityDeletePopupComponent,
    ],
    providers: [
        BusinessEntityService,
        BusinessEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumBusinessEntityModule {}
