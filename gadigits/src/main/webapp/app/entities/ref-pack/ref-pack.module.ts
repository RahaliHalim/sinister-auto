import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import {
    RefPackService
} from './';

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        AuxiliumSharedModule
    ],
    declarations: [
    ],

    entryComponents: [
    ], 
    providers: [
        RefPackService,
    ],
    
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefPackModule {}
