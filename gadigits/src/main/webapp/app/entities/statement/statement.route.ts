import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { StatementComponent } from './statement.component';
import { InvoiceComponent } from './invoice.component';

export const statementRoute: Routes = [
    {
        path: 'invoice',
        component: InvoiceComponent,
        data: {
            pageTitle: 'auxiliumApp.statement.home.invoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'statement',
        component: StatementComponent,
        data: {
            pageTitle: 'auxiliumApp.statement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
