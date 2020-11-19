import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefRemorqueur } from './ref-remorqueur.model';
import { RefRemorqueurPopupService } from './ref-remorqueur-popup.service';
import { RefRemorqueurService } from './ref-remorqueur.service';
import { RefMotif, RefMotifService } from '../ref-motif';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, Principal, ConfirmationDialogService } from '../../shared';
import { RaisonAssistanceService } from '../raison-assistance';
@Component({
    selector: 'jhi-ref-remorqueur-bloque-dialog',
    templateUrl: './ref-remorqueur-bloque-dialog.component.html'
})
export class RefRemorqueurBloqueDialogComponent {
    motifs: RefMotif[];
    refRemorqueur: RefRemorqueur;
    SysAction: any;
    selectedMotifs: any;
    reclamationRmq: string;
    value: any;
    bloque: boolean = false;
    disabledMotif: boolean = true;
    disabledReclamation: boolean = false;
    refMotif: RefMotif;
    checked: any[] = [];
    idMotif: number;
    motifss: number[];
    constructor(
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private refRemorqueurService: RefRemorqueurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
        private refMotifService: RefMotifService,
        private alertService: JhiAlertService,
        private confirmationDialogService: ConfirmationDialogService,
        private raisonAssistanceService: RaisonAssistanceService
    ) {
    }
    ngOnInit() {
        if (this.refRemorqueur.isBloque == false) { this.bloque = true }
       
       
        console.log("iciiiiiiiiiiii motifs");
        this.raisonAssistanceService.findMotifsByOperation(2).subscribe((subRes: ResponseWrapper) => {
            this.motifs =  subRes.json;
            console.log("iciiiiiiiiiiii motifs"+this.motifs.length);
        })
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    handleClickMotif() {
        this.disabledMotif = true;
        this.disabledReclamation = false;
    }
    handleClickReclamation() {
        this.disabledMotif = false;
        this.disabledReclamation = true;
    }

    confirmBlock(refRmq: RefRemorqueur) {
        if (refRmq.isBloque == true) {
            refRmq.isBloque = false;
            this.subscribeToSaveResponse(this.refRemorqueurService.update(refRmq));
        }
        else if (this.disabledMotif == true) {
            this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir bolqueur ce Remorqueur avec Motif ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        this.refMotifService.find(this.idMotif).subscribe((res: RefMotif) => {
                            const motifss: number[] = [];
                            motifss.push(res.id);
                            if (this.refRemorqueur.isBloque == true) {
                                this.refRemorqueurService.bloquerMotif(this.refRemorqueur.id, motifss).subscribe((res: RefRemorqueur) => { });
                            }
                        });
                        refRmq.reclamation = null;
                        refRmq.isBloque = true;
                        this.subscribeToSaveResponse(this.refRemorqueurService.update(refRmq));
                        console.log('User confirmed Bloquer Remorqueur avec Motif:', this.refRemorqueur.id);
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
        }

        else if (this.disabledReclamation == true) {
            this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir bloquer avec reclamation ce Remorqueur ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        refRmq.isBloque = true;
                        refRmq.reclamation = this.reclamationRmq;
                        this.subscribeToSaveResponse(this.refRemorqueurService.update(refRmq));
                        console.log('User confirmed Bloquer avec reclamation  ce  Remorqueur:', this.refRemorqueur.id);
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
        }

    }
    private subscribeToSaveResponse(result: Observable<RefRemorqueur>) {
        result.subscribe((res: RefRemorqueur) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: RefRemorqueur) {
        this.activeModal.close(result);
        this.eventManager.broadcast({ name: 'refRemorqueurListModification', content: 'Bloque an refRemorqueur' });
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

    trackRefMotifById(index: number, item: RefMotif) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ref-remorqueur-bloque-popup',
    template: ''
})
export class RefRemorqueurBloquePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refRemorqueurPopupService: RefRemorqueurPopupService
    ) { }

    ngOnInit() {

        this.routeSub = this.route.params.subscribe((params) => {
            this.refRemorqueurPopupService
                .open(RefRemorqueurBloqueDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
