import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    StepService,
    StepPopupService,
    StepComponent,
    StepDetailComponent,
    StepDialogComponent,
    StepPopupComponent,
    StepDeletePopupComponent,
    StepDeleteDialogComponent,
    stepRoute,
    stepPopupRoute,
} from './';

const ENTITY_STATES = [
    ...stepRoute,
    ...stepPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StepComponent,
        StepDetailComponent,
        StepDialogComponent,
        StepDeleteDialogComponent,
        StepPopupComponent,
        StepDeletePopupComponent,
    ],
    entryComponents: [
        StepComponent,
        StepDialogComponent,
        StepPopupComponent,
        StepDeleteDialogComponent,
        StepDeletePopupComponent,
    ],
    providers: [
        StepService,
        StepPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumStepModule {}
