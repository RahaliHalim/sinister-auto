import { SinisterPrestationNotEligibleComponent } from './sinister-prestation-not-eligible.component';
import { SinisterPrestationDetailComponent } from './sinister-prestation-detail.component';
import { SinisterPrestationCanceledComponent } from './sinister-prestation-canceled.component';
import { SinisterPrestationClosedComponent } from './sinister-prestation-closed.component';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { SinisterComponent } from './sinister.component';
import { SinisterDetailComponent } from './sinister-detail.component';
import { SinisterPecAgentDialogComponent } from '../sinister-pec/sinister-pec-agent-dialog.component';
import { SinisterDialogComponent } from './sinister-dialog.component';
import { SinisterPrestationInProgressComponent } from './sinister-prestation-inprogress.component';
import { SinisterPrestationReport1Component } from './sinister-prestation-report1.component';
import { SinisterPrestationReport2Component } from './sinister-prestation-report2.component';
import { ReportTugPerformanceComponent } from './report-tug-performance.component';
import { DossiersComponent } from './dossiers.component';
import {DossiersDetailComponent} from './dossiers-detail.component'
import {AssitancesComponent} from './assitances.component';
import {AssitancesDetailComponent} from './assitances-detail.component';
import {PriseEnChargeComponent} from './priseencharge.component';
import {SinisterVrComponent} from './sinister-vr.component';
export const sinisterRoute: Routes = [
    {
        path: 'sinister',
        component: SinisterComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister/:id',
        component: SinisterDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-annul/:annulPrest',
        component: SinisterDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    },{
        path: 'sinister-refus/:refusPrest',
        component: SinisterDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    },{
        path: 'sinister-confirm-annul/:confirmAnnul',
        component: SinisterDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    },{
        path: 'sinister-confirm-refus/:confirmRefus',
        component: SinisterDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        
        canActivate: [UserRouteAccessService]
    },{
        path: 'motifs',
        component: SinisterDetailComponent,
        data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'auxiliumApp.sinister.home.title'
        },
       canActivate: [UserRouteAccessService]

    },
    {
        path: 'sinister-new',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-new/:immatriculation',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister/:id/edit',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    }, 
     {
        path: 'sinister/:sinisterId/traiter',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    
    {
        path: 'sinister-new/idcontrat/:idcontrat/immatriculation/:immatriculation',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestation-inprogress',
        component: SinisterPrestationInProgressComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.home.inprogress'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestation-closed',
        component: SinisterPrestationClosedComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.home.closed'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestation-canceled',
        component: SinisterPrestationCanceledComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.home.canceled'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestation-not-eligible',
        component: SinisterPrestationNotEligibleComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.home.notEligible'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'report-suivi-assistance',
        component: SinisterPrestationReport1Component,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.report.report1'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'report-frequency-rate',
        component: SinisterPrestationReport2Component,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.report.report2'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'report-tug-performance',
        component: ReportTugPerformanceComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.report.report3'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestation/:id/view',
        component: SinisterPrestationDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'prestation/:idService/edit',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'prestation/:idService/:action',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'sinister-agent-new',
        component: SinisterDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.dossier.home.title'
        },
        canActivate: [UserRouteAccessService],
    },{
        path: 'sinister-pec-agent-new',
        component: SinisterPecAgentDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 111,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
    }
    ,{
        path: 'sinister-pec-agent-new/:id',
        component: SinisterPecAgentDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 112,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
    }
    ,{
        path: 'acueildossiers',
        component: DossiersComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'acueildossiers/:id',
        component: DossiersDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'acueilassitances',
        component: AssitancesComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'acueilassitances/:id',
        component: AssitancesDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'acueilpriseencharge',
        component: PriseEnChargeComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
    },{
        path: 'sinister-pec-agent-new-c/:immatriculationPec',
        component: SinisterPecAgentDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
    },{
        path: 'sinister-vr/:status',
        component: SinisterVrComponent,
        data: {
            pageTitle: 'auxiliumApp.sinisterPrestation.home.PrestationVr'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestation-vr/:idVR/:mode',
        component: SinisterPrestationDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],
    }
];
