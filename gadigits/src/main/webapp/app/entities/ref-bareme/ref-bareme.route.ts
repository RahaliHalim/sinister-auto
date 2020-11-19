import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefBaremeComponent } from './ref-bareme.component';
import { RefBaremeDetailComponent } from './ref-bareme-detail.component';
import { RefBaremePopupComponent } from './ref-bareme-dialog.component';
import { RefBaremeDeletePopupComponent } from './ref-bareme-delete-dialog.component';
import { RefBaremePopComponent } from './ref-bareme-popup-detail.component';

@Injectable()
export class RefBaremeResolvePagingParams implements Resolve<any> {

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

export const refBaremeRoute: Routes = [
    {
        path: 'ref-bareme',
        component: RefBaremeComponent,
        resolve: {
            'pagingParams': RefBaremeResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refBareme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-bareme/:id',
        component: RefBaremeDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refBareme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refBaremePopupRoute: Routes = [
    {
        path: 'ref-bareme-new',
        component: RefBaremePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refBareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-bareme/:id/edit',
        component: RefBaremePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refBareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-bareme/:id/delete',
        component: RefBaremeDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refBareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-bareme-pop',
        component: RefBaremePopComponent,
        data: {
            pageTitle: 'auxiliumApp.refBareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];