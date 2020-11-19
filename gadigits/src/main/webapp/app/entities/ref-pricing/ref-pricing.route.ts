import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RefPricingComponent } from './ref-pricing.component';
import { RefPricingDetailComponent } from './ref-pricing-detail.component';
import { RefPricingDialogComponent } from './ref-pricing-dialog.component';

export const RefPricingRoute: Routes = [
    {
        path: 'ref-pricing',
        component: RefPricingComponent,
        data: {
            pageTitle: 'auxiliumApp.refPricing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-pricing/:id',
        component: RefPricingDetailComponent,
        data: {
            pageTitle: 'auxiliumApp.refPricing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-pricing-new',
        component: RefPricingDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.refPricing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ref-pricing/:id/edit',
        component: RefPricingDialogComponent,
        data: {
            pageTitle: 'auxiliumApp.refPricing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
