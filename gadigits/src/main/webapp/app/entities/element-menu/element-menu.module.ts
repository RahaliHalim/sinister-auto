import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ElementMenuService,
    ElementMenuPopupService,
    ElementMenuComponent,
    ElementMenuDetailComponent,
    ElementMenuDialogComponent,
    ElementMenuPopupComponent,
    ElementMenuDeletePopupComponent,
    ElementMenuDeleteDialogComponent,
    elementMenuRoute,
    elementMenuPopupRoute,
} from './';

const ENTITY_STATES = [
    ...elementMenuRoute,
    ...elementMenuPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ElementMenuComponent,
        ElementMenuDetailComponent,
        ElementMenuDialogComponent,
        ElementMenuDeleteDialogComponent,
        ElementMenuPopupComponent,
        ElementMenuDeletePopupComponent,
    ],
    entryComponents: [
        ElementMenuComponent,
        ElementMenuDialogComponent,
        ElementMenuPopupComponent,
        ElementMenuDeleteDialogComponent,
        ElementMenuDeletePopupComponent,
    ],
    providers: [
        ElementMenuService,
        ElementMenuPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumElementMenuModule {}
