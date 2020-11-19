import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ObservationComponent } from './observation.component';
import { ObservationDetailComponent } from './observation-detail.component';
import { ObservationPopupComponent } from './observation-dialog.component';
import { ObservationDeletePopupComponent } from './observation-delete-dialog.component';

@Injectable()
export class ObservationResolvePagingParams implements Resolve<any> {

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

export const observationRoute: Routes = [
    {
        path: 'observation',
        component: ObservationComponent,
        resolve: {
            'pagingParams': ObservationResolvePagingParams
        },
        data: {
            pageTitle: 'AuxiliumApp.observation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'observation/:id',
        component: ObservationDetailComponent,
        data: {
            pageTitle: 'AuxiliumApp.observation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const observationPopupRoute: Routes = [
    {
        path: 'observation-new',
        component: ObservationPopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.observation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'observation/:id/edit',
        component: ObservationPopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.observation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'observation/:id/delete',
        component: ObservationDeletePopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.observation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
