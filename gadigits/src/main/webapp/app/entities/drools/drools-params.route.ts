import { Route, Routes, CanActivate } from '@angular/router';

import { DroolsParamsComponent } from './drools-params.component';
import { UserRouteAccessService } from '../../shared';

export const droolsRoute: Route = {
    path: 'drools',
    component: DroolsParamsComponent,
    data: {
        pageTitle: 'auxiliumApp.drools.home.title.title'
    }
};

    




