import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PolicyNatureComponent } from './policy-nature.component';
import { PolicyNatureDetailComponent } from './policy-nature-detail.component';
import { PolicyNaturePopupComponent } from './policy-nature-dialog.component';
import { PolicyNatureDeletePopupComponent } from './policy-nature-delete-dialog.component';

export const policyNatureRoute: Routes = [
    {
        path: 'policy-nature',
        component: PolicyNatureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyNature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy-nature/:id',
        component: PolicyNatureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyNature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const policyNaturePopupRoute: Routes = [
    {
        path: 'policy-nature-new',
        component: PolicyNaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyNature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-nature/:id/edit',
        component: PolicyNaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyNature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-nature/:id/delete',
        component: PolicyNatureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyNature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
