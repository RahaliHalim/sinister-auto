import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefTypePjComponent } from './ref-type-pj.component';
import { RefTypePjDetailComponent } from './ref-type-pj-detail.component';
import { RefTypePjPopupComponent } from './ref-type-pj-dialog.component';
import { RefTypePjDeletePopupComponent } from './ref-type-pj-delete-dialog.component';

@Injectable()
export class RefTypePjResolvePagingParams implements Resolve<any> {

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

export const refTypePjRoute: Routes = [
    {
        path: 'ref-type-pj',
        component: RefTypePjComponent,
        resolve: {
            'pagingParams': RefTypePjResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refTypePj.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-type-pj/:id',
        component: RefTypePjDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePj.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refTypePjPopupRoute: Routes = [
    {
        path: 'ref-type-pj-new',
        component: RefTypePjPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePj.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-pj/:id/edit',
        component: RefTypePjPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePj.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-pj/:id/delete',
        component: RefTypePjDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePj.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
