import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefEtatDossierComponent } from './ref-etat-dossier.component';
import { RefEtatDossierDetailComponent } from './ref-etat-dossier-detail.component';
import { RefEtatDossierPopupComponent } from './ref-etat-dossier-dialog.component';
import { RefEtatDossierDeletePopupComponent } from './ref-etat-dossier-delete-dialog.component';

@Injectable()
export class RefEtatDossierResolvePagingParams implements Resolve<any> {

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

export const refEtatDossierRoute: Routes = [
    {
        path: 'ref-etat-dossier',
        component: RefEtatDossierComponent,
        resolve: {
            'pagingParams': RefEtatDossierResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refEtatDossier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-etat-dossier/:id',
        component: RefEtatDossierDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatDossier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refEtatDossierPopupRoute: Routes = [
    {
        path: 'ref-etat-dossier-new',
        component: RefEtatDossierPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatDossier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-etat-dossier/:id/edit',
        component: RefEtatDossierPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatDossier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-etat-dossier/:id/delete',
        component: RefEtatDossierDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refEtatDossier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
