import { Injectable } from '@angular/core';
import { Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { VehicleEnergyComponent } from './vehicle-energy.component';

export const vehicleEnergyRoute: Routes = [
    {
        path: 'vehicle-energy',
        component: VehicleEnergyComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 70,
            functionality: 1,
            pageTitle: 'auxiliumApp.vehicleEnergy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
