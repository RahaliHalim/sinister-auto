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
import { Tiers, TiersService } from '../tiers';
import { GaDatatable } from '../../constants/app.constants';
import { Sinister } from '../sinister/sinister.model';
import { forEach } from '@angular/router/src/utils/collection';
import { DataTableDirective } from 'angular-datatables';
import { DomSanitizer } from '@angular/platform-browser';
import { SinisterPecPopupService } from './sinister-pec-popup.service';
import { PieceJointeSignatureBonSortieComponent } from './piece-jointe-signature-bon-sortie.component';
import { PermissionAccess, UserExtraService } from '../user-extra';
import { saveAs } from 'file-saver/FileSaver';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
declare var Afficher: { documentOnLoad: Function, documentOnReady: Function };
declare var Chart: any;

@Component({
    selector: 'jhi-signature-bon-sortie',
    templateUrl: './signature-bon-sortie.html'
})

export class SignatureBonSortieComponent implements OnInit, OnDestroy {

    private ngbModalRef: NgbModalRef;
    sinister: Sinister;
    sinisterPec: SinisterPec;
    sinisterPecsApprove: SinisterPec[];
    sinisterPecsBonSortie: ViewSinisterPec[] = [];
    currentAccount: any;
    immatriculationTier: any;
    extensionImage: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    constructor(
        private tiersService: TiersService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private sinisterPecService: SinisterPecService,
        private sanitizer: DomSanitizer,
        private sinisterPecPopupService: SinisterPecPopupService,
        private userExtraService: UserExtraService,
        private viewSinisterPecService: ViewSinisterPecService
    ) { }

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
            this.viewSinisterPecService.findAllSinPecForSignatureBonSortie(this.currentAccount.id).subscribe((res) => {
                this.sinisterPecsBonSortie = res.json;
                this.dtTrigger.next();
            });

        });
    }

    getPieceNewIOOB(entityName: string, sinisterPecId: number, label: string) {
        this.sinisterPecService.getPieceBySinPecAndLabelIOOB(entityName, sinisterPecId, label).subscribe((blob: Blob) => {
            const indexOfFirst = (blob.type).indexOf('/');
            this.extensionImage = ((blob.type).substring((indexOfFirst + 1), ((blob.type).length)));
            this.extensionImage = this.extensionImage.toLowerCase();
            let fileName = label + "." + this.extensionImage;
            console.log(fileName);
            if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                window.navigator.msSaveOrOpenBlob(blob, fileName);
                console.log("if--------------------");
            } else {
                console.log("else--------------------");
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


    getBonSortiePdf(sinisterPec: ViewSinisterPec) {
        if (sinisterPec.id == null || sinisterPec.id === undefined) {
            console.log("Erreur lors de la génération");

        } else { // OK
            this.sinisterPecService.getBonSortiePdf(sinisterPec.id).subscribe((blob: Blob) => {
                let fileName = "BonSortie" + ".pdf";
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
    }

    attacher(pec: ViewSinisterPec) {
        this.ngbModalRef = this.sinisterPecPopupService.openBonSortieModal(PieceJointeSignatureBonSortieComponent as Component, pec);
        this.ngbModalRef.result.then((result: ViewSinisterPec) => {
            if (result !== null && result !== undefined) {
                pec.step = 40;
                this.sinisterPecService.updatePec(result.id).subscribe((pecRes) => {
                });
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });

    }

    trackId(index: number, item: SinisterPec) {
        return item.id;
    }

    printBonSortie(sinPec: SinisterPec) {
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