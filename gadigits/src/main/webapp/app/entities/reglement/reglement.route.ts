import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReglementComponent } from './reglement.component';
import { ReglementDetailComponent } from './reglement-detail.component';
import { ReglementPopupComponent } from './reglement-dialog.component';
import { ReglementDeletePopupComponent } from './reglement-delete-dialog.component';

@Injectable()
export class ReglementResolvePagingParams implements Resolve<any> {

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

export const reglementRoute: Routes = [
    {
        path: 'reglement',
        component: ReglementComponent,
        resolve: {
            'pagingParams': ReglementResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.reglement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reglement/:id',
        component: ReglementDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.reglement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reglementPopupRoute: Routes = [
    {
        path: 'reglement-new',
        component: ReglementPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.reglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reglement/:id/edit',
        component: ReglementPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.reglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reglement/:id/delete',
        component: ReglementDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.reglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
