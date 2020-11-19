import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    DroolsParamsComponent,
    DroolsParamsService,
    DroolsService
    
} from './';
import { droolsRoute } from './drools-params.route';


const ENTITY_STATES = [
    droolsRoute
    
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DroolsParamsComponent,
       
    ],
    entryComponents: [
        DroolsParamsComponent,
       
    ],
    providers: [
        DroolsParamsService,
        DroolsService
        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumDroolsModule {}
