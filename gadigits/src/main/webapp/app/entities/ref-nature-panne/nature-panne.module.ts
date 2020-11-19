import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../../shared';
import { NaturePanneComponent } from './nature-panne.component';
import { NaturePanneRoute } from './nature-panne.route';
import { NaturePanneService } from './nature-panne.service';


const ENTITY_STATES = [
    ...NaturePanneRoute
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        NaturePanneComponent
    ],
    entryComponents: [
        NaturePanneComponent
    ],
    providers: [
        NaturePanneService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumNaturePanneModule {}
