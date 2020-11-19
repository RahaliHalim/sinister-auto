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
    templateUrl: './dealer-detail.component.html'
})
export class DealerDetailComponent implements OnInit {

    partner: Partner = new Partner();
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    dtOptions: any = {};
    isSaving: boolean;
    disabledForm1 = true;
    visAVis: VisAVis = new VisAVis();
    gerant: VisAVis = new VisAVis();
    logoPreview = false;
    visAViss: VisAVis[] = [];
    isCompanyLoaded = true;
    creervisavis = false;
    editVisaVis: boolean = false;
    activeBloque: boolean = false;
    logoFile: File;
    private ngbModalRef: NgbModalRef;
    entityName = 'Partner';
    textPatterntextPattern = Global.textPattern;
    textPatternAlphaNum = '^[a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';
    textPatternNotAlphNul = '^[a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';

    constructor(
        private eventManager: JhiEventManager,
        private partnerService: PartnerService,
        private route: ActivatedRoute,
        private historyPopupService: HistoryPopupService,
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
                        if (this.partner.concessionnaireLogoAttachmentName === 'jpg') { this.partner.concessionnaireLogoAttachmentName = 'jpeg' }
                        if (this.partner.concessionnaireLogoAttachmentName === 'PNG') { this.partner.concessionnaireLogoAttachmentName = 'png' }
                        if (this.partner.concessionnaireLogoAttachment64 !== null && this.partner.concessionnaireLogoAttachment64 !== undefined) {
                            if (this.partner.companyLogoAttachmentName == 'pdf') {
                                const concessBlob = this.dataURItoBlobAtt(this.partner.concessionnaireLogoAttachment64, this.partner.concessionnaireLogoAttachmentName, 'application');
                                this.logoFile = new File([concessBlob], 'company', { type: 'application/' + this.partner.concessionnaireLogoAttachmentName });
                                this.logoPreview = true;
                            } else {
                                const concessBlob = this.dataURItoBlobAtt(this.partner.concessionnaireLogoAttachment64, this.partner.concessionnaireLogoAttachmentName, 'image');
                                this.logoFile = new File([concessBlob], 'company', { type: 'image/' + this.partner.concessionnaireLogoAttachmentName });
                                this.logoPreview = true;
                            }

                        }
                        this.activeBloque = this.partner.active;
                        if (this.activeBloque) {
                            this.partner.active = false;
                        }
                        if (!this.activeBloque) {
                            this.partner.active = true;
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

    downloadLogoFile() {
        if (this.logoFile) {
            saveAs(this.logoFile);
        }
    }

    addForm1() {
        this.disabledForm1 = true
        this.editVisaVis = false;
        this.visAVis = new VisAVis();
    }

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

    registerChangeInPartners() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partnerListModification',
            (response) => this.load(this.partner.id)
        );
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
