import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AgentGeneralComponent } from './agent-general.component';
import { AgentGeneralDetailComponent } from './agent-general-detail.component';
import { AgentGeneralPopupComponent, AgentGeneralDialogComponent } from './agent-general-dialog.component';
import { AgentGeneralDeletePopupComponent } from './agent-general-delete-dialog.component';

@Injectable()
export class AgentGeneralResolvePagingParams implements Resolve<any> {

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

export const agentGeneralRoute: Routes = [
    {
        path: 'agent-general',
        component: AgentGeneralComponent,
        resolve: {
            'pagingParams': AgentGeneralResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.agentGeneral.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agent-general/:id',
        component: AgentGeneralDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.agentGeneral.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agent-general-new',
        component: AgentGeneralDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.agentGeneral.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'agent-general/:id/edit/:idPp',
        component: AgentGeneralDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.agentGeneral.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
];

export const agentGeneralPopupRoute: Routes = [
    {
        path: 'agent-general/:id/delete',
        component: AgentGeneralDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.agentGeneral.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
