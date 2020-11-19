import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VehiculeLoueurComponent } from './vehicule-loueur.component';
import { VehiculeLoueurDetailComponent } from './vehicule-loueur-detail.component';
import { VehiculeLoueurPopupComponent } from './vehicule-loueur-dialog.component';
import { VehiculeLoueurDeletePopupComponent } from './vehicule-loueur-delete-dialog.component';

export const vehiculeLoueurRoute: Routes = [
    {
        path: 'vehicule-loueur',
        component: VehiculeLoueurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.vehiculeLoueur.home.title'
        }//,
        //canActivate: [UserRouteAccessService]
    }, {
        path: 'vehicule-loueur/:id',
        component: VehiculeLoueurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.vehiculeLoueur.home.title'
        }//,
        //canActivate: [UserRouteAccessService]
    }
];

export const vehiculeLoueurPopupRoute: Routes = [
    {
        path: 'vehicule-loueur-new',
        component: VehiculeLoueurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.vehiculeLoueur.home.title'
        },
        //canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vehicule-loueur/:id/edit',
        component: VehiculeLoueurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.vehiculeLoueur.home.title'
        },
        //canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vehicule-loueur/:id/delete',
        component: VehiculeLoueurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.vehiculeLoueur.home.title'
        },
        //canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
