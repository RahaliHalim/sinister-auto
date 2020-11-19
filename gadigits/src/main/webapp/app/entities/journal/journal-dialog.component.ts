import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Journal } from './journal.model';
import { JournalPopupService } from './journal-popup.service';
import { JournalService } from './journal.service';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { RefMotif, RefMotifService } from '../ref-motif';
import { ResponseWrapper } from '../../shared';
import { SinisterPec } from '../sinister-pec';

@Component({
    selector: 'jhi-journal-dialog',
    templateUrl: './journal-dialog.component.html'
})
export class JournalDialogComponent implements OnInit {

    journal: Journal;
    isSaving: boolean;

    sysactionutilisateurs: SysActionUtilisateur[];


    refmotifs: RefMotif[];
    prestations: any[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private journalService: JournalService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private refMotifService: RefMotifService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sysActionUtilisateurService.query()
            .subscribe((res: ResponseWrapper) => { this.sysactionutilisateurs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refMotifService.query()
            .subscribe((res: ResponseWrapper) => { this.refmotifs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.journal.id !== undefined) {
            this.subscribeToSaveResponse(
                this.journalService.update(this.journal));
        } else {
            this.subscribeToSaveResponse(
                this.journalService.create(this.journal));
        }
    }

    private subscribeToSaveResponse(result: Observable<Journal>) {
        result.subscribe((res: Journal) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Journal) {
        this.eventManager.broadcast({ name: 'journalListModification', content: 'OK'});
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

    trackSysActionUtilisateurById(index: number, item: SysActionUtilisateur) {
        return item.id;
    }

    trackPrestationById(index: number, item: SinisterPec) {
        return item.id;
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
    selector: 'jhi-journal-popup',
    template: ''
})
export class JournalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private journalPopupService: JournalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.journalPopupService
                    .open(JournalDialogComponent as Component, params['id']);
            } else {
                this.journalPopupService
                    .open(JournalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
