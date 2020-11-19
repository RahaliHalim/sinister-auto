import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FunctionalityComponent } from './functionality.component';
import { FunctionalityDetailComponent } from './functionality-detail.component';
import { FunctionalityPopupComponent } from './functionality-dialog.component';
import { FunctionalityDeletePopupComponent } from './functionality-delete-dialog.component';

export const functionalityRoute: Routes = [
    {
        path: 'functionality',
        component: FunctionalityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.functionality.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'functionality/:id',
        component: FunctionalityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.functionality.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const functionalityPopupRoute: Routes = [
    {
        path: 'functionality-new',
        component: FunctionalityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.functionality.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'functionality/:id/edit',
        component: FunctionalityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.functionality.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'functionality/:id/delete',
        component: FunctionalityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.functionality.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
