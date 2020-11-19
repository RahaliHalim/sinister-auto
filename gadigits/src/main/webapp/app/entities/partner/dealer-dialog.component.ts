import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ConfirmationDialogService } from '../../shared';


import { Partner } from './partner.model';
import { PartnerPopupService } from './partner-popup.service';
import { PartnerService } from './partner.service';
import { VisAVis } from '../vis-a-vis'
import { Principal } from '../../shared';
import { GaDatatable, Global } from '../../constants/app.constants';
import { saveAs } from 'file-saver/FileSaver';
import { Attachment } from '../attachments/attachment.model';

@Component({
    selector: 'jhi-partner-dialog',
    templateUrl: './dealer-dialog.component.html'
})
export class DealerDialogComponent implements OnInit {

    partner: Partner = new Partner();

    isSaving: boolean;
    visAVis: VisAVis = new VisAVis();
    gerant: VisAVis = new VisAVis();
    visAViss: VisAVis[] = [];
    editVisaVis: boolean = false;
    dtOptions: any = {};
    logoFile: File;
    logoPreview = false;
    disabledForm1 = true;
    ajout1 = true;
    showCompanyAttachment = true;
    showCompany = false;
    listFeaturesPartner: any;
    creervisavis = false;
    logoAttachment: Attachment = new Attachment();
    labellogo: String = "concessionnaire";
    attachmentBool: boolean = true;
    edit: boolean = false;
    create: boolean = false;
    isDealerUnique: boolean = true;
    isDealerRegistreUnique: boolean = true;
    isCompanyLoaded = true;
    activeBloque: boolean = false;
    textPattern = Global.textPattern;
    textPatternAlphaNum = '^[a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';
    textPatternNotAlphNul = '^[a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';
    extensionImage: string;
    nomImage: string;
    updateLogo = false;

    constructor(
        private route: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private partnerService: PartnerService,
        public principal: Principal,
        private router: Router,
        private confirmationDialogService: ConfirmationDialogService,
        private eventManager: JhiEventManager

    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        //this.disabledForm1 = false;
        this.initPartner();
    }

    initPartner() {
        this.route.params.subscribe((params) => {
            if (params['id']) {
                this.partnerService
                    .find(params['id'])
                    .subscribe((partner: Partner) => {
                        this.partner = partner;
                        this.showCompanyAttachment = false;
                        this.showCompany = true;
                        this.attachmentBool = false;
                        /*if (this.partner.concessionnaireLogoAttachmentName === 'jpg') { this.partner.concessionnaireLogoAttachmentName = 'jpeg' }
                        if (this.partner.concessionnaireLogoAttachmentName === 'PNG') { this.partner.concessionnaireLogoAttachmentName = 'png' }
                        if (this.partner.concessionnaireLogoAttachment64 !== null && this.partner.concessionnaireLogoAttachment64 !== undefined) {
                            if (this.partner.companyLogoAttachmentName == 'pdf') {
                                const concessBlob = this.dataURItoBlobAtt(this.partner.concessionnaireLogoAttachment64, this.partner.concessionnaireLogoAttachmentName, 'application');
                                this.logoFile = new File([concessBlob], 'Concessionnaire', { type: 'application/' + this.partner.concessionnaireLogoAttachmentName });
                                this.logoPreview = true;
                                this.showCompanyAttachment = false;
                                this.attachmentBool = false;
                            } else {
                                const concessBlob = this.dataURItoBlobAtt(this.partner.concessionnaireLogoAttachment64, this.partner.concessionnaireLogoAttachmentName, 'image');
                                this.logoFile = new File([concessBlob], 'Concessionnaire', { type: 'image/' + this.partner.concessionnaireLogoAttachmentName });
                                this.logoPreview = true;
                                this.showCompanyAttachment = false;
                                this.attachmentBool = false;
                            }

                        }*/
                        this.activeBloque = this.partner.active;
                        if (this.activeBloque) {
                            this.partner.active = false;
                        }
                        if (!this.activeBloque) {
                            this.partner.active = true;
                        }
                        if (partner.visAViss) {
                            /*partner.visAViss.forEach(visAVis => {
                                if (visAVis.isGerant == true) {
                                    this.gerant = visAVis;
                                    partner.visAViss.forEach((item, index) => {
                                        if (item === visAVis) partner.visAViss.splice(index, 1);
                                    });
                                }
                            });*/
                            this.visAViss = partner.visAViss;
                            if (this.partner.visAViss.length) {
                                this.disabledForm1 = false;
                            }
                        }
                    });
            }
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, partner, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                partner[field] = base64Data;
                partner[`${field}ContentType`] = file.type;
            });
        }
    }

    trimServiceTypeNumber() {
        this.partner.companyName = this.partner.companyName.trim();
        this.partner.address = this.partner.address.trim();
        this.partner.tradeRegister = this.partner.tradeRegister.trim();
        this.visAVis.nom = this.visAVis.nom.trim();
        this.visAVis.prenom = this.visAVis.prenom.trim();
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.SaveListeVisAVis();
                this.isSaving = true;
                this.partner.genre = 2;
                this.activeBloque = this.partner.active;
                if (this.activeBloque) {
                    this.partner.active = false;
                }
                if (!this.activeBloque) {
                    this.partner.active = true;
                }
                if (this.partner.id !== undefined) {
                    this.subscribeperToSaveResponse(
                        this.partnerService.update(this.partner));
                    this.edit = true;
                } else {
                    this.subscribeperToSaveResponse(
                        this.partnerService.create(this.partner));
                    this.create = true;
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    deleteLogoFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showCompanyAttachment = true;
                this.logoPreview = false;
                this.logoFile = null;
                this.logoAttachment.label = null;
                this.attachmentBool = true;
                this.showCompany = false;
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    getPieceNew(entityName: string, partnerId: number, label: string) {
        this.partnerService.getPieceBySinPecAndLabel(entityName, partnerId, label).subscribe((blob: Blob) => {
            const indexOfFirst = (blob.type).indexOf('/');
            this.extensionImage = ((blob.type).substring((indexOfFirst + 1), ((blob.type).length)));
            this.extensionImage = this.extensionImage.toLowerCase();
            let fileName = label + "." + this.extensionImage;
            console.log(fileName);

            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, fileName);
            } else {
                var a = document.createElement('a');
                a.href = URL.createObjectURL(blob);
                a.download = fileName;
                a.target = '_blank';
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            }
        },
            err => {
                alert("Error while downloading. File Not Found on the Server");
            });
    }

    private subscribeperToSaveResponse(result: Observable<Partner>) {
        result.subscribe((res: Partner) => {
            this.onSaveSuccess(res);
            this.saveAttachment(res.id);
        },
            (res: Response) =>
                this.onSaveError(res));
    }


    private onSaveSuccess(result: Partner) {
        this.eventManager.broadcast({ name: 'dealerListModification', content: 'OK' });
        this.router.navigate(['../dealer']);
        this.isSaving = false;
        if (this.edit) {
            this.alertService.success('auxiliumApp.partner.updatedConcessionnaire', null, null);
        }
        if (this.create) {
            this.alertService.success('auxiliumApp.partner.createdConcessionnaire', null, null);
        }
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

    addVisAVis() {
        if (this.editVisaVis == false) {
            this.visAViss.push(this.visAVis);
            console.log("test" + this.visAVis)
            this.visAVis = new VisAVis();
            this.disabledForm1 = false;
        }
        else if (this.editVisaVis == true) {
            this.visAVis = new VisAVis();
            this.disabledForm1 = false;
        }
        console.log("testLENGHTH" + this.visAViss.length)

    }
    deleteVisAVis(visAVis) {
        this.visAViss.forEach((item, index) => {
            if (item === visAVis) this.visAViss.splice(index, 1);
        });
    }
    editVisAVis(visAVis) {
        this.editVisaVis = true;
        this.visAVis = visAVis;
        this.disabledForm1 = true;
        this.ajout1 = false;
    }
    SaveListeVisAVis() {
        /*if (this.gerant !== undefined) {
            this.gerant.isGerant = true;
            this.visAViss.push(this.gerant);
        }*/
        if (this.visAViss.length > 0) {
            this.visAViss.forEach(visAVis => {
                visAVis.entityName = "partner"; console.log("testLENGHTH" + this.visAViss.length)

            });
        }
        this.partner.visAViss = [];
        this.partner.visAViss = this.visAViss;
        console.log("testLENGHTH" + this.visAViss.length)
    }

    addForm1() {
        this.disabledForm1 = true
        this.editVisaVis = false;
        this.visAVis = new VisAVis();
    }

    onLogoFileChange(fileInput: any) {
        this.updateLogo = true;
        this.nomImage = 'noExtension';
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint != null) {
            this.logoAttachment.name = "noExtension";

        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.logoAttachment.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.logoFile = fileInput.target.files[0];
        this.logoPreview = true;
        console.log(this.logoFile);
        this.attachmentBool = false;
    }

    downloadLogoFile() {
        if (this.logoFile) {
            saveAs(this.logoFile);
        }
    }

    saveAttachment(partnerId: number) {

        if (this.logoFile && !this.logoAttachment.id && this.updateLogo) {
            this.partnerService.saveAttachmentsConcessionnaire(partnerId, this.logoFile, this.labellogo, this.logoAttachment.name).subscribe((res: Attachment) => {
                this.logoAttachment = res;
            });
        }
    }


    findDealerByNameReg() {
        console.log('_______________________________________________________________');
        if (this.partner.tradeRegister !== null && this.partner.tradeRegister !== undefined && this.partner.tradeRegister !== '' && this.partner.companyName !== null && this.partner.companyName !== undefined && this.partner.companyName !== '') {
            this.partnerService.findByDealerNameReg(this.partner).subscribe((subRes: Partner) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.partner.id) {
                    this.isDealerUnique = false;
                    this.alertService.error('auxiliumApp.partner.uniqueConcess', null, null);
                } else {
                    this.isDealerUnique = true;
                }
            });
        }
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

}

@Component({
    selector: 'jhi-partner-popup',
    template: ''
})
export class DealerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partnerPopupService: PartnerPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.partnerPopupService
                    .open(DealerDialogComponent as Component, params['id']);
            } else {
                this.partnerPopupService
                    .open(DealerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
