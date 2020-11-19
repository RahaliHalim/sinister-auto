import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { StampDutyComponent } from './stamp-duty.component';

export const stampDutyRoute: Routes = [
    {
        path: 'stamp-duty',
        component: StampDutyComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 87,
            functionality: 1,        	
            pageTitle: 'auxiliumApp.stampDuty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
