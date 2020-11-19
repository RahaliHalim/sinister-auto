import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AmendmentTypeComponent } from './amendment-type.component';
import { AmendmentTypeDetailComponent } from './amendment-type-detail.component';
import { AmendmentTypePopupComponent } from './amendment-type-dialog.component';
import { AmendmentTypeDeletePopupComponent } from './amendment-type-delete-dialog.component';

export const amendmentTypeRoute: Routes = [
    {
        path: 'amendment-type',
        component: AmendmentTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.amendmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'amendment-type/:id',
        component: AmendmentTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.amendmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amendmentTypePopupRoute: Routes = [
    {
        path: 'amendment-type-new',
        component: AmendmentTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.amendmentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amendment-type/:id/edit',
        component: AmendmentTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.amendmentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amendment-type/:id/delete',
        component: AmendmentTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.amendmentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
