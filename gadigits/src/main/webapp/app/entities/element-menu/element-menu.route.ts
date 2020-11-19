import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ElementMenuComponent } from './element-menu.component';
import { ElementMenuDetailComponent } from './element-menu-detail.component';
import { ElementMenuPopupComponent } from './element-menu-dialog.component';
import { ElementMenuDeletePopupComponent } from './element-menu-delete-dialog.component';

export const elementMenuRoute: Routes = [
    {
        path: 'element-menu',
        component: ElementMenuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.elementMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'element-menu/:id',
        component: ElementMenuDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.elementMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elementMenuPopupRoute: Routes = [
    {
        path: 'element-menu-new',
        component: ElementMenuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.elementMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'element-menu/:id/edit',
        component: ElementMenuPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.elementMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'element-menu/:id/delete',
        component: ElementMenuDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.elementMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
