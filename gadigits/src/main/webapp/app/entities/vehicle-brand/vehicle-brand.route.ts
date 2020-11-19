import { Injectable } from '@angular/core';
import { Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { UserComponent } from './user.component';
import { VehicleBrandComponent } from './vehicle-brand.component';

export const vehicleBrandRoute: Routes = [
    {
        path: 'vehicle-brand',
        component: VehicleBrandComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 71,
            functionality: 1,
            pageTitle: 'auxiliumApp.vehicleBrand.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vehicle-brand/test',
        component: UserComponent,
        data: {
            pageTitle: 'auxiliumApp.vehicleBrand.home.title'
        },
        canActivate: [UserRouteAccessService]
    }    
];
