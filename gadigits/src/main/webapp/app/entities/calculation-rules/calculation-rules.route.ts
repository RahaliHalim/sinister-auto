import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { CalculationRulesDialogComponent } from './calculation-rules-dialog.component';
import { CalculationRulesComponent } from './calculation-rules.component';


export const calculationrulesRoute: Routes = [
    {
        path: 'calculation-rules',
        component: CalculationRulesComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 93,
            functionality: 1,
            pageTitle: 'auxiliumApp.calculationrules.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'calculation-rules-new',
        component: CalculationRulesDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 93,
            functionality: 2,
            pageTitle: 'auxiliumApp.calculationrules.home.title'
        },
        canActivate: [UserRouteAccessService],
    }, {
        path: 'calculation-rules/:id/edit',
        component: CalculationRulesDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 93,
            functionality: 3,
            pageTitle: 'auxiliumApp.calculationrules.home.title'
        },
        canActivate: [UserRouteAccessService],

    }
];
