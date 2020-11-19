import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefNatureExpertiseComponent } from './ref-nature-expertise.component';
import { RefNatureExpertiseDetailComponent } from './ref-nature-expertise-detail.component';
import { RefNatureExpertisePopupComponent } from './ref-nature-expertise-dialog.component';
import { RefNatureExpertiseDeletePopupComponent } from './ref-nature-expertise-delete-dialog.component';

@Injectable()
export class RefNatureExpertiseResolvePagingParams implements Resolve<any> {

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

export const refNatureExpertiseRoute: Routes = [
    {
        path: 'ref-nature-expertise',
        component: RefNatureExpertiseComponent,
        resolve: {
            'pagingParams': RefNatureExpertiseResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refNatureExpertise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-nature-expertise/:id',
        component: RefNatureExpertiseDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureExpertise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refNatureExpertisePopupRoute: Routes = [
    {
        path: 'ref-nature-expertise-new',
        component: RefNatureExpertisePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureExpertise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-nature-expertise/:id/edit',
        component: RefNatureExpertisePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureExpertise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-nature-expertise/:id/delete',
        component: RefNatureExpertiseDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refNatureExpertise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
