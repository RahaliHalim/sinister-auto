import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GrilleComponent } from './grille.component';
import { GrilleDetailComponent } from './grille-detail.component';
import { GrillePopupComponent } from './grille-dialog.component';
import { GrilleDeletePopupComponent } from './grille-delete-dialog.component';

@Injectable()
export class GrilleResolvePagingParams implements Resolve<any> {

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

export const grilleRoute: Routes = [
    {
        path: 'grille',
        component: GrilleComponent,
        resolve: {
            'pagingParams': GrilleResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.grille.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'grille/:id',
        component: GrilleDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.grille.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grillePopupRoute: Routes = [
    {
        path: 'grille-new',
        component: GrillePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.grille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grille/:id/edit',
        component: GrillePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.grille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grille/:id/delete',
        component: GrilleDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.grille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
