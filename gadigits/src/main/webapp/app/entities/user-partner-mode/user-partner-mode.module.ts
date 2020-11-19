import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumAdminModule } from '../../admin/admin.module';
import {
    UserPartnerModeService,
} from '.';

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumAdminModule
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        UserPartnerModeService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumUserPartnerModeModule {}
