import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefPositionGaService,
    RefPositionGaPopupService,
    RefPositionGaComponent,
    RefPositionGaDetailComponent,
    RefPositionGaDialogComponent,
    RefPositionGaPopupComponent,
    RefPositionGaDeletePopupComponent,
    RefPositionGaDeleteDialogComponent,
    refPositionGaRoute,
    refPositionGaPopupRoute,
    RefPositionGaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refPositionGaRoute,
    ...refPositionGaPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefPositionGaComponent,
        RefPositionGaDetailComponent,
        RefPositionGaDialogComponent,
        RefPositionGaDeleteDialogComponent,
        RefPositionGaPopupComponent,
        RefPositionGaDeletePopupComponent,
    ],
    entryComponents: [
        RefPositionGaComponent,
        RefPositionGaDialogComponent,
        RefPositionGaPopupComponent,
        RefPositionGaDeleteDialogComponent,
        RefPositionGaDeletePopupComponent,
    ],
    providers: [
        RefPositionGaService,
        RefPositionGaPopupService,
        RefPositionGaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefPositionGaModule {}
