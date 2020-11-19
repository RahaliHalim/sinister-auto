import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { Upload } from './upload.model';
import { Referentiel } from './referentiel.model';
import { UploadService } from './upload.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { DataTableDirective } from 'angular-datatables';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { GaDatatable } from './../../constants/app.constants';
import { DomSanitizer } from '@angular/platform-browser';
import { saveAs } from 'file-saver/FileSaver';

@Component({
    selector: 'jhi-upload',
    templateUrl: './upload.component.html'
})
export class UploadComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    uploads: Upload[];
    referentiels: Referentiel[];
    upload: Upload = new Upload();
    uploadCheck: Upload;
    selectedFiles: FileList;
    isSaving: boolean;
    currentAccount: any;
    dtOptions: any = {};
    dtTrigger: Subject<Upload> = new Subject();
    existLabel = true;
    currentFileUpload: File;
    croquisUrl: any;
    croquisPreview = false;
    labelRef: any;
    croquis = false;

    constructor(
        private uploadService: UploadService,
        private alertService: JhiAlertService,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private sanitizer: DomSanitizer,
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
        this.uploadService.queryUpload().subscribe(
            (res: ResponseWrapper) => {
                this.uploads = res.json;
                this.uploads.forEach(element => {
                    console.log("--------------------");
                    console.log(element.originalName);
                });
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    selectFile(event) {
        this.croquis = false;
        this.selectedFiles = event.target.files;
        console.log(event.target.files[0]);
        const reader = new FileReader();
        if (event.target.files[0] != null && event.target.files[0] != undefined) {
            this.croquis = true;
        } else {
            this.croquis = false;
        }
        this.labelRef = event.target.files[0].name;
        reader.readAsDataURL(event.target.files[0]); // read file as data url
        reader.onload = () => { // called once readAsDataURL is completed
            this.croquisPreview = true;
            this.croquisUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result.toString());
            //console.log(this.croquisUrl + "!!!!!!!!!!");
        }
    }
    downloadFile() {
        if (this.selectedFiles && this.selectedFiles.length > 0) {
            this.currentFileUpload = this.selectedFiles.item(0);
        }
        saveAs(this.currentFileUpload);
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Voulez-vous enregistrer ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.isSaving = true;
                    if (this.upload.id !== undefined) {
                        // Update
                        if (
                            this.selectedFiles &&
                            this.selectedFiles.length > 0
                        ) {
                            this.currentFileUpload = this.selectedFiles.item(
                                0
                            );
                        }
                        console.log("update ref******");
                        this.subscribeToSaveResponse(
                            this.uploadService.createRef(this.currentFileUpload, this.upload)
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
                            this.uploadService.createRef(this.currentFileUpload, this.upload)
                        );
                    }

                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    edit(id: number) {
        this.uploadService.find(id).subscribe(upload => {
            this.upload = upload;
        });
    }

    cancel() {
        this.upload = new Upload();
        this.upload.referentielId = undefined;
        this.croquisPreview = false;
        this.selectedFiles = undefined;
        this.labelRef = undefined;
    }

    getPiece(up:Upload) {
        this.uploadService.getUploadXls(up.id).subscribe((blob: Blob) => {
            let fileName = "referentiel" + ".xlsx";
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

    dataURItoBlobAtt(dataURI, extention, type) {
        const byteString = window.atob(dataURI);
        const arrayBuffer = new ArrayBuffer(byteString.length);
        const int8Array = new Uint8Array(arrayBuffer);
        for (let i = 0; i < byteString.length; i++) {
            int8Array[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([int8Array], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
        return blob;
    }


    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.uploadService.queryReferentiel().subscribe(
            (res: ResponseWrapper) => {
                this.referentiels = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
    }

    trackId(index: number, item: Upload) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<Upload>) {
        result.subscribe((res: Upload) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: Upload) {
        this.isSaving = false;
        this.cancel();
        this.loadAll();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.isSaving = false;
        this.alertService.error(error.message, null, null);
    }
}