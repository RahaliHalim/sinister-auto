import { Component, OnInit, Input, OnDestroy, EventEmitter, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetailsMo } from './details-mo.model';
import { DetailsMoPopupService } from './details-mo-popup.service';
import { DetailsMoService } from './details-mo.service';
import { Devis, DevisService } from '../devis';
import { Grille, GrilleService } from '../grille';
import { Piece, PieceService } from '../piece';
import { RefTypeIntervention, RefTypeInterventionService } from '../ref-type-intervention';
import { UserCellule, UserCelluleService } from '../user-cellule';
import { ITEMS_PER_PAGE, ResponseWrapper, Principal } from '../../shared';
import { FormBuilder, Validators, FormGroup, FormControl} from '@angular/forms';

import { ReactiveFormsModule } from '@angular/forms';
//import {NgbPaginationConfig} from '@ng-bootstrap/ng-bootstrap';
@Component({
    selector: 'jhi-details-mo-dialog',
    templateUrl: './details-mo-dialog.component.html'
})
export class DetailsMoDialogComponent implements OnInit {

    isSaving: boolean;
    detailsMo: DetailsMo = new DetailsMo();
    
    @Input() detailsMos: DetailsMo[] = [];
    @Output() addMo = new EventEmitter<any>();//ridha
    @Input() total: any;
    
    detailsMosBis: DetailsMo[] = [];

    nbreHeures = 0;

    totalTTC = 0;
    
    devis: Devis[];
    
    reftypeinterventions: RefTypeIntervention[];
    reparateurGrille:any;
    grille: Grille = new Grille();
    currentAccount: any;
    pieces: Piece[];
    pec: any;
    authorities: any;
    reparateur: any;
    expert: any;
    userCellule: any;
    isGestionnaireTechnique: any;
    devisForPage: any;
    etat: any;
    controlIndex: number;
    iRow: number;
    requisitionForm: FormGroup;

    /** Pour la pagination */
    p: number = 1;

    routeData: any;
    itemsPerPage: any;
    page: any;
    previousPage: any;
    reverse: any;
    predicate: any;
    currentSearch: string;

    constructor(
        private alertService: JhiAlertService,
        private detailsMoService: DetailsMoService,
        private devisService: DevisService,
        private grilleService: GrilleService,
        private userCelluleService: UserCelluleService,
        private pieceService: PieceService,
        private refTypeInterventionService: RefTypeInterventionService,
        private eventManager: JhiEventManager,
        public builder: FormBuilder,
        private route: ActivatedRoute,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        
    ) {
        {
           
        }
    }
    
   
    ngOnInit() {
        
        this.iRow = 1;
        // Get current quotation 
        this.route.params.subscribe(( params ) => {
            console.log(params['id']);
            if ( params['id'] ) {
                this.devisService.find( params['id'] ).subscribe(( res ) => {
                this.devisForPage = res;
                    this.etat = this.devisForPage.etatDevis.toString();
                } )
            }
        } )
            
        this.isSaving = false;
        // calculate user profile
        this.navigationDevis();
        // Get all quotation from the database
        this.devisService.query().subscribe((res: ResponseWrapper) => { this.devis = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        // Get all intervention type from the database
        this.refTypeInterventionService.query().subscribe((res: ResponseWrapper) => { this.reftypeinterventions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        // Get all piece for type 1 
        this.pieceService.getPiecesByType(1).subscribe((res) => { this.pieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
    }
  
      loadDetailsMo(id) {
         
           
   
    }
    rowValidateForm(i: number, scenario?: string) {
        let action = 'addControl';
        if (scenario === 'remove') {
            action = 'removeControl';
        }
        this.requisitionForm[action]('item_designation_' + i, new FormControl('', []));
        this.requisitionForm[action]('item_typeIntervention_' + i, new FormControl('', []));
        this.requisitionForm[action]('item_nbreHeures_' + i, new FormControl('', []));
        
    }
     
    /** Ajouter une ligne vide */

    addNew() {
       
        this.detailsMos.push(<DetailsMo>{controlIndex: this.iRow});
        //this.iRow++;
    }
    

    /** Declenchement d'évenement d'utilisateur */  

    getRowData(typeInterventionId,designationId,nbreHeures,index){
   
       
       /*** Parcour de la liste des anciens détails MO (déja saisi)  */

        for (let entry of this.detailsMosBis) {
            console.log("control index:"+entry.controlIndex);
            console.log("index:"+index);

            /** si le mem object on fait update  */

            if(entry.controlIndex == index){
             
            entry.th = this.grille.th;
            entry.vat = this.grille.tva;
            entry.discount = this.grille.remise;
            
            entry.nombreHeures = nbreHeures;

            }
            
           /** si un nouveau objet on l'ajoute */

            else{
                
                console.log("insert control index:"+entry.controlIndex);
                console.log("insert index:"+index);

       this.detailsMo.th = this.grille.th;
       this.detailsMo.vat = this.grille.tva;
       this.detailsMo.discount = this.grille.remise;
       this.detailsMo.nombreHeures = nbreHeures;
       this.detailsMo.designationId = designationId;
       this.detailsMo.typeInterventionId = typeInterventionId;
       this.detailsMo.controlIndex = index;

       /** on ajoute l'objet dans la liste */

       this.detailsMosBis.push(this.detailsMo);
       
       /** On envoi le tableau au parent */

        this.addMo.emit(this.detailsMosBis);
            }
            
        }

        /** Si la liste est vide on insére le premier objet */

        if(this.detailsMosBis.length == 0){
           
           console.log("si la liste est vide"+this.detailsMosBis.length); 
            this.detailsMo.th = this.grille.th;
       this.detailsMo.vat = this.grille.tva;
       this.detailsMo.discount = this.grille.remise;
       this.detailsMo.nombreHeures = nbreHeures;
       this.detailsMo.designationId = designationId;
       this.detailsMo.typeInterventionId = typeInterventionId;
       this.detailsMo.controlIndex = index;

       /** on ajoute l'objet dans la liste */

       this.detailsMosBis.push(this.detailsMo);
      
      
        /** On envoi le tableau au parent */

        this.addMo.emit(this.detailsMosBis);
        }
       
   
       
    }

    /** Supprimer la ligne selectionnée  */

    removeRow(index) {
        
        let controlIndex = this.detailsMos[index].controlIndex-1;
       
        this.detailsMos.splice(index, 1);
         
            this.rowValidateForm(controlIndex, 'remove');
        
            //this.detailsMosBis.splice(index, 1);
            
      }
    
    /**
     * Calculate user profile (is reparateur, gestionnaire technique or expert 
     */
    navigationDevis() {
        const listeCellule: any[] = [];
        this.principal.identity().then(( account ) => {
            this.currentAccount = account;
            this.authorities = this.currentAccount.authorities;
            const index = this.authorities.indexOf( 'ROLE_REPARATEUR' );
            if ( index !== -1 ) {
                this.reparateur = true;
            }
            const indexGestionnaire = this.authorities.indexOf( 'ROLE_GESTIONNAIRE' );
            if ( indexGestionnaire !== -1 ) {
                this.userCelluleService.findByUser( this.currentAccount.id ).subscribe(( resUser ) => {
                this.userCellule = resUser;
                    for ( let i = 0; i < this.userCellule.length; i++ ) {
                        listeCellule.push( this.userCellule[i].celluleId );
                    }
                    const indexList = listeCellule.indexOf( 4 );
                    if ( indexList !== -1 ) {
                        this.isGestionnaireTechnique = true;
                    }
                } )
            }
            const indexExpert = this.authorities.indexOf( 'ROLE_EXPERT' );
            if ( indexExpert !== -1 ) {
                this.expert = true;
            }
        } );
    }

    save() {
        this.isSaving = true;
        if (this.detailsMo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detailsMoService.update(this.detailsMo));
        } else {
            this.subscribeToSaveResponse(
                this.detailsMoService.create(this.detailsMo));
        }
    }

    private subscribeToSaveResponse(result: Observable<DetailsMo>) {
        result.subscribe((res: DetailsMo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DetailsMo) {
        this.eventManager.broadcast({ name: 'detailsMoListModification', content: 'OK'});
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

    trackDevisById(index: number, item: Devis) {
        return item.id;
    }

    trackRefTypeInterventionById(index: number, item: RefTypeIntervention) {
        return item.id;
    }
    trackPieceById(index: number, item: Piece) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-details-mo-popup',
    template: ''
})
export class DetailsMoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailsMoPopupService: DetailsMoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.detailsMoPopupService
                    .open(DetailsMoDialogComponent as Component, params['id']);
            } else {
                this.detailsMoPopupService
                    .open(DetailsMoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
