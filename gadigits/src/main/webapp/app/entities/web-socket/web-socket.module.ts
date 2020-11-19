import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


import {

  

} from './';
import { WebSocketService } from './web-socket.service';



@NgModule({
    imports: [

    ],
    declarations: [
    
    ],
    entryComponents: [
      
    ],
    providers: [
        WebSocketService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebSocketModule {}