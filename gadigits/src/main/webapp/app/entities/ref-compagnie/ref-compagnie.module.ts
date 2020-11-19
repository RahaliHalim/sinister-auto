import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import { AuxiliumPersonneMoraleModule } from '../personne-morale/personne-morale.module';

import {
    RefCompagnieService,
    RefCompagniePopupService
} from './';

@NgModule({
    imports: [
        AuxiliumPersonneMoraleModule,
        AuxiliumSharedModule
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        RefCompagnieService,
        RefCompagniePopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumRefCompagnieModule {}
