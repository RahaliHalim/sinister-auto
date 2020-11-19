import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { RaisonAssistanceComponent } from './raison-assistance.component';

export const raisonAssistanceRoute: Routes = [
    {
        path: 'raison-assistance',
        component: RaisonAssistanceComponent,
        data: {
            pageTitle: 'auxiliumApp.raisonAssistance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
