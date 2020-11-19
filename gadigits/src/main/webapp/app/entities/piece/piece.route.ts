import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PieceComponent } from './piece.component';
import { PieceDetailComponent } from './piece-detail.component';
import { PiecePopupComponent } from './piece-dialog.component';
import { PieceDeletePopupComponent } from './piece-delete-dialog.component';

@Injectable()
export class PieceResolvePagingParams implements Resolve<any> {

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

export const pieceRoute: Routes = [
    {
        path: 'piece',
        component: PieceComponent,
        resolve: {
            'pagingParams': PieceResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.piece.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'piece/:id',
        component: PieceDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.piece.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const piecePopupRoute: Routes = [
    {
        path: 'piece-new',
        component: PiecePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.piece.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'piece/:id/edit',
        component: PiecePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.piece.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'piece/:id/delete',
        component: PieceDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.piece.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
