import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VehicleUsageComponent } from './vehicle-usage.component';

export const vehicleUsageRoute: Routes = [
    {
        path: 'vehicle-usage',
        component: VehicleUsageComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 73,
            functionality: 1,
            pageTitle: 'auxiliumApp.vehicleUsage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];