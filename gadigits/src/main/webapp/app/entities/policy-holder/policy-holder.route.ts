import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PolicyHolderComponent } from './policy-holder.component';
import { PolicyHolderDetailComponent } from './policy-holder-detail.component';
import { PolicyHolderPopupComponent } from './policy-holder-dialog.component';
import { PolicyHolderDeletePopupComponent } from './policy-holder-delete-dialog.component';

export const policyHolderRoute: Routes = [
    {
        path: 'policy-holder',
        component: PolicyHolderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyHolder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy-holder/:id',
        component: PolicyHolderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyHolder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const policyHolderPopupRoute: Routes = [
    {
        path: 'policy-holder-new',
        component: PolicyHolderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyHolder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-holder/:id/edit',
        component: PolicyHolderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyHolder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-holder/:id/delete',
        component: PolicyHolderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyHolder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
