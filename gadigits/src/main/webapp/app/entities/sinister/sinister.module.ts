import { AuxiliumUtilsModule } from './../../utils/utils.module';
import { DateUtils } from './../../utils/date-utils';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';

import {
    SinisterService,
    ReportAssistanceService,
    SiniterPrestationTugPopupService,
    SinisterComponent,
    SinisterDetailComponent,
    SinisterDialogComponent,
    SinisterPrestationDetailComponent,
    SinisterPrestationViewComponent,
    SinisterPrestationInProgressComponent,
    SinisterPrestationClosedComponent,
    SinisterPrestationCanceledComponent,
    SinisterPrestationNotEligibleComponent,
    SinisterPrestationReport1Component,
    SinisterPrestationReport2Component,
    ReportTugPerformanceComponent,
    SinisterPrestationTugComponent,
    sinisterRoute,
    RefGroundsService,
    VehiculeRemplacementComponent,
    SinisterVrComponent
} from '.';
import { SinisterPrestationService } from './sinister-prestation.service';
import { DossiersComponent } from './dossiers.component';
import { DossiersDetailComponent} from './dossiers-detail.component';
import {AssitancesComponent} from './assitances.component';
import {AssitancesDetailComponent} from './assitances-detail.component';
import {PriseEnChargeComponent} from './priseencharge.component';
import { SinisterPrestationLoueurComponent } from './sinister-prestation-loueur.component';
const ENTITY_STATES = [
    ...sinisterRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule,
        AuxiliumUtilsModule
    ],
    declarations: [
        SinisterComponent,
        SinisterDetailComponent,
        SinisterDialogComponent,
        SinisterPrestationDetailComponent,
        SinisterPrestationViewComponent,
        SinisterPrestationInProgressComponent,
        SinisterPrestationClosedComponent,
        SinisterPrestationCanceledComponent,
        SinisterPrestationNotEligibleComponent,
        SinisterPrestationReport1Component,
        SinisterPrestationReport2Component,
        ReportTugPerformanceComponent,
        SinisterPrestationTugComponent,
        DossiersComponent,
        DossiersDetailComponent,
        AssitancesComponent,
        AssitancesDetailComponent,
        PriseEnChargeComponent,
        VehiculeRemplacementComponent,
        SinisterPrestationLoueurComponent,
        SinisterVrComponent
        
    ],
    entryComponents: [
        SinisterComponent,
        SinisterDialogComponent,
        SinisterPrestationDetailComponent,
        SinisterPrestationViewComponent,
        SinisterPrestationInProgressComponent,
        SinisterPrestationClosedComponent,
        SinisterPrestationCanceledComponent,
        SinisterPrestationNotEligibleComponent,
        SinisterPrestationReport1Component,
        SinisterPrestationReport2Component,
        ReportTugPerformanceComponent,
        SinisterPrestationTugComponent,
        VehiculeRemplacementComponent,
        SinisterPrestationLoueurComponent,
        SinisterVrComponent
    ],
    providers: [
        SinisterService, ReportAssistanceService, SiniterPrestationTugPopupService, RefGroundsService, DateUtils, SinisterPrestationService
    ],
    exports: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumSinisterModule {}
