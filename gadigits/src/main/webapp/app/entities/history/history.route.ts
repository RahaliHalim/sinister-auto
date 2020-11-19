import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';


import { HistoryPopupDetail } from './history-popup-detail';

@Injectable()
export class AgencyResolvePagingParams implements Resolve<any> {

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
export const historyRoute: Routes = [
   
];

export const historyPopupRoute: Routes = [
   {
        path: 'HistoryPopup',
        component: HistoryPopupDetail,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.agency.home.HistoryAgence'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
