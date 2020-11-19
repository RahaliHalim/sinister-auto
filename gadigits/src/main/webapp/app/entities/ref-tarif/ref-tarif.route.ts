import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefTarifComponent } from './ref-tarif.component';
import { RefTarifDetailComponent } from './ref-tarif-detail.component';
import { RefTarifPopupComponent } from './ref-tarif-dialog.component';
import { RefTarifDeletePopupComponent } from './ref-tarif-delete-dialog.component';

@Injectable()
export class RefTarifResolvePagingParams implements Resolve<any> {

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

export const refTarifRoute: Routes = [
    {
        path: 'ref-tarif',
        component: RefTarifComponent,
        resolve: {
            'pagingParams': RefTarifResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 3,
            functionality: 1,
            pageTitle: 'auxiliumApp.refTarif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-tarif/:id',
        component: RefTarifDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refTarif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
export const refTarifPopupRoute: Routes = [
    {
        path: 'ref-tarif-new',
        component: RefTarifPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-tarif/:id/edit',
        component: RefTarifPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-tarif/:id/delete',
        component: RefTarifDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTarif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
