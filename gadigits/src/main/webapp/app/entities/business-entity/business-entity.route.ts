import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BusinessEntityComponent } from './business-entity.component';
import { BusinessEntityDetailComponent } from './business-entity-detail.component';
import { BusinessEntityPopupComponent } from './business-entity-dialog.component';
import { BusinessEntityDeletePopupComponent } from './business-entity-delete-dialog.component';

export const businessEntityRoute: Routes = [
    {
        path: 'business-entity',
        component: BusinessEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.businessEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'business-entity/:id',
        component: BusinessEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.businessEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const businessEntityPopupRoute: Routes = [
    {
        path: 'business-entity-new',
        component: BusinessEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.businessEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'business-entity/:id/edit',
        component: BusinessEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.businessEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'business-entity/:id/delete',
        component: BusinessEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.businessEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
