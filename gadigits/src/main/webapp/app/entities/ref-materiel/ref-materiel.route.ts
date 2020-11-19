import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefMaterielComponent } from './ref-materiel.component';
import { RefMaterielDetailComponent } from './ref-materiel-detail.component';
import { RefMaterielPopupComponent } from './ref-materiel-dialog.component';
import { RefMaterielDeletePopupComponent } from './ref-materiel-delete-dialog.component';

@Injectable()
export class RefMaterielResolvePagingParams implements Resolve<any> {

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

export const refMaterielRoute: Routes = [
    {
        path: 'ref-materiel',
        component: RefMaterielComponent,
        resolve: {
            'pagingParams': RefMaterielResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.refMateriel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-materiel/:id',
        component: RefMaterielDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refMateriel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refMaterielPopupRoute: Routes = [
    {
        path: 'ref-materiel-new',
        component: RefMaterielPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refMateriel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-materiel/:id/edit',
        component: RefMaterielPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refMateriel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-materiel/:id/delete',
        component: RefMaterielDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refMateriel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
