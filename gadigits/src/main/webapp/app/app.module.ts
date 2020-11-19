import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';
import { LoginModule } from './login';
import { NgbDateParserFormatter } from "@ng-bootstrap/ng-bootstrap";
import { ScrollToModule } from '@nicky-lenaers/ngx-scroll-to';

import {AuxiliumSharedModule, NgbDateFRParserFormatter, UserRouteAccessService, ConfirmationDialogComponent, ShowErrorsComponent, ConfirmationDialogService, AlertUtil} from './shared';
import { AuxiliumHomeModule } from './home/home.module';
import { AuxiliumAdminModule } from './admin/admin.module';
import { AuxiliumAccountModule } from './account/account.module';
import { AuxiliumEntityModule } from './entities/entity.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; 
import { FormBuilder, Validators, FormGroup, FormControl} from '@angular/forms';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    JhiPublicComponent,
    LayoutRoutingModule,
    SidebarComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        ScrollToModule.forRoot(),
        AuxiliumSharedModule,
        AuxiliumHomeModule,
        LoginModule,
        AuxiliumAdminModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        AuxiliumAccountModule,
        AuxiliumEntityModule,
        BrowserAnimationsModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        JhiPublicComponent,
        SidebarComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
        ConfirmationDialogComponent,
        ShowErrorsComponent,
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        ConfirmationDialogService,
        {provide: NgbDateParserFormatter, useClass: NgbDateFRParserFormatter},
        AlertUtil,
    ],
    entryComponents: [ConfirmationDialogComponent],
    bootstrap: [ JhiMainComponent ]
})
export class AuxiliumAppModule {}
