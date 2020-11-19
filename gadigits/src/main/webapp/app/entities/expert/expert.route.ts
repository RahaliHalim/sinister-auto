import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ExpertComponent } from './expert.component';
import { ExpertDetailComponent } from './expert-detail.component';
import { ExpertPopupComponent } from './expert-dialog.component';
import { ExpertDialogComponent } from './expert-dialog.component';
import { ExpertDeletePopupComponent } from './expert-delete-dialog.component';

@Injectable()
export class ExpertResolvePagingParams implements Resolve<any> {

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

export const expertRoute: Routes = [
    {
        path: 'expert',
        component: ExpertComponent,
        resolve: {
            'pagingParams': ExpertResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 16,
            functionality: 1,
            pageTitle: 'auxiliumApp.expert.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'expert/:id',
        component: ExpertDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 16,
            functionality: 1,
            pageTitle: 'auxiliumApp.expert.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const expertPopupRoute: Routes = [
    {
        path: 'expert-new',
        component: ExpertDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 16,
            functionality: 2,            
            pageTitle: 'auxiliumApp.expert.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'expert/:id/edit',
        component: ExpertDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 16,
            functionality: 3,
            pageTitle: 'auxiliumApp.expert.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'expert/:id/delete',
        component: ExpertDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 16,
            functionality: 5,
            pageTitle: 'auxiliumApp.expert.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
