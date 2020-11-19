import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../shared';
import { HOME_ROUTE, HomeComponent } from './';
import { NgProgressModule , NgProgressBrowserXhr} from 'ngx-progressbar';
import { BrowserXhr, HttpModule } from '@angular/http';

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true }),
        DataTablesModule,
        NgProgressModule
        
    ],
    declarations: [
        HomeComponent
    ],
    entryComponents: [
    ],
    providers: [
      {provide: BrowserXhr, useClass: NgProgressBrowserXhr}  
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumHomeModule {}
