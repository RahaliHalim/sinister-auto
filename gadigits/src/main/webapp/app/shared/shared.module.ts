import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    AuxiliumSharedLibsModule,
    AuxiliumSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    GroupService,
    GroupDetailsService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent,
    ShowErrorsComponent
} from './';

@NgModule({
    imports: [
        AuxiliumSharedLibsModule,
        AuxiliumSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        GroupService,
        GroupDetailsService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        AuxiliumSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class AuxiliumSharedModule {}
