import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SysActionUtilisateur } from './sys-action-utilisateur.model';
import { SysActionUtilisateurPopupService } from './sys-action-utilisateur-popup.service';
import { SysActionUtilisateurService } from './sys-action-utilisateur.service';
import { RefMotif, RefMotifService } from '../ref-motif';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sys-action-utilisateur-dialog',
    templateUrl: './sys-action-utilisateur-dialog.component.html'
})
export class SysActionUtilisateurDialogComponent implements OnInit {

    sysActionUtilisateur: SysActionUtilisateur;
    isSaving: boolean;
    refmotifs: RefMotif[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private refMotifService: RefMotifService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refMotifService.query()
            .subscribe((res: ResponseWrapper) => { this.refmotifs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sysActionUtilisateur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sysActionUtilisateurService.update(this.sysActionUtilisateur));
        } else {
            this.subscribeToSaveResponse(
                this.sysActionUtilisateurService.create(this.sysActionUtilisateur));
        }
    }

    private subscribeToSaveResponse(result: Observable<SysActionUtilisateur>) {
        result.subscribe((res: SysActionUtilisateur) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SysActionUtilisateur) {
        this.eventManager.broadcast({ name: 'sysActionUtilisateurListModification', content: 'OK'});
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

    trackRefMotifById(index: number, item: RefMotif) {
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
    selector: 'jhi-sys-action-utilisateur-popup',
    template: ''
})
export class SysActionUtilisateurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sysActionUtilisateurPopupService: SysActionUtilisateurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sysActionUtilisateurPopupService
                    .open(SysActionUtilisateurDialogComponent as Component, params['id']);
            } else {
                this.sysActionUtilisateurPopupService
                    .open(SysActionUtilisateurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
