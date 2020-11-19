import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { RaisonPecComponent } from './raison-pec.component';

export const raisonPecRoute: Routes = [
    {
        path: 'raison-pec',
        component: RaisonPecComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 105,
            functionality: 1,
            pageTitle: 'auxiliumApp.raisonPec.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
