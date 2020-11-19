import { Injectable } from '@angular/core';
import { Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { NaturePanneComponent } from './nature-panne.component';

export const NaturePanneRoute: Routes = [
    {
        path: 'nature-panne',
        component: NaturePanneComponent,
        data: {
           // authorities: ['ROLE_USER'],
           // entity: 71,
           // functionality: 1,
            pageTitle: 'auxiliumApp.naturePanne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }   
];
