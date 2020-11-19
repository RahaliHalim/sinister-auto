
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GroupMgmtComponent } from './group-management.component';
import { GroupDeleteComponent } from './group-popup-delete';

@Injectable()
export class GroupResolvePagingParams implements Resolve<any> {

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

export const groupRoute: Routes = [
    {
        path: 'group',
        component: GroupMgmtComponent,
        resolve: {
            'pagingParams': GroupResolvePagingParams
        },
        data: {
            pageTitle: 'AuxiliumApp.group.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupPopupRoute: Routes = [
    
    {
        path: 'group/:id/delete',
        component: GroupDeleteComponent,
        outlet: 'popup'
    }
];