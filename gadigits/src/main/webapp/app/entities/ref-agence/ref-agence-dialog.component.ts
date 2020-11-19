import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RefAgence } from './ref-agence.model';
import { RefAgencePopupService } from './ref-agence-popup.service';
import { RefAgenceService } from './ref-agence.service';
import { RefCompagnie, RefCompagnieService } from '../ref-compagnie';
import { SysVille, SysVilleService } from '../sys-ville';
import { ResponseWrapper } from '../../shared';
import { SysGouvernorat } from '../sys-gouvernorat/sys-gouvernorat.model';
import { SysGouvernoratService } from '../sys-gouvernorat/sys-gouvernorat.service';

@Component({
    selector: 'jhi-ref-agence-dialog',
    templateUrl: './ref-agence-dialog.component.html'
})
export class RefAgenceDialogComponent implements OnInit {
    sysGouvernorat: SysGouvernorat;
    sysgouvernorats: SysGouvernorat[];
    refAgence: RefAgence = new RefAgence();
    isSaving: boolean;

    refcompagnies: RefCompagnie[];
    sysVille: SysVille;
    sysvilles: SysVille[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refAgenceService: RefAgenceService,
        private refCompagnieService: RefCompagnieService,
        private sysGouvernoratService: SysGouvernoratService,
        private sysVilleService: SysVilleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refCompagnieService.getUnblockedCompany()
            .subscribe((res: ResponseWrapper) => { this.refcompagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            this.sysVilleService.findAllWithoutPagination()
            .subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query()
            .subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
            if ( this.refAgence.gouvernorat == null) {
                this.refAgence.gouvernorat = new SysGouvernorat()
            }
            if ( this.refAgence.villeId != null ) {
                this.findGouvernoratOfVille(this.refAgence.villeId)
            }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.refAgence.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refAgenceService.update(this.refAgence));
        } else {
            this.subscribeToSaveResponse(
                this.refAgenceService.create(this.refAgence));
        }
    }

    private subscribeToSaveResponse(result: Observable<RefAgence>) {
        result.subscribe((res: RefAgence) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefAgence) {
        this.eventManager.broadcast({ name: 'refAgenceListModification', content: 'OK'});
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

    trackRefCompagnieById(index: number, item: RefCompagnie) {
        return item.id;
    }

    trackSysVilleById(index: number, item: SysVille) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: SysGouvernorat) {
        return item.id;
    }
    listVillesByGouvernoratLieu(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: SysGouvernorat) => {
          this.sysGouvernorat = subRes;
            this.sysVilleService.findByGouvernorat(this.sysGouvernorat.id).subscribe((subRes1: SysVille[]) => {
            this.sysvilles = subRes1;
         });
 });
    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: SysVille) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.gouvernoratId).subscribe((subRes: SysGouvernorat) => {
            this.refAgence.gouvernorat = subRes
            })
        }
    )
    }
}

@Component({
    selector: 'jhi-ref-agence-popup',
    template: ''
})
export class RefAgencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refAgencePopupService: RefAgencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.refAgencePopupService
                    .open(RefAgenceDialogComponent as Component, params['id']);
            } else {
                this.refAgencePopupService
                    .open(RefAgenceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
