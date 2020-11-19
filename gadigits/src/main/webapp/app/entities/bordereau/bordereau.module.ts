import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuxiliumSharedModule } from '../../shared';
import {
    BordereauComponent,
    bordereauRoute,
    //BordereauResolvePagingParams
}  from './';
import { AuxiliumRefRemorqueurModule } from '../ref-remorqueur/ref-remorqueur.module';
import { DataTablesModule } from 'angular-datatables';
//import { PdfViewerModule } from 'ng2-pdf-viewer';
import { BrowserModule } from '@angular/platform-browser';
const ENTITY_STATES = [
    ...bordereauRoute,
];
@NgModule({
    imports: [
        AuxiliumRefRemorqueurModule,
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule, BrowserModule
    ],
    declarations: [
        BordereauComponent,
    
    ],
    entryComponents: [
        BordereauComponent,
    
    ],
    providers: [
        //BordereauResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumBordereauModule {}
