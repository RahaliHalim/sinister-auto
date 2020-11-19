import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TiersComponent } from './tiers.component';
import { TiersDetailComponent } from './tiers-detail.component';
import { TiersPopupComponent } from './tiers-dialog.component';
import { TiersDeletePopupComponent } from './tiers-delete-dialog.component';
import { TiersDialogForDetailComponent } from   './tiers-dialog-for-detail.component';

@Injectable()
export class TiersResolvePagingParams implements Resolve<any> {

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

export const tiersRoute: Routes = [
    {
        path: 'tiers',
        component: TiersComponent,
        resolve: {
            'pagingParams': TiersResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.tiers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tiers/:id',
        component: TiersDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.tiers.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tiersPopupRoute: Routes = [
    {
        path: 'tiers-new',
        component: TiersPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.tiers.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'tiers/:id/edit',
        component: TiersPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.tiers.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'tiers/:id/delete',
        component: TiersDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.tiers.home.title'
        },
        canActivate: [UserRouteAccessService],
    }
];
