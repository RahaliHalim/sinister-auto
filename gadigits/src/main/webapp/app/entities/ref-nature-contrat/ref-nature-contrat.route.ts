import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefNatureContratComponent } from './ref-nature-contrat.component';
import { RefNatureContratDetailComponent } from './ref-nature-contrat-detail.component';
import { RefNatureContratPopupComponent } from './ref-nature-contrat-dialog.component';
import { RefNatureContratDeletePopupComponent } from './ref-nature-contrat-delete-dialog.component';

@Injectable()
export class RefNatureContratResolvePagingParams implements Resolve<any> {

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

export const refNatureContratRoute: Routes = [
    {
        path: 'ref-nature-contrat',
        component: RefNatureContratComponent,
        resolve: {
            'pagingParams': RefNatureContratResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refNatureContrat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-nature-contrat/:id',
        component: RefNatureContratDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureContrat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refNatureContratPopupRoute: Routes = [
    {
        path: 'ref-nature-contrat-new',
        component: RefNatureContratPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-nature-contrat/:id/edit',
        component: RefNatureContratPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-nature-contrat/:id/delete',
        component: RefNatureContratDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
