import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserCelluleComponent } from './user-cellule.component';
import { UserCelluleDetailComponent } from './user-cellule-detail.component';
import { UserCellulePopupComponent } from './user-cellule-dialog.component';
import { UserCelluleDeletePopupComponent } from './user-cellule-delete-dialog.component';

@Injectable()
export class UserCelluleResolvePagingParams implements Resolve<any> {

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

export const userCelluleRoute: Routes = [
    {
        path: 'user-cellule',
        component: UserCelluleComponent,
        resolve: {
            'pagingParams': UserCelluleResolvePagingParams
        },
        data: {
            pageTitle: 'AuxiliumApp.userCellule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-cellule/:id',
        component: UserCelluleDetailComponent,
        data: {
            pageTitle: 'AuxiliumApp.userCellule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userCellulePopupRoute: Routes = [
    {
        path: 'user-cellule-new',
        component: UserCellulePopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.userCellule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-cellule/:id/edit',
        component: UserCellulePopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.userCellule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-cellule/:id/delete',
        component: UserCelluleDeletePopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.userCellule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
