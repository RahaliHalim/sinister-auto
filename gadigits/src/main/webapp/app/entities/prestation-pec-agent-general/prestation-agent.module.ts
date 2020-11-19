import { DataTablesModule } from 'angular-datatables';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumVehiculeAssureModule } from '../vehicule-assure/vehicule-assure.module';
import { AuxiliumAssureModule} from '../assure/assure.module';
import { AuxiliumSharedModule } from '../../shared';
import { FormsModule } from '@angular/forms';
import { AuxiliumTiersModule } from '../tiers/tiers.module';

import {
    PrestationAgentService,
    DemandPecService,
    dossierRoute,
    DossierResolvePagingParams,
    DemandPecOutstandingComponent,
    DemandPecComponent, DemandPecSentComponent, DemandPecAccordComponent
} from './';

const ENTITY_STATES = [
    ...dossierRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumTiersModule,
        AuxiliumVehiculeAssureModule,
        AuxiliumAssureModule,
        FormsModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        DemandPecOutstandingComponent,
        DemandPecSentComponent,
        DemandPecAccordComponent,
        DemandPecComponent
    ],
    entryComponents: [
        DemandPecOutstandingComponent,
        DemandPecSentComponent,
        DemandPecAccordComponent,
        DemandPecComponent
    ],
    providers: [PrestationAgentService, DemandPecService, DossierResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumDossierAgentModule {}
