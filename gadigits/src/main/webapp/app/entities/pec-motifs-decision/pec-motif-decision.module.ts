import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import { PecMotifDecisionService } from './pec-motif-decision.service';

const ENTITY_STATES = [
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
       
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        PecMotifDecisionService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumPecMotifDecisionModule {}
