import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PeriodicityComponent } from './periodicity.component';
import { PeriodicityDetailComponent } from './periodicity-detail.component';
import { PeriodicityPopupComponent } from './periodicity-dialog.component';
import { PeriodicityDeletePopupComponent } from './periodicity-delete-dialog.component';

export const periodicityRoute: Routes = [
    {
        path: 'periodicity',
        component: PeriodicityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.periodicity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'periodicity/:id',
        component: PeriodicityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.periodicity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodicityPopupRoute: Routes = [
    {
        path: 'periodicity-new',
        component: PeriodicityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.periodicity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'periodicity/:id/edit',
        component: PeriodicityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.periodicity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'periodicity/:id/delete',
        component: PeriodicityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.periodicity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
