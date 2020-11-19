import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefFractionnementComponent } from './ref-fractionnement.component';
import { RefFractionnementDetailComponent } from './ref-fractionnement-detail.component';
import { RefFractionnementPopupComponent } from './ref-fractionnement-dialog.component';
import { RefFractionnementDeletePopupComponent } from './ref-fractionnement-delete-dialog.component';

@Injectable()
export class RefFractionnementResolvePagingParams implements Resolve<any> {

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

export const refFractionnementRoute: Routes = [
    {
        path: 'ref-fractionnement',
        component: RefFractionnementComponent,
        resolve: {
            'pagingParams': RefFractionnementResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refFractionnement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-fractionnement/:id',
        component: RefFractionnementDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refFractionnement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refFractionnementPopupRoute: Routes = [
    {
        path: 'ref-fractionnement-new',
        component: RefFractionnementPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refFractionnement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-fractionnement/:id/edit',
        component: RefFractionnementPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refFractionnement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-fractionnement/:id/delete',
        component: RefFractionnementDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refFractionnement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
