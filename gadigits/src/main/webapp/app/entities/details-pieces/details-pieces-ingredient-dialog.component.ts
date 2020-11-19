import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetailsPieces } from './details-pieces.model';
import { DetailsPiecesPopupService } from './details-pieces-popup.service';
import { DetailsPiecesService } from './details-pieces.service';
import { Fournisseur, FournisseurService } from '../fournisseur';
import { Devis, DevisService } from '../devis';
import { Piece, PieceService } from '../piece';
import { UserCellule, UserCelluleService } from '../user-cellule';
import { ResponseWrapper, Principal } from '../../shared';
import { FormBuilder, Validators, FormGroup, FormControl} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
    selector: 'jhi-details-pieces-ingredient-dialog',
    templateUrl: './details-pieces-ingredient-dialog.component.html'
})
export class DetailsPiecesIngredientDialogComponent implements OnInit {
    @Input() detailsPiecesIngredient: DetailsPieces[] = [];
    isSaving: boolean;

    fournisseurs: Fournisseur[];

    devis: Devis[];
    devisForPage: any;

    pieces: Piece[];
    detailsPiece: DetailsPieces = new DetailsPieces();
    currentAccount: any;
    authorities: any;
    reparateur: any;
    isGestionnaireTechnique: any;
    expert: any;
    userCellule: any;
    etat: any;
    controlIndex: number;
    iRow: number;
    requisitionForm: FormGroup;

    /** Pour la pagination */
    pin: number = 1;
    
    constructor(
        private alertService: JhiAlertService,
        private detailsPiecesService: DetailsPiecesService,
        private fournisseurService: FournisseurService,
        private devisService: DevisService,
        private pieceService: PieceService,
        private userCelluleService: UserCelluleService,
        private principal: Principal,
        public builder: FormBuilder,
        private route: ActivatedRoute,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.iRow = 0;
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.devisService.find(params['id']).subscribe((res) => {this.devisForPage = res;
                this.etat = this.devisForPage.etatDevis.toString();
                })
            }})
        this.isSaving = false;
        this.navigationDevis();
        this.fournisseurService.query()
            .subscribe((res: ResponseWrapper) => { this.fournisseurs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.devisService.query()
            .subscribe((res: ResponseWrapper) => { this.devis = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.pieceService.getPiecesByType(2)
            .subscribe((res) => { this.pieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    rowValidateForm(i: number, scenario?: string) {
        let action = 'addControl';
        if (scenario === 'remove') {
            action = 'removeControl';
        }
        this.requisitionForm[action]('item_designation_' + i, new FormControl('', []));
        this.requisitionForm[action]('item_prixUnit_' + i, new FormControl('', []));
        this.requisitionForm[action]('item_quantite_' + i, new FormControl('', []));
        this.requisitionForm[action]('item_tva_' + i, new FormControl('', []));
    }
    addNew() {
        this.detailsPiecesIngredient.push(<DetailsPieces>{controlIndex: this.iRow});
        this.rowValidateForm(this.iRow++, 'add');
    }
    removeRow(index) {
        let controlIndex = null;
        console.log(index,controlIndex,this.iRow);
        this.detailsPiecesIngredient.splice(index, 1);
            this.rowValidateForm(controlIndex, 'remove');
      }

    navigationDevis() {
        const listeCellule: any[] = [];
             this.principal.identity().then((account) => {
                this.currentAccount = account;
                this.authorities = this.currentAccount.authorities;
                const index = this.authorities.indexOf('ROLE_REPARATEUR');
                if (index !== -1) {
                    this.reparateur = true;
                }
                const indexGestionnaire = this.authorities.indexOf('ROLE_GESTIONNAIRE');
                if (indexGestionnaire !== -1) {
                    this.userCelluleService.findByUser(this.currentAccount.id).subscribe((resUser) => {this.userCellule = resUser;
                         for (let i = 0; i < this.userCellule.length; i++) {
                      listeCellule.push(this.userCellule[i].celluleId); }
                      const indexList  = listeCellule.indexOf(4);
                      if (indexList !== -1) {
                          this.isGestionnaireTechnique = true;
                      }
                    })
                }
                const indexExpert = this.authorities.indexOf('ROLE_EXPERT');
                if (indexExpert !== -1) {
                    this.expert = true;
                }
            });
        }
    save() {
        this.isSaving = true;
        if (this.detailsPiece.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detailsPiecesService.update(this.detailsPiece));
        } else {
            this.subscribeToSaveResponse(
                this.detailsPiecesService.create(this.detailsPiece));
        }
    }

    private subscribeToSaveResponse(result: Observable<DetailsPieces>) {
        result.subscribe((res: DetailsPieces) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DetailsPieces) {
        this.eventManager.broadcast({ name: 'detailsPiecesListModification', content: 'OK'});
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

    trackFournisseurById(index: number, item: Fournisseur) {
        return item.id;
    }

    trackDevisById(index: number, item: Devis) {
        return item.id;
    }

    trackPieceById(index: number, item: Piece) {
        return item.id;
    }
    trackId(index: number, item: DetailsPieces) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-details-pieces-popup',
    template: ''
})
export class DetailsPiecesIngredientPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailsPiecesPopupService: DetailsPiecesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.detailsPiecesPopupService
                    .open(DetailsPiecesIngredientDialogComponent as Component, params['id']);
            } else {
                this.detailsPiecesPopupService
                    .open(DetailsPiecesIngredientDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
