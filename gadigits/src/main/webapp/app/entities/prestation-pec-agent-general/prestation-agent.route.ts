import { DemandPecOutstandingComponent } from './demand-pec-outstanding.component';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import {DemandPecSentComponent} from "./demand-pec-sent.component";
import {DemandPecAccordComponent} from "./demand-pec-accord.component";

@Injectable()
export class DossierResolvePagingParams implements Resolve<any> {

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

export const dossierRoute: Routes = [
    {
        path: 'outstanding',
        component: DemandPecOutstandingComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demands-agent',
        component: DemandPecSentComponent,
        data: {
            pageTitle: 'auxiliumApp.dossier.demand.home'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'agreement-validation',
        component: DemandPecAccordComponent,
        data: {
            pageTitle: 'auxiliumApp.dossier.demand.home'
        },
        canActivate: [UserRouteAccessService]
    }

];