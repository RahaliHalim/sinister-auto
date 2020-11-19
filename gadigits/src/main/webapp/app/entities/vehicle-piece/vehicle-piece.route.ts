import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { VehiclePieceComponent } from './vehicle-piece.component';

export const vehiclePieceRoute: Routes = [
    {
        path: 'vehicle-piece',
        component: VehiclePieceComponent,
        data: {
            pageTitle: 'auxiliumApp.vehiclePiece.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];