import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefPositionGaComponent } from './ref-position-ga.component';
import { RefPositionGaDetailComponent } from './ref-position-ga-detail.component';
import { RefPositionGaPopupComponent } from './ref-position-ga-dialog.component';
import { RefPositionGaDeletePopupComponent } from './ref-position-ga-delete-dialog.component';

@Injectable()
export class RefPositionGaResolvePagingParams implements Resolve<any> {

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

export const refPositionGaRoute: Routes = [
    {
        path: 'ref-position-ga',
        component: RefPositionGaComponent,
        resolve: {
            'pagingParams': RefPositionGaResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refPositionGa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-position-ga/:id',
        component: RefPositionGaDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refPositionGa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refPositionGaPopupRoute: Routes = [
    {
        path: 'ref-position-ga-new',
        component: RefPositionGaPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refPositionGa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-position-ga/:id/edit',
        component: RefPositionGaPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refPositionGa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-position-ga/:id/delete',
        component: RefPositionGaDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refPositionGa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
