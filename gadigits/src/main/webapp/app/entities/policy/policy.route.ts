import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { PolicyComponent } from './policy.component';
import { PolicyDetailComponent } from './policy-detail.component';
import { PolicyDialogComponent } from './policy-dialog.component';

export const policyRoute: Routes = [
    {
        path: 'policy',
        component: PolicyComponent,
        data: {
            pageTitle: 'auxiliumApp.policy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy-new',
        component: PolicyDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.policy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'policy/:id',
        component: PolicyDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.policy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
