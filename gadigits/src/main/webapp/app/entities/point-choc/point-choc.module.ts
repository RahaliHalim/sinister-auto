import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    PointChocService,
    PointChocPopupService,
    PointChocComponent,
    PointChocDetailComponent,
    PointChocDialogComponent,
    PointChocPopupComponent,
    PointChocDeletePopupComponent,
    PointChocDeleteDialogComponent,
    pointChocRoute,
    pointChocPopupRoute,
    PointChocResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pointChocRoute,
    ...pointChocPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PointChocComponent,
        PointChocDetailComponent,
        PointChocDialogComponent,
        PointChocDeleteDialogComponent,
        PointChocPopupComponent,
        PointChocDeletePopupComponent,
    ],
    entryComponents: [
        PointChocComponent,
        PointChocDialogComponent,
        PointChocPopupComponent,
        PointChocDeleteDialogComponent,
        PointChocDeletePopupComponent,
    ],
    providers: [
        PointChocService,
        PointChocPopupService,
        PointChocResolvePagingParams,
    ],
    exports: [
        PointChocDialogComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPointChocModule {}
