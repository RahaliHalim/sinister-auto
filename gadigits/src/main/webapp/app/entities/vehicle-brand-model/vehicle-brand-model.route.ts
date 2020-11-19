import { Injectable } from '@angular/core';
import { Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { VehicleBrandModelComponent } from './vehicle-brand-model.component';

export const vehicleBrandModelRoute: Routes = [
    {
        path: 'vehicle-brand-model',
        component: VehicleBrandModelComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 72,
            functionality: 1,
            pageTitle: 'auxiliumApp.vehicleBrandModel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
