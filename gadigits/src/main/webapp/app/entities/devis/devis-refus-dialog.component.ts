import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Devis, EtatDevis } from './devis.model';
import { DevisPopupService } from './devis-popup.service';
import { DevisService } from './devis.service';
import { ResponseWrapper, Principal } from '../../shared';
import { RefMotif, RefMotifService } from '../ref-motif';
import {SysActionUtilisateur, SysActionUtilisateurService} from '../sys-action-utilisateur';
import {Observation, ObservationService, TypeObservation} from '../observation';

@Component({
    selector: 'jhi-devis-refus-dialog',
    templateUrl: './devis-refus-dialog.component.html'
})
export class DevisRefusDialogComponent implements OnInit {

    devis: Devis;
    motifs: RefMotif[];
    SysAction: any;
    selectedMotifs: any;
    value: any;
    checked: any[] = [];
    observation: Observation = new Observation();
    pec: any;
    decision: any;
    motif: any;
    listMotif: any;
    currentAccount: any;
    listLibelle: String[] = [];
    authorities: string[] = ['ROLE_GESTIONNAIRE', 'ROLE_CCELLULE', 'ROLE_RESPONSABLE', 'ROLE_RAPPORTEUR'];
    result: boolean;

    constructor(
        private devisService: DevisService,
        private observationService: ObservationService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private refMotifService: RefMotifService,
        private alertService: JhiAlertService,
        private router: Router,
        private principal: Principal
    ) {
    }
    ngOnInit() {
         this.sysActionUtilisateurService.find(97551)
            .subscribe((subRes: SysActionUtilisateur) => {this.SysAction = subRes;
                this.motifs = this.SysAction.motifs});
                
    }
    /**
     * Rectif by gestech
     */
    refuserDevis() {
    }
    updateChecked(option, event) {
    const index = this.checked.indexOf(option);
    if (event.target.checked) {
      if (index === -1) {
        this.checked.push(option);
      }
    } else {
      if (index !== -1) {
        this.checked.splice(index, 1);
      }
    }
  }

    save() {
        if (this.devis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.devisService.update(this.devis));
        }
    }

    private subscribeToSaveResponseObservation(result: Observable<Observation>) {
        result.subscribe((res: Observation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private subscribeToSaveResponse(result: Observable<Devis>) {
        result.subscribe((res: Devis) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: Devis) {
        this.eventManager.broadcast({ name: 'serviceRmqListModification', content: 'OK'});
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    clear() {
        this.activeModal.dismiss('cancel');
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
    trackRefMotifById(index: number, item: RefMotif) {
        return item.id;
    }
}
@Component({
    selector: 'jhi-dossier-rmq-motif-popup',
    template: ''
})
export class DevisRefusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devisPopupService: DevisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.devisPopupService
                .open(DevisRefusDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
