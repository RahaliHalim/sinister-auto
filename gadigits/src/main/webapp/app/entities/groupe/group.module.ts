import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TreeviewModule, TreeviewConfig, TreeviewI18n, TreeviewI18nDefault, TreeviewEventParser, DefaultTreeviewEventParser,OrderDownlineTreeviewEventParser } from 'ngx-treeview';
import { AuxiliumSharedModule } from '../../shared';
import {DataTablesModule} from "angular-datatables";
import {
   
   
    GroupMgmtComponent,
    GroupMgmtDeleteComponent,
    GroupDeleteComponent,
    GroupModalService,
    groupRoute,
    groupPopupRoute,
    GroupResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...groupRoute,
    ...groupPopupRoute
   
];

@NgModule({
    imports: [
        AuxiliumSharedModule,
        TreeviewModule,
        DataTablesModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
       
    ],
    declarations: [
        GroupMgmtComponent,
        GroupMgmtDeleteComponent,
        GroupDeleteComponent
       
    ],
    entryComponents: [
        GroupMgmtComponent,
        GroupMgmtDeleteComponent,
        GroupDeleteComponent
        
    ],
    providers: [
            GroupModalService,
            TreeviewConfig,
            { provide: TreeviewI18n, useClass: TreeviewI18nDefault },
            { provide: TreeviewEventParser, useClass: DefaultTreeviewEventParser },
            { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser },
       
        
        GroupResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumGroupModule {}
