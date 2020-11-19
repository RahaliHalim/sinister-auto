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
import { saveAs } from 'file-saver/FileSaver';
import { PermissionAccess, UserExtraService } from '../user-extra';
import { ViewSinisterPec } from '../view-sinister-pec';
declare var Afficher: { documentOnLoad: Function, documentOnReady: Function };
declare var Chart: any;

@Component({
    selector: 'jhi-generation-rapport-prestation',
    templateUrl: './reportIDAOuverture.component.html',
})

export class GenerationRapportComponent implements OnInit, OnDestroy {

    sinister: Sinister;
    sinisterPecsApprove: ViewSinisterPec[];
    sinisterPecsIdaOuverture: ViewSinisterPec[] = [];
    constatFiles: File;
    currentAccount: any;
    immatriculationTier: any;
    sinisterPec: SinisterPec = new SinisterPec();
    eventSubscriber: Subscription;
    extensionImage: string;
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private sinisterPecService: SinisterPecService,
        private userExtraService: UserExtraService
    ) { }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            this.userExtraService.findFunctionnalityEntityByUser(85, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInSinisterPecs();
    }

    loadAll() {

        this.sinisterPecService.findAllPrestationPECForIdaOuverture(this.currentAccount.id).subscribe((res) => {
            this.sinisterPecsIdaOuverture = res.json;
            this.dtTrigger.next();
        });
    }

    trackId(index: number, item: SinisterPec) {
        return item.id;
    }

    printlettreIDAAndOuverture(sinPec: ViewSinisterPec) {

        this.sinisterPecService.findPrestationPec(sinPec.id).subscribe((pecRes: SinisterPec) => {
            this.sinisterPec = pecRes;

            if (this.sinisterPec.id != null && this.sinisterPec.id !== undefined) {
                this.getPieceNew('prestationPEC', this.sinisterPec.id, 'Constat');
            }

            switch (this.sinisterPec.modeId) {
                case 1:
                    this.printlettreIDA(this.sinisterPec);
                    this.printlettreReouverture(this.sinisterPec);
                    this.sinisterPec.generatedLetter = true;
                    break;
                case 2:
                    this.printlettreIDA(this.sinisterPec);
                    this.printlettreReouverture(this.sinisterPec);
                    this.sinisterPec.generatedLetter = true;
                    break;
                case 3:
                    this.printlettreReouverture(this.sinisterPec);
                    this.sinisterPec.generatedLetter = true;
                    break;
                case 4:
                    this.printlettreReouverture(this.sinisterPec);
                    this.sinisterPec.generatedLetter = true;
                    break;
                default:
                    if (this.sinisterPec.labelPosGa == "Recours") {
                        this.printlettreIDA(this.sinisterPec);
                        this.printlettreReouverture(this.sinisterPec);
                        this.sinisterPec.generatedLetter = true;
                    }
                    break;
            }
            this.sinisterPecService.update(this.sinisterPec).subscribe((resSinister) => {
            });
            this.sinisterPecsIdaOuverture.forEach((item, index) => {
                if (item === sinPec) this.sinisterPecsIdaOuverture.splice(index, 1);
            });
        });

    }

    printlettreIDA(sinisterPec: SinisterPec) {
        this.sinisterPecService.generatelettreIDA(sinisterPec).subscribe((res) => {
            window.open(res.headers.get('pdfname'), '_blank');
        });
    }

    printlettreReouverture(sinisterPec: SinisterPec) {
        this.sinisterPecService.generatelettreReouverture(sinisterPec).subscribe((res) => {
            window.open(res.headers.get('pdfname'), '_blank');
        })
    }

    getPieceNew(entityName: string, sinisterPecId: number, label: string) {
        this.sinisterPecService.getPieceBySinPecAndLabel(entityName, sinisterPecId, label).subscribe((blob: Blob) => {
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
   

    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

}
