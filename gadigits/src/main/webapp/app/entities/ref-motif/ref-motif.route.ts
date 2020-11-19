import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefMotifComponent } from './ref-motif.component';
import { RefMotifDetailComponent } from './ref-motif-detail.component';
import { RefMotifPopupComponent } from './ref-motif-dialog.component';
import { RefMotifDeletePopupComponent } from './ref-motif-delete-dialog.component';

@Injectable()
export class RefMotifResolvePagingParams implements Resolve<any> {

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

export const refMotifRoute: Routes = [
    {
        path: 'ref-motif',
        component: RefMotifComponent,
        resolve: {
            'pagingParams': RefMotifResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refMotif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-motif/:id',
        component: RefMotifDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refMotif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refMotifPopupRoute: Routes = [
    {
        path: 'ref-motif-new',
        component: RefMotifPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refMotif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-motif/:id/edit',
        component: RefMotifPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refMotif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-motif/:id/delete',
        component: RefMotifDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refMotif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
