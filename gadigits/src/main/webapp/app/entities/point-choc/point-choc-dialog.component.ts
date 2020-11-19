import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PointChoc } from './point-choc.model';
import { PointChocPopupService } from './point-choc-popup.service';
import { PointChocService } from './point-choc.service';

@Component({
    selector: 'jhi-point-choc-dialog',
    templateUrl: './point-choc-dialog.component.html'
})
export class PointChocDialogComponent implements OnInit {

    @Input()  pointChoc: PointChoc = new PointChoc();
    isSaving: boolean;

    constructor(
        private alertService: JhiAlertService,
        private pointChocService: PointChocService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }
    save() {
        this.isSaving = true;
        if (this.pointChoc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pointChocService.update(this.pointChoc));
        } else {
            this.subscribeToSaveResponse(
                this.pointChocService.create(this.pointChoc));
        }
    }

    private subscribeToSaveResponse(result: Observable<PointChoc>) {
        result.subscribe((res: PointChoc) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PointChoc) {
        this.eventManager.broadcast({ name: 'pointChocListModification', content: 'OK'});
        this.isSaving = false;
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
}

@Component({
    selector: 'jhi-point-choc-popup',
    template: ''
})
export class PointChocPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pointChocPopupService: PointChocPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pointChocPopupService
                    .open(PointChocDialogComponent as Component, params['id']);
            } else {
                this.pointChocPopupService
                    .open(PointChocDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
