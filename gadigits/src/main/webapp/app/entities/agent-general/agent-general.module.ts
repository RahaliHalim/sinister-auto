import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AuxiliumSharedModule } from '../../shared';
import {
    AgentGeneralService,
    AgentGeneralPopupService,
    AgentGeneralComponent,
    AgentGeneralDetailComponent,
    AgentGeneralDialogComponent,
    AgentGeneralPopupComponent,
    AgentGeneralDeletePopupComponent,
    AgentGeneralDeleteDialogComponent,
    agentGeneralRoute,
    agentGeneralPopupRoute,
    AgentGeneralResolvePagingParams,
} from './';
import { AuxiliumPersonnePhysiqueModule } from '../personne-physique/personne-physique.module';

const ENTITY_STATES = [
    ...agentGeneralRoute,
    ...agentGeneralPopupRoute,
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        AuxiliumPersonnePhysiqueModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AgentGeneralComponent,
        AgentGeneralDetailComponent,
        AgentGeneralDialogComponent,
        AgentGeneralDeleteDialogComponent,
        AgentGeneralPopupComponent,
        AgentGeneralDeletePopupComponent,
    ],
    entryComponents: [
        AgentGeneralComponent,
        AgentGeneralDialogComponent,
        AgentGeneralPopupComponent,
        AgentGeneralDeleteDialogComponent,
        AgentGeneralDeletePopupComponent,
    ],
    providers: [
        AgentGeneralService,
        AgentGeneralPopupService,
        AgentGeneralResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumAgentGeneralModule {}
