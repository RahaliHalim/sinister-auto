import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BordereauComponent } from './bordereau.component';

@Injectable()
export class BordereauResolvePagingParams implements Resolve<any> {

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

export const bordereauRoute: Routes = [
    {
        path: 'bordereau',
        component: BordereauComponent,
        resolve: {
            'pagingParams': BordereauResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const BordereauPopupRoute: Routes = [


];
