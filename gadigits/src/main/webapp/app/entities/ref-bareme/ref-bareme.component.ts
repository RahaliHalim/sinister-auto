import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, Observable, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RefBareme } from './ref-bareme.model';
import { RefBaremeService } from './ref-bareme.service';
import { Principal, ConfirmationDialogService } from '../../shared';
import { DomSanitizer } from '@angular/platform-browser';
import { saveAs } from 'file-saver/FileSaver';
import { Attachment } from '../attachments';
import { GaDatatable, Global } from '../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
import { UserExtraService, PermissionAccess } from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';
import { PartnerService } from '../partner';

@Component({
    selector: 'jhi-ref-bareme',
    templateUrl: './ref-bareme.component.html'
})
export class RefBaremeComponent implements OnInit, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    refBaremes: RefBareme[] = [];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    selectedFiles: FileList;
    croquisUrl: any;
    croquisPreview = false;
    currentFileUpload: File;
    croquisAttachment: Attachment = new Attachment();
    labelCroquis: String = "Croquis";
    isSaving: boolean;
    refBareme = new RefBareme();
    imgRefBareme: String;
    private ngbModalRef: NgbModalRef;
    showBareme = false;

    dtOptions: any = {};
    dtTrigger: Subject<RefBareme> = new Subject();
    croquis = false;
    textPattern = Global.textPattern;
    private image: any;
    private readonly imageType: string = 'data:image/PNG;base64,';
    showBaremeAttachment: boolean = true;
    nomImage: string;
    extensionImage: string;

    constructor(
        private refBaremeService: RefBaremeService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService,
        private historyPopupService: HistoryPopupService,
        private partnerService: PartnerService,
    ) {

    }

    getImage(image64: string) {
        return this.sanitizer.bypassSecurityTrustUrl(this.imageType + image64);
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

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(41, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });

    }
    loadAll() {
        this.refBaremeService.query().subscribe((res) => {
            this.refBaremes = res.json;
            this.rerender();
        });
    }

    selectHistory(id) {
        console.log("premier logggggg" + id);
        this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id, "RefBareme");
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

    selectFile(event) {
        this.croquis = false;
        this.nomImage = 'noExtension';
        const indexOfFirst = (event.target.files[0].type).indexOf('/');
        this.extensionImage = ((event.target.files[0].type).substring((indexOfFirst + 1), ((event.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (event.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint != null) {
        } else {
            this.nomImage = ((event.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.nomImage = this.nomImage.concat('.', this.extensionImage);

        }
        this.refBareme.nomImage = this.nomImage;
        this.selectedFiles = event.target.files;
        const reader = new FileReader();
        if (event.target.files[0] != null && event.target.files[0] != undefined) {
            this.croquis = true;
        } else {
            this.croquis = false;
        }
        reader.readAsDataURL(event.target.files[0]); // read file as data url
        reader.onload = () => { // called once readAsDataURL is completed
            this.croquisPreview = true;
            this.croquisUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result.toString());
            console.log(this.croquisUrl + "!!!!!!!!!!");
        }
    }
    downloadFile() {
        if (this.selectedFiles && this.selectedFiles.length > 0) {
            this.currentFileUpload = this.selectedFiles.item(0);
        }
        saveAs(this.currentFileUpload);
    }
    edit(id: number) {
        this.refBaremeService.find(id).subscribe((refBareme) => {
            this.refBareme = refBareme;
            this.showBaremeAttachment = false;
            this.croquis = true;
            this.showBareme = true;
            /*if (this.refBareme.attachmentName === 'jpg') { this.refBareme.attachmentName = 'jpeg' }
            if (this.refBareme.attachmentName === 'PNG') { this.refBareme.attachmentName = 'png' }
            if (this.refBareme.attachment64 !== null && this.refBareme.attachment64 !== undefined) {
                const refBaremeBlob = this.dataURItoBlobAtt(this.refBareme.attachment64, this.refBareme.attachmentName);
                this.currentFileUpload = new File([refBaremeBlob], 'refBareme', { type: 'image/' + this.refBareme.attachmentName });
                this.croquisPreview = true;
                this.showBaremeAttachment = false;
                this.croquis = true;
            }*/
            //this.imgRefBareme = require('/home/local/GA/ridha.bouazizi/sources/auxilium-16-06-2019/images/refBareme/refBareme_1560783716400.png');
        });
    }
    goToTop() {
        this.router.navigate([], { fragment: 'destination' });
    }

    dataURItoBlobAtt(dataURI, typeImage) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: 'image/' + typeImage });
        return blob;
    }

    cancel() {
        this.refBareme = new RefBareme();
        this.imgRefBareme = null;
        this.croquisPreview = false;
        this.showBaremeAttachment = true;
        this.showBareme = false;
    }

    deleteBaremeFile() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.showBaremeAttachment = true;
                this.croquisPreview = false;
                this.currentFileUpload = null;
                this.croquis = false;
                this.showBareme = false;
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
    /**
  * Save current pricing element
  */
    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.refBareme.id !== undefined) {
                        // Update
                        if (
                            this.selectedFiles &&
                            this.selectedFiles.length > 0
                        ) {
                            this.currentFileUpload = this.selectedFiles.item(
                                0
                            );
                        }
                        this.subscribeToSaveResponse(
                            this.refBaremeService.createRef(this.currentFileUpload, this.refBareme)
                        );
                    } else {
                        // Create
                        if (
                            this.selectedFiles &&
                            this.selectedFiles.length > 0
                        ) {
                            this.currentFileUpload = this.selectedFiles.item(
                                0
                            );
                        }
                        this.subscribeToSaveResponse(
                            this.refBaremeService.createRef(this.currentFileUpload, this.refBareme)
                        );
                    }

                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    private subscribeToSaveResponse(result: Observable<RefBareme>) {
        result.subscribe(
            (res: RefBareme) => {
                this.onSaveSuccess(res);
                //this.saveAttachments(res.id);
            },
            (res: Response) => this.onSaveError(res)
        );
    }
    private onSaveSuccess(result: RefBareme) {
        this.isSaving = false;
        this.cancel();
        this.loadAll();
        this.refBareme = new RefBareme();
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

    /**
    * Save prestation pecs tiers
    * @param prestationPecId
    * @param Attachments
    */
    saveAttachments(refBaremeId: number) {
        if (this.croquisAttachment.id && this.currentFileUpload) {
            this.refBaremeService.updateAttachments(this.croquisAttachment.id, this.currentFileUpload, this.labelCroquis).subscribe((res: Attachment) => {
                this.croquisAttachment = res;
            });
        } else if (this.currentFileUpload && !this.croquisAttachment.id) {
            this.refBaremeService.saveAttachments(refBaremeId, this.currentFileUpload, this.labelCroquis).subscribe((res: Attachment) => {
                this.croquisAttachment = res;
            });
        }
    }

    trackId(index: number, item: RefBareme) {
        return item.id;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
