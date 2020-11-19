import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    QuotationService,
     quotationPopupRoute,
    
 
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
        QuotationService
      
     
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumQuotationModule {}
