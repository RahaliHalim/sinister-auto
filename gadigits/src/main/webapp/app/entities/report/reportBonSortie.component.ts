import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { Injectable } from '@angular/core'
import {  Tiers, TiersService } from '../tiers';
import { GaDatatable } from '../../constants/app.constants';
import { Sinister } from '../sinister/sinister.model';
import { forEach } from '@angular/router/src/utils/collection';
import { DataTableDirective } from 'angular-datatables';
import { DomSanitizer } from '@angular/platform-browser';
import { UserExtraService, PermissionAccess } from '../user-extra';
declare var Afficher: { documentOnLoad: Function, documentOnReady: Function };
declare var Chart: any;

@Component({
  selector: 'jhi-generation-bon-sortie',
  templateUrl: './reportBonSortie.component.html',
})

export class GenerationBonSortieComponent implements OnInit, OnDestroy {

    sinister: Sinister;
    sinisterPecsApprove: SinisterPec[];
    sinisterPecsBonSortie: SinisterPec[] = [];
    currentAccount: any;
    immatriculationTier: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
     constructor(
        private tiersService: TiersService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private sinisterPecService: SinisterPecService,
        private sanitizer: DomSanitizer,
        private userExtraService: UserExtraService
    ) {    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.registerChangeInSinisterPecs();
        }

    loadAll() {

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(47, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
                this.sinisterPecService.findAllPrestationPECWithValidAccord(this.currentAccount.id).subscribe((res) => {
                    this.sinisterPecsBonSortie = res.json;
                    this.sinisterPecsBonSortie.forEach(sinPec => {
                        const opdate = new Date(sinPec.declarationDate);
                        sinPec.declarationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' +opdate.getHours() + ':' + opdate.getMinutes()+ ':' + opdate.getSeconds());
                        sinPec.tiers.forEach(tr => {
                            if(tr.responsible){
                                sinPec.immatriculationTier = tr.immatriculation ;
                            }
                        });
                    });
                    this.dtTrigger.next();   
            });
           
            
        });
    }

    trackId(index: number, item: SinisterPec) {
        return item.id;
    }

    printBonSortie( sinPec: SinisterPec) {
        this.sinisterPecService.generateBonSortie(sinPec).subscribe((res) => {

            /*const file = new Blob([res.headers.get('pdfname')], { type: 'application/pdf' });
            const fileURL = URL.createObjectURL(file);
            window.open(fileURL);*/
           
                window.open(res.headers.get('pdfname'), '_blank');
                //this.showPdf(res);
         
        });
     }

     registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }

    dateAsYYYYMMDDHHNNSSLDT(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + 'T' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
            + String(val)).slice(String(val).length);
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

}