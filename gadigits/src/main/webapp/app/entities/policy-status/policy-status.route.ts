import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PolicyStatusComponent } from './policy-status.component';
import { PolicyStatusDetailComponent } from './policy-status-detail.component';
import { PolicyStatusPopupComponent } from './policy-status-dialog.component';
import { PolicyStatusDeletePopupComponent } from './policy-status-delete-dialog.component';

export const policyStatusRoute: Routes = [
    {
        path: 'policy-status',
        component: PolicyStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy-status/:id',
        component: PolicyStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const policyStatusPopupRoute: Routes = [
    {
        path: 'policy-status-new',
        component: PolicyStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-status/:id/edit',
        component: PolicyStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-status/:id/delete',
        component: PolicyStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
