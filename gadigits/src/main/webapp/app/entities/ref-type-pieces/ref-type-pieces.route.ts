import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefTypePiecesComponent } from './ref-type-pieces.component';
import { RefTypePiecesDetailComponent } from './ref-type-pieces-detail.component';
import { RefTypePiecesPopupComponent } from './ref-type-pieces-dialog.component';
import { RefTypePiecesDeletePopupComponent } from './ref-type-pieces-delete-dialog.component';

@Injectable()
export class RefTypePiecesResolvePagingParams implements Resolve<any> {

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

export const refTypePiecesRoute: Routes = [
    {
        path: 'ref-type-pieces',
        component: RefTypePiecesComponent,
        resolve: {
            'pagingParams': RefTypePiecesResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refTypePieces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-type-pieces/:id',
        component: RefTypePiecesDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePieces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refTypePiecesPopupRoute: Routes = [
    {
        path: 'ref-type-pieces-new',
        component: RefTypePiecesPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-pieces/:id/edit',
        component: RefTypePiecesPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-pieces/:id/delete',
        component: RefTypePiecesDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypePieces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
