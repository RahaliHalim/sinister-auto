import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ConfirmationDialogService } from '../../shared';

import { Partner } from './partner.model';
import { PartnerPopupService } from './partner-popup.service';
import { PartnerService } from './partner.service';
import { VisAVis } from '../vis-a-vis'
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable, Global, SettlementMode } from '../../constants/app.constants';
import { saveAs } from 'file-saver/FileSaver';
import { RefCompagnie } from '../ref-compagnie';
import { RefCompagniePopupService } from '../ref-compagnie';
import { RefCompagnieService } from '../ref-compagnie';
import { PersonneMorale, PersonneMoraleService } from '../personne-morale';
import { RefNatureContrat, RefNatureContratService } from '../ref-nature-contrat';
import { RefTypeContrat, RefTypeContratService } from '../ref-type-contrat';
import { Attachment } from '../attachments/attachment.model';

@Component({
    selector: 'jhi-partner-dialog',
    templateUrl: './partner-dialog.component.html'
})
export class PartnerDialogComponent implements OnInit {

    partner: Partner = new Partner();

    isSaving: boolean;
    visAVis: VisAVis = new VisAVis();
    gerant: VisAVis = new VisAVis();
    visAViss: VisAVis[] = [];
    editVisaVis: boolean = false;
    logoFile: File;
    logoPreview = false;
    dtOptions: any = {};
    disabledForm1 = true;
    visAVisLength = false;
    showVisAVisAndTradeRegister = false;
    ajout1 = true;
    listFeaturesPartner: any;
    showCompany = false;
    creervisavis = false;
    logoAttachment: Attachment = new Attachment();
    labellogo: String = "company";
    attachmentBool: boolean = true;
    attachmentList: Attachment[];
    edit: boolean = false;
    create: boolean = false;
    isPartNameUnique: boolean = true;
    isPartUnique: boolean = true;
    //'Une nouvelle Compagnie a été créé'
    isCompanyLoaded = true;
    showCompanyAttachment = true;
    activeBloque: boolean = false;
    textPattern = Global.textPattern;
    textPatternAlphaNum = '^[a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';
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
        this.initPartner();
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
                        this.showCompanyAttachment = false;
                        this.showCompany = true;
                        /*if (this.partner.companyLogoAttachmentName === 'jpg') { this.partner.companyLogoAttachmentName = 'jpeg' }
                        if (this.partner.companyLogoAttachmentName === 'PNG') { this.partner.companyLogoAttachmentName = 'png' }
                        if (this.partner.companyLogoAttachment64 !== null && this.partner.companyLogoAttachment64 !== undefined) {
                            if (this.partner.companyLogoAttachmentName == 'pdf') {
                                const companyBlob = this.dataURItoBlobAtt(this.partner.companyLogoAttachment64, this.partner.companyLogoAttachmentName, 'application');
                                this.logoFile = new File([companyBlob], 'company', { type: 'application/' + this.partner.companyLogoAttachmentName });
                                this.logoPreview = true;
                                this.showCompanyAttachment = false;
                                this.attachmentBool = false;
                            } else {
                                const companyBlob = this.dataURItoBlobAtt(this.partner.companyLogoAttachment64, this.partner.companyLogoAttachmentName, 'image');
                                this.logoFile = new File([companyBlob], 'company', { type: 'image/' + this.partner.companyLogoAttachmentName });
                                this.logoPreview = true;
                                this.showCompanyAttachment = false;
                                this.attachmentBool = false;
                            }

                        }*/
                        this.attachmentBool = false;
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
                            this.verifVisAVisLength();
                            console.log("lengthffff of visavis" + this.visAViss.length);
                            if (this.partner.visAViss.length) {
                                this.disabledForm1 = false;
                                console.log("test" + this.visAViss.length);
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



    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.SaveListeVisAVis();

                console.log("test logo", this.partner.id);
                this.isSaving = true;
                this.partner.genre = 1;
                this.activeBloque = this.partner.active;
                if (this.activeBloque) {
                    this.partner.active = false;
                }
                if (!this.activeBloque) {
                    this.partner.active = true;
                }
                console.log("test bLoque" + this.partner.active);
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

    private subscribeperToSaveResponse(result: Observable<Partner>) {
        result.subscribe((res: Partner) => {
            this.onSaveSuccess(res);
            this.saveAttachment(res.id);
        },
            (res: Response) =>
                this.onSaveError(res));
    }

    changeConventionne(etat) {
        if (etat == false) {
            this.partner.conventionne = false;
            this.showVisAVisAndTradeRegister = false;
            this.isPartUnique = true;
            this.visAVisLength = true;
            this.visAViss = [];
            this.partner.tradeRegister = null;
        } else {
            this.partner.conventionne = true;
            this.showVisAVisAndTradeRegister = true;
            this.verifVisAVisLength();
        }
    }


    verifVisAVisLength() {
        if (this.visAViss.length) {
            this.visAVisLength = true;
        } else {
            this.visAVisLength = false;
        }
    }


    changeForeignCompany(etat) {
        if (etat == false) {
            this.partner.foreignCompany = false;
        } else {
            this.partner.foreignCompany = true;
        }
    }


    private onSaveSuccess(result: Partner) {
        this.eventManager.broadcast({ name: 'partnerListModification', content: 'OK' });
        this.router.navigate(['../partner']);
        this.isSaving = false;
        if (this.edit) {
            this.alertService.success('auxiliumApp.partner.updated', null, null);
        }
        if (this.create) {
            this.alertService.success('auxiliumApp.partner.created', null, null);
        }
    }

    addVisAVis() {
        this.verifVisAVisLength();
        if (this.editVisaVis == false) {
            this.visAViss.push(this.visAVis);
            console.log("test" + this.visAVis)
            this.visAVis = new VisAVis();
            this.disabledForm1 = false;
            this.verifVisAVisLength();
        }
        if (this.editVisaVis == true) {
            this.visAVis = new VisAVis();
            this.disabledForm1 = false;
            this.verifVisAVisLength();
        }
        console.log("testLENGHTH" + this.visAViss.length)
    }
    deleteVisAVis(visAVis) {
        this.visAViss.forEach((item, index) => {
            if (item === visAVis) this.visAViss.splice(index, 1);
        });
        this.verifVisAVisLength();
    }
    editVisAVis(visAVis) {
        this.editVisaVis = true;
        this.visAVis = visAVis;
        this.disabledForm1 = true;
        this.ajout1 = false;
        this.verifVisAVisLength();
    }
    SaveListeVisAVis() {
        /*if (this.gerant !== undefined) {
            this.gerant.isGerant = true;
            this.visAViss.push(this.gerant);
        }*/
        if (this.visAViss.length > 0) {
            this.visAViss.forEach(visAVis => {
                visAVis.entityName = "partner";

            });
        }
        this.partner.visAViss = this.visAViss;
        this.verifVisAVisLength();
        console.log("testLENGHTH" + this.visAViss.length)
    }


    findCompanyByNameReg() {
        console.log('_______________________________________________________________');

        if (this.partner.tradeRegister !== null && this.partner.tradeRegister !== undefined && this.partner.tradeRegister !== '' && this.partner.companyName !== null && this.partner.companyName !== undefined && this.partner.companyName !== '') {
            this.partnerService.findByCompanyNameRegistre(this.partner).subscribe((subRes: Partner) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.partner.id) {
                    this.isPartUnique = false;
                    this.alertService.error('auxiliumApp.partner.uniqueCompany', null, null);
                } else {
                    this.isPartUnique = true;
                }
            });
        }
    }

    addForm1() {
        this.disabledForm1 = true
        this.editVisaVis = false;
        this.visAVis = new VisAVis();
        this.verifVisAVisLength();
    }

    saveAttachment(partnerId: number) {

        if (this.logoFile && !this.logoAttachment.id && this.updateLogo) {
            this.partnerService.saveAttachmentsCompany(partnerId, this.logoFile, this.labellogo, this.nomImage).subscribe((res: Attachment) => {
                this.logoAttachment = res;
            });
        }

    }

    onLogoFileChange(fileInput: any) {
        this.updateLogo = true;
        this.nomImage = 'noExtension';
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint != null) {
        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.nomImage = this.nomImage.concat('.', this.extensionImage);

        }
        this.logoFile = fileInput.target.files[0];
        this.logoPreview = true;
        console.log("test logo file" + this.logoFile.name);
        this.attachmentBool = false;
    }

    downloadLogoFile() {
        if (this.logoFile) {
            saveAs(this.logoFile);
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

    confirm(data) {
        this.alertService.success('auxiliumApp.partner.home.titleDealers', null, null);
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

    trackPersonneMoraleById(index: number, item: PersonneMorale) {
        return item.id;
    }

    trackRefNatureContratById(index: number, item: RefNatureContrat) {
        return item.id;
    }

    trackRefTypeContratById(index: number, item: RefTypeContrat) {
        return item.id;
    }

    trimServiceTypeNumber() {
        this.partner.companyName = this.partner.companyName.trim();
        this.partner.address = this.partner.address.trim();
        this.partner.tradeRegister = this.partner.tradeRegister.trim();
        this.visAVis.prenom = this.visAVis.prenom.trim();
        this.visAVis.nom = this.visAVis.nom.trim();
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
export class PartnerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partnerPopupService: PartnerPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.partnerPopupService
                    .open(PartnerDialogComponent as Component, params['id']);
            } else {
                this.partnerPopupService
                    .open(PartnerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
