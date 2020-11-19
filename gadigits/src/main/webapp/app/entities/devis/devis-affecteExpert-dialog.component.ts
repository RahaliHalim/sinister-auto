import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { DevisPopupService } from './devis-popup.service';
import { Devis} from './devis.model';
import { DevisService } from './devis.service';
import { ResponseWrapper } from '../../shared';
import { Expert, ExpertService } from '../expert';
import { SysVilleService, SysVille } from '../sys-ville';
import {PersonneMoraleService} from '../personne-morale';
import {PersonnePhysiqueService} from '../personne-physique';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';

@Component({
    selector: 'jhi-devis-affecte-expert-dialog',
    templateUrl: './devis-affecteExpert-dialog.component.html'
})
export class DevisAffecteExpertDialogComponent implements OnInit {

    devis: Devis;
    selectedRow: Number;
    experts: Expert[];
    selectedVille: any;
    sysvilles: any;
    prestation: any;
    dossier: any;
    contratAssurance: any;
    pec: any;
    assure: any;
    physique: any;
    morale: any;
    constructor(
        private devisService: DevisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private expertService: ExpertService,
        private contratAssuranceService: ContratAssuranceService,
        private assureService: AssureService,
        private personnePhysiqueService: PersonnePhysiqueService,
        private personneMoraleService: PersonneMoraleService,
        private sysVilleService: SysVilleService,
        private alertService: JhiAlertService,
        private router: Router
    ) {
    }
    ngOnInit() {
        this.sysVilleService.findAllWithoutPagination()
        .subscribe((res: ResponseWrapper) => { this.sysvilles = res.json });
        this.init();
    }
    init() {
        
    }
    listExpertsByVille(id: number) {
         this.expertService.queryByVille(id).subscribe((resExpertVille: ResponseWrapper) => {this.experts = resExpertVille.json; })
    }
    setClickedRow(index) {
            this.selectedRow = index;
        }
    affecteExpert(id: number) {
        this.router.navigate(['../devis/' + this.devis.prestationId + '-' + id + '/edit/' + this.devis.id]);
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
    trackVilleById(index: number, item: SysVille) {
            return item.id;
        }
    }
@Component({
    selector: 'jhi-devis-affecte-expert-popup',
    template: ''
})
export class DevisAffecteExpertPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devisPopupService: DevisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.devisPopupService
                .open(DevisAffecteExpertDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
