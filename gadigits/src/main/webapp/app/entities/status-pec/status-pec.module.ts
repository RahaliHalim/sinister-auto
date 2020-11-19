import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AuxiliumSharedModule } from '../../shared';
import {
    StatusPecService,
} from './';

@NgModule({
    imports: [
        AuxiliumSharedModule,
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        StatusPecService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumStatusPecModule {}
