import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    QuotationMPService,
     quotationPopupRoute
    
 
} from '.';


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
        QuotationMPService
      
     
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumQuotationMPModule {}
