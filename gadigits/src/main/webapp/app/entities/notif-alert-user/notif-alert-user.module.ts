import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import {
    NotifAlertUserService
} from './';

const ENTITY_STATES = [

];


@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
    
    ],
    entryComponents: [
       
    ],
    providers: [
        NotifAlertUserService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumNotificationAlertUserModule {}
