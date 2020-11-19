import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefZoneGeoComponent } from './ref-zone-geo.component';
import { RefZoneGeoDetailComponent } from './ref-zone-geo-detail.component';
import { RefZoneGeoPopupComponent } from './ref-zone-geo-dialog.component';
import { RefZoneGeoDeletePopupComponent } from './ref-zone-geo-delete-dialog.component';

@Injectable()
export class RefZoneGeoResolvePagingParams implements Resolve<any> {

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

export const refZoneGeoRoute: Routes = [
    {
        path: 'ref-zone-geo',
        component: RefZoneGeoComponent,
        resolve: {
            'pagingParams': RefZoneGeoResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refZoneGeo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-zone-geo/:id',
        component: RefZoneGeoDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refZoneGeo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refZoneGeoPopupRoute: Routes = [
    {
        path: 'ref-zone-geo-new',
        component: RefZoneGeoPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refZoneGeo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-zone-geo/:id/edit',
        component: RefZoneGeoPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refZoneGeo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-zone-geo/:id/delete',
        component: RefZoneGeoDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refZoneGeo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
