import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PieceJointeComponent } from './piece-jointe.component';
import { PieceJointeDetailComponent } from './piece-jointe-detail.component';
import { PieceJointePopupComponent } from './piece-jointe-dialog.component';
import { PieceJointeDeletePopupComponent } from './piece-jointe-delete-dialog.component';

@Injectable()
export class PieceJointeResolvePagingParams implements Resolve<any> {

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

export const pieceJointeRoute: Routes = [
    {
        path: 'piece-jointe',
        component: PieceJointeComponent,
        resolve: {
            'pagingParams': PieceJointeResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'piece-jointe/:id',
        component: PieceJointeDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pieceJointePopupRoute: Routes = [
    {
        path: 'piece-jointe-new',
        component: PieceJointePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'piece-jointe/:id/edit',
        component: PieceJointePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'piece-jointe/:id/delete',
        component: PieceJointeDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.pieceJointe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
