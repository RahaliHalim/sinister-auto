import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumVehiculeAssureModule } from '../vehicule-assure/vehicule-assure.module';
import { AuxiliumAssureModule} from '../assure/assure.module';
import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumPersonnePhysiqueModule } from '../personne-physique/personne-physique.module';
import {DataTablesModule} from 'angular-datatables';
import {
    ContratAssuranceService,
    ContratAssurancePopupService,
    ContratAssuranceComponent,
    ContratAssuranceDialogComponent,
    contratAssuranceRoute,
    contratAssurancePopupRoute,
    ContratAssuranceDetailsComponent
} from './';
import { AssistanceComponent } from './assistance/assistance.component';
import { GlobalComponent } from './global/global.component';
import { ReparationComponent } from './reparation/reparation.component';
import { HistoryAssistanceComponent } from './history-assistance/history-assistance.component';
import { HistoryReparationComponent } from './history-reparation/history-reparation.component';
import { HistoryDossierComponent } from './history-dossier/history-dossier.component';
import { SouscriptionComponent } from './souscription/souscription.component';
import { SouscriptionGaComponent } from './souscription-ga/souscription-ga.component';
import { CnxErrorComponent } from './cnx-error/cnx-error.component';

const ENTITY_STATES = [
    ...contratAssuranceRoute,
    ...contratAssurancePopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumVehiculeAssureModule,
        AuxiliumPersonnePhysiqueModule,
        AuxiliumAssureModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        ContratAssuranceComponent,
        ContratAssuranceDialogComponent,
        ContratAssuranceDetailsComponent,        
        AssistanceComponent,
        GlobalComponent,
        ReparationComponent,
        HistoryAssistanceComponent,
        HistoryReparationComponent,
        HistoryDossierComponent,
        SouscriptionComponent,
        SouscriptionGaComponent,
        CnxErrorComponent
    ],
    entryComponents: [
        ContratAssuranceComponent,
        ContratAssuranceDetailsComponent,
        ContratAssuranceDialogComponent,HistoryAssistanceComponent,HistoryReparationComponent,HistoryDossierComponent
        ,CnxErrorComponent
    ],
    providers: [
        ContratAssuranceService,
        ContratAssurancePopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumContratAssuranceModule {}
