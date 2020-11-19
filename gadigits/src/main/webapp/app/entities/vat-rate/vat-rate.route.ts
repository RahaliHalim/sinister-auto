import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { VatRateComponent } from './vat-rate.component';

export const vatRateRoute: Routes = [
    {
        path: 'vat-rate',
        component: VatRateComponent,
        data: {
			authorities: ['ROLE_USER'],
            entity: 86,
            functionality: 1,
            pageTitle: 'auxiliumApp.vatRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
