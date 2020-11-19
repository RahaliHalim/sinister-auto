import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    VisAVisService,

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
        VisAVisService,
      
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumVisAVisModule {}