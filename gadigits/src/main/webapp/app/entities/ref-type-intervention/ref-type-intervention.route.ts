import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefTypeInterventionComponent } from './ref-type-intervention.component';
import { RefTypeInterventionDetailComponent } from './ref-type-intervention-detail.component';
import { RefTypeInterventionPopupComponent } from './ref-type-intervention-dialog.component';
import { RefTypeInterventionDeletePopupComponent } from './ref-type-intervention-delete-dialog.component';

@Injectable()
export class RefTypeInterventionResolvePagingParams implements Resolve<any> {

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

export const refTypeInterventionRoute: Routes = [
    {
        path: 'ref-type-intervention',
        component: RefTypeInterventionComponent,
        resolve: {
            'pagingParams': RefTypeInterventionResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refTypeIntervention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-type-intervention/:id',
        component: RefTypeInterventionDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeIntervention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refTypeInterventionPopupRoute: Routes = [
    {
        path: 'ref-type-intervention-new',
        component: RefTypeInterventionPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeIntervention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-intervention/:id/edit',
        component: RefTypeInterventionPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeIntervention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-type-intervention/:id/delete',
        component: RefTypeInterventionDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refTypeIntervention.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
