import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../shared';
import { LOGIN_ROUTE, LoginComponent } from './';


@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot([ LOGIN_ROUTE ], { useHash: true })
    ],
    declarations: [
        LoginComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LoginModule {}
