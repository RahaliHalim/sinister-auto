import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PolicyReceiptStatusComponent } from './policy-receipt-status.component';
import { PolicyReceiptStatusDetailComponent } from './policy-receipt-status-detail.component';
import { PolicyReceiptStatusPopupComponent } from './policy-receipt-status-dialog.component';
import { PolicyReceiptStatusDeletePopupComponent } from './policy-receipt-status-delete-dialog.component';

export const policyReceiptStatusRoute: Routes = [
    {
        path: 'policy-receipt-status',
        component: PolicyReceiptStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyReceiptStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy-receipt-status/:id',
        component: PolicyReceiptStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyReceiptStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const policyReceiptStatusPopupRoute: Routes = [
    {
        path: 'policy-receipt-status-new',
        component: PolicyReceiptStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyReceiptStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-receipt-status/:id/edit',
        component: PolicyReceiptStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyReceiptStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'policy-receipt-status/:id/delete',
        component: PolicyReceiptStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.policyReceiptStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
