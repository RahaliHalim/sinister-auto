import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DetailsMoComponent } from './details-mo.component';
import { DetailsMoDetailComponent } from './details-mo-detail.component';
import { DetailsMoPopupComponent } from './details-mo-dialog.component';
import { DetailsMoDeletePopupComponent } from './details-mo-delete-dialog.component';

@Injectable()
export class DetailsMoResolvePagingParams implements Resolve<any> {

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

export const detailsMoRoute: Routes = [
    {
        path: 'details-mo',
        component: DetailsMoComponent,
        resolve: {
            'pagingParams': DetailsMoResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.detailsMo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'details-mo/:id',
        component: DetailsMoDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsMo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailsMoPopupRoute: Routes = [
    {
        path: 'details-mo-new',
        component: DetailsMoPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsMo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'details-mo/:id/edit',
        component: DetailsMoPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsMo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'details-mo/:id/delete',
        component: DetailsMoDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsMo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
