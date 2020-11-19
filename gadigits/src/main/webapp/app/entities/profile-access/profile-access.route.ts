import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProfileAccessComponent } from './profile-access.component';
import { ProfileAccessDetailComponent } from './profile-access-detail.component';
import { ProfileAccessPopupComponent } from './profile-access-dialog.component';
import { ProfileAccessDeletePopupComponent } from './profile-access-delete-dialog.component';

export const profileAccessRoute: Routes = [
    {
        path: 'profile-access',
        component: ProfileAccessComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.profileAccess.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profile-access/:id',
        component: ProfileAccessDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.profileAccess.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profileAccessPopupRoute: Routes = [
    {
        path: 'profile-access-new',
        component: ProfileAccessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.profileAccess.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-access/:id/edit',
        component: ProfileAccessPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.profileAccess.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-access/:id/delete',
        component: ProfileAccessDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.profileAccess.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
