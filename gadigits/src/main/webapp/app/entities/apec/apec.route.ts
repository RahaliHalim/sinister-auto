import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ApecComponent } from './apec.component';
import { ApecDetailComponent } from './apec-detail.component';
import { ApecPopupComponent, ApecDialogComponent } from './apec-dialog.component';
import { ApecDeletePopupComponent } from './apec-delete-dialog.component';
import { ApecModifComponent } from './apec-modif.component';
import { ApecForValidationComponent } from './apec-for-validation.component';
import { ApecImprimeComponent } from './apec-imprime.component';
import { ApecValidAssurComponent } from './apec-valid-assure.component';
import { ApecApprouveComponent } from './apec-a-approuver.component';

@Injectable()
export class ApecResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

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

export const apecRoute: Routes = [
    {
        path: 'apec',
        component: ApecComponent,
        data: {
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'apec/:id',
        component: ApecDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apec/:id/edit/:modif',
        component: ApecDialogComponent,
        data: {
            /*authorities: ['ROLE_USER'],
            entity: 55,
            functionality: [30, 31, 32, 33, 9],*/
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apec-approuv',
        component: ApecApprouveComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 55,
            functionality: 119,
            pageTitle: 'auxiliumApp.apec.approuverApec'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apec-modif',
        component: ApecModifComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 55,
            functionality: 120,
            pageTitle: 'auxiliumApp.apec.modifierApec'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apec-valid',
        component: ApecForValidationComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 55,
            functionality: 121,
            pageTitle: 'auxiliumApp.apec.validerApec'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apec-imprim',
        component: ApecImprimeComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 55,
            functionality: 123,
            pageTitle: 'auxiliumApp.apec.imprimerApec'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'apec-valid-assur',
        component: ApecValidAssurComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 55,
            functionality: 122,
            pageTitle: 'auxiliumApp.apec.validerParticipationAssure'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const apecPopupRoute: Routes = [
    {
        path: 'apec-new',
        component: ApecPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    /*{
        path: 'apec/:id/edit',
        component: ApecPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },*/
    
    {
        path: 'apec/:id/delete',
        component: ApecDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.apec.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
