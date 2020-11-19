import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoueurPopupService } from './loueur-popup.service';
import { RefMotif, RefMotifService } from '../ref-motif';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, Principal, ConfirmationDialogService } from '../../shared';
import { RaisonAssistanceService } from '../raison-assistance';
import { Loueur } from './loueur.model';
import { LoueurService } from './loueur.service';
@Component({
    selector: 'jhi-loueur-bloque-dialog',
    templateUrl: './loueur-bloque-dialog.component.html'
})
export class LoueurBloqueDialogComponent {
    motifs: RefMotif[];
    loueur: Loueur = new Loueur();
    @Input() loueurId: number;
    SysAction: any;
    selectedMotifs: any;
    reclamationLoueur: string;
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
        private loueurService: LoueurService,
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
        console.log("***********");
        console.log(this.loueurId);

        this.loueurService.find(this.loueurId).subscribe((loueur) => {
            this.loueur = loueur;
            if (this.loueur.dateEffetConvention) {
                this.loueur.dateEffetConvention = {
                    year: this.loueur.dateEffetConvention.getFullYear(),
                    month: this.loueur.dateEffetConvention.getMonth() + 1,
                    day: this.loueur.dateEffetConvention.getDate()
                };
            }
            if (this.loueur.dateFinConvention) {
                this.loueur.dateFinConvention = {
                    year: this.loueur.dateFinConvention.getFullYear(),
                    month: this.loueur.dateFinConvention.getMonth() + 1,
                    day: this.loueur.dateFinConvention.getDate()
                };
            }

            if (this.loueur.blocage == false) { this.bloque = true }


        });


        this.raisonAssistanceService.findMotifsByOperation(3).subscribe((subRes: ResponseWrapper) => {
            this.motifs = subRes.json;
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

    confirmBlock(loueur: Loueur) {
        if (loueur.blocage == true) {
            loueur.blocage = false;
            loueur.motifId = null;
            this.subscribeToSaveResponse(this.loueurService.update(loueur));
        }
        else if (this.disabledMotif == true) {
            this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir bolqueur ce Remorqueur avec Motif ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        // this.refMotifService.find(this.idMotif).subscribe((res: RefMotif) => {
                        //     const motifss: number[] = [];
                        //     motifss.push(res.id);
                        //     if (this.loueur.blocage == true) {
                        //         this.loueurService.bloquerMotif(this.loueur.id, motifss).subscribe((res: Loueur) => { });
                        //     }
                        // });
                        loueur.reclamation = null;
                        loueur.blocage = true;
                        loueur.motifId = this.idMotif;
                        this.subscribeToSaveResponse(this.loueurService.update(loueur));
                        console.log('User confirmed Bloquer Loueur avec Motif:', this.loueur.id);
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
        }

        else if (this.disabledReclamation == true) {
            this.confirmationDialogService.confirm('Confirmation', 'Etes vous sûrs de vouloir bloquer avec reclamation ce Remorqueur ?', 'Oui', 'Non', 'lg')
                .then((confirmed) => {
                    console.log('User confirmed:', confirmed);
                    if (confirmed) {
                        loueur.blocage = true;
                        loueur.reclamation = this.reclamationLoueur;
                        this.subscribeToSaveResponse(this.loueurService.update(loueur));
                        console.log('User confirmed Bloquer avec reclamation  ce  Remorqueur:', this.loueur.id);
                    }
                })
                .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
        }

    }
    private subscribeToSaveResponse(result: Observable<Loueur>) {
        result.subscribe((res: Loueur) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: Loueur) {
        this.activeModal.close(true);
       // this.eventManager.broadcast({ name: 'refRemorqueurListModification', content: 'Bloque an refRemorqueur' });
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
    selector: 'jhi-loueur-bloque-popup',
    template: ''
})
export class LoueurBloquePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private loueurPopupService: LoueurPopupService
    ) { }

    ngOnInit() {

        this.routeSub = this.route.params.subscribe((params) => {
            this.loueurPopupService
                .open(LoueurBloqueDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
