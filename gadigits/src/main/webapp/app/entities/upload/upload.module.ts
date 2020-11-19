import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import {
    UploadService,
    UploadComponent,
    UploadRoute,
   
} from './';

const ENTITY_STATES = [
    ...UploadRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        UploadComponent,
    ],
    entryComponents: [
        UploadComponent,
    ],
    providers: [
        UploadService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumUploadModule {}
