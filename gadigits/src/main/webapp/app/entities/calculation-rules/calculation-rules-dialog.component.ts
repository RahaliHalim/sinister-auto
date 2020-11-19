import { Component, OnInit} from "@angular/core";
import { Client } from "../client/client.model";
import { CalculationRules } from "./calculation-rules.model";
import { RefModeGestion } from "../ref-mode-gestion/ref-mode-gestion.model";
import { CalculationRulesService } from "./calculation-rules.service";
import { Rules } from "../rules/rules.model";
import { RulesService } from "../rules/rules.service";
import { CalculationRulesConst } from "../../constants/app.constants";
import { Guarantee } from "../../constants/mode-gestion";
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogService } from "../../shared/confirmation-dialog/confirmation-dialog.service";
import { ConventionService } from "../convention/convention.service";
import { Convention } from "../convention";
import { RefPack } from "../ref-pack/ref-pack.model";
import { PartnerService } from "../partner/partner.service";

import { JhiAlertService } from "ng-jhipster/src/service/alert.service";


@Component({
    selector: "jhi-calculation-rules-dialog",
    templateUrl: "./calculation-rules-dialog.component.html"
})
export class CalculationRulesDialogComponent implements OnInit {

    clients: Client[];
    client: Client = new Client();
    convention: Convention;
    listConvention: Convention[];
    packs: RefPack[];
    partnerrule: CalculationRules = new CalculationRules();
    historypartnerrule: CalculationRules;

    partnerruleexist: CalculationRules;

    listtva: Rules[];
    timbrerules: Rules[];
    vetustestva: Rules[];
    partsresponstva: Rules[];
    depassplafondtva: Rules[];
    regleproportva: Rules[];
    vetustesnontva: Rules[];
    partsresponsnontva: Rules[];
    depassplafondnontva: Rules[];
    reglepropornontva: Rules[];
    engihctva: Rules[];
    engihcntva: Rules[];
    engtvainf: Rules[];
    engtvaequ: Rules[];
    engntvainf: Rules[];
    engntvaequ: Rules[];
    engtdevisinf: Rules[];
    engtdevissup: Rules[];
    engndevisinf: Rules[];
    engndevissup: Rules[];
    reglesfraishida: Rules[];
    reglesavancefactures: Rules[];
    reglesfranchise: Rules[];
    guaranties: RefModeGestion[] = [];
    fieldVisibility: any = {};
    fraishida: any;
    partnerModeExist =  false;

    constructor( 
        private partnerService: PartnerService,
        private calculationRulesService: CalculationRulesService,
        private rulesService: RulesService,
        private confirmationDialogService: ConfirmationDialogService,
        private conventionService: ConventionService,

        private alertService: JhiAlertService,

        private router: Router,
        private route: ActivatedRoute,
    )
    {}
    /**
     * Initialisation
     */
    ngOnInit(): void {
        console.log("init dialog-------------------------");
      this.fieldVisibility = {
        IDA: false,
        CONNEXE: false,
        HIDAINF: false,
        HIDASUP: false,
        DOMCOLLIS: false,
        DOMVEHIC: false,
        BRIGLACE: false,
        VOLPART: false,
        INCPART: false,
        TIERCE: false,
        TIERCECOLLIS: false,
        FRANCHIS_PERCENTAGE: false,
        FRANCHIS_MIN: false,
        FRANCHIS_FIXED: false  
       }
       this.route.params.subscribe((params) => { //Mode Edit
            if (params['id'] != null) {
                this.calculationRulesService.find(params['id']).subscribe((res) =>{
                    this.partnerrule = res;
                    this.selectedGuarantee(this.partnerrule.refModeGestionId);// init ENG GA rules
                    this.selectedFranchise(this.partnerrule.insuranceDeductibleRuleId); // init Franchise rule
                    if (this.partnerrule.startDateConvention) {
                        const date = new Date(this.partnerrule.startDateConvention);
                        this.partnerrule.startDateConvention = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    }
                    if(this.partnerrule.refPartnerId != null && this.partnerrule.refPartnerId != undefined){        
                            this.getGuarantee(this.partnerrule.refPartnerId); //init mode de gestion
                    }
                })
            }
        })
        this.partnerService.findAllCompanies().subscribe((res) => {this.clients = res.json;}); //Liste des compagnies
        /**
       * load of rules list according to type (code)
       */ 
        this.rulesService.findByCode(CalculationRulesConst.TVA).subscribe((res) => {this.listtva = res;});
        this.rulesService.findByCode(CalculationRulesConst.DTMB).subscribe((res) => {this.timbrerules = res;});
        this.rulesService.findByCode(CalculationRulesConst.VETUSTETVA).subscribe((res) => {this.vetustestva = res;});
        this.rulesService.findByCode(CalculationRulesConst.PARTRESPTVA).subscribe((res) => {this.partsresponstva = res;});
        this.rulesService.findByCode(CalculationRulesConst.DEPASSPLAFONDTVA).subscribe((res) => {this.depassplafondtva = res;});
        this.rulesService.findByCode(CalculationRulesConst.REGPROPTVA).subscribe((res) => {this.regleproportva = res;});
        this.rulesService.findByCode(CalculationRulesConst.VETUSTENONTVA).subscribe((res) => {this.vetustesnontva = res;});
        this.rulesService.findByCode(CalculationRulesConst.PARTRESPNONTVA).subscribe((res) => {this.partsresponsnontva = res;});
        this.rulesService.findByCode(CalculationRulesConst.DEPASSPLAFONDNONTVA).subscribe((res) => {this.depassplafondnontva = res;});
        this.rulesService.findByCode(CalculationRulesConst.REGPROPNONTVA).subscribe((res) => {this.reglepropornontva = res;});
        // Régles des Engagement GA
        this.rulesService.findByCode(CalculationRulesConst.ENGIHCTVA).subscribe((res) => {this.engihctva = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGIHCNTVA).subscribe((res) => {this.engihcntva = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGTVAINF).subscribe((res) => {this.engtvainf = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGTVAEQU).subscribe((res) => {this.engtvaequ = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGNTVAINF).subscribe((res) => {this.engntvainf = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGNTVAEQU).subscribe((res) => {this.engntvaequ = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGTDEVISINF).subscribe((res) => {this.engtdevisinf = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGTDEVISSUP).subscribe((res) => {this.engtdevissup = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGNDEVISINF).subscribe((res) => {this.engndevisinf = res;});
        this.rulesService.findByCode(CalculationRulesConst.ENGNDEVISSUP).subscribe((res) => {this.engndevissup = res;});

        this.rulesService.findByCode(CalculationRulesConst.REGLESFRAISHIDA).subscribe((res) => {this.reglesfraishida = res;});//Régles frais HIDA
        this.rulesService.findByCode(CalculationRulesConst.REGLESAVANCEFACTURE).subscribe((res) => {this.reglesavancefactures = res;});//Régles avance sur facture 
        this.rulesService.findByCode(CalculationRulesConst.REGLESFRANCHISE).subscribe((res) => {this.reglesfranchise = res;}); //Régles franchise
    }
    /**
     * Get guarantie by partner
     * @param client 
     */
    getGuarantee(clientId: number){
      this.guaranties.splice(0,this.guaranties.length);
      
      this.conventionService.query().subscribe((res)=>{
        this.listConvention = res.json;
        for (let i = 0; i < this.listConvention.length; i++) {
            if(this.listConvention[i].clientId == clientId ){
                for (let j = 0; j < this.listConvention[i].packs.length; j++){
                    for (let l = 0; l < this.listConvention[i].packs[j].modeGestions.length; l++){
                        this.guaranties.push(this.listConvention[i].packs[j].modeGestions[l]);
                        var cache = {};
                            this.guaranties = this.guaranties.filter(function (elem) {
                                return cache[elem.id] ? 0 : cache[elem.id] = 1;
                            })
                    }                  
                }
            }  
        }
    });
    }
    /**
     * Visibilité ENG GA rules By guarantee
     * @param modeId 
     */


    selectedWhenEditGuarantee(modeId: number){
        this.partnerModeExist = false;
      if(this.partnerrule.id == null && this.partnerrule.id == undefined && this.partnerrule.refPartnerId != null && modeId != null){
          console.log("test if exist partner mode");
            this.calculationRulesService.findByPartnerAndMode(this.partnerrule.refPartnerId,modeId).subscribe((res) =>{
                this.partnerruleexist = res;
                if(this.partnerruleexist.id != null && this.partnerruleexist.id != undefined){
                    this.partnerModeExist = true;
                    this.alertService.error("auxiliumApp.partnerrules.detail.rulespartnermode",null,null);
                }else{
                    this.partnerModeExist = false;
                }
            });
          }
          if(this.partnerrule.id != null && this.partnerrule.id != undefined){
            this.partnerModeExist = false;
          }
    }

    selectedGuarantee(modeId: number){
       
      this.fieldVisibility.IDA = false;
      this.fieldVisibility.CONNEXE = false;
      this.fieldVisibility.HIDAINF = false;
      this.fieldVisibility.HIDASUP = false;
      this.fieldVisibility.DOMCOLLIS = false;
      this.fieldVisibility.DOMVEHIC = false;
      this.fieldVisibility.BRIGLACE = false;
      this.fieldVisibility.VOLPART = false;
      this.fieldVisibility.INCPART = false; 
      this.fieldVisibility.TIERCE = false;
      this.fieldVisibility.TIERCECOLLIS= false;

      
     
      switch (modeId) {
        case Guarantee.MODE_GESION_IDA:
            this.fieldVisibility.IDA = true;
            break;
        case Guarantee.MODE_GESION_CONNEXE:
            this.fieldVisibility.CONNEXE = true;
            break;
        case Guarantee.MODE_GESION_INF_HIDA:
            this.fieldVisibility.HIDAINF = true;
            break;
        case Guarantee.MODE_GESION_SUP_HIDA:
            this.fieldVisibility.HIDASUP = true;
            break;    
        case Guarantee.MODE_GESION_DOM_COLLIS:
            this.fieldVisibility.DOMCOLLIS = true;
            break;
        case Guarantee.MODE_GESION_DOM_VEHIC:
            this.fieldVisibility.DOMVEHIC = true;
            break;
        case Guarantee.MODE_GESION_BRI_GLACE:
            this.fieldVisibility.BRIGLACE = true; 
            break; 
        case Guarantee.MODE_GESION_VOL_PART:
            this.fieldVisibility.VOLPART = true;
            break;
        case Guarantee.MODE_GESION_INC_PART:
            this.fieldVisibility.INCPART = true;  
            break;
        case Guarantee.MODE_GESION_TIERCE:
            this.fieldVisibility.TIERCE = true;
            break;
        case Guarantee.MODE_GESION_TIERCE_COLLIS:
            this.fieldVisibility.TIERCECOLLIS= true;
            break;
      }

    }



    selectedFranchise(franchiseId: number){
     
        this.fieldVisibility.FRANCHIS_PERCENTAGE = false;
        this.fieldVisibility.FRANCHIS_MIN = false;
        this.fieldVisibility.FRANCHIS_FIXED = false;

        switch (franchiseId) {
        case 4:
        this.fieldVisibility.FRANCHIS_PERCENTAGE = true;
        break;
        case 5:
        this.fieldVisibility.FRANCHIS_PERCENTAGE = true;
        break;
        case 11:
        this.fieldVisibility.FRANCHIS_PERCENTAGE = true;
        this.fieldVisibility.FRANCHIS_MIN = true;
        break;
        case 12:
            this.fieldVisibility.FRANCHIS_FIXED = true;
        break;
        case 14:
            this.fieldVisibility.FRANCHIS_PERCENTAGE = true;
            this.fieldVisibility.FRANCHIS_FIXED = true;
        break;
      }
    }
    /**
     * Save Partner rule
     */
save(){

        // Confirmation to save or update
this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
 .then(confirmed => {
  if (confirmed) {
        if(this.partnerrule.hidaServiceCharges){
          this.partnerrule.hidaServiceLowerId = 6;
          this.partnerrule.hidaServiceBetweenId = 7;
          this.partnerrule.hidaServiceHigherId = 8;
        }
        // Reset if deselected each participation costs
    if(this.partnerrule.id != null && this.partnerrule.id != undefined){  
        if(!this.partnerrule.stampDuty){
            this.partnerrule.stampDutySubjectVatId = null;
            this.partnerrule.stampDutyNotSubjectVatId = null;
        }else{
            this.partnerrule.stampDutySubjectVatId = 52;
            this.partnerrule.stampDutyNotSubjectVatId = 52;
        }
        if(!this.partnerrule.vetuste){
            this.partnerrule.vetusteSubjectVatId = null;
            this.partnerrule.vetusteNotSubjectVatId = null;
        }
        if(!this.partnerrule.advanceOnInvoice){
            this.partnerrule.advanceInvoiceId = null;
            this.partnerrule.advanceInvoiceHigher = null;
        }
        if(!this.partnerrule.insuranceDeductible){
            this.partnerrule.insuranceDeductibleRuleId = null;
            this.partnerrule.insuranceDeductiblePercentage = null;
            this.partnerrule.insuranceDeductibleMin = null;
            this.partnerrule.insuranceDeductibleFixed = null;
        }
        if(!this.partnerrule.hidaServiceCharges){
            this.partnerrule.hidaServiceChargesLower = null;
            this.partnerrule.hidaServiceChargesBetween = null;
            this.partnerrule.hidaServiceChargesHigher = null;
        }
        if(!this.partnerrule.proportionalRule){
            this.partnerrule.proportionalRuleSubjectVatId = null;
            this.partnerrule.proportionalRuleNotSubjectVatId = null;
        }
        if(!this.partnerrule.shareResponsibility){
            this.partnerrule.shareResponsabilitySubjectvatId = null;
            this.partnerrule.shareResponsabilityNotSubjectVatId = null;
        }
        if(!this.partnerrule.ceilingOverflow){
            this.partnerrule.ceilingOverflowSubjectVatId = null;
            this.partnerrule.ceilingOverflowNotSubjectVatId = null;
         }
         // initialisation des ENGAGEMENT GA lors de changement de mode gestion (en mode edit)
        if(this.fieldVisibility.IDA == true || this.fieldVisibility.CONNEXE == true || this.fieldVisibility.HIDAINF == true || this.fieldVisibility.HIDASUP == true){ 
            
            this.partnerrule.commitmentGaNdevisInfId = null;// dommagcollis tiercecollis brisglace
            this.partnerrule.commitmentGaNdevisSupId = null; // dommagcollis tiercecollis brisglace
            this.partnerrule.commitmentGaNvatEquId = null; // domvehic tierce volpartiel incpartiel
            this.partnerrule.commitmentGaNvatInfId = null; // domvehic tierce volpartiel incpartiel
            this.partnerrule.commitmentGaTdevisInfId = null; // dommagcollis tiercecollis brisglace
            this.partnerrule.commitmentGaTdevisSupId = null; // dommagcollis tiercecollis brisglace
            this.partnerrule.commitmentGaVatEquId = null; // domvehic tierce volpartiel incpartiel
            this.partnerrule.commitmentGaVatInfId = null;// domvehic tierce volpartiel incpartiel            
        }
        if((this.fieldVisibility.DOMCOLLIS == true && this.partnerrule.refPartnerId != 3) || this.fieldVisibility.TIERCECOLLIS == true || this.fieldVisibility.BRIGLACE == true ){

            this.partnerrule.commitmentGaIhcNvatId = null;//ida connexe hida
            this.partnerrule.commitmentGaIhcVatId = null; //ida connexe hida
            this.partnerrule.commitmentGaNvatEquId = null; 
            this.partnerrule.commitmentGaNvatInfId = null; 
            this.partnerrule.commitmentGaVatEquId = null; 
            this.partnerrule.commitmentGaVatInfId = null;
        }
        if(this.fieldVisibility.DOMCOLLIS == true && this.partnerrule.refPartnerId == 3){
            this.partnerrule.commitmentGaIhcNvatId = null;//ida connexe hida
            this.partnerrule.commitmentGaIhcVatId = null; //ida connexe hida
        }
        if(this.fieldVisibility.DOMVEHIC == true || this.fieldVisibility.TIERCE == true || this.fieldVisibility.VOLPART == true || this.fieldVisibility.INCPART == true){

            this.partnerrule.commitmentGaIhcNvatId = null;
            this.partnerrule.commitmentGaIhcVatId = null; 
            this.partnerrule.commitmentGaNdevisInfId = null;
            this.partnerrule.commitmentGaNdevisSupId = null;
            this.partnerrule.commitmentGaTdevisInfId = null;
            this.partnerrule.commitmentGaTdevisSupId = null;
        }

    }

        // Save or update partenr rules
        this.calculationRulesService.create(this.partnerrule).subscribe((res) =>{
            this.partnerrule = res;
            this.calculationRulesService.createHistory(this.partnerrule).subscribe((res) =>{

                //this.historypartnerrule = res;
            
            });

            this.router.navigate(['/calculation-rules']);
            });


       
  }
 })
  .catch(() => {
            console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)');
   });
}
}