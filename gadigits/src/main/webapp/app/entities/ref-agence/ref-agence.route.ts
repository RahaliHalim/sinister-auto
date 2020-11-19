import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefAgenceComponent } from './ref-agence.component';
import { RefAgenceDetailComponent } from './ref-agence-detail.component';
import { RefAgencePopupComponent } from './ref-agence-dialog.component';
import { RefAgenceDeletePopupComponent } from './ref-agence-delete-dialog.component';

@Injectable()
export class RefAgenceResolvePagingParams implements Resolve<any> {

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

export const refAgenceRoute: Routes = [
    {
        path: 'ref-agence',
        component: RefAgenceComponent,
        resolve: {
            'pagingParams': RefAgenceResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refAgence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-agence/:id',
        component: RefAgenceDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refAgence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refAgencePopupRoute: Routes = [
    {
        path: 'ref-agence-new',
        component: RefAgencePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refAgence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-agence/:id/edit',
        component: RefAgencePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refAgence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-agence/:id/delete',
        component: RefAgenceDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refAgence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
