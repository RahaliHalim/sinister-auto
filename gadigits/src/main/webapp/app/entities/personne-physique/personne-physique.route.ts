import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PersonnePhysiqueComponent } from './personne-physique.component';
import { PersonnePhysiqueDetailComponent } from './personne-physique-detail.component';
import { PersonnePhysiquePopupComponent, PersonnePhysiqueDialogComponent } from './personne-physique-dialog.component';
import { PersonnePhysiqueDialogForDetailComponent } from './personne-physique-dialog-for-detail.component';
import { PersonnePhysiqueDeletePopupComponent } from './personne-physique-delete-dialog.component';

@Injectable()
export class PersonnePhysiqueResolvePagingParams implements Resolve<any> {

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

export const personnePhysiqueRoute: Routes = [
    {
        path: 'personne-physique',
        component: PersonnePhysiqueComponent,
        resolve: {
            'pagingParams': PersonnePhysiqueResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.personnePhysique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personne-physique/:id',
        component: PersonnePhysiqueDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.personnePhysique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personnePhysiquePopupRoute: Routes = [
    {
        path: 'personne-physique-new',
        component: PersonnePhysiqueDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.personnePhysique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'personne-physique/:id/edit',
        component: PersonnePhysiquePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.personnePhysique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personne-physique/:id/delete',
        component: PersonnePhysiqueDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.personnePhysique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
