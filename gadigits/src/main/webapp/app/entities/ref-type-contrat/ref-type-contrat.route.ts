import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefTypeContratComponent } from './ref-type-contrat.component';
import { RefTypeContratDetailComponent } from './ref-type-contrat-detail.component';
import { RefTypeContratPopupComponent } from './ref-type-contrat-dialog.component';
import { RefTypeContratDeletePopupComponent } from './ref-type-contrat-delete-dialog.component';

@Injectable()
export class RefTypeContratResolvePagingParams implements Resolve<any> {

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

export const refTypeContratRoute: Routes = [
    {
        path: 'ref-type-contrat',
        component: RefTypeContratComponent,
        resolve: {
            'pagingParams': RefTypeContratResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refTypeContrat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-type-contrat/:id',
        component: RefTypeContratDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeContrat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refTypeContratPopupRoute: Routes = [
    {
        path: 'ref-type-contrat-new',
        component: RefTypeContratPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-contrat/:id/edit',
        component: RefTypeContratPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-contrat/:id/delete',
        component: RefTypeContratDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
