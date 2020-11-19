import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DataTablesModule } from 'angular-datatables';

import { AuxiliumSharedModule } from '../../shared';
import {
    LoueurService,
    LoueurComponent,
    LoueurDetailComponent,
    LoueurDialogComponent,
    LoueurDeletePopupComponent,
    LoueurDeleteDialogComponent,
    loueurRoute,
    loueurPopupRoute,
} from './';
import { LoueurBloqueDialogComponent, LoueurBloquePopupComponent } from './loueur-bloque-dialog.component';
import { LoueurPopupService } from './loueur-popup.service';

const ENTITY_STATES = [
    ...loueurRoute,
    ...loueurPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        DataTablesModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LoueurComponent,
        LoueurDetailComponent,
        LoueurDialogComponent,
        LoueurDeleteDialogComponent,
        LoueurDeletePopupComponent,
        LoueurBloquePopupComponent,
        LoueurBloqueDialogComponent,
    ],
    entryComponents: [
        LoueurComponent,
        LoueurDialogComponent,
        LoueurDeleteDialogComponent,
        LoueurDeletePopupComponent,
        LoueurBloquePopupComponent,
        LoueurBloqueDialogComponent,
    ],
    providers: [
        LoueurService,
        NgbActiveModal,
       LoueurPopupService,


    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumLoueurModule {}
