import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ServiceAssuranceComponent } from './service-assurance.component';
import { ServiceAssuranceDetailComponent } from './service-assurance-detail.component';
import { ServiceAssurancePopupComponent } from './service-assurance-dialog.component';
import { ServiceAssuranceDeletePopupComponent } from './service-assurance-delete-dialog.component';

@Injectable()
export class ServiceAssuranceResolvePagingParams implements Resolve<any> {

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

export const serviceAssuranceRoute: Routes = [
    {
        path: 'service-assurance',
        component: ServiceAssuranceComponent,
        resolve: {
            'pagingParams': ServiceAssuranceResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.serviceAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'service-assurance/:id',
        component: ServiceAssuranceDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.serviceAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const serviceAssurancePopupRoute: Routes = [
    {
        path: 'service-assurance-new',
        component: ServiceAssurancePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.serviceAssurance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-assurance/:id/edit',
        component: ServiceAssurancePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.serviceAssurance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-assurance/:id/delete',
        component: ServiceAssuranceDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.serviceAssurance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
