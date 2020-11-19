import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReasonComponent } from './reason.component';
import { ReasonDetailComponent } from './reason-detail.component';
import { ReasonPopupComponent } from './reason-dialog.component';
import { ReasonDeletePopupComponent } from './reason-delete-dialog.component';

export const reasonRoute: Routes = [
    {
        path: 'reason',
        component: ReasonComponent,
        data: {
            pageTitle: 'auxiliumApp.reason.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reason/:id',
        component: ReasonDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.reason.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reasonPopupRoute: Routes = [
    {
        path: 'reason-new',
        component: ReasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.reason.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reason/:id/edit',
        component: ReasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.reason.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reason/:id/delete',
        component: ReasonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.reason.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
