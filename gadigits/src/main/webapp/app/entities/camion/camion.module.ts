import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule }from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuxiliumSharedModule } from '../../shared';
import { BrowserModule } from '@angular/platform-browser';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import {
    CamionService,
} from '.';

@NgModule({
    imports: [
        AuxiliumSharedModule,
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        BrowserModule,
        NgMultiSelectDropDownModule.forRoot(),
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        CamionService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumCamionModule {}
