import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiAlertService } from 'ng-jhipster';
import { saveAs } from 'file-saver/FileSaver';
import { ResponseWrapper } from '../../shared';

import { Partner, PartnerService } from './../partner';
import { Search } from '../sinister/search.model';
import { PolicyIndicator } from './policy-indicator.model';
import { ReportsService } from './reports.service';
import { Governorate, GovernorateService } from './../governorate';
import { RefPack, RefPackService } from './../ref-pack';

import { GaDatatable } from '../../constants/app.constants';

@Component({
    selector: "jhi-policy-indicator",
    templateUrl: "./policy-indicator.component.html"
})
export class PolicyIndicatorComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    dtOptions: any = {};
    dtTrigger: Subject<PolicyIndicator> = new Subject();

    currentAccount: any;
    policyIndicators: PolicyIndicator[];
    partners: Partner[];
    governorate: Governorate[];
    pack: RefPack[];
    search: Search = new Search();

    constructor(
        private reportsService: ReportsService,
        private partnerService: PartnerService,
        private goverService: GovernorateService,
        private refpackService: RefPackService,
        private alertService: JhiAlertService
    ) { }

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        // Init datatable options
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.dtOptions.scrollX = true;
        this.goverService.query().subscribe((res: ResponseWrapper) => {
            this.governorate = res.json;
        });
        this.partnerService.query().subscribe((res: ResponseWrapper) => {
            this.partners = res.json;
        });
        this.refpackService.query().subscribe((res: ResponseWrapper) => {
            this.pack = res.json;
        });

        this.loadAll();
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }

    /**
     * Load all sinister
     */
    loadAll() {
        this.reportsService
            .findPolicyIndicatorReport(this.search)
            .subscribe(
                (res: PolicyIndicator[]) => {
                    this.policyIndicators = res;
                    this.rerender();
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    filter() {
        this.loadAll();
    }

    clear() {
        this.search = new Search();
        this.loadAll();
    }

    changeMode(companyId) {
        this.pack = [];
        this.refpackService.findByPartner(companyId).subscribe((res: ResponseWrapper) => {
            this.pack = res.json;
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
    }
}
