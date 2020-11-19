import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';
import { FeatureComponent } from './feature.component';
import { FeatureDeleteComponent } from './feature-popup-delete';


@Injectable()
export class FeatureResolvePagingParams implements Resolve<any> {

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

export const featureRoute: Routes = [
    {
        path: 'feature',
        component: FeatureComponent,
        resolve: {
            'pagingParams': FeatureResolvePagingParams
        },
        data: {
            pageTitle: 'AuxiliumApp.profil.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
export const featurePopupRoute: Routes = [

    {
        path: 'feature/:id/delete',
        component: FeatureDeleteComponent,
        outlet: 'popup'
    }
];


