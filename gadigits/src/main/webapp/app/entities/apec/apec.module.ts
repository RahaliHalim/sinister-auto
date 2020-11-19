import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables/src/angular-datatables.module';
import { AuxiliumSharedModule } from '../../shared';
import {
    ApecService,
    ApecPopupService,
    ApecComponent,
    ApecDetailComponent,
    ApecDialogComponent,
    ApecPopupComponent,
    ApecDeletePopupComponent,
    ApecDeleteDialogComponent,
    apecRoute,
    apecPopupRoute,
    ApecResolvePagingParams,
    ApecForValidationComponent,
    ApecImprimeComponent,
    ApecValidAssurComponent,
    ApecModifComponent,
    ApecApprouveComponent,
} from './';


const ENTITY_STATES = [
    ...apecRoute,
    ...apecPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        DataTablesModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ApecComponent,
        ApecDetailComponent,
        ApecDialogComponent,
        ApecDeleteDialogComponent,
        ApecPopupComponent,
        ApecDeletePopupComponent,
        ApecForValidationComponent,
        ApecImprimeComponent,
        ApecValidAssurComponent,
        ApecModifComponent,
        ApecApprouveComponent
    ],
    entryComponents: [
        ApecComponent,
        ApecDialogComponent,
        ApecPopupComponent,
        ApecDeleteDialogComponent,
        ApecDeletePopupComponent,
        ApecForValidationComponent,
        ApecImprimeComponent,
        ApecValidAssurComponent,
        ApecModifComponent,
        ApecApprouveComponent
    ],
    providers: [
        ApecService,
        ApecPopupService,
        ApecResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumApecModule {}
