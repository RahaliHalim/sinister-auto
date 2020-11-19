import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReparationAjoutSaisieDevisComponent } from './reparation-ajout-saisie-devis.component';
import { GenerationAPECComponent } from './generation-apec.component';
import { GenerationBSComponent } from './genere-bs.component';
import { InstanceReparationComponent } from './instance-reparation.component';
import { SignatureAccordComponent } from './signature-accord.component';
import { SignatureBSComponent } from './signature-bs.component';
//import { PieceJointePrestationPopupComponent } from '../prestation-pec/piece-jointe-prestation-dialog.component';
import { RevueValidationDevisComponent } from './validation-devis.component';
import { DemontageComponent } from './demontage.component';
import { VerificationComponent } from './verification.component';
import { ConfirmationComponent } from './confirmation.component';
import { ExpertMissionaryComponent } from '../sinister-pec/expert-missionary.component';
import { ExpertOpinionComponent } from './expert-opinion.component';
import { RectificationComponent } from './rectification.component';
import { PieceJointeSignatureAccordComponent } from './piece-jointe-signature-accord.component';
import { ConfirmationModificationPrixComponent } from './confirmation-modification-prix.component';
import { ConfirmationDevisComplementaryComponent } from './confirmation-devis-complementary.component';
@Injectable()
export class PrestationPECResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const reparationRoute: Routes = [
    {
        path: 'ajout-saisie-devis',
        component: ReparationAjoutSaisieDevisComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 92,
            pageTitle: 'auxiliumApp.sinisterPec.home.receptionVehicule'
        },
        canActivate: [UserRouteAccessService]
    }
    ,
    {
        path: 'rectification-devis',
        component: RectificationComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 98,
            pageTitle: 'auxiliumApp.sinisterPec.home.rectificationDevis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'generer-accord',
        component: GenerationAPECComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {

            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'generer-bon-sortie',
        component: GenerationBSComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 114,
            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'facturation-devis-complementaire',
        component: InstanceReparationComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 1,
            pageTitle: 'auxiliumApp.sinisterPec.home.instanceReparation'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'signature-accord',
        component: SignatureAccordComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 55,
            functionality: 102,
            pageTitle: 'auxiliumApp.apec.home.signatureAccord'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'signature-bs',
        component: SignatureBSComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {

            pageTitle: 'auxiliumApp.sinisterPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demontage-devis',
        component: DemontageComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 113,
            pageTitle: 'auxiliumApp.sinisterPec.home.demontage'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'confirmation-devis',
        component: ConfirmationComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 96,
            pageTitle: 'auxiliumApp.sinisterPec.home.confirmationDevis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'confirmation-devis-complemantary',
        component: ConfirmationDevisComplementaryComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 103,
            pageTitle: 'auxiliumApp.sinisterPec.home.confirmationDevisComplementaire'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'verification',
        component: VerificationComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 94,
            pageTitle: 'auxiliumApp.sinisterPec.home.verification'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'revue-validation-devis',
        component: RevueValidationDevisComponent,
        resolve: {

            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 100,
            pageTitle: 'auxiliumApp.sinisterPec.home.revueValidationDevis'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'expert-opinion',
        component: ExpertOpinionComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 48,
            functionality: 117,
            pageTitle: 'auxiliumApp.sinisterPec.home.avisExpert'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'confirmation-modification-prix',
        component: ConfirmationModificationPrixComponent,
        resolve: {
            'pagingParams': PrestationPECResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 74,
            pageTitle: 'auxiliumApp.sinisterPec.home.confirmationModificationPrix'
        },
        canActivate: [UserRouteAccessService]
    }

];

export const prestationPECPopupRoute: Routes = [
    
    {
        path: 'piece-jointe-signature-accord',
        component: PieceJointeSignatureAccordComponent,
        data: {
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }

];