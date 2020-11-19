import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TarifComponent } from './tarif.component';
import { TarifDetailComponent } from './tarif-detail.component';
import { TarifPopupComponent } from './tarif-dialog.component';
import { TarifDeletePopupComponent } from './tarif-delete-dialog.component';

@Injectable()
export class TarifResolvePagingParams implements Resolve<any> {

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

export const tarifRoute: Routes = [
    {
        path: 'tarif',
        component: TarifComponent,
        resolve: {
            'pagingParams': TarifResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tarif/:id',
        component: TarifDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tarifPopupRoute: Routes = [
    {
        path: 'tarif-new',
        component: TarifPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tarif/:id/edit',
        component: TarifPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tarif/:id/delete',
        component: TarifDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.tarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
