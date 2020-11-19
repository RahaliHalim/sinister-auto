import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SysActionUtilisateurComponent } from './sys-action-utilisateur.component';
import { SysActionUtilisateurDetailComponent } from './sys-action-utilisateur-detail.component';
import { SysActionUtilisateurPopupComponent } from './sys-action-utilisateur-dialog.component';
import { SysActionUtilisateurDeletePopupComponent } from './sys-action-utilisateur-delete-dialog.component';

@Injectable()
export class SysActionUtilisateurResolvePagingParams implements Resolve<any> {

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

export const sysActionUtilisateurRoute: Routes = [
    {
        path: 'sys-action-utilisateur',
        component: SysActionUtilisateurComponent,
        resolve: {
            'pagingParams': SysActionUtilisateurResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.sysActionUtilisateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sys-action-utilisateur/:id',
        component: SysActionUtilisateurDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.sysActionUtilisateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sysActionUtilisateurPopupRoute: Routes = [
    {
        path: 'sys-action-utilisateur-new',
        component: SysActionUtilisateurPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.sysActionUtilisateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sys-action-utilisateur/:id/edit',
        component: SysActionUtilisateurPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.sysActionUtilisateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sys-action-utilisateur/:id/delete',
        component: SysActionUtilisateurDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.sysActionUtilisateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
