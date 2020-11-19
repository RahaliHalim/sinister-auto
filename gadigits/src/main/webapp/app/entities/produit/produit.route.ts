import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProduitComponent } from './produit.component';
import { ProduitDetailComponent } from './produit-detail.component';
import { ProduitPopupComponent } from './produit-dialog.component';
import { ProduitDeletePopupComponent } from './produit-delete-dialog.component';

@Injectable()
export class ProduitResolvePagingParams implements Resolve<any> {

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

export const produitRoute: Routes = [
    {
        path: 'produit',
        component: ProduitComponent,
        resolve: {
            'pagingParams': ProduitResolvePagingParams
        },
        data: {
            pageTitle: 'AuxiliumApp.produit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'produit/:id',
        component: ProduitDetailComponent,
        data: {
            pageTitle: 'AuxiliumApp.produit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const produitPopupRoute: Routes = [
    {
        path: 'produit-new',
        component: ProduitPopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.produit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'produit/:id/edit',
        component: ProduitPopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.produit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'produit/:id/delete',
        component: ProduitDeletePopupComponent,
        data: {
            pageTitle: 'AuxiliumApp.produit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
