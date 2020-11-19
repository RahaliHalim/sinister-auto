import { Route } from '@angular/router';

import { LoginComponent } from './';
import { UserRouteAccessService } from '../shared';

export const LOGIN_ROUTE: Route = {
    path: 'login',
    component: LoginComponent,
    data: {
        pageTitle: 'home.title'
    },
    canActivate: [UserRouteAccessService]
};
