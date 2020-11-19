import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BonSortieComponent } from './bon-sortie.component';
import { BonSortieDetailComponent } from './bon-sortie-detail.component';
import { BonSortiePopupComponent } from './bon-sortie-dialog.component';
import { BonSortieDeletePopupComponent } from './bon-sortie-delete-dialog.component';

@Injectable()
export class BonSortieResolvePagingParams implements Resolve<any> {

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

export const bonSortieRoute: Routes = [
    {
        path: 'bon-sortie',
        component: BonSortieComponent,
        resolve: {
            'pagingParams': BonSortieResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.bonSortie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bon-sortie/:id',
        component: BonSortieDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.bonSortie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bonSortiePopupRoute: Routes = [
    {
        path: 'bon-sortie-new',
        component: BonSortiePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.bonSortie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bon-sortie/:id/edit',
        component: BonSortiePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.bonSortie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bon-sortie/:id/delete',
        component: BonSortieDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.bonSortie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
