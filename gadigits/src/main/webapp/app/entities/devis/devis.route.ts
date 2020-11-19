import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DevisComponent } from './devis.component';
import { DevisDetailComponent } from './devis-detail.component';
import { DevisPopupComponent } from './devis-dialog.component';
import { DevisDialogComponent } from './devis-dialog.component';
import { DevisRefusPopupComponent } from './devis-refus-dialog.component';
import { FacturationComponent } from './facturation.component';
import { DevisDeletePopupComponent } from './devis-delete-dialog.component';
import { DevisAffecteExpertPopupComponent } from './devis-affecteExpert-dialog.component';
import { QuotationDialogComponent } from './quotation.dialog.component';
import { GTEstimateComponent } from './gt-estimate.component';

@Injectable()
export class DevisResolvePagingParams implements Resolve<any> {

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

export const devisRoute: Routes = [
    {
        path: 'devis',
        component: DevisComponent,
        resolve: {
            'pagingParams': DevisResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'devis/:id',
        component: DevisDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demontage/:idSinisterPecDemontage',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 114,
            pageTitle: 'auxiliumApp.sinisterPec.home.demontage'
        },
        canActivate: [UserRouteAccessService],
    }
    ,
    {
        path: 'expert-opinion/:idPrestationPec',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 48,
            functionality: 29,
            pageTitle: 'auxiliumApp.devis.home.avisExpert'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'validation-devis/:idValidationDevisPrestationPec',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 101,
            pageTitle: 'auxiliumApp.devis.home.validation'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'confirmation-devis/:idConfirmePec',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 97,
            pageTitle: 'auxiliumApp.sinisterPec.home.confirmationDevis'
        },
        canActivate: [UserRouteAccessService],

    }
    ,{
        path: 'confirmation-devis-complementary/:idConfirmePecComplementaire',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 41,
            pageTitle: 'auxiliumApp.sinisterPec.home.confirmationDevis'
        },
        canActivate: [UserRouteAccessService],

    }
    ,
    {
        path: 'verification-devis/:idVerificationPec',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 95,
            pageTitle: 'auxiliumApp.devis.home.verification'
        },
        canActivate: [UserRouteAccessService],

    }
    ,
    {
        path: 'facturation/:idPecFacturation',
        component: FacturationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 35,
            pageTitle: 'auxiliumApp.devis.home.instanceReparation'
        },
        canActivate: [UserRouteAccessService],

    },
    {
        path: 'modification-prix/:idModificationPrix',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 99,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],

    },
    {
        path: 'confirmation-modification-prix/:idConfirmationModificationPrix',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 118,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],

    },{
        path: 'devis-gt-estimate/:idEstimate/:idSinPec',
        component: GTEstimateComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 93,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
    },


];

export const devisPopupRoute: Routes = [
    {
        path: 'devis-new/:prestationId',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 93,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'complementary-devis-new/:prestationDevisComplementaryId/:apecId',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 102,
            functionality: 46,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'quotation/:prestationId/edit/:id',
        component: QuotationDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'devis/:prestationId/edit/:id',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 99,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
    },


    {
        path: 'devis/:prestationId/edit/:id/:cpl',
        component: DevisDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 42,
            functionality: 99,
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
    },

    {
        path: 'devis/:id/delete',
        component: DevisDeletePopupComponent,
        data: {

            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'devis/:id/affecteExpert',
        component: DevisAffecteExpertPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },

    {
        path: 'devis/:id/refus',
        component: DevisRefusPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
