import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuxiliumSharedModule } from '../../shared';
import {DataTablesModule} from "angular-datatables";
import {
   
    FeatureService,
    FeatureComponent,
    featureRoute,
    featurePopupRoute,
    FeatureResolvePagingParams,
    FeatureModalService,
    FeatureMgmtDeleteComponent,
    FeatureDeleteComponent
 
} from '.';


const ENTITY_STATES = [
    ...featureRoute,
    ...featurePopupRoute

];

@NgModule({
    imports: [
        
        AuxiliumSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTablesModule
    ],
    declarations: [
        FeatureComponent,
        FeatureMgmtDeleteComponent,
        FeatureDeleteComponent
   
    ],
    entryComponents: [
        FeatureComponent,
        FeatureMgmtDeleteComponent,
        FeatureDeleteComponent
       
    ],
    providers: [
        FeatureModalService,
        FeatureService,
        FeatureResolvePagingParams,
        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumFeatureModule {}
