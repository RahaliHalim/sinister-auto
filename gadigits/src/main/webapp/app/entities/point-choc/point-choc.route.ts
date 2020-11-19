import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PointChocComponent } from './point-choc.component';
import { PointChocDetailComponent } from './point-choc-detail.component';
import { PointChocPopupComponent } from './point-choc-dialog.component';
import { PointChocDialogComponent } from './point-choc-dialog.component';
import { PointChocDeletePopupComponent } from './point-choc-delete-dialog.component';

@Injectable()
export class PointChocResolvePagingParams implements Resolve<any> {

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

export const pointChocRoute: Routes = [
    {
        path: 'point-choc',
        component: PointChocComponent,
        resolve: {
            'pagingParams': PointChocResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.pointChoc.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'point-choc/:id',
        component: PointChocDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.pointChoc.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pointChocPopupRoute: Routes = [
    {
        path: 'point-choc-new',
        component: PointChocDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.pointChoc.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'point-choc/:id/edit',
        component: PointChocDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.pointChoc.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'point-choc/:id/delete',
        component: PointChocDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.pointChoc.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
