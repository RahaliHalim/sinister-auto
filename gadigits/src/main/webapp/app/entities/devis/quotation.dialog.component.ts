import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Devis, EtatDevis } from './devis.model';
import { DevisPopupService } from './devis-popup.service';
import { DevisService } from './devis.service';
import { ContratAssurance, ContratAssuranceService } from '../contrat-assurance';
import { Assure, AssureService } from '../assure';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { Reparateur, ReparateurService } from '../reparateur';
import { DetailsMo, DetailsMoService } from '../details-mo';
import { DetailsPieces, DetailsPiecesService } from '../details-pieces';
import {UserCelluleService } from '../user-cellule';
import { ObservationService } from '../observation/observation.service';
import { PieceJointeService } from '../piece-jointe/piece-jointe.service';
import { Grille, GrilleService } from '../grille';
import { ResponseWrapper, Principal } from '../../shared';
import { AccordPriseCharge } from './accord-prise-charge.model';
import { ExpertService } from '../expert/expert.service';
import { PersonneMoraleService } from '../personne-morale/personne-morale.service';
import { PersonnePhysiqueService } from '../personne-physique/personne-physique.service';
import { PersonneMorale } from '../personne-morale/personne-morale.model';
import { Contact } from '../contact/contact.model';
import { PersonnePhysique } from '../personne-physique/personne-physique.model';
import { ContactService } from '../contact/contact.service';
import { RefAgenceService } from '../ref-agence/ref-agence.service';
import { RefAgence } from '../ref-agence/ref-agence.model';
import { RefCompagnieService } from '../ref-compagnie/ref-compagnie.service';
import { RefCompagnie } from '../ref-compagnie/ref-compagnie.model';
import { VehicleUsageService } from '../vehicle-usage/vehicle-usage.service';
import { VehicleUsage } from '../vehicle-usage/vehicle-usage.model';
import { Expert } from '../expert/expert.model';
import { Piece } from "../piece/piece.model";
import { PieceService } from "../piece/piece.service";
import { RefTypeIntervention } from "../ref-type-intervention/ref-type-intervention.model";
import { RefTypeInterventionService } from "../ref-type-intervention/ref-type-intervention.service";
import { PieceJointe } from '../piece-jointe/piece-jointe.model';
import { Client, ClientService } from '../client';
import { SysVilleService } from '../sys-ville/sys-ville.service';
import { SysVille } from '../sys-ville/sys-ville.model';
import { Result } from '../sinister-pec/result.model';
import { Decision } from '../expertise';
import {Authorities, ManagementModes} from "../../constants/app.constants";
import {ModeGesions} from "../../constants/mode-gestion";
import { SafeResourceUrl, DomSanitizer, EventManager } from '@angular/platform-browser';
import { GAEstimateService } from "./gaestimate.service";
import { PrimaryQuotationService } from '../PrimaryQuotation/primary-quotation.service';
import { PrimaryQuotation } from '../PrimaryQuotation/primary-quotation.model';
import { QuotationService } from '../quotation/quotation.service';
import { Quotation } from '../quotation/quotation.model';
import { SinisterPec } from '../sinister-pec';
@Component({
    selector: "jhi-quotation-dialog",
    templateUrl: "./quotation-dialog.component.html",
    providers:[GAEstimateService]
})
export class QuotationDialogComponent implements OnInit {
      // reparatorFactoration: Boolean = false;
      devis: Devis = new Devis();
      primaryQuotation: PrimaryQuotation = new PrimaryQuotation();
      primaryQuotationSaved: PrimaryQuotation;
      quotation: Quotation;
      isSaving: boolean;
      isCompany = false;
      reparateurs: Reparateur[];
      reparateurAffiche: Reparateur = new Reparateur();
      dateGenerationDp: any;
      prestation: any;
      contratAssurance: ContratAssurance = new ContratAssurance();
      vehicule: VehiculeAssure = new VehiculeAssure();
      assure: Assure = new Assure();
      refUsage: VehicleUsage = new VehicleUsage();
      vehiculeIsActive = false;
      assureIsActive = false;
      // Ref list
      moPieces: Piece[];
      ingredientPieces: Piece[];
      fourniturePieces: Piece[];
      refOperationTypes: RefTypeIntervention[];
  
      // list of pieces
      detailsMos: DetailsMo[] = [];
      detailsPiecesIngredient: DetailsPieces[] = [];
      detailsPiecesFourniture: DetailsPieces[] = [];
      detailsPiecesMO: DetailsPieces[] = [];
      detailsPieces: DetailsPieces[] = [];
  
      iMoRow: number;
      detailsMo: DetailsMo = new DetailsMo();
      detailsPieceMo: DetailsPieces = new DetailsPieces();
      iPaintRow: number;
  
  
      accordPriseCharge: AccordPriseCharge = new AccordPriseCharge();
      affectExpert: any;
      grille: Grille = new Grille();
  
      lastDevis: any;
      currentAccount: any;
      authorities: string[] = ['ROLE_GESTIONNAIRE', 'ROLE_CCELLULE', 'ROLE_RESPONSABLE', 'ROLE_RAPPORTEUR', 'ROLE_REPARATEUR'];
      reparateur: any;
      userCellule: any;
      isGestionnaireTechnique: any;
      expert: any;
      pec: any;
      observations: any;
      piecesJointes: any;
      result: boolean;
      devisAccepte: any;
      devisPreview: any;
      devisValide: any;
      devisSaved: any;
      piece: any;
      verifExport: any;
      etat: any;
      totalTtc = 0;
      moTotalTtc = 0;
      ingTotalTtc = 0;
      fournTotalTtc = 0;
      piecesTotalTtc = 0;
  
      public devisTotalTtc: any;
      public grilleDetailsMo: any;
       ttc: any[] = [];
      ttaffect: any;
      assureNom: any;
      assureAdresse: any;
      assureVille: any;
      assureTel: any;
      contacts: any;
      personnePhysique: any;
      devisToAccept: any;
      devisFacture: any;
      morale: PersonneMorale = new PersonneMorale();
      editDateReceptionVehicule: boolean;
       //let total: any[] = [];
        //primes: number[] = [];
      totalTTCtest: any;
      validAccord: any;
  
      admin: any;
      isResponsable: any;
      isGestionnaire: any;
      isChefCellule: any;
      pieceJointe: PieceJointe;
      pj: number;
      deleteDetailsMo: boolean = false;
      deletedpIngredient: boolean = true;
      resTotalHt = 0;
      resTotalTtc = 0;
      client: any;
      compagnie: any;
      usage: any;
      refusagecontrats: VehicleUsage[];
      sysPhyVille: any;
      time;
      pieceJointelength: boolean = false;
      piecesJointesConstat: PieceJointe[] = [];
      piecesJointesFaceAvant: PieceJointe[] = [];
      piecesJointesLatGauche: PieceJointe[] = [];
      piecesJointesLatDroit: PieceJointe[] = [];
      piecesJointesNumSerie: PieceJointe[] = [];
      piecesJointesImmatriculation: PieceJointe[] = [];
      piecesJointesCompteur: PieceJointe[] = [];
      piecesJointesReparation: PieceJointe[] = [];
      piecesJointesCasse: PieceJointe[] = [];
      piecesJointesFournisseur: PieceJointe[] = [];
      sumTH : any;
      files: any;
      filesLatGauche:any;
      filesNumSerie:any;
      filesLatDroit:any;
      filesImmatriculation:any;
      filesCompteur: any;
      filesDevisReparation: any;
      filesDevisCasse: any;
      filesDevisFournisseur:any;
      chemin: Result = new Result();
      pieceJ: PieceJointe = new PieceJointe();
      deletePiece = false;
      dateExpertiseDp: any;
      detailsM: any;
      fourniture: any;
      ingerdient: any;
      decisionExpert: any;
      decisionExpertise: any;
      devisFinded: Devis = new Devis();
  
      options = [
          {name:'Accord pour prise en charge', value:'1', checked:false},
          {name:'Accord partiel pour démontage', value:'2', checked:false},
          {name:'Expertise terrain obligatoire', value:'3', checked:false}
        ];
  
      optionsExpertiseTerrain= [
          {name:'Accord pour prise en charge', value:'1', checked:false},
          {name:'Accord partiel pour démontage', value:'2', checked:false},
          {name:'Circonstance de l\'accident non conforme avec le constat' , value:'3', checked:false}
        ];
  
      pecAnnule = false;
      gaestimateUrl: SafeResourceUrl;
      gaestimateLink: string;
      useGtEstimate = false;
      constructor(
          private alertService: JhiAlertService,
          private devisService: DevisService,
          private grilleService: GrilleService,
          private observationService: ObservationService,
          private pieceJointeService: PieceJointeService,
          private contratAssuranceService: ContratAssuranceService,
          private assureService: AssureService,
          private vehiculeAssureService: VehiculeAssureService,
          private reparateurService: ReparateurService,
          private expertService: ExpertService,
          private detailsMoService: DetailsMoService,
          private userCelluleService: UserCelluleService,
          private detailsPiecesService: DetailsPiecesService,
          private refAgenceService: RefAgenceService,
          private personneMoraleService: PersonneMoraleService,
          private personnePhysiqueService: PersonnePhysiqueService,
          private contactService: ContactService,
          private refCompagnieService: RefCompagnieService,
          private refUsageContratService: VehicleUsageService,
          private primaryQuotationService: PrimaryQuotationService,
          private quotationService: QuotationService,
          private route: ActivatedRoute,
          private principal: Principal,
          private router: Router,
          private eventManager: JhiEventManager,
          private pieceService: PieceService,
          private refTypeInterventionService: RefTypeInterventionService,
          private clientService: ClientService,
          private sysVilleService: SysVilleService,
          private sanitizer: DomSanitizer,
          private gaEstimateService: GAEstimateService,
          private evtManager: EventManager
      ) {}
  
      calculateVetuste(pieces){
          this.resTotalHt = pieces.vetuste*pieces.totalHt/100;
      }
  
      /**
       * Add a new row in mo list
       */
      addNewMoLine() {
          this.detailsPiecesMO.push(new DetailsPieces());
      }
  
      /**
       * Add a new row in pieces list
       */
      addNewPieceLine(listPieces: any) {
          listPieces.push(new DetailsPieces());
      }
  
      onClickedByExpert(option, event){
          console.log(event.target.value);
          console.log(option.name);
          this.decisionExpert = event.target.value;
          console.log(this.decisionExpert);
      }
  
      onClickedExpertiseTerrainByExpert(option, event){
          console.log(event.target.value);
          console.log(option.name);
          this.decisionExpertise = event.target.value;
          console.log(this.decisionExpertise);
      }
      
  
      /**
       * Load grille informations
       * @param detailsMoLine
       */
      loadDetailsMo(detailsMoLine: DetailsPieces) {
   
              return this.grille;

      }
  
      /**
       * Calculate the total Ttc when droit timbre change
       */
      changeDroitTimbre() {
          this.primaryQuotation.ttcAmount = this.primaryQuotation.stampDuty ? this.primaryQuotation.stampDuty : 0;
          this.primaryQuotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
          this.primaryQuotation.ttcAmount = +(this.primaryQuotation.ttcAmount).toFixed(3);
      }
  
      /**
       * calculate totalHt and Ttc on change of hours number
       * @param detailsMoLine
       */
      changeHourCount(detailsMoLine: DetailsPieces) {
          detailsMoLine.totalHt = +(detailsMoLine.prixUnit*detailsMoLine.nombreHeures).toFixed(3);;
          detailsMoLine.totalTtc = detailsMoLine.totalHt + (detailsMoLine.totalHt*detailsMoLine.tva/100);
          detailsMoLine.totalTtc = +(detailsMoLine.totalTtc - (detailsMoLine.totalTtc*detailsMoLine.discount/100)).toFixed(3);;
          this.calculateGlobalMoTtc();
      }
  
      /**
       * change Ttc on change of discount
       */
      changeDiscountCount(detailsLine: DetailsPieces, typeLine: any){
  
          if(detailsLine.discount != undefined ){
  
              console.log("discount"+detailsLine.totalTtc*detailsLine.discount/100);
              // detailsLine.totalHt = +(detailsLine.prixUnit*detailsLine.quantite).toFixed(3);
              detailsLine.totalTtc = +(detailsLine.prixUnit*detailsLine.quantite*(1 + detailsLine.tva/100)).toFixed(3);
              detailsLine.totalTtc = +(detailsLine.totalTtc - (detailsLine.totalTtc*detailsLine.discount/100)).toFixed(3);
  
              if(typeLine != undefined && typeLine == 1) {
  
                  this.calculateGlobalIngTtc();
              } else if(typeLine != undefined && typeLine == 2) {
  
                  this.calculateGlobalFournTtc();
              } else if(typeLine != undefined && typeLine == 3) {
  
                  this.calculateGlobalPiecesTtc();
              }
          }
      }
  
      /**
       * calculate totalHt
       * @param detailsLine
       */
      calculateTotalHt(detailsLine: DetailsPieces, typeLine: any) {
          if(detailsLine.prixUnit != undefined && detailsLine.quantite != undefined) { // Calculate HT
              detailsLine.totalHt = +(detailsLine.prixUnit*detailsLine.quantite).toFixed(3);;
          }
          if(detailsLine.totalHt != undefined && detailsLine.tva != undefined) { // Calculate TTC
              detailsLine.totalTtc = +(detailsLine.totalHt*(1 + detailsLine.tva/100)).toFixed(3);;
              if(typeLine != undefined && typeLine == 1) {
                  this.calculateGlobalIngTtc();
              } else if(typeLine != undefined && typeLine == 2) {
                  this.calculateGlobalFournTtc();
              } else if(typeLine != undefined && typeLine == 3) {
                  this.calculateGlobalPiecesTtc();
              }
  
          }
      }

      /**
       * calculate totalTTC
       * @param detailsLine
       */
      calculateTotalTtc(detailsLine: DetailsPieces, typeLine: any) {
          if(detailsLine.totalHt != undefined && detailsLine.tva != undefined) { // Calculate TTC
              detailsLine.totalTtc = +(detailsLine.totalHt*(1 + detailsLine.tva/100)).toFixed(3);
              if(typeLine != undefined && typeLine == 1) {
                  this.calculateGlobalIngTtc();
              } else if(typeLine != undefined && typeLine == 2) {
                  this.calculateGlobalFournTtc();
              } else if(typeLine != undefined && typeLine == 3) {
                  this.calculateGlobalPiecesTtc();
              }
          }
      }
  
      /**
       * Calculate the total Ttc in quotation where ing modified
       */
      calculateGlobalIngTtc() {
          this.primaryQuotation.ttcAmount = this.primaryQuotation.stampDuty ? this.primaryQuotation.stampDuty : 0;
          this.ingTotalTtc = 0;
          if(this.detailsPiecesIngredient && this.detailsPiecesIngredient.length > 0) {
              for (let i = 0; i < this.detailsPiecesIngredient.length ; i++) {
                  this.ingTotalTtc +=  this.detailsPiecesIngredient[i].totalTtc;
              }
          }
          this.ingTotalTtc = +(this.ingTotalTtc).toFixed(3);
          this.primaryQuotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
          this.primaryQuotation.ttcAmount = +(this.primaryQuotation.ttcAmount).toFixed(3);
      }
  
      /**
       * Calculate the total Ttc in quotation where fourn modified
       */
      calculateGlobalFournTtc() {
          this.primaryQuotation.ttcAmount = this.primaryQuotation.stampDuty ? this.primaryQuotation.stampDuty : 0;
          this.fournTotalTtc = 0;
          if(this.detailsPiecesFourniture && this.detailsPiecesFourniture.length > 0) {
              for (let i = 0; i < this.detailsPiecesFourniture.length ; i++) {
                  this.fournTotalTtc +=  this.detailsPiecesFourniture[i].totalTtc;
              }
          }
          this.fournTotalTtc = +(this.fournTotalTtc).toFixed(3);
          this.primaryQuotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
          this.primaryQuotation.ttcAmount = +(this.primaryQuotation.ttcAmount).toFixed(3);
      }
  
      /**
       * Calculate the total Ttc in quotation where pieces modified
       */
      calculateGlobalPiecesTtc() {
          this.primaryQuotation.ttcAmount = this.primaryQuotation.stampDuty ? this.primaryQuotation.stampDuty : 0;
          this.piecesTotalTtc = 0;
          if(this.detailsPieces && this.detailsPieces.length > 0) {
              for (let i = 0; i < this.detailsPieces.length ; i++) {
                  this.piecesTotalTtc +=  this.detailsPieces[i].totalTtc;
              }
          }
          this.piecesTotalTtc = +(this.piecesTotalTtc).toFixed(3);
          this.primaryQuotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
          this.primaryQuotation.ttcAmount = +(this.primaryQuotation.ttcAmount).toFixed(3);
      }
  
  
      /**
       * Calculate the total Ttc in quotation where mo modified
       */
      calculateGlobalMoTtc() {
          this.primaryQuotation.ttcAmount = this.primaryQuotation.stampDuty ? this.primaryQuotation.stampDuty : 0;
          this.moTotalTtc = 0;
          if(this.detailsPiecesMO && this.detailsPiecesMO.length > 0) {
              for (let i = 0; i < this.detailsPiecesMO.length ; i++) {
                  this.moTotalTtc +=  this.detailsPiecesMO[i].totalTtc;
              }
          }
          
          this.moTotalTtc = +(this.moTotalTtc).toFixed(3);
          this.primaryQuotation.ttcAmount += this.moTotalTtc + this.ingTotalTtc + this.fournTotalTtc + this.piecesTotalTtc;
          this.primaryQuotation.ttcAmount = +(this.primaryQuotation.ttcAmount).toFixed(3);
      }
  
  
      /**
       * Remove mo line
       * @param index
       */
      removeMoLine(detailsPieceMo, index) {
          if(detailsPieceMo.id != undefined){
              this.detailsPiecesMO.splice(index, 1);
              this.detailsPiecesService.delete(detailsPieceMo.id).subscribe((res)=>{
                  this.deleteDetailsMo = true;
              });
          }
          else{
              this.detailsPiecesMO.splice(index, 1);
          
          }
  
          this.calculateGlobalMoTtc();
          
          
      }
  
      /**
       * Remove ingredient line
       * @param index
       */
      removeIngredientLine(dpIngredient, index) {
          if(dpIngredient.id != undefined){
              this.detailsPiecesIngredient.splice(index, 1);
              this.detailsPiecesService.delete(dpIngredient.id).subscribe((res)=>{
                  this.deletedpIngredient = true;
              });
          }else{
              this.detailsPiecesIngredient.splice(index, 1);
          }
  
          this.calculateGlobalIngTtc();
          
      }
  
      /**
       * Remove ingredient line
       * @param index
       */
      removeFournitureLine(fourniture, index) {
          if(fourniture.id != undefined){
              this.detailsPiecesFourniture.splice(index, 1);
              this.detailsPiecesService.delete(fourniture.id).subscribe((res)=>{
                  this.deletedpIngredient = true;
              });
          }else{
              this.detailsPiecesFourniture.splice(index, 1);
          }
  
          this.calculateGlobalFournTtc();
          
      }
  
      /**
       * Remove ingredient line
       * @param index
       */
      removePieceLine(pieces, index) {
          if(pieces.id != undefined){
              this.detailsPieces.splice(index, 1);
              this.detailsPiecesService.delete(pieces.id).subscribe((res)=>{
                  this.deletedpIngredient = true;
              });
          }else{
              this.detailsPieces.splice(index, 1);
          }
  
          this.calculateGlobalPiecesTtc();
          
      }
  
      /**
       * Init component
       */
      ngOnInit() {
          //this.time = {hour: 13, minute: 30};
          // Init Mo list and vars
          this.iMoRow = 1;
          // Get all piece for type 1
          this.pieceService.getPiecesByType(1).subscribe((res) => { this.moPieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
          // Get all piece for type 2
          this.pieceService.getPiecesByType(2).subscribe((res) => { this.ingredientPieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
          // Get all piece for type 3
          this.pieceService.getPiecesByType(3).subscribe((res) => { this.fourniturePieces = res; }, (res: ResponseWrapper) => this.onError(res.json));
  
          // Get all ref Usage from the database
          this.refUsageContratService.query()
              .subscribe((res: ResponseWrapper) => { this.refusagecontrats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
          // Get all intervention type from the database
          this.refTypeInterventionService.query().subscribe((res: ResponseWrapper) => { this.refOperationTypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
          // Get prestation
          this.principal.identity().then((account) => {
              this.currentAccount = account;
              this.route.params.subscribe((params) => {
                  if ( params['prestationId'] ) {
                      
                  }
              })
          });
  
          this.isSaving = false;
          this.editDateReceptionVehicule = false;
          this.principal.identity().then((account) => {
              this.currentAccount = account;
              this.result = this.principal.hasAnyAuthorityDirect(this.authorities);
          });
          
          this.reparateurService.query()
              .subscribe((res: ResponseWrapper) => { this.reparateurs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
          this.init();
          this.navigationDevis();
  
  
  }
      receiveMessage(event) {
          var RegexTargetURI = /gtestimate.com/i;
          if (RegexTargetURI.test(event.origin)) {
              //Close iFrame
              //var iframe = document.getElementById('theIframe');
              this.useGtEstimate = false;
              // TODO : get estimation details
              //iframe.parentNode.removeChild(iframe);
              alert("Vous avez cloture GA Estimate!!!");
          } else {
              return;
          }
      }
      
      
      addListenerToIframe(e) {
          this.evtManager.addGlobalEventListener('window', 'message', this.receiveMessage);
      }
      
      getGAEstimateCreationUrl() {
          this.devisService.createEstimation().subscribe((res: Response) => {
              if (res) {
                  this.useGtEstimate = true;
                  this.gaestimateUrl = this.sanitizer.bypassSecurityTrustResourceUrl(res.text());
                  console.log(this.gaestimateUrl);
                  //this.gaestimateLink = res.text();
                  //window.open(res.text(), '_blank');
              }
          });
      }
  
      init() {
  
          this.reparateur = this.principal.hasAnyAuthorityDirect(['ROLE_REPARATEUR']);
          this.expert = this.principal.hasAnyAuthorityDirect(['ROLE_EXPERT']);
          console.log(this.expert);
          this.admin = this.principal.hasAnyAuthorityDirect(['ROLE_ADMIN']);
          this.isResponsable = this.principal.hasAnyAuthorityDirect(['ROLE_RESPONSABLE']);
          this.isGestionnaire = this.principal.hasAnyAuthorityDirect(['ROLE_GESTIONNAIRE']);
          this.isChefCellule = this.principal.hasAnyAuthorityDirect(['ROLE_CCELLULE']);
          this.isCompany = this.principal.hasAnyAuthorityDirect([Authorities.AGCOMPAGNIE]);
          console.log(this.isCompany);
  
          this.route.params.subscribe((params) => {
              if (params['prestationId']) {
                  const splits = params['prestationId'].split('-');
                  if (splits[1] === undefined) {
                  
          } else { // Expert affectation
              this.devis.expertId = splits[1];
              this.expertService.find(this.devis.expertId).subscribe((resExpert) => {
                  this.expert = resExpert;
                  this.personneMoraleService.find(this.expert.personneMoraleId).subscribe((resMorale) => this.morale = resMorale)
              })
              this.devisService.update(this.devis).subscribe((res) => this.devis = res)
              this.affectExpert.expertId = splits[1];
              this.affectExpert.prestationId = splits[0];
          }
      }
      if (params['id']) {
         
          this.quotationService.find(params['id']).subscribe((res) => {
              this.quotation = res;
              console.log("params id devis"+params['id']);
              console.log("id devissssss"+this.quotation.id);
              const date = new Date(this.quotation.expertiseDate);
              if (this.quotation.expertiseDate) {
                  this.quotation.expertiseDate = {
                      year: date.getFullYear(),
                      month: date.getMonth() + 1,
                      day: date.getDate()
                  };
              }
           
              //this.etat = this.devis.quotationStatusId;
             
              /*if (this.devis.expertId) {
                  this.expertService.find(this.devis.expertId).subscribe((resExpert) => {
                      this.expert = resExpert;
                      this.personneMoraleService.find(this.expert.personneMoraleId).subscribe((resMorale) => this.morale = resMorale)
                  })
              }
              if (this.etat === 'Accepte') {
                  this.verifExport = true;
              }*/
  
              
             
              this.loadAllDetailsMo();
              this.loadAllIngredient();
              this.loadAllRechange();
              this.loadAllFourniture();
              this.loadAllObservations();
              this.loadAllPieces();
  
          })
      }
  })
  }
      editDateReceptionVehiculeMethode() {
          console.log('editDateReceptionVehiculeMethode()')
          this.editDateReceptionVehicule = true;
      }
  
      /**
       * Load all pieces jointes with type constat
       */
  
      loadAllPieceJointes() {
          
      }
  
      generatePJ(pieceJointe){
          console.log(pieceJointe);
  
          this.pieceJointeService.generatePJ(pieceJointe).subscribe((pieceJointePrint) => {
              window.open(pieceJointePrint.headers.get('pdfname'), '_blank');
          })
     
  
  }
  
  
  
      /**
       * Load all mo lines
       */
      loadAllDetailsMo() {
          this.detailsPiecesService.queryByDevisAndType(this.primaryQuotation.id, 1, true) .subscribe((subRes: ResponseWrapper) => {
              this.detailsPiecesMO = subRes.json;
              this.calculateGlobalMoTtc();
              this.getSum4();
          })
  
          /*this.detailsMoService.findByDevis(this.devis.id).subscribe((res: ResponseWrapper) => {
              this.detailsMos = res.json;
              console.log(this.detailsMos);
              this.calculateGlobalMoTtc();
              this.getSum4();
              
              
          })*/
          
      }
      /**
       * Load all ingredient line
       */
      loadAllIngredient() {
          this.detailsPiecesService.queryByDevisAndType(this.primaryQuotation.id, 2, false) .subscribe((subRes: ResponseWrapper) => {
              this.detailsPiecesIngredient = subRes.json;
              this.calculateGlobalIngTtc();
          })
      }
      loadAllRechange() {
          this.detailsPiecesService.queryByDevisAndType(this.primaryQuotation.id, 1, false) .subscribe((subRes: ResponseWrapper) => {
              this.detailsPieces = subRes.json;
              this.calculateGlobalPiecesTtc();
          })
      }
  
      loadAllFourniture() {
          this.detailsPiecesService.queryByDevisAndType(this.primaryQuotation.id, 3, false) .subscribe((subRes: ResponseWrapper) => {
              this.detailsPiecesFourniture = subRes.json;
              this.calculateGlobalFournTtc();
          })
      }
  
  
      exportPiece(id){
  
          this.pieceJointeService.find(id).subscribe((pieceJointe) => {
              this.pieceJointe = pieceJointe;
  
              this.devisService.generatePiece(this.pieceJointe).subscribe((pieceJointePrint) => {
                  window.open(pieceJointePrint.headers.get('pdfname'), '_blank');
              })
          });
  
      }
      /**
       * Remove mo line
       * @param index
       */
  
      getSum4() : number {
          let sum = 0;
          for(let i = 0; i < this.detailsMos.length; i++) {
              if(this.detailsMos[i].typeInterventionId && this.detailsMos[i].nombreHeures){
                  sum += this.detailsMos[i].totalHt;
              }
          }
          return sum;
        }
  
       
  
        getSum7() : number {
          let sum = 0;
          for(let i = 0; i < this.detailsMos.length; i++) {
              if(this.detailsMos[i].typeInterventionId && this.detailsMos[i].nombreHeures){
                  sum += this.detailsMos[i].totalTtc;
              }
          }
          return sum;
        }
  
        getSumPieceHT() : number {
          let sum = 0;
          for(let i = 0; i < this.detailsPieces.length; i++) {
              if(this.detailsPieces[i].prixUnit && this.detailsPieces[i].quantite && this.detailsPieces[i].tva){
                  sum += this.detailsPieces[i].totalHt;
              }
          }
          return sum;
        }
  
        getSumPieceTTC() : number {
          let sum = 0;
          for(let i = 0; i < this.detailsPieces.length; i++) {
              if(this.detailsPieces[i].prixUnit && this.detailsPieces[i].quantite && this.detailsPieces[i].tva){
                  sum += this.detailsPieces[i].totalTtc;
              }
          }
          return sum;
        }
  
  
      onFileChange(fileInput: any) {
      this.files = fileInput.target.files[0];
      console.log(this.files);
      //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
  
      onFileChangeLatGauche(fileInput: any) {
      this.filesLatGauche = fileInput.target.files[0];
      console.log(this.files);
      //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
      
      onFileChangeLatDroit(fileInput: any) {
          this.filesLatDroit = fileInput.target.files[0];
          console.log(this.files);
          //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
      onFileChangeNumSerie(fileInput: any) {
          this.filesNumSerie = fileInput.target.files[0];
          console.log(this.files);
          //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
      onFileChangeImmatriculation(fileInput: any) {
      this.filesImmatriculation = fileInput.target.files[0];
      console.log(this.files);
      //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
  
      onFileChangeCompteur(fileInput: any) {
          this.filesCompteur = fileInput.target.files[0];
          console.log(this.files);
      //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
  
      onFileChangeDevisReparation(fileInput: any) {
          this.filesDevisReparation = fileInput.target.files[0];
          console.log(this.files);
          //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
  
      onFileChangeDevisCasse(fileInput: any) {
          this.filesDevisCasse = fileInput.target.files[0];
          console.log(this.files);
          //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
      onFileChangeDevisFournisseur(fileInput: any) {
          this.filesDevisFournisseur = fileInput.target.files[0];
          console.log(this.files);
          //this.pieceJointeService.updateFile(this.pieceJointe, files);
      }
  
  
      affichePieceFaceAvant(){
  
      }
  
  
  
      savePhotoAvant() {
          if(this.files){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
      saveLatGauche() {
          if(this.filesLatGauche){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
      saveLatDroit() {
          if(this.filesLatDroit){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
      saveNumSerie() {
          if(this.filesNumSerie){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
      saveImmatriculation() {
          if(this.filesImmatriculation){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
      saveCompteur() {
          if(this.filesCompteur){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
             
          }
          )
      }
  
      }
  
      saveDevisReparation() {
          if(this.filesDevisReparation){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
  
      saveDevisCasse() {
          if(this.filesDevisCasse){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
  
      saveDevisFournisseur() {
          if(this.filesDevisFournisseur){
          this.isSaving = true;
          //console.log(this.prestationpec.prestationId);
          
          //this.prestationService.find(this.prestationpec.prestationId).subscribe((res) => {this.pj.prestationId = res.id; console.log(this.pieceJointe.prestationId); 
        
              this.pieceJointeService.affectChemin().subscribe((res) => {
              this.chemin = res;
              console.log(this.chemin);
              console.log(this.pj);
              
          }
          )
      }
  
      }
      
  
  
  
      /**
       * Save quotation
       */
        
        
  
        save() {
          console.log("beforetesteditquotation"+this.quotation.id);
          if(this.quotation.id != null){
              console.log("editquotation"+this.quotation.id);
              this.subscribeDetailsToUpdateResponse(this.quotationService.update(this.quotation));
             
             
              this.router.navigate(['../../../']); // GOTO TODO LIST
  
          }else{
  
          this.isSaving = true;
          this.savePhotoAvant();
          this.saveLatGauche();
          this.saveLatDroit();
          this.saveNumSerie();
          this.saveImmatriculation();
          this.saveCompteur();
          this.saveDevisReparation();
          this.saveDevisCasse();
          this.saveDevisFournisseur();
                    
          
              this.primaryQuotation.statusId = 1;
              this.subscribeDetailsToSaveResponse(this.primaryQuotationService.updateQuotation(this.primaryQuotation));
              this.router.navigate(['../../../']); // GOTO TODO LIST
       
          }
      }
  
      private subscribeToSaveResponse(result: Observable<PieceJointe>) {
          result.subscribe((res: PieceJointe) =>
              this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
      }
  
      private onSaveSuccess(result: PieceJointe) {
          this.eventManager.broadcast({ name: 'pieceJointeListeModification', content: 'OK'});
          this.isSaving = false;
          
      }
  
      /**
       * Soumettre the quotation to review
       */
  
         private subscribeDetailsToSaveResponse(result: Observable<PrimaryQuotation>) {
          
          result.subscribe((res: PrimaryQuotation) =>
          this.onDetailsSaveSuccess(res), (res: Response) => this.onSaveError(res));
      }
      private subscribeDetailsToUpdateResponse(result: Observable<Quotation>) {
          
          result.subscribe((res: Quotation) =>
          this.onDetailsUpdateSuccess(res), (res: Response) => this.onSaveError(res));
      }
  
       private subscribePieceDeRechangeToSaveResponse(result: Observable<Devis>) {
          result.subscribe((res: Devis) =>
              this.onPieceDeRechangeSaveSuccess(res), (res: Response) => this.onSaveError(res));
      }
  
      private onPieceDeRechangeSaveSuccess(result: Devis) {
          for (let i = 0; i < this.detailsPieces.length; i++) {
              this.detailsPieces[i].quotationId = result.id;
              this.detailsPieces[i].id = null;
                  this.subscribeToSaveResponsePiece(
                      this.detailsPiecesService.create(this.detailsPieces[i]));
          }
      }
  
  
  
       soumettre(devis) {
          this.isSaving = true;
          this.savePhotoAvant();
          this.saveLatGauche();
          this.saveLatDroit();
          this.saveNumSerie();
          this.saveImmatriculation();
          this.saveCompteur();
          this.saveDevisReparation();
          this.saveDevisCasse();
          this.saveDevisFournisseur();
          //this.subscribeToSaveResponsePEC(this.prestationPECService.update(this.prestationpec));
          //this.devis.id = null;
          //this.devis.prestationId = this.prestationpec.id;
          //this.devis.reparateurId = this.prestationpec.reparateurId;
          //this.devisService.find(id).subscribe((resLast) => {
              //this.devisFinded = resLast;
              /*if (this.lastDevis  == null) {
                  this.devis.version = 1;
              } else {
                  this.devis.version = this.lastDevis.version + 1;
              }*/
              this.devis.quotationStatusId = 2;
              this.subscribeDetailsToSaveResponse(this.devisService.update(devis));
              this.router.navigate(['../../../']);
         // })
      }
  
        soumettreDevis(devis) { console.log('etat=====soumis');
          devis.etatDevis = EtatDevis.Soumis;
          this.devisService.update(devis).subscribe((resBonSortie) => devis = resBonSortie);
          this.router.navigate(['../../../']);
  
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
                  console.log(this.expert);
              }
          });
      }
      accepterGenerationBonSortie(devis) {
          devis.etatDevis = EtatDevis.ValideFacturation;
          this.devisService.update(devis).subscribe((resBonSortie) => devis = resBonSortie);
          this.router.navigate(['../../../']);
      }
      RectifierApresFacture(devis) {
          devis.etatDevis = EtatDevis.Refuse_Apres_Facture;
          this.devisService.update(devis).subscribe((resRectif) => devis = resRectif);
          this.router.navigate(['../../../']);
      }
      corrigerDevisFacture(devis) {
          devis.etatDevis = EtatDevis.Refuse;//TODO: confirmer Facture ==> refuse
          this.devisService.update(devis).subscribe((resCorrige) => devis = resCorrige);
          this.router.navigate(['../../../']);
      }
  
      validerDevisParGestTech(devis) {
          
          this.router.navigate(['../../../']);
  
      }
  
      loadAllObservations() {
          
  //})
      }
  
      loadAllPieces() {
        
      }
  
  
  
  
      genererAccord(devis) {
          if (this.reparateur == true) {
              this.devis.quotationStatusId =  9 ;
              this.devisService.update(devis).subscribe((resBonSortie) => devis = resBonSortie);
              this.loadAccordPriseCharge(this.devis);
            this.router.navigate(['../../../'])
          }
          if(this.isGestionnaireTechnique == true){
              this.loadAccordPriseCharge(this.devis);
  
          }
      }
  
      /**
       * Preview
       * @param id
       */
      previewDevis(id) {
          this.devisService.find(id).subscribe((resPreviewDevis) => {this.devisPreview = resPreviewDevis;
          this.loadAccordPriseCharge(this.devisPreview);
          // this.router.navigate(['../../../'])
      })
      }
  
      /**
       * Final validation by GesTeh (Accord Button)
       * @param id
       */
      accordDevisParGesTech(devis)  {
          devis.etatDevis = EtatDevis.Accord ;
          
          this.devisService.update(devis).subscribe((resRectif) => devis = resRectif);
          this.router.navigate(['../../../']);
  
      }
  
      loadAccordPriseCharge(sinisterPec: SinisterPec) {
      }
  
      refuserDevis(id) {
          for (let i = 0; i < this.detailsPieces.length; i++) {
              this.detailsPieces[i].vetuste = this.detailsPieces[i].vetuste;
                      this.detailsPiecesService.update(this.detailsPieces[i]).subscribe((resPiece) => this.piece = resPiece)
                  }
                  this.router.navigate(['/', { outlets: { popup: 'devis/' + id + '/refus'} }]);
      }
  
      decisionDevisParExpert(devis){
          
          //Valider devis: Etat = Valide
          if(this.decisionExpert == 1 ){
              this.validerDevis(devis);
              console.log(this.decisionExpert);
          }
          
          if(this.decisionExpert == 2){
              this.demontage(devis);
          }
          if(this.decisionExpert == 3){
              this.expertise(devis);
          }
          
      }
  
      decisionExpertiseTerrain(devis){
          
          //Valider devis: Etat = Valide
          if(this.decisionExpertise == 1 ){
              this.validerDevis(devis);
              console.log(this.decisionExpertise);
          }
          
          if(this.decisionExpertise == 2){
              this.demontage(devis);
          }
          if(this.decisionExpertise == 3){
              this.annulerPec(devis);
          }
          
      }
  
  
      validerDevis(devis) {
          devis.etatDevis = EtatDevis.Valide ;
          this.devisService.update(devis).subscribe((resValid) => {this.devisValide = resValid;
              for (let i = 0; i < this.detailsPieces.length; i++) {
                  this.detailsPieces[i].vetuste = this.detailsPieces[i].vetuste;
                      this.detailsPiecesService.update(this.detailsPieces[i]).subscribe((resPiece) => this.piece = resPiece);
              }
              for (let i = 0; i < this.detailsMos.length; i++) {
                  this.detailsMoService.update(this.detailsMos[i]).subscribe((resDetailsMo) => this.detailsM = resDetailsMo);
              }
              for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
                  this.detailsPiecesService.update(this.detailsPiecesFourniture[i]).subscribe((resFournitures) => this.fourniture = resFournitures);
              }
              for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
                  this.detailsPiecesService.update(this.detailsPiecesIngredient[i]).subscribe((resIngredients) => this.ingerdient = resIngredients)
              }
              
          });
          
          this.router.navigate(['../../../']);
      }
  
      expertise(devis) {
          devis.etatDevis = EtatDevis.Expertise ;
          this.devisService.update(devis).subscribe((resValid) => {this.devisValide = resValid;
              for (let i = 0; i < this.detailsPieces.length; i++) {
                  this.detailsPieces[i].vetuste = this.detailsPieces[i].vetuste;
                      this.detailsPiecesService.update(this.detailsPieces[i]).subscribe((resPiece) => this.piece = resPiece);
              }
              for (let i = 0; i < this.detailsMos.length; i++) {
                  this.detailsMoService.update(this.detailsMos[i]).subscribe((resDetailsMo) => this.detailsM = resDetailsMo);
              }
              for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
                  this.detailsPiecesService.update(this.detailsPiecesFourniture[i]).subscribe((resFournitures) => this.fourniture = resFournitures);
              }
              for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
                  this.detailsPiecesService.update(this.detailsPiecesIngredient[i]).subscribe((resIngredients) => this.ingerdient = resIngredients)
              }
              
          });
          
          this.router.navigate(['../../../']);
      }
  
      annulerPec(devis){
          
  
          
      }
  
      demontage(devis) {
          devis.etatDevis = EtatDevis.Demontage ;
          console.log(devis.etatDevis);
          this.devisService.update(devis).subscribe((resValid) => {this.devisValide = resValid;
              for (let i = 0; i < this.detailsPieces.length; i++) {
                  this.detailsPieces[i].vetuste = this.detailsPieces[i].vetuste;
                      this.detailsPiecesService.update(this.detailsPieces[i]).subscribe((resPiece) => this.piece = resPiece);
              }
              for (let i = 0; i < this.detailsMos.length; i++) {
                  this.detailsMoService.update(this.detailsMos[i]).subscribe((resDetailsMo) => this.detailsM = resDetailsMo);
              }
              for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
                  this.detailsPiecesService.update(this.detailsPiecesFourniture[i]).subscribe((resFournitures) => this.fourniture = resFournitures);
              }
              for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
                  this.detailsPiecesService.update(this.detailsPiecesIngredient[i]).subscribe((resIngredients) => this.ingerdient = resIngredients)
              }
              
          });
          
          this.router.navigate(['../../../']);
      }
  
  
      private onDetailsUpdateSuccess(result: Quotation) {
          
          
          for (let i = 0; i < this.detailsPiecesMO.length ; i++) {
              this.detailsPiecesMO[i].quotationId = result.id;
             this.detailsPiecesMO[i].id = null;
             this.detailsPiecesMO[i].isMo = true;
             this.subscribeToSaveResponseDetailsMo(
                 this.detailsPiecesService.create(this.detailsPiecesMO[i])
             );
         }
          for (let i = 0; i < this.detailsPieces.length; i++) {
          this.detailsPieces[i].quotationId = result.id;
          this.detailsPieces[i].id = null;
              this.subscribeToSaveResponsePiece(
                  this.detailsPiecesService.create(this.detailsPieces[i]));
          }
          for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
          this.detailsPiecesFourniture[i].quotationId = result.id;
          this.detailsPiecesFourniture[i].id = null;
              this.subscribeToSaveResponsePiece(
                  this.detailsPiecesService.create(this.detailsPiecesFourniture[i]));
          }
          for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
          this.detailsPiecesIngredient[i].quotationId = result.id;
          this.detailsPiecesIngredient[i].id = null;
              this.subscribeToSaveResponsePiece(
                  this.detailsPiecesService.create(this.detailsPiecesIngredient[i]));
          }
      }
  
  
  
  
      private onDetailsSaveSuccess(result: PrimaryQuotation) {
          
          
          for (let i = 0; i < this.detailsPiecesMO.length ; i++) {
              this.detailsPiecesMO[i].quotationId = result.id;
             this.detailsPiecesMO[i].id = null;
             this.detailsPiecesMO[i].isMo = true;
             this.subscribeToSaveResponseDetailsMo(
                 this.detailsPiecesService.create(this.detailsPiecesMO[i])
             );
         }
          for (let i = 0; i < this.detailsPieces.length; i++) {
          this.detailsPieces[i].quotationId = result.id;
          this.detailsPieces[i].id = null;
              this.subscribeToSaveResponsePiece(
                  this.detailsPiecesService.create(this.detailsPieces[i]));
          }
          for (let i = 0; i < this.detailsPiecesFourniture.length; i++) {
          this.detailsPiecesFourniture[i].quotationId = result.id;
          this.detailsPiecesFourniture[i].id = null;
              this.subscribeToSaveResponsePiece(
                  this.detailsPiecesService.create(this.detailsPiecesFourniture[i]));
          }
          for (let i = 0; i < this.detailsPiecesIngredient.length; i++) {
          this.detailsPiecesIngredient[i].quotationId = result.id;
          this.detailsPiecesIngredient[i].id = null;
              this.subscribeToSaveResponsePiece(
                  this.detailsPiecesService.create(this.detailsPiecesIngredient[i]));
          }
      }
  
      reparatorFacturation(devis: Devis) { // prestationpec
               this.devis.quotationStatusId=  10 ;
             this.devisService.update(devis).subscribe((resRectif) => devis = resRectif);
          this.router.navigate(['../../../']); // GOTO TODO LIST
  
      }
  
  
      accepterFacture(devis: Devis) {
          this.devis.quotationStatusId=  11 ;
          this.devisService.update(devis).subscribe((resRectif) => devis = resRectif);
          this.router.navigate(['../../../']); // GOTO TODO LIST
  
      }
  
     private subscribeToSaveResponseDetailsMo(result: Observable<DetailsMo>) {
          result.subscribe((res: DetailsMo) =>
              this.onSaveSuccessDetailMo(res), (res: Response) => this.onSaveError(res));
      }
  
      private subscribeToSaveResponsePiece(result: Observable<DetailsPieces>) {
          result.subscribe((res: DetailsPieces) =>
              this.onSaveSuccessPiece(res), (res: Response) => this.onSaveError(res));
      }
  
      private onSaveSuccessDetailMo(result: DetailsMo) {
          this.eventManager.broadcast({ name: 'refCompagnieListModification', content: 'OK'});
          this.isSaving = false;
      }
  
      private onSaveSuccessPiece(result: DetailsPieces) {
          this.eventManager.broadcast({ name: 'refCompagnieListModification', content: 'OK'});
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
  
      trackPrestationPECById(index: number, item: SinisterPec) {
          return item.id;
      }
  
      trackReparateurById(index: number, item: Reparateur) {
          return item.id;
      }
  
      trackVehicleUsageById(index: number, item: VehicleUsage) {
          return item.id;
      }
   
    
}

@Component({
    selector: 'jhi-devis-popup',
    template: ''
})
export class QuotationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devisPopupService: DevisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.devisPopupService
                    .open(QuotationDialogComponent as Component, params['id']);
            } else {
                this.devisPopupService
                    .open(QuotationDialogComponent as Component);
            }
        });
    }*

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
