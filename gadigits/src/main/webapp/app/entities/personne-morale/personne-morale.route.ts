import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PersonneMoraleComponent } from './personne-morale.component';
import { PersonneMoraleDetailComponent } from './personne-morale-detail.component';
import { PersonneMoraleDialogForDetailComponent } from './personne-morale-dialog-for-detail.component';
import { PersonneMoraleDeletePopupComponent } from './personne-morale-delete-dialog.component';
import {ContactPersonneMoralePopupComponent} from './contact-personneMorale-dialog.component';
@Injectable()
export class PersonneMoraleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const personneMoraleRoute: Routes = [
    {
        path: 'personne-morale',
        component: PersonneMoraleComponent,
        resolve: {
            'pagingParams': PersonneMoraleResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.personneMorale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personne-morale/:id',
        component: PersonneMoraleDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.personneMorale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personneMoralePopupRoute: Routes = [
    {
        path: 'personne-morale/:id/delete',
        component: PersonneMoraleDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.personneMorale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
     {
        path: 'new-contact',
        component: ContactPersonneMoralePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact/:id/edit',
        component: ContactPersonneMoralePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];

