import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefEtatBsComponent } from './ref-etat-bs.component';
import { RefEtatBsDetailComponent } from './ref-etat-bs-detail.component';
import { RefEtatBsPopupComponent } from './ref-etat-bs-dialog.component';
import { RefEtatBsDeletePopupComponent } from './ref-etat-bs-delete-dialog.component';

@Injectable()
export class RefEtatBsResolvePagingParams implements Resolve<any> {

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

export const refEtatBsRoute: Routes = [
    {
        path: 'ref-etat-bs',
        component: RefEtatBsComponent,
        resolve: {
            'pagingParams': RefEtatBsResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refEtatBs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-etat-bs/:id',
        component: RefEtatBsDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatBs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refEtatBsPopupRoute: Routes = [
    {
        path: 'ref-etat-bs-new',
        component: RefEtatBsPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatBs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-etat-bs/:id/edit',
        component: RefEtatBsPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatBs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-etat-bs/:id/delete',
        component: RefEtatBsDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatBs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
