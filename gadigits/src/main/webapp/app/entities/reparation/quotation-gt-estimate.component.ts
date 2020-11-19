import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { Response } from '@angular/http';
import { GaDatatable } from '../../constants/app.constants';
import { Observable, Subject } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { Principal, ResponseWrapper } from '../../shared';
import { RefBareme } from '../ref-bareme';

@Component({
    selector: 'jhi-quotation-gt-estimate',
    templateUrl: './quotation-gt-estimate.component.html'
})
export class QuotationGtEstimateComponent implements OnInit {

    refBaremes: RefBareme[] = [];
    dtOptions: any = {};
    dtTrigger: Subject<any> = new Subject();
    constructor(
        public activeModal: NgbActiveModal,
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
    }


    clear() {
        this.activeModal.dismiss('cancel');
    }

    onError(error: any): any {
        throw new Error("Method not implemented.");
    }
}