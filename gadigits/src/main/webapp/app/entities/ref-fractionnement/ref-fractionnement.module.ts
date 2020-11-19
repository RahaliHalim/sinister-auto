import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    RefFractionnementService,
    RefFractionnementPopupService,
    RefFractionnementComponent,
    RefFractionnementDetailComponent,
    RefFractionnementDialogComponent,
    RefFractionnementPopupComponent,
    RefFractionnementDeletePopupComponent,
    RefFractionnementDeleteDialogComponent,
    refFractionnementRoute,
    refFractionnementPopupRoute,
    RefFractionnementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...refFractionnementRoute,
    ...refFractionnementPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RefFractionnementComponent,
        RefFractionnementDetailComponent,
        RefFractionnementDialogComponent,
        RefFractionnementDeleteDialogComponent,
        RefFractionnementPopupComponent,
        RefFractionnementDeletePopupComponent,
    ],
    entryComponents: [
        RefFractionnementComponent,
        RefFractionnementDialogComponent,
        RefFractionnementPopupComponent,
        RefFractionnementDeleteDialogComponent,
        RefFractionnementDeletePopupComponent,
    ],
    providers: [
        RefFractionnementService,
        RefFractionnementPopupService,
        RefFractionnementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefFractionnementModule {}
