import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ViewPolicyService,
    ViewPolicyPopupService,
    ViewPolicyComponent,
    ViewPolicyDetailComponent,
    ViewPolicyDialogComponent,
    ViewPolicyPopupComponent,
    ViewPolicyDeletePopupComponent,
    ViewPolicyDeleteDialogComponent,
    viewPolicyRoute,
    viewPolicyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...viewPolicyRoute,
    ...viewPolicyPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ViewPolicyComponent,
        ViewPolicyDetailComponent,
        ViewPolicyDialogComponent,
        ViewPolicyDeleteDialogComponent,
        ViewPolicyPopupComponent,
        ViewPolicyDeletePopupComponent,
    ],
    entryComponents: [
        ViewPolicyComponent,
        ViewPolicyDialogComponent,
        ViewPolicyPopupComponent,
        ViewPolicyDeleteDialogComponent,
        ViewPolicyDeletePopupComponent,
    ],
    providers: [
        ViewPolicyService,
        ViewPolicyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumViewPolicyModule {}
