import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AuxiliumSharedModule } from '../../shared';
import { DataTablesModule } from 'angular-datatables';

import {
    AssistanceMonitoringCompanyComponent,
    PecMonitoringPrestationComponent,
    PecMonitoringPrestationStepComponent,
    PolicyIndicatorComponent,
    ReportsService,
    reportsRoute
} from '.';

const ENTITY_STATES = [
    ...reportsRoute,
];

@NgModule({
    imports: [
        BrowserModule,
        AuxiliumSharedModule,
        DataTablesModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AssistanceMonitoringCompanyComponent,
        PecMonitoringPrestationComponent,
        PecMonitoringPrestationStepComponent,
        PolicyIndicatorComponent
    ],
    entryComponents: [
        AssistanceMonitoringCompanyComponent,
        PecMonitoringPrestationComponent,
        PecMonitoringPrestationStepComponent,
        PolicyIndicatorComponent
    ],
    providers: [ ReportsService ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumReportsModule {}