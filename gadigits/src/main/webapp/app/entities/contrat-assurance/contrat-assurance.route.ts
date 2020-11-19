import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { ContratAssuranceComponent } from './contrat-assurance.component';
import { ContratAssuranceDialogComponent } from './contrat-assurance-dialog.component';
import { AssistanceComponent } from './assistance/assistance.component';
import { GlobalComponent } from './global/global.component';
import { ReparationComponent } from './reparation/reparation.component';
import { SouscriptionComponent } from './souscription/souscription.component';
import { SouscriptionGaComponent } from './souscription-ga/souscription-ga.component';
import { CnxErrorComponent } from './cnx-error/cnx-error.component';
import { ContratAssuranceDetailsComponent } from './contrat-assurance-details.component';

export const contratAssuranceRoute: Routes = [
    {
        path: 'contrat-assurance',
        component: ContratAssuranceComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 34,
            functionality: 1,
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'souscriptionGa',
        component: SouscriptionGaComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.souscription.title'
        },
        canActivate: [UserRouteAccessService]
    },
    
    {
        path: 'souscriptionOld',
        component: SouscriptionComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.reportName'
        },
        canActivate: [UserRouteAccessService]
    }
    
   , {
        path: 'assistance/:typeService',
        component: AssistanceComponent,
        data: {
            pageTitle: 'auxiliumApp.assitances.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    },{
        path: 'global',
        component: GlobalComponent,
        data: {
            pageTitle: 'auxiliumApp.dossiers.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    },{
        path: 'reparation',
        data: {
            pageTitle: 'auxiliumApp.priseEnCharge.home.title'
        },
        component: ReparationComponent,
        
        canActivate: [UserRouteAccessService]
    }
];
export const contratAssurancePopupRoute: Routes = [
    {
        path: 'contrat-assurance-new',
        component: ContratAssuranceDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 34,
            functionality: 2,
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },

    {
        path: 'contrat-assurance/:id',
        component: ContratAssuranceDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.contratAssurance.home.title',
            entity: 34,
            functionality: 1,
        },
        canActivate: [UserRouteAccessService]
    },
    
    {
        path: 'contrat-assurance-new/:immatriculation',
        component: ContratAssuranceDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contrat-assurance/:id/edit',
        component: ContratAssuranceDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 34,
            functionality: 3,
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contrat-assurance/:idc/sinister/:immmat',
        component: ContratAssuranceDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'contrat-assurance/:idContrat/edit/:idAssure',
        component: ContratAssuranceDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },

    {
        path: 'contrat-assuranceP-new/:immatriculationPec',
        component: ContratAssuranceDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }

];
