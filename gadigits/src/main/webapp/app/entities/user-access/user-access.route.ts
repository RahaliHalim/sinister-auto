import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserAccessComponent } from './user-access.component';
import { UserAccessDetailComponent } from './user-access-detail.component';
import { UserAccessPopupComponent } from './user-access-dialog.component';
import { UserAccessDeletePopupComponent } from './user-access-delete-dialog.component';

export const userAccessRoute: Routes = [
    {
        path: 'user-access',
        component: UserAccessComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.userAccess.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-access/:id',
        component: UserAccessDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.userAccess.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAccessPopupRoute: Routes = [
    {
        path: 'user-access-new',
        component: UserAccessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.userAccess.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-access/:id/edit',
        component: UserAccessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.userAccess.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-access/:id/delete',
        component: UserAccessDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.userAccess.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
