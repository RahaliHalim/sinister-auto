import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ElementMenu } from './element-menu.model';
import { ElementMenuPopupService } from './element-menu-popup.service';
import { ElementMenuService } from './element-menu.service';

@Component({
    selector: 'jhi-element-menu-dialog',
    templateUrl: './element-menu-dialog.component.html'
})
export class ElementMenuDialogComponent implements OnInit {

    elementMenu: ElementMenu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private elementMenuService: ElementMenuService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.elementMenu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.elementMenuService.update(this.elementMenu));
        } else {
            this.subscribeToSaveResponse(
                this.elementMenuService.create(this.elementMenu));
        }
    }

    private subscribeToSaveResponse(result: Observable<ElementMenu>) {
        result.subscribe((res: ElementMenu) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ElementMenu) {
        this.eventManager.broadcast({ name: 'elementMenuListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-element-menu-popup',
    template: ''
})
export class ElementMenuPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elementMenuPopupService: ElementMenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.elementMenuPopupService
                    .open(ElementMenuDialogComponent as Component, params['id']);
            } else {
                this.elementMenuPopupService
                    .open(ElementMenuDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
