import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';
import { CamionComponent } from './camion.component';
import { CamionDetailComponent } from './camion-detail.component';
import { CamionPopupComponent } from './camion-dialog.component';
import { CamionDeletePopupComponent } from './camion-delete-dialog.component';

@Injectable()
export class CamionResolvePagingParams implements Resolve<any> {

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

export const camionRoute: Routes = [
    {
        path: 'camion',
        component: CamionComponent,
        resolve: {
            'pagingParams': CamionResolvePagingParams
        },
        data: {
            pageTitle: 'auxiliumApp.camion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'camion/:id',
        component: CamionDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.camion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const camionPopupRoute: Routes = [
    {
        path: 'new-camion',
        component: CamionPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.camion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'camion/:id/edit',
        component: CamionPopupComponent,
        data: {
            pageTitle: 'auxiliumApp.camion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'camion/:id/delete',
        component: CamionDeletePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.camion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
