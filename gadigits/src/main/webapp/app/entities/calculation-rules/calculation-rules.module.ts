import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from 'angular-datatables';
import { AuxiliumSharedModule } from '../../shared';
import {
    CalculationRulesDialogComponent,
    CalculationRulesService,
    calculationrulesRoute,
    CalculationRulesComponent
} from './';


const ENTITY_STATES = [
    ...calculationrulesRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule,
        
    ],
    declarations: [
        CalculationRulesDialogComponent,
        CalculationRulesComponent

    ],
    entryComponents: [
        CalculationRulesDialogComponent,
        CalculationRulesComponent

    ],
    providers: [
        CalculationRulesService

    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumCalculationRulesModule {}
