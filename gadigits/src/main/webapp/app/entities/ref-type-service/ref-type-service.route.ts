import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { RefTypeServiceComponent } from './ref-type-service.component';

export const refTypeServiceRoute: Routes = [
    {
        path: 'ref-type-service',
        component: RefTypeServiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 5,
            functionality: 1,
            pageTitle: 'auxiliumApp.refTypeService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
