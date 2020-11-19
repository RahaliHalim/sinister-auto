import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { SinisterPecComponent } from './sinister-pec.component';
import { SinisterPecDetailComponent } from './sinister-pec-detail.component';
import { SinisterPecPopupComponent } from './sinister-pec-dialog.component';
import { SinisterPecDeletePopupComponent } from './sinister-pec-delete-dialog.component';
import { SinisterPecDialogComponent } from './sinister-pec-dialog.component';
import { SinisterPecAgentDialogComponent } from './sinister-pec-agent-dialog.component';
import { SinisterRepriseDialogComponent } from './sinister-pec-reprise';
import { SinisterPecToApproveComponent } from './sinister-pec-to-approve.component';
import { ConsultationDemandePecComponent } from './consultation-demand-pec.component';
import { DemandePecComponent } from '../sinister-pec/demand-pec.component';
import { DemandPecInProgressComponent } from '../sinister-pec/demand-pec-in-progress.component';
import { SinisterPecRefused } from './sinister-pec-refused.component';
import { SinisterPecCanceled } from './sinister-pec-canceled.component';
import { SinisterPecReservesLifted } from './sinister-pec-reserves-lifted.component';
import { PrestationPECAffectReparationComponent } from './prestation-pec-affect-reparation.component';
import { SinisterPecChangeStatus } from './sinister-pec-change-status.component';
import { SinisterPecModificationStatus } from './sinister-pec-modification-status.component';
import { SinisterPecCanceledAndRefused } from './sinister-pec-canceled-refused.component';
import { VerificationOriginalsPrintedComponent } from './verification-originals-printed.component';
import { PriseEnChargeDetailComponent } from './priseencharge-detail.component';
import { ModificationChargeComponent } from './modification-charge.component';
import { AnnulationPrestationComponent } from './annulation-prestation.component';
import { ConfirmationAnnulationPrestationComponent } from './confirmation-annulation-prestation.component';
import { RefusPrestationComponent } from './refused-prestation.component';
import { ConfirmationRefusPrestationComponent } from './confirmation-refus-prestation.component';
import { CancelExpertMissionaryComponent } from './cancel-expert-missionary.component';
import { ExpertMissionaryComponent } from './expert-missionary.component'
import { ImprimeComponent } from './imprime.component';
import { SignatureBonSortieComponent } from './signature-bon-sortie';
import { SinisterPecModificationPrestation } from './sinister-pec-modification-prestation.component';
import { AutresPiecesComponent } from './autres-pieces.component';


import { PrestationPECAnnulerAffectReparationComponent } from './prestation-pec-annuler-affect-reparation.component';
import { DerogationComponent } from './derogation.component';
import { DerogationPecComponent } from './derogation-pec.component';
export const sinisterPecRoute: Routes = [
    {
        path: 'sinister-pec',
        component: SinisterPecComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 1,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec/:id',
        component: SinisterPecDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 1,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pec-to-approve',
        component: SinisterPecToApproveComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 84,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecToApprove'
        },
        canActivate: [UserRouteAccessService]
    },

    {
        path: 'demande-pec',
        component: DemandePecComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 110,
            pageTitle: 'auxiliumApp.sinisterPec.home.demandeOuverture'
        },

        canActivate: [UserRouteAccessService]
    },

    {
        path: 'pec-being-processed',
        component: DemandPecInProgressComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 86,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecInProgress'
        },

        canActivate: [UserRouteAccessService]
    },
    {
        path: 'consulter-pec',
        component: ConsultationDemandePecComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 88,
            pageTitle: 'auxiliumApp.sinisterPec.home.consulterDemandePec'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'demande-pec',
        component: DemandePecComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 110,
            pageTitle: 'auxiliumApp.sinisterPec.home.demandeOuverture'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec-refused',
        component: SinisterPecRefused,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 76,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecRefused'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec-canceled',
        component: SinisterPecCanceled,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 75,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecCanceled'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec-reserves-lifted',
        component: SinisterPecReservesLifted,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 82,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecReservesLifted'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec/consulter/:idPec',
        component: SinisterPecAgentDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 89,
            pageTitle: 'auxiliumApp.sinister.home.details'
        },
        canActivate: [UserRouteAccessService],

    }
    , {
        path: 'sinister-pec-affectation-reparateur',
        component: PrestationPECAffectReparationComponent,

        data: {
            authorities: ['ROLE_USER'],
            entity: 27,
            functionality: 106,
            pageTitle: 'auxiliumApp.sinisterPec.home.affectReparateur'
        },
        canActivate: [UserRouteAccessService]
    }

    , {
        path: 'sinister-pec-annuler-affectation-reparateur',
        component: PrestationPECAnnulerAffectReparationComponent,

        data: {
            authorities: ['ROLE_USER'],
            entity: 27,
            functionality: 108,
            pageTitle: 'auxiliumApp.sinisterPec.home.affectReparateur'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cancel-missionner-expert',
        component: CancelExpertMissionaryComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 26,
            functionality: 105,
            pageTitle: 'auxiliumApp.sinisterPec.home.cancelMissionnerExpert'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'missionner-expert',
        component: ExpertMissionaryComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 26,
            functionality: 104,
            pageTitle: 'auxiliumApp.sinisterPec.home.missionnerexpert'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sinister-pec-change-status',
        component: SinisterPecChangeStatus,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 81,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecAcceptedWithChangeStatus'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec-modification-status',
        component: SinisterPecModificationStatus,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 79,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecModificationStatus'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec-reprise-prestation',
        component: SinisterPecCanceledAndRefused,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 115,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecRefusedCanceled'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'verification-originals-printed',
        component: VerificationOriginalsPrintedComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 47,
            functionality: 72,
            pageTitle: 'auxiliumApp.sinisterPec.home.verificationOriginalsPrinted'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modification-charge',
        component: ModificationChargeComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 62,
            pageTitle: 'auxiliumApp.modificationCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'annuler-prestation',
        component: AnnulationPrestationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 64,
            pageTitle: 'auxiliumApp.sinisterPec.annulationPrestation'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'confirmation-annuler-prestation',
        component: ConfirmationAnnulationPrestationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 65,
            pageTitle: 'auxiliumApp.sinisterPec.confirmAnnulationPrestation'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'refus-prestation',
        component: RefusPrestationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 66,
            pageTitle: 'auxiliumApp.sinisterPec.refusPrestation'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'confirmation-refus-prestation',
        component: ConfirmationRefusPrestationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 67,
            pageTitle: 'auxiliumApp.sinisterPec.confirmRefusPrestation'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'imprime',
        component: ImprimeComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 69,
            pageTitle: 'auxiliumApp.sinisterPec.imprime'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'imprimeReload',
        component: ImprimeComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 69,
            pageTitle: 'auxiliumApp.sinisterPec.imprime'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'devis-missionary-expert/:sinisterPecMissionExpertId',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 26,
            functionality: 14,
            pageTitle: 'auxiliumApp.sinisterPec.home.missionnerexpert'
        },
        canActivate: [UserRouteAccessService],
    }
    ,
    {
        path: 'devis-cancel-missionary-expert/:sinisterPecCancelExpertId',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 26,
            functionality: 28,
            pageTitle: 'auxiliumApp.sinisterPec.home.missionnerexpert'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'signature-bon-sortie',
        component: SignatureBonSortieComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 47,
            functionality: 73,
            pageTitle: 'auxiliumApp.sinisterPec.home.signatureBonSortie'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modification-prestation',
        component: SinisterPecModificationPrestation,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 68,
            pageTitle: 'auxiliumApp.sinisterPec.home.modificationPrestation'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'autres-pieces-jointes',
        component: AutresPiecesComponent,
        data: {
            authorities: ['ROLE_USER'],
            //entity: 100,
            //functionality: 1,
            pageTitle: 'auxiliumApp.sinisterPec.autresPiecesJointes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'derogation',
        component: DerogationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 126,
            pageTitle: 'auxiliumApp.sinisterPec.derogation'
        },
        canActivate: [UserRouteAccessService]
    }

];

export const sinisterPecPopupRoute: Routes = [
    {
        path: 'sinister-pec-new/:immatriculation',
        component: SinisterPecDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 111,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinister-pec-reprise-canceled/:idPecCanceled',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 78,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecCanceled'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-accepted-with-reserve/:idPecAccWithReserve',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 83,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecAcceptedWithReserve'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'sinister-pec-affectation-reparateur/:sinisterPecId',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 27,
            functionality: 107,
            pageTitle: 'auxiliumApp.sinisterPec.home.affectReparateur'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'sinister-pec-annuler-affectation-reparateur/:sinisterPecAnnuleAffectationId',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 27,
            functionality: 109,
            pageTitle: 'auxiliumApp.sinister.home.details'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'sinister-pec-reprise-refused/:idPecRefused',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 77,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecRefused'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-to-approuve/:idPecToApprouve',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 85,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecToApprove'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-in-progress/:idPecInProgress',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 87,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecInProgress'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-new',
        component: SinisterPecDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 111,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sinister-pec/:id/edit',
        component: SinisterPecPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 3,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sinister-pec/consulter/:idPec',
        component: SinisterPecAgentDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 89,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sinister-pec/:id/delete',
        component: SinisterPecDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 5,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }, {
        path: 'sinister-pec-reprise-accepted-with-change-status/:idPecAccWithChangeStatus',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 80,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecModificationStatus'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-change-status/:idPecChangeStatus',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 81,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecAcceptedWithChangeStatus'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-refused-canceled/:idPecRefusedCanceled',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 116,
            pageTitle: 'auxiliumApp.sinisterPec.home.titlePecRefusedCanceled'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-bon-sortie/:idPecBonSortie',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 47,
            functionality: 71,
            pageTitle: 'auxiliumApp.sinisterPec.home.bonSortie'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-verif-origin-printed/:idPecVerOrgPrint',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 47,
            functionality: 45,
            pageTitle: 'auxiliumApp.sinisterPec.home.verificationOriginalsPrinted'
        },
        canActivate: [UserRouteAccessService],

    },
    {
        path: 'acueilpriseencharge/:id',
        component: PriseEnChargeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.sinister.home.title'
        },
        canActivate: [UserRouteAccessService],

    }, {
        path: 'sinister-pec-reprise-Canc-Pec/:idCancPec',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 36,
            pageTitle: 'auxiliumApp.sinisterPec.annulationPrestation'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-confirm-canc-Pec/:idConfirmCancPec',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 43,
            pageTitle: 'auxiliumApp.sinisterPec.confirmAnnulationPrestation'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-refus-Pec/:idRefusPec',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 37,
            pageTitle: 'auxiliumApp.sinisterPec.refusPrestation'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-reprise-confirm-refus-Pec/:idConfirmRefPec',
        component: SinisterRepriseDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 100,
            functionality: 44,
            pageTitle: 'auxiliumApp.sinisterPec.confirmRefusPrestation'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'sinister-pec-derogation/:id',
        component: DerogationPecComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 85,
            functionality: 148,
            pageTitle: 'auxiliumApp.sinisterPec.derogation'
        },
        canActivate: [UserRouteAccessService],
    }

];
