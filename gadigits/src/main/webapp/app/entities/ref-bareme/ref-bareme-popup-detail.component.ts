import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { Response } from '@angular/http';
import { GaDatatable } from '../../constants/app.constants';
import { Observable, Subject } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { RefBareme } from './ref-bareme.model';
import { RefBaremePopupDetailService } from './ref-bareme-popup-detail.service';
import { RefBaremeService } from './ref-bareme.service';
import { Principal, ResponseWrapper } from '../../shared';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'jhi-ref-bareme-popup-detail',
    templateUrl: './ref-bareme-popup-detail.component.html'
})
export class RefBaremePopupDetailComponent implements OnInit {

    refBareme1: RefBareme;
    refBaremes: RefBareme[];
    isSaving: boolean;
    refB: RefBareme;
    idBareme: any;
    codeBareme: any;
    descriptionBareme: any;
    selectedRow: Number;
    refBid: number;
    idd: number;
    imgBareme: String;
    //@Output() toEmit= new EventEmitter<number>();
    predicate: any;
    reverse: any;
    selectedItem: boolean = true;
    dtOptions: any = {};
    dtTrigger: Subject<RefBareme> = new Subject();
    private readonly imageType: string = 'data:image/PNG;base64,';
    private readonly imageTypeJpeg: string = 'data:image/jpeg;base64,';
    constructor(
        public activeModal: NgbActiveModal,
        private refBaremePopupDetailService: RefBaremePopupDetailService,
        private router: Router,
        private sanitizer: DomSanitizer,
        private refBaremeService: RefBaremeService
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        this.refBaremeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.refBaremes = res.json;
              
                this.dtTrigger.next(); // Actualize datatables
            });
    }

    getImage(refB: RefBareme) {
        if(refB.attachmentName == 'png' || refB.attachmentName == 'PNG'){
            return this.sanitizer.bypassSecurityTrustUrl(this.imageType + refB.attachment64);
        } else {
            return this.sanitizer.bypassSecurityTrustUrl(this.imageTypeJpeg + refB.attachment64);
        }
    }
   

    clear() {
        this.activeModal.dismiss('cancel');
    }

    setClickedRow(index: number, refbid: number) {
        
        
        this.selectedRow = index;
        this.refBid = refbid
        this.selectedItem = false;
        /*
        this.refBaremePopupDetailService.changeMessage(refbid);

        this.refBaremePopupDetailService.currentmessage.subscribe((idresu) => {
            this.idd = idresu;
        });
        this.selectedRow = index;
        this.refBid = refbid;
        this.selectedItem = false;*/
        //console.log("setClickedRow" + this.refBid);
    }

    afficherBareme() {
        this.refBaremePopupDetailService.changeMessage(this.refBid);

        this.refBaremePopupDetailService.currentmessage.subscribe((idresu) => {
            this.idd = idresu;
        });
        this.selectedItem = false;
        this.activeModal.dismiss('cancel');
    }

    onError(error: any): any {
        throw new Error("Method not implemented.");
    }
}

@Component({
    selector: 'jhi-ref-bareme-pop',
    template: ''
})
export class RefBaremePopComponent implements OnInit, OnDestroy {
    routeSub: any;
    refBaremes: RefBareme[];
    dtOptions: any = {};
    dtTrigger: Subject<RefBareme> = new Subject();
    private readonly imageType: string = 'data:image/PNG;base64,';
    constructor(
        private refBaremePopupDetailService: RefBaremePopupDetailService,
        private refBaremeService: RefBaremeService,
        public principal: Principal,
        private route: ActivatedRoute,
        private sanitizer: DomSanitizer
    ) { }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.routeSub = this.route.params.subscribe((params) => {
            this.refBaremePopupDetailService
                .open(RefBaremePopupDetailComponent as Component);
        });
        //this.loadAll();
    }
    loadAll() {
        this.refBaremeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.refBaremes = res.json;
                this.dtTrigger.next(); // Actualize datatables
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    onError(json: any): void {
        throw new Error("Method not implemented.");
    }

    getImage(image64: string) {
        return this.sanitizer.bypassSecurityTrustUrl(this.imageType + image64);
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
    trackId(index: number, item: RefBareme) {
        return item.id;
    }
}



