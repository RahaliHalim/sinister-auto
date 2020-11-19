import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    ContactService,
    ContactPopupService,
    ContactComponent,
    ContactDetailComponent,
    ContactDialogComponent,
    ContactPopupComponent,
    ContactDeletePopupComponent,
    ContactDeleteDialogComponent,
    contactRoute,
    contactPopupRoute,
    ContactResolvePagingParams,
} from './';
import { AuxiliumPersonnePhysiqueModule } from '../personne-physique/personne-physique.module';

const ENTITY_STATES = [
    ...contactRoute,
    ...contactPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumPersonnePhysiqueModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContactComponent,
        ContactDetailComponent,
        ContactDialogComponent,
        ContactDeleteDialogComponent,
        ContactPopupComponent,
        ContactDeletePopupComponent,
    ],
    entryComponents: [
        ContactComponent,
        ContactDialogComponent,
        ContactPopupComponent,
        ContactDeleteDialogComponent,
        ContactDeletePopupComponent,
    ],
    providers: [
        ContactService,
        ContactPopupService,
        ContactResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumContactModule {}
