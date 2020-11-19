import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GageoComponent } from './ga-geo.component';

@Injectable()
export class GageoResolvePagingParams implements Resolve<any> {

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

export const gaGeoRoute: Routes = [
    {
        path: 'ga-geo',
        component: GageoComponent,
        resolve: {
            'pagingParams': GageoResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.gaGeo.home.title'
        },
        canActivate: [UserRouteAccessService]
    } 
];



