import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefModeReglementComponent } from './ref-mode-reglement.component';
import { RefModeReglementDetailComponent } from './ref-mode-reglement-detail.component';
import { RefModeReglementPopupComponent } from './ref-mode-reglement-dialog.component';
import { RefModeReglementDeletePopupComponent } from './ref-mode-reglement-delete-dialog.component';

@Injectable()
export class RefModeReglementResolvePagingParams implements Resolve<any> {

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

export const refModeReglementRoute: Routes = [
    {
        path: 'ref-mode-reglement',
        component: RefModeReglementComponent,
        resolve: {
            'pagingParams': RefModeReglementResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refModeReglement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-mode-reglement/:id',
        component: RefModeReglementDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeReglement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refModeReglementPopupRoute: Routes = [
    {
        path: 'ref-mode-reglement-new',
        component: RefModeReglementPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeReglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-mode-reglement/:id/edit',
        component: RefModeReglementPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeReglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-mode-reglement/:id/delete',
        component: RefModeReglementDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeReglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
