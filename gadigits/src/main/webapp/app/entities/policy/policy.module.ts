import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';
import {
    PolicyService,
    PolicyPopupService,
    PolicyComponent,
    PolicyDetailComponent,
    PolicyDialogComponent,
    PolicyDeletePopupComponent,
    PolicyDeleteDialogComponent,
    policyRoute,
} from './';

const ENTITY_STATES = [
    ...policyRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        PolicyComponent,
        PolicyDetailComponent,
        PolicyDialogComponent,
        PolicyDeleteDialogComponent,
        PolicyDeletePopupComponent
    ],
    entryComponents: [
        PolicyComponent,
        PolicyDialogComponent,
        PolicyDeleteDialogComponent,
        PolicyDeletePopupComponent
    ],
    providers: [PolicyService, PolicyPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPolicyModule {}
