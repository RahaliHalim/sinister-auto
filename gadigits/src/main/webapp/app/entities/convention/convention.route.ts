import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { ConventionComponent } from './convention.component';
import { ConventionDialogComponent } from './convention-dialog.component';
import { ConventionAmendmentComponent } from './convention-amendment.component';
import { ConventionDetailComponent } from './convention-detail.component';
import {RefPackPopupDetail} from './ref-pack-popup-detail';

export const conventionRoute: Routes = [
    {
        path: 'convention',
        component: ConventionComponent,
        data: {
            pageTitle: 'auxiliumApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'convention-new',
        component: ConventionDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'convention/:id',
        component: ConventionDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService],

    }, {
        path: 'convention/:id/edit',
        component: ConventionAmendmentComponent,
        data: {
            pageTitle: 'auxiliumApp.convention.home.title'
        },
        canActivate: [UserRouteAccessService],

    }
];
export const conventionPopupRoute: Routes = [
    {
        path: 'ref-pack/:id',
        component: RefPackPopupDetail,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'auxiliumApp.refPack.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
    
];

