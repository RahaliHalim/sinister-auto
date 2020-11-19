import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReparateurComponent } from './reparateur.component';
import { ReparateurDetailComponent } from './reparateur-detail.component';
import { ReparateurPopupComponent, ReparateurDialogComponent } from './reparateur-dialog.component';
import { ReparateurDeletePopupComponent } from './reparateur-delete-dialog.component';

@Injectable()
export class ReparateurResolvePagingParams implements Resolve<any> {

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

export const reparateurRoute: Routes = [
    {
        path: 'reparateur',
        component: ReparateurComponent,
        resolve: {
            'pagingParams': ReparateurResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 7,
            functionality: 1,
            pageTitle: 'auxiliumApp.reparateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reparateur/:id',
        component: ReparateurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 7,
            functionality: 1,
            pageTitle: 'auxiliumApp.reparateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reparateurPopupRoute: Routes = [
    {
        path: 'reparateur-new',
        component: ReparateurDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 7,
            functionality: 2,
            pageTitle: 'auxiliumApp.reparateur.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'reparateur/:idRep/edit',
        component: ReparateurDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 7,
            functionality: 3,
            pageTitle: 'auxiliumApp.reparateur.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'reparateur/:id/delete',
        component: ReparateurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 7,
            functionality: 5,
            pageTitle: 'auxiliumApp.reparateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
