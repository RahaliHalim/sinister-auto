import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LienComponent } from './lien.component';
import { LienDetailComponent } from './lien-detail.component';
import { LienPopupComponent } from './lien-dialog.component';
import { LienDeletePopupComponent } from './lien-delete-dialog.component';

@Injectable()
export class LienResolvePagingParams implements Resolve<any> {

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

export const lienRoute: Routes = [
    {
        path: 'lien',
        component: LienComponent,
        resolve: {
            'pagingParams': LienResolvePagingParams
        },
        data: {
            pageTitle: 'AuxiliumApp.lien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lien/:id',
        component: LienDetailComponent,
        data: {
            pageTitle: 'AuxiliumApp.lien.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lienPopupRoute: Routes = [
    {
        path: 'lien-new',
        component: LienPopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.lien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lien/:id/edit',
        component: LienPopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.lien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lien/:id/delete',
        component: LienDeletePopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.lien.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
