import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';
import { Statement } from './statement.model';
import { StatementService } from './statement.service';
import { PartnerService } from './../partner/partner.service';
import { Partner } from './../partner/partner.model';
import { Principal, ResponseWrapper } from '../../shared';
import { DataTableDirective } from 'angular-datatables';
import { GaDatatable } from './../../constants/app.constants';

@Component({
    selector: 'jhi-statement',
    templateUrl: './statement.component.html'
})
export class StatementComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    partners: Partner[];    
    currentAccount: any;
    eventSubscriber: Subscription;
    month: string;
    year: string;

    months = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
    years = [];
    dtOptions: any = {};
    dtTrigger: Subject<Partner> = new Subject();

    constructor(
        private partnerService: PartnerService,
        private statementService: StatementService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private sanitizer: DomSanitizer,
        private principal: Principal
    ) {
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

    loadAll() {

        this.partnerService.findAllCompaniesForStatement(this.month + this.year).subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json));
    }
    
    /**
     * Export user in excel format
     * @param id : the user id
     */
    excelExport(partner: Partner) {
        if(partner.id == null || partner.id === undefined) {
            console.log("Erreur lors de la génération");
            
        } else { // OK
            this.statementService.exportStatementInExcel(partner.id, this.month + this.year).subscribe((blob: Blob) => {
                let companyName = partner.companyName.split(' ').join('_');
                let fileName="Bordereau_" + companyName + ".xlsx";
                if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                    window.navigator.msSaveOrOpenBlob(blob, fileName);
                } else {
                    var a = document.createElement('a');
                    a.href = URL.createObjectURL(blob);
                    a.download = fileName;
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                }
            },
            err => {
              alert("Error while downloading. File Not Found on the Server");
            }); 
        }
    }
    
    changeMonth() {
        this.loadAll();
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        // Init year list
        const d = new Date();
        const date = d.getDate();
        let i = 2016;
        while (i <= d.getFullYear()) {
            this.years.push("" + (i));
            i++;
        }

        // Init filter 
        this.month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : '' + (d.getMonth() + 1)  ; // Since getMonth() returns month from 0-11 not 1-12
        this.year = '' + d.getFullYear();

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
