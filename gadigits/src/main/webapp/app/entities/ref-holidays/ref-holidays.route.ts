import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { RefHolidaysComponent } from './ref-holidays.component';

export const RefHolidaysRoute: Routes = [
    {
        path: 'ref-holidays',
        component: RefHolidaysComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 2,
            functionality: 1,
            pageTitle: 'auxiliumApp.refHolidays.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
