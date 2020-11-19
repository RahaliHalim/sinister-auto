import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DevisComponent } from '../devis/devis.component';
import { DevisDetailComponent } from '../devis/devis-detail.component';
import { DevisDialogComponent } from '../devis/devis-dialog.component';
import { QuotationDialogComponent } from '../devis/quotation.dialog.component';


@Injectable()
export class PrimaryQuotationResolvePagingParams implements Resolve<any> {

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

export const quotationRoute: Routes = [
    {
        path: 'devis',
        component: DevisComponent,
        resolve: {
            'pagingParams': PrimaryQuotationResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'devis/:id',
        component: DevisDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.devis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationPopupRoute: Routes = [
 
   
];
