import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DetailsPiecesComponent } from './details-pieces.component';
import { DetailsPiecesDetailComponent } from './details-pieces-detail.component';
import { DetailsPiecesPopupComponent } from './details-pieces-rechange-dialog.component';
import { DetailsPiecesDeletePopupComponent } from './details-pieces-delete-dialog.component';

@Injectable()
export class DetailsPiecesResolvePagingParams implements Resolve<any> {

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

export const detailsPiecesRoute: Routes = [
    {
        path: 'details-pieces',
        component: DetailsPiecesComponent,
        resolve: {
            'pagingParams': DetailsPiecesResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.detailsPieces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'details-pieces/:id',
        component: DetailsPiecesDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsPieces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailsPiecesPopupRoute: Routes = [
    {
        path: 'details-pieces-new',
        component: DetailsPiecesPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsPieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'details-pieces/:id/edit',
        component: DetailsPiecesPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsPieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'details-pieces/:id/delete',
        component: DetailsPiecesDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.detailsPieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
