import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ViewPolicyComponent } from './view-policy.component';
import { ViewPolicyDetailComponent } from './view-policy-detail.component';
import { ViewPolicyPopupComponent } from './view-policy-dialog.component';
import { ViewPolicyDeletePopupComponent } from './view-policy-delete-dialog.component';

export const viewPolicyRoute: Routes = [
    {
        path: 'view-policy',
        component: ViewPolicyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.viewPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'view-policy/:id',
        component: ViewPolicyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.viewPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const viewPolicyPopupRoute: Routes = [
    {
        path: 'view-policy-new',
        component: ViewPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.viewPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'view-policy/:id/edit',
        component: ViewPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.viewPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'view-policy/:id/delete',
        component: ViewPolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.viewPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
