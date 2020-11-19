import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PartnerComponent } from './partner.component';
import { DealerComponent } from './dealer.component';
import { PartnerDetailComponent } from './partner-detail.component';
import { PartnerPopupComponent } from './partner-dialog.component';
import { PartnerDialogComponent } from './partner-dialog.component';
import { PartnerDeletePopupComponent } from './partner-delete-dialog.component';
import { DealerDetailComponent } from './dealer-detail.component';
import { DealerDialogComponent } from './dealer-dialog.component';
import { DealerDeletePopupComponent } from './dealer-delete-dialog.component';



@Injectable()
export class PartnerResolvePagingParams implements Resolve<any> {

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
export const partnerRoute: Routes = [
    {
        path: 'partner',
        component: PartnerComponent,
        resolve: {
            'pagingParams': PartnerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 78,
            functionality: 1,
            pageTitle: 'auxiliumApp.partner.home.titlecompanies'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'dealer',
        component: DealerComponent,
        resolve: {
            'pagingParams': PartnerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 14,
            functionality: 127,
            pageTitle: 'auxiliumApp.partner.home.titleDealers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'partner/:id',
        component: PartnerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 78,
            functionality: 1,
            pageTitle: 'auxiliumApp.partner.home.titlecompanies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dealer/:id',
        component: DealerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 14,
            functionality: 127,
            pageTitle: 'auxiliumApp.partner.home.titleDealers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partnerPopupRoute: Routes = [
    {
        path: 'partner-new',
        component: PartnerDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 78,
            functionality: 2,
            pageTitle: 'auxiliumApp.partner.home.titlecompanies'
        },
        canActivate: [UserRouteAccessService]
        },
    {
        path: 'partner/:id/edit',
        component: PartnerDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 78,
            functionality: 3,
            pageTitle: 'auxiliumApp.partner.home.titlecompanies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'partner/:id/delete',
        component: PartnerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 78,
            functionality: 5,
            pageTitle: 'auxiliumApp.partner.home.titlecompanies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }, {
        path: 'dealer-new',
        component: DealerDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 14,
            functionality: 128,
            pageTitle: 'auxiliumApp.partner.home.titleDealers'
        },
        canActivate: [UserRouteAccessService]
        },
    {
        path: 'dealer/:id/edit',
        component: DealerDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 14,
            functionality: 129,
            pageTitle: 'auxiliumApp.partner.home.titleDealers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dealer/:id/delete',
        component: DealerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 14,
            functionality: 133,
            pageTitle: 'auxiliumApp.partner.home.titleDealers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
