import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LoueurComponent } from './loueur.component';
import { LoueurDetailComponent } from './loueur-detail.component';
import { LoueurDialogComponent } from './loueur-dialog.component';
import { LoueurDeletePopupComponent } from './loueur-delete-dialog.component';
import { LoueurBloquePopupComponent } from './loueur-bloque-dialog.component';

export const loueurRoute: Routes = [
    {
        path: 'loueur',
        component: LoueurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.loueur.home.title',
            entity: 107,
            functionality: 1,
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'loueur/:id',
        component: LoueurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.loueur.home.title',
            entity: 107,
            functionality: 1,
        },
        canActivate: [UserRouteAccessService]
    }
    
];

export const loueurPopupRoute: Routes = [
    {
        path: 'loueur-new',
        component: LoueurDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.loueur.home.title',
            entity: 107,
            functionality: 2,
        },
        
        canActivate: [UserRouteAccessService],
       // outlet: 'popup'
    },
    {
        path: 'loueur/:idLoueur/edit',
        component: LoueurDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.loueur.home.title',
            entity: 107,
            functionality: 3,
        },
        canActivate: [UserRouteAccessService],
        //outlet: 'popup'
    },
    {
        path: 'loueur/:id/delete',
        component: LoueurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.loueur.home.title',
            entity: 107,
            functionality: 5,
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },

    {
        path: 'loueur/:id/bloque',
        component: LoueurBloquePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.loueur.home.title',
            entity: 107,
            functionality: 6,
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
