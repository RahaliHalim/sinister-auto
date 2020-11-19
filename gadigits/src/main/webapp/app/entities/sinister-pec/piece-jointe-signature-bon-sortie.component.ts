import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { Attachment } from '../attachments';
import { Apec } from '../apec';
import { ConfirmationDialogService } from '../../shared';
import * as Stomp from 'stompjs';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { Reparateur, ReparateurService } from '../reparateur';
import { Authorities } from '../../constants/app.constants';
import { saveAs } from 'file-saver/FileSaver';
import { ViewSinisterPec } from '../view-sinister-pec';
@Component({
    selector: 'jhi-piece-jointe-dialog',
    templateUrl: './piece-jointe-signature-bon-sortie.component.html'
})
export class PieceJointeSignatureBonSortieComponent implements OnInit, OnDestroy {

    pieceJointe: Attachment = new Attachment();
    enquetePieceJointe: Attachment = new Attachment();
    isSaving: boolean;
    //new implementation
    currentFileUpload: File;
    enqueteFileUpload: File;
    label: string = 'BonSortie';
    note: string = 'Signature de Bon Sortie';
    labelEnquete: string = 'Enquete';
    noteEnquete: string = 'Signature de Bon Sortie';
    original: boolean = true;
    BonSortiePreview = false;
    enquetePreview = false;
    extensionImage: string;
    nomImage: string;

    @Input() sinisterPec: ViewSinisterPec;
    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private prestationService: SinisterPecService,
        private reparateurService: ReparateurService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,

    ) {
    }
    ngOnInit() {
        this.isSaving = false;
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }
    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.isSaving = true;
                    this.prestationService.saveAttachmentsBonSortie(this.sinisterPec.id, this.currentFileUpload, this.label, this.note, this.original).subscribe((res: Attachment) => {
                        this.pieceJointe = res;

                        this.prestationService.saveAttachmentsBonSortie(this.sinisterPec.id, this.enqueteFileUpload, this.labelEnquete, this.noteEnquete, this.original).subscribe((res: Attachment) => {
                            this.enquetePieceJointe = res;
                            //this.activeModal.dismiss('cancel');
                            this.activeModal.close(this.sinisterPec);

                        });

                    });

                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
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
    onBonSortieFileChange(fileInput: any) {
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.pieceJointe.name = "noExtension";
        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.pieceJointe.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.currentFileUpload = fileInput.target.files[0];
        this.BonSortiePreview = true;
        console.log(this.currentFileUpload);
    }

    downloadBonSortieFile() {
        if (this.currentFileUpload) {
            saveAs(this.currentFileUpload);
        }
    }

    onEnqueteFileChange(fileInput: any) {
        const indexOfFirst = (fileInput.target.files[0].type).indexOf('/');
        this.extensionImage = ((fileInput.target.files[0].type).substring((indexOfFirst + 1), ((fileInput.target.files[0].type).length)));
        console.log("kkk211 " + this.extensionImage);
        const indexOfFirstPoint = (fileInput.target.files[0].name).indexOf('.');
        if (indexOfFirstPoint !== -1) {
            this.enquetePieceJointe.name = "noExtension";
        } else {
            this.nomImage = ((fileInput.target.files[0].name).substring(0, (indexOfFirstPoint)));
            console.log(this.nomImage.concat('.', this.extensionImage));
            this.enquetePieceJointe.name = this.nomImage.concat('.', this.extensionImage);

        }
        this.enqueteFileUpload = fileInput.target.files[0];
        this.enquetePreview = true;
        console.log(this.enqueteFileUpload);
    }

    downloadEnqueteFile() {
        if (this.enqueteFileUpload) {
            saveAs(this.enqueteFileUpload);
        }
    }

    ngOnDestroy() {
    }
}