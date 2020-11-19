import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { AuxiliumSharedModule } from '../shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */




import { BrowserModule } from '@angular/platform-browser';




import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BootstrapModalModule } from 'ng2-bootstrap-modal';
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import { NgxPaginationModule } from 'ngx-pagination';


//import { ChartsModule } from 'ng2-charts'


import {
    
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    JhiMetricsMonitoringModalComponent,
    JhiMetricsMonitoringComponent,
    JhiHealthModalComponent,
    JhiHealthCheckComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    AuditsService,
    JhiConfigurationService,
    JhiHealthService,
    JhiMetricsService,
    LogsService,
    UserResolvePagingParams,
    UserResolve,
    UserModalService,
    FunctionnalityAccessComponent
} from './';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { TreeviewModule, TreeviewConfig, TreeviewI18n, TreeviewI18nDefault, TreeviewEventParser, DefaultTreeviewEventParser,OrderDownlineTreeviewEventParser } from 'ngx-treeview';
import { UserReslationService } from '../shared/auth/user-relation.service';

@NgModule({
    imports: [
        AuxiliumSharedModule,
        RouterModule.forRoot(adminState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
        DataTablesModule,
        NgMultiSelectDropDownModule,
        TreeviewModule
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiMetricsMonitoringComponent,
        JhiMetricsMonitoringModalComponent,
        FunctionnalityAccessComponent
    ],
    entryComponents: [
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        JhiHealthModalComponent,
        JhiMetricsMonitoringModalComponent,
    ],
    providers: [
        UserReslationService,
        AuditsService,
        JhiConfigurationService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService,
        TreeviewConfig,
        { provide: TreeviewI18n, useClass: TreeviewI18nDefault },
        { provide: TreeviewEventParser, useClass: DefaultTreeviewEventParser },
        { provide: TreeviewEventParser, useClass: OrderDownlineTreeviewEventParser },
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumAdminModule {}
