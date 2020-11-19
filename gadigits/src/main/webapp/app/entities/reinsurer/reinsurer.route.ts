import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReinsurerComponent } from './reinsurer.component';

export const reinsurerRoute: Routes = [
    {
        path: 'reinsurer',
        component: ReinsurerComponent,
        data: {
            pageTitle: 'auxiliumApp.reinsurer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
