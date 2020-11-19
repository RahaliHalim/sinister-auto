import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import {
    RefBaremeService,
    RefBaremePopupService,
    RefBaremeComponent,
    RefBaremeDetailComponent,
    RefBaremeDialogComponent,
    RefBaremePopupComponent,
    RefBaremeDeletePopupComponent,
    RefBaremeDeleteDialogComponent,
    refBaremeRoute,
    refBaremePopupRoute,
    RefBaremeResolvePagingParams,
} from './';
import { RefBaremePopupDetailComponent, RefBaremePopComponent } from './ref-bareme-popup-detail.component';
import { RefBaremePopupDetailService } from './ref-bareme-popup-detail.service';
import { DataTablesModule } from 'angular-datatables';

const ENTITY_STATES = [
    ...refBaremeRoute,
    ...refBaremePopupRoute,
];
@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        RefBaremeComponent,
        RefBaremeDetailComponent,
        RefBaremeDialogComponent,
        RefBaremeDeleteDialogComponent,
        RefBaremePopupComponent,
        RefBaremeDeletePopupComponent,
        RefBaremePopupDetailComponent,
        RefBaremePopComponent,
    ],
    entryComponents: [
        RefBaremeComponent,
        RefBaremeDialogComponent,
        RefBaremePopupComponent,
        RefBaremeDeleteDialogComponent,
        RefBaremeDeletePopupComponent,
        RefBaremePopupDetailComponent,
        RefBaremePopComponent,
    ],
    providers: [
        RefBaremeService,
        RefBaremePopupService,
        RefBaremeResolvePagingParams,
        RefBaremePopupDetailService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefBaremeModule {}
