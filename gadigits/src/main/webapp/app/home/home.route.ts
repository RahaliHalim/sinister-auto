import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
    path: '',
    component: HomeComponent,
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_GESTIONNAIRE', 'ROLE_CCELLULE', 'ROLE_ASSURE', 'ROLE_REPARATEUR', 'ROLE_RESPONSABLE', 'ROLE_EXPERT', 'ROLE_RAPPORTEUR', 'ROLE_AGCOMPAGNIE', 'ROLE_AGGENERAL'],
        pageTitle: 'home.title',
    },
    canActivate: [UserRouteAccessService]
};
