import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PolicyTypeComponent } from './policy-type.component';
import { PolicyTypeDetailComponent } from './policy-type-detail.component';
import { PolicyTypePopupComponent } from './policy-type-dialog.component';
import { PolicyTypeDeletePopupComponent } from './policy-type-delete-dialog.component';

export const policyTypeRoute: Routes = [
    {
        path: 'policy-type',
        component: PolicyTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy-type/:id',
        component: PolicyTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const policyTypePopupRoute: Routes = [
    {
        path: 'policy-type-new',
        component: PolicyTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-type/:id/edit',
        component: PolicyTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-type/:id/delete',
        component: PolicyTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
