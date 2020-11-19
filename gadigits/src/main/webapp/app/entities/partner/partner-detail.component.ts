import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Partner } from './partner.model';
import { PartnerService } from './partner.service';
import { GaDatatable, Global, SettlementMode } from '../../constants/app.constants';
import { VisAVis } from '../vis-a-vis'
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AgencyPopupService } from '../agency';
import { AgenceConcessPopupDetail } from '../agency/agenceConcessHistoryPopup';
import { saveAs } from 'file-saver/FileSaver';



@Component({
    selector: 'jhi-partner-detail',
    templateUrl: './partner-detail.component.html'
})
export class PartnerDetailComponent implements OnInit {

    partner: Partner = new Partner();
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    dtOptions: any = {};
    isSaving: boolean;
    logoFile: File;
    logoPreview = false;
    disabledForm1 = true;
    visAVis: VisAVis = new VisAVis();
    gerant: VisAVis = new VisAVis();
    visAViss: VisAVis[] = [];
    isCompanyLoaded = true;
    creervisavis = false;
    editVisaVis: boolean = false;
    showVisAVisAndTradeRegister = false;
    activeBloque: boolean = false;
    textPattern = Global.textPattern;
    textPatternAlphaNum = '^[a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';
    private ngbModalRef: NgbModalRef;
    entityName = 'Partner';

    constructor(
        private eventManager: JhiEventManager,
        private partnerService: PartnerService,
        private route: ActivatedRoute,
        private agencyPopupService: AgencyPopupService,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.disabledForm1 = false;
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.initPartner();
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPartners();
    }

    initPartner() {
        this.route.params.subscribe((params) => {
            if (params['id']) {
                this.partnerService
                    .find(params['id'])
                    .subscribe((partner: Partner) => {
                        this.partner = partner;
                        if (this.partner.conventionne) {
                            this.showVisAVisAndTradeRegister = true;
                        }
                        if (this.partner.companyLogoAttachmentName === 'jpg') { this.partner.companyLogoAttachmentName = 'jpeg' }
                        if (this.partner.companyLogoAttachmentName === 'PNG') { this.partner.companyLogoAttachmentName = 'png' }
                        if (this.partner.companyLogoAttachment64 !== null && this.partner.companyLogoAttachment64 !== undefined) {
                            if (this.partner.companyLogoAttachmentName == 'pdf') {
                                const companyBlob = this.dataURItoBlobAtt(this.partner.companyLogoAttachment64, this.partner.companyLogoAttachmentName, 'application');
                                this.logoFile = new File([companyBlob], 'company', { type: 'application/' + this.partner.companyLogoAttachmentName });
                                this.logoPreview = true;
                            } else {
                                const companyBlob = this.dataURItoBlobAtt(this.partner.companyLogoAttachment64, this.partner.companyLogoAttachmentName, 'image');
                                this.logoFile = new File([companyBlob], 'company', { type: 'image/' + this.partner.companyLogoAttachmentName });
                                this.logoPreview = true;
                            }

                        }
                        this.activeBloque = this.partner.active;
                        if (this.activeBloque) {
                            this.partner.active = false;
                            console.log("testcompname111" + this.partner.active)
                        }
                        if (!this.activeBloque) {
                            this.partner.active = true;
                            console.log("testcompname22" + this.partner.active)
                        }
                        if (partner.visAViss) {
                            partner.visAViss.forEach(visAVis => {
                                if (visAVis.isGerant == true) {
                                    this.gerant = visAVis;
                                    partner.visAViss.forEach((item, index) => {
                                        if (item === visAVis) partner.visAViss.splice(index, 1);
                                    });
                                }
                            });
                            this.visAViss = partner.visAViss;
                        }
                    });
            }
        });
    }

    load(id) {
        this.partnerService.find(id).subscribe((partner) => {
            this.partner = partner;
        });
    }


    /*ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }*/

    dataURItoBlobAtt(dataURI, extention, type) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: type + '/' + extention });
        return blob;
    }

    addForm1() {
        this.disabledForm1 = true
        this.editVisaVis = false;
        this.visAVis = new VisAVis();
    }


    registerChangeInPartners() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partnerListModification',
            (response) => this.load(this.partner.id)
        );
    }

    downloadLogoFile() {
        if (this.logoFile) {
            saveAs(this.logoFile);
        }
    }

    selectHistory(id, entityName) {
        this.ngbModalRef = this.agencyPopupService.openHist(AgenceConcessPopupDetail as Component, id, entityName);
        this.ngbModalRef.result.then((result: any) => {
            if (result !== null && result !== undefined) {
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });
    }
}
