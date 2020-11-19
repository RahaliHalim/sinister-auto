import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AgencyComponent } from './agency.component';
import { AgencyDetailComponent } from './agency-detail.component';
import { AgencyDialogComponent } from './agency-dialog.component';
import { AgencyPopupComponent } from './agency-dialog.component';
import { AgencyDeletePopupComponent } from './agency-delete-dialog.component';
import { AgenceConcessionnaireComponent } from './agenceConcessionnaire.component';
import { AgenceConcessionnaireDetailComponent } from './agenceConcessionnaire-detail.component';
import { AgenceConcessionnaireDialogComponent } from './agenceConcessionnaire-dialog.component';
import { AgenceConcessionnaireDeletePopupComponent } from './agenceConcessionnaire-delete-dialog.component';
import { AgenceConcessPopupComponent } from './agenceConcessHistoryPopup';
import { AgenceConcessPopupDetail } from './agenceConcessHistoryPopup';

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
export const agencyRoute: Routes = [
    {
        path: 'agency',
        component: AgencyComponent,
        resolve: {
            'pagingParams': AgencyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 94,
            functionality: 1,
            pageTitle: 'auxiliumApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agency/:id',
        component: AgencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 94,
            functionality: 1,
            pageTitle: 'auxiliumApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'agenceConcessionnaire',
        component: AgenceConcessionnaireComponent,
        resolve: {
            'pagingParams': AgencyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 95,
            functionality: 130,
            pageTitle: 'auxiliumApp.agency.home.titleConcess'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agenceConcessionnaire/:id',
        component: AgenceConcessionnaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 95,
            functionality: 130,
            pageTitle: 'auxiliumApp.agency.home.titleConcess'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agencyPopupRoute: Routes = [
    {
        path: 'agency-new',
        component: AgencyDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 94,
            functionality: 2,
            pageTitle: 'auxiliumApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'agency/:id/edit',
        component: AgencyDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 94,
            functionality: 3,
            pageTitle: 'auxiliumApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'agency/:id/delete',
        component: AgencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 94,
            functionality: 5,
            pageTitle: 'auxiliumApp.agency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },{
        path: 'agenceConcessionnaire-new',
        component: AgenceConcessionnaireDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 95,
            functionality: 131,
            pageTitle: 'auxiliumApp.agency.home.titleConcess'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'agenceConcessionnaire/:id/edit',
        component: AgenceConcessionnaireDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 95,
            functionality: 132,
            pageTitle: 'auxiliumApp.agency.home.titleConcess'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'agenceConcessionnaire/:id/delete',
        component: AgenceConcessionnaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 95,
            functionality: 134,
            pageTitle: 'auxiliumApp.agency.home.titleConcess'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },{
        path: 'agenceConcessionnaireHistory',
        component: AgenceConcessPopupDetail,
        data: {
            authorities: ['ROLE_USER'],
            entity: 94,
            //functionality: 67,
            pageTitle: 'auxiliumApp.agency.home.HistoryAgence'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
