import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefZoneGeo } from './ref-zone-geo.model';
import { RefZoneGeoPopupService } from './ref-zone-geo-popup.service';
import { RefZoneGeoService } from './ref-zone-geo.service';
import { SysGouvernorat, SysGouvernoratService } from '../sys-gouvernorat';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ref-zone-geo-dialog',
    templateUrl: './ref-zone-geo-dialog.component.html'
})
export class RefZoneGeoDialogComponent implements OnInit {

    refZoneGeo: RefZoneGeo;
    isSaving: boolean;

    sysgouvernorats: SysGouvernorat[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refZoneGeoService: RefZoneGeoService,
        private sysGouvernoratService: SysGouvernoratService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sysGouvernoratService.queryNf()
            .subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.refZoneGeo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refZoneGeoService.update(this.refZoneGeo));
        } else {
            this.subscribeToSaveResponse(
                this.refZoneGeoService.create(this.refZoneGeo));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefZoneGeo>) {
        result.subscribe((res: RefZoneGeo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefZoneGeo) {
        this.eventManager.broadcast({ name: 'refZoneGeoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
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

    trackSysGouvernoratById(index: number, item: SysGouvernorat) {
        return item.id;
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
    selector: 'jhi-ref-zone-geo-popup',
    template: ''
})
export class RefZoneGeoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refZoneGeoPopupService: RefZoneGeoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refZoneGeoPopupService
                    .open(RefZoneGeoDialogComponent as Component, params['id']);
            } else {
                this.refZoneGeoPopupService
                    .open(RefZoneGeoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
