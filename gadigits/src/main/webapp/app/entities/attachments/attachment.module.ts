import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AuxiliumSharedModule } from '../../shared';

import {
    AttachmentService
} from '.';

@NgModule({
    imports: [
        AuxiliumSharedModule,
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    exports: [
    ],

    providers: [
        AttachmentService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumAttachmentModule {}
