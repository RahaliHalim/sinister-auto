import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefModeGestionComponent } from './ref-mode-gestion.component';
import { RefModeGestionDetailComponent } from './ref-mode-gestion-detail.component';
import { RefModeGestionPopupComponent } from './ref-mode-gestion-dialog.component';
import { RefModeGestionDeletePopupComponent } from './ref-mode-gestion-delete-dialog.component';

@Injectable()
export class RefModeGestionResolvePagingParams implements Resolve<any> {

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

export const refModeGestionRoute: Routes = [
    {
        path: 'ref-mode-gestion',
        component: RefModeGestionComponent,
        resolve: {
            'pagingParams': RefModeGestionResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refModeGestion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-mode-gestion/:id',
        component: RefModeGestionDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeGestion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refModeGestionPopupRoute: Routes = [
    {
        path: 'ref-mode-gestion-new',
        component: RefModeGestionPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeGestion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-mode-gestion/:id/edit',
        component: RefModeGestionPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeGestion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-mode-gestion/:id/delete',
        component: RefModeGestionDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refModeGestion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
