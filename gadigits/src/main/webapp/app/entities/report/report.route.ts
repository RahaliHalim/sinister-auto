import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GenerationRapportComponent } from './reportIDAOuverture.component';
import { GenerationBonSortieComponent } from './reportBonSortie.component';
import { SuiviNbreDossiersComponent } from './suivi-nbre-dossiers/suivi-nbre-dossiers.component';
import { BonificationDepensesComponent } from './bonification-depenses/bonification-depenses.component';
import { SuiviAnnulationComponent } from './suivi-annulation/suivi-annulation.component';
import { CoutMoyenSinistreComponent } from './cout-moyen-sinistre/cout-moyen-sinistre.component';
import { DelaiMoyenImmobilisationComponent } from './delai-moyen-immobilisation/delai-moyen-immobilisation.component';
import { PaiementReparationComponent } from './paiement-reparation/paiement-reparation.component';
import { PerformanceRemorqueurComponent } from './performance-remorqueur/performance-remorqueur.component';


export const generationRapportRoute: Routes = [
    {
    path: 'generation-ida-ouverture',
    component: GenerationRapportComponent,
    data: {
        pageTitle: 'auxiliumApp.sinisterPec.home.titleIdaOuverture'
    },
    canActivate: [UserRouteAccessService]
},   {
    path: 'suiviAnnulation',
    component: SuiviAnnulationComponent,
    data: {
        pageTitle: 'auxiliumApp.rapport.suiviAnnulation.title'
    },
    canActivate: [UserRouteAccessService]

},{
    path: 'generation-bon-sortie',
    component: GenerationBonSortieComponent,
    data: {
        pageTitle: 'auxiliumApp.sinisterPec.home.bonSortie'
    },
    canActivate: [UserRouteAccessService]
}, {
    path: 'bonification',
    component: BonificationDepensesComponent,
    data: {
        pageTitle: 'auxiliumApp.rapport.bonification.title'
    },
    canActivate: [UserRouteAccessService]
},{
        path: 'generation-ida-ouverture',
        component: GenerationRapportComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 91,
            pageTitle: 'auxiliumApp.sinisterPec.home.titleIdaOuverture'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'suivi-nbre-dossiers',
        component: SuiviNbreDossiersComponent,
        data: {
            pageTitle: 'auxiliumApp.rapport.suiviDossier.title'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'cout-moyen-sinistre',
        component: CoutMoyenSinistreComponent,
        data: {
            pageTitle: 'auxiliumApp.rapport.coutMoyenSinistre.title'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'delai-moyen-immobilisation',
        component: DelaiMoyenImmobilisationComponent,
        data: {
            pageTitle: 'auxiliumApp.rapport.Delai_moyen.title'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'paiement-reparation',
        component: PaiementReparationComponent,
        data: {
            pageTitle: 'auxiliumApp.rapport.coutMoyenSinistres.title'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'performance-remorqueur',
        component: PerformanceRemorqueurComponent,
        data: {
            pageTitle: 'auxiliumApp.rapport.performanceRemorqueur.title'
        },
        canActivate: [UserRouteAccessService]
    }
    
    
    
    
    
    
    ,{
        path: 'generation-bon-sortie',
        component: GenerationBonSortieComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 47,
            functionality: 70,
            pageTitle: 'auxiliumApp.sinisterPec.home.bonSortie'
        },
        canActivate: [UserRouteAccessService]
    }];
