import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { AuxiliumSharedModule } from "../../shared";
import { FonctionnaliteService } from "./fonctionnalite.service";

@NgModule({
    imports:[
        AuxiliumSharedModule,
    ],
    declarations:[

    ],
    entryComponents:[

    ],
    providers:[
        FonctionnaliteService,
    ],
    schemas:[
        CUSTOM_ELEMENTS_SCHEMA
    ]
})
export class AuxiliumFonctionnaliteModule{}