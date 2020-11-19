import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RefMotif } from './ref-motif.model';
import { RefMotifPopupService } from './ref-motif-popup.service';
import { RefMotifService } from './ref-motif.service';
import { Journal, JournalService } from '../journal';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { ResponseWrapper, JhiLanguageHelper, User, UserService } from '../../shared';
@Component({
    selector: 'jhi-ref-motif-dialog',
    templateUrl: './ref-motif-dialog.component.html'
})
export class RefMotifDialogComponent implements OnInit {

    refMotif: RefMotif;
    isSaving: boolean;
    journals: Journal[];
    sysactionutilisateurs: SysActionUtilisateur[];
    authorities: any[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refMotifService: RefMotifService,
        private userService: UserService,
        private journalService: JournalService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.authorities().subscribe((authorities) => {
            this.authorities = authorities;
        });
        this.sysActionUtilisateurService.query()
            .subscribe((res: ResponseWrapper) => { this.sysactionutilisateurs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.journalService.query()
            .subscribe((res: ResponseWrapper) => { this.journals = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.refMotif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refMotifService.update(this.refMotif));
        } else {
            this.subscribeToSaveResponse(
                this.refMotifService.create(this.refMotif));
        }
    }
   
    private subscribeToSaveResponse(result: Observable<RefMotif>) {
        result.subscribe((res: RefMotif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefMotif) {
        this.eventManager.broadcast({ name: 'refMotifListModification', content: 'OK'});
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

    trackJournalById(index: number, item: Journal) {
        return item.id;
    }

    trackSysActionUtilisateurById(index: number, item: SysActionUtilisateur) {
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
    selector: 'jhi-ref-motif-popup',
    template: ''
})
export class RefMotifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refMotifPopupService: RefMotifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refMotifPopupService
                    .open(RefMotifDialogComponent as Component, params['id']);
            } else {
                this.refMotifPopupService
                    .open(RefMotifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
