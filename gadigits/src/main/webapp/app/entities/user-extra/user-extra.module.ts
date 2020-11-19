import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumAdminModule } from '../../admin/admin.module';
import {
    UserExtraService,
} from './';

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
        UserExtraService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumUserExtraModule {}
