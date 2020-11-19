import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { AuxiliumSharedModule } from '../../shared';
import {DataTablesModule} from 'angular-datatables';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

import {
    GenerationRapportComponent,
    generationRapportRoute,
    GenerationBonSortieComponent
} from '.';
import { SuiviNbreDossiersComponent } from './suivi-nbre-dossiers/suivi-nbre-dossiers.component';
import { BonificationDepensesComponent } from './bonification-depenses/bonification-depenses.component';
import { ReportService } from './report.service';
import { SuiviAnnulationComponent } from './suivi-annulation/suivi-annulation.component';
import { PerformanceRemorqueurComponent } from './performance-remorqueur/performance-remorqueur.component';
import { DelaiMoyenImmobilisationComponent } from './delai-moyen-immobilisation/delai-moyen-immobilisation.component';
import { CoutMoyenSinistreComponent } from './cout-moyen-sinistre/cout-moyen-sinistre.component';
import { PaiementReparationComponent } from './paiement-reparation/paiement-reparation.component';

const ENTITY_STATES = [
    ...generationRapportRoute,
];

@NgModule({
    imports: [
        BrowserModule,
        AuxiliumSharedModule,
        DataTablesModule,
        NgMultiSelectDropDownModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GenerationRapportComponent,
        GenerationBonSortieComponent,
        SuiviNbreDossiersComponent,
        BonificationDepensesComponent,
        SuiviAnnulationComponent,
        PerformanceRemorqueurComponent,
        DelaiMoyenImmobilisationComponent,
        CoutMoyenSinistreComponent,
        PaiementReparationComponent
    ],
    entryComponents: [
        GenerationRapportComponent,
        GenerationBonSortieComponent
    ],
    providers: [ReportService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumGenerationRapportModule {}