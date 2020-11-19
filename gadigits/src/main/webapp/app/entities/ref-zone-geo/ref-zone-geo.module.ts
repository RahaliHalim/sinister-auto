import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefZoneGeoService,
    RefZoneGeoPopupService,
    RefZoneGeoComponent,
    RefZoneGeoDetailComponent,
    RefZoneGeoDialogComponent,
    RefZoneGeoPopupComponent,
    RefZoneGeoDeletePopupComponent,
    RefZoneGeoDeleteDialogComponent,
    refZoneGeoRoute,
    refZoneGeoPopupRoute,
    RefZoneGeoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refZoneGeoRoute,
    ...refZoneGeoPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefZoneGeoComponent,
        RefZoneGeoDetailComponent,
        RefZoneGeoDialogComponent,
        RefZoneGeoDeleteDialogComponent,
        RefZoneGeoPopupComponent,
        RefZoneGeoDeletePopupComponent,
    ],
    entryComponents: [
        RefZoneGeoComponent,
        RefZoneGeoDialogComponent,
        RefZoneGeoPopupComponent,
        RefZoneGeoDeleteDialogComponent,
        RefZoneGeoDeletePopupComponent,
    ],
    providers: [
        RefZoneGeoService,
        RefZoneGeoPopupService,
        RefZoneGeoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefZoneGeoModule {}
