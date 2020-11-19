import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    PrimaryQuotationService,
     quotationPopupRoute
    
 
} from './';


const ENTITY_STATES = [
    ...quotationPopupRoute
 
];

@NgModule({
    imports: [
        
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
      
   
    ],
    entryComponents: [
       
       
    ],
    providers: [
        PrimaryQuotationService
      
     
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPrimaryQuotationModule {}
