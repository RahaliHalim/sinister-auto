import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UploadComponent } from './upload.component';

export const UploadRoute: Routes = [
    {
        path: 'upload',
        component: UploadComponent,
        data: {
            //authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.upload.home.createLabel'
        },
        canActivate: [UserRouteAccessService]
    }
];

