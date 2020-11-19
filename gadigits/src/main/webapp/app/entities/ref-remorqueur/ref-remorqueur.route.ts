import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';
import { RefRemorqueurComponent } from './ref-remorqueur.component';
import { RefRemorqueurDetailComponent } from './ref-remorqueur-detail.component';
import { RefRemorqueurPopupComponent } from './ref-remorqueur-dialog.component';
import { RefRemorqueurDialogComponent } from './ref-remorqueur-dialog.component';
import { RefRemorqueurDeletePopupComponent } from './ref-remorqueur-delete-dialog.component';
import { RefRemorqueurBloquePopupComponent } from './ref-remorqueur-bloque-dialog.component';

@Injectable()
export class RefRemorqueurResolvePagingParams implements Resolve<any> {

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

export const refRemorqueurRoute: Routes = [
    {
        path: 'ref-remorqueur',
        component: RefRemorqueurComponent,
        resolve: {
            'pagingParams': RefRemorqueurResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            entity: 6,
            functionality: 1,
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-remorqueur/:id',
        component: RefRemorqueurDetailComponent,
        data: {            
            authorities: ['ROLE_USER'],
            entity: 6,
            functionality: 1,
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const refRemorqueurPopupRoute: Routes = [
    {
        path: 'ref-remorqueur-new',
        component: RefRemorqueurDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 6,
            functionality: 2,
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'ref-remorqueur/:idRmq/edit/:idPm',
        component: RefRemorqueurDialogComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 6,
            functionality: 3,
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
    {
        path: 'ref-remorqueur/:idRmq/view/:idPm',
        component: RefRemorqueurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 6,
            functionality: 1,
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
    },
     {
        path: 'ref-remorqueur/:id/delete',
        component: RefRemorqueurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            entity: 6,
            functionality: 5,
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ref-remorqueur/:id/bloque',
        component: RefRemorqueurBloquePopupComponent,
        data: {
            pageTitle: 'auxiliumApp.refRemorqueur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
