import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AssistanceMonitoringCompanyComponent } from './assistance-monitoring-company.component';
import { PecMonitoringPrestationComponent } from './pec-monitoring-prestation.component';
import { PecMonitoringPrestationStepComponent } from './pec-monitoring-prestation-step.component';
import { PolicyIndicatorComponent } from './policy-indicator.component';

export const reportsRoute: Routes =  [
    {
        path: 'assistance-monitoring-cie',
        component: AssistanceMonitoringCompanyComponent,
        data: {
            pageTitle: 'auxiliumApp.reports.home.assistanceMonitoringCompany'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'pec-monitoring-prestation',
        component: PecMonitoringPrestationComponent,
        data: {
            pageTitle: 'auxiliumApp.reports.home.pecMonitoringPrestation'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'pec-monitoring-prestation-step',
        component: PecMonitoringPrestationStepComponent,
        data: {
            pageTitle: 'auxiliumApp.reports.home.pecMonitoringPrestationStep'
        },
        canActivate: [UserRouteAccessService]
    },{
        path: 'souscription',
        component: PolicyIndicatorComponent,
        data: {
            pageTitle: 'auxiliumApp.contratAssurance.reportName'
        },
        canActivate: [UserRouteAccessService]
    }
];
