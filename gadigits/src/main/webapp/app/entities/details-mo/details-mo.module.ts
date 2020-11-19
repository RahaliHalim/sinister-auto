import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {NgxPaginationModule} from 'ngx-pagination'; 
import { AuxiliumSharedModule } from '../../shared';
import {
    DetailsMoService,
    DetailsMoPopupService,
    DetailsMoComponent,
    DetailsMoDetailComponent,
    DetailsMoDialogComponent,
    DetailsMoPopupComponent,
    DetailsMoDeletePopupComponent,
    DetailsMoDeleteDialogComponent,
    detailsMoRoute,
    detailsMoPopupRoute,
    DetailsMoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...detailsMoRoute,
    ...detailsMoPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        NgxPaginationModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DetailsMoComponent,
        DetailsMoDetailComponent,
        DetailsMoDialogComponent,
        DetailsMoDeleteDialogComponent,
        DetailsMoPopupComponent,
        DetailsMoDeletePopupComponent,
    ],
    entryComponents: [
        DetailsMoComponent,
        DetailsMoDialogComponent,
        DetailsMoPopupComponent,
        DetailsMoDeleteDialogComponent,
        DetailsMoDeletePopupComponent,
    ],
    providers: [
        DetailsMoService,
        DetailsMoPopupService,
        DetailsMoResolvePagingParams,
    ],
    exports: [
        DetailsMoDialogComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumDetailsMoModule {}
