import { Component, OnInit } from '@angular/core';

import { Principal, ResponseWrapper } from '../../shared';
import { DroolsParamsService } from './drools-params.service';
import { ParamsValueDrools, TypeParamDrools } from './drools-params.model';
import { ParamsDrools } from './drools.model';
import { ClientService } from '../client/client.service';
import { DroolsService } from './drools.service';

@Component({
    selector: 'drools',
    templateUrl: './drools-params.component.html'
})
export class DroolsParamsComponent implements OnInit {
    
    paramsValueDrools: ParamsValueDrools = new ParamsValueDrools();
    paramsDrools: ParamsDrools = new ParamsDrools();

    modeId: any;
    garantieId: any;
    clients: any[] = [];
    listGarantiesForModeGestion: any[] = [];

    montantavancesurfacture: any;
    plafondAvanceFacture: any;
    typeavancefacture: any;

    montantarajouter: any;
    montantarajouterbis: any;
    plafondTrttc: any;
    typeMontantARajouter: any;
    typeMontantARajouterbis: any;

    typeRachat: any;
    montantrachat: any;
    minmontantrachat: any;

    isVetuste: any;
    isFranchise: any;
    participations: any[] = [];
    selectedParticipation: any[] = [];
    rachatGarantie: any;
    fraishida: any;
    avancefacture: any;
    franchise: any;
    franchiseValue: any;
    partresponsabilite: any;
    regleproportionnel: any;
    depassplafond: any;
    
    

    constructor(
       
        private droolsParamsService: DroolsParamsService,
        private droolsService: DroolsService,
        
        private clientService: ClientService

    ) {
    }

    ngOnInit() {
        
        this.droolsParamsService.query()
                    .subscribe((res) => { this.clients = res.json; });

                     this.participations = ["Droit de Timbre", "Vetuste", "Avance sur Facture", "Franchise","Frais de dossier HIDA"
                                            ,"Régles Proportionnel", "Dépassement Plafond", "Part de Résponsabilité", "Rachat de la garantie"];

    }

    save(){

        if(this.modeId == 1 || this.modeId == 2){

            if(this.typeRachat == "Pourcentage"){

                this.paramsValueDrools.paramDroolsId = 1;
                this.paramsValueDrools.typeParamDrools = "Pourcentage";
                this.paramsValueDrools.paramValue = this.montantrachat;
                this.paramsValueDrools.minValue = this.minmontantrachat;

                this.droolsParamsService.create(this.paramsValueDrools).subscribe(((res) => {
                    
                }));

            }else if(this.typeRachat == "Montant"){

               this.paramsValueDrools.paramDroolsId = 1;
               this.paramsValueDrools.typeParamDrools = "Montant";
               this.paramsValueDrools.fixValue = this.montantrachat;

               this.droolsParamsService.create(this.paramsValueDrools).subscribe(((res) => {
                    
            }));
            }
        }
        if(this.modeId == 2){
          
            this.paramsDrools.dafaultValue = this.plafondTrttc;
            this.paramsDrools.id = 5;
            this.droolsService.update(this.paramsDrools).subscribe(((res) => {
                    
            }));
           
           // Si > plafond

            if(this.typeMontantARajouter == "Pourcentage"){

                this.paramsValueDrools.paramDroolsId = 5;
                this.paramsValueDrools.paramParentId = 2;
                this.paramsValueDrools.typeParamDrools = "Pourcentage";
                this.paramsValueDrools.paramValue = this.montantarajouter;

                this.droolsParamsService.create(this.paramsValueDrools).subscribe(((res) => {
                    
                }));
            }
            if(this.typeMontantARajouter == "Montant"){

                this.paramsValueDrools.paramDroolsId = 5;
                this.paramsValueDrools.paramParentId = 2;
                this.paramsValueDrools.typeParamDrools = "Montant";
                this.paramsValueDrools.fixValue = this.montantarajouter;

                this.droolsParamsService.create(this.paramsValueDrools).subscribe(((res) => {
                    
                }));
            }

            // Si < plafond

            if(this.typeMontantARajouterbis == "Pourcentage"){

                this.paramsValueDrools.paramDroolsId = 6;
                this.paramsValueDrools.paramParentId = 2;
                this.paramsValueDrools.typeParamDrools = "Pourcentage";
                this.paramsValueDrools.paramValue = this.montantarajouterbis;

                this.droolsParamsService.create(this.paramsValueDrools).subscribe(((res) => {
                    
                }));
            }
            if(this.typeMontantARajouterbis == "Montant"){

                this.paramsValueDrools.paramDroolsId = 6;
                this.paramsValueDrools.paramParentId = 2;
                this.paramsValueDrools.typeParamDrools = "Montant";
                this.paramsValueDrools.fixValue = this.montantarajouterbis;

                this.droolsParamsService.create(this.paramsValueDrools).subscribe(((res) => {
                    
                }));
            }
            
        }

        
    }

    listeGarantiesByModeId(modeId){

        this.clientService.findGarantiesByClientAndMode(this.paramsValueDrools.compagnieId, modeId).subscribe((res: ResponseWrapper) => {
            this.listGarantiesForModeGestion = res.json;   
            });

    }

    fieldsChange(evt:any, participation){
        
       
            if(participation == "Rachat de la garantie"){
                this.rachatGarantie = evt.target.checked;
            }
            if(participation == "Frais de dossier HIDA"){
                this.fraishida = evt.target.checked;
            }
            if(participation == "Avance sur Facture"){
                this.avancefacture = evt.target.checked;
            }
            if(participation == "Franchise"){
                this.franchise = evt.target.checked;
            }
            if(participation == "Part de Résponsabilité"){
                this.partresponsabilite = evt.target.checked;
            }
            if(participation == "Régles Proportionnel"){
                this.regleproportionnel = evt.target.checked;
            }
            if(participation == "Dépassement Plafond"){
                this.depassplafond = evt.target.checked;
            }
          
    }

   
}
