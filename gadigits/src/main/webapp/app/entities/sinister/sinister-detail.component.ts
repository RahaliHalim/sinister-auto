import { SinisterPrestation } from './sinister-prestation.model';
import { Assure } from './../assure/assure.model';
import { VehiculeAssure } from './../vehicule-assure/vehicule-assure.model';
import { VehiculeAssureService } from './../vehicule-assure/vehicule-assure.service';
import { Component, OnInit, ViewChild, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';

import { Sinister } from './sinister.model';
import { SinisterService } from './sinister.service';
import { ContratAssurance } from './../contrat-assurance/contrat-assurance.model';
import { ContratAssuranceService } from './../contrat-assurance/contrat-assurance.service';
import { ResponseWrapper, Principal } from '../../shared';
import { Subscription } from 'rxjs/Rx';
import { Features, GaDatatable } from '../../constants/app.constants';
import { Subject } from 'rxjs';
import { AssureService } from '../assure';
import {  ReasonService, Reason } from '../reason';
import { SinisterPec, SinisterPecService, HistoryPopupDetailsPec, SinisterPecPopupService} from '../sinister-pec';
import { Observation } from '../observation';
import { GovernorateService, Governorate } from '../governorate';
import { DelegationService, Delegation } from '../delegation';
import { SinisterPrestationViewComponent } from './sinister-prestation-view.component';
import { SiniterPrestationTugPopupService } from './sinister-prestation-popup.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ViewSinisterPec } from '../view-sinister-pec';
import { DataTableDirective } from 'angular-datatables';


@Component({
    selector: 'jhi-sinister-detail',
    templateUrl: './sinister-detail.component.html'
})
export class SinisterDetailComponent implements OnInit,  OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    datatableElement: DataTableDirective;

    private ngbModalRef: NgbModalRef;
    dtTrigger: Subject<ViewSinisterPec> = new Subject();

    contratAssurance: ContratAssurance = new ContratAssurance();
    sinister: Sinister = new Sinister();

    insured: Assure = new Assure();

    currentAccount: any;
    eventSubscriber: Subscription;
    authorities: any[];
    prestations: any;
    vehiculeAssure: VehiculeAssure = new VehiculeAssure();
    userTypeId: any;
    id: Number;
    viewsinisterPec: ViewSinisterPec[] = [];

    dtOptions: any = {};
    annulPrest = false;
    reason: Reason = new Reason();
    reasons: Reason[]; 
    sinisterPec: SinisterPec = new SinisterPec();
    sinisterPecs :SinisterPec[];
    annulPrestMotif = false;
    refusPrest = false;
    refusPrestMotif = false;
    confirmAnnul = false;
    AnnulButton = false;
    confirmRefus = false;
    RefusButton = false;
    confirmation = false;
    Observ : string;
    assure : string;

    constructor(
        private alertService: JhiAlertService,
        private sinisterService: SinisterService,
        private sysGouvernoratService: GovernorateService,
        private sysVilleService: DelegationService,
        private contractService: ContratAssuranceService,
        private vehicleService: VehiculeAssureService,
        private insuredService: AssureService,
        public principal: Principal,
        private route: ActivatedRoute,
        private reasonService: ReasonService,
        private sinisterPecService: SinisterPecService,
        private siniterPrestationTugPopupService: SiniterPrestationTugPopupService,
        private sinisterPecPopupService: SinisterPecPopupService
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.initSinister();
    }

    /**
     * Init sinister
     */
    initSinister() {
        this.route.params.subscribe((params) => {
            if (params['id']) {
                const id = params['id'];

                this.sinisterService.find(id).subscribe((result: Sinister) => {
                    this.sinister = result;
                    
                    if (this.sinister.contractId !== null && this.sinister.contractId !== undefined) {
                        this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                            this.contratAssurance = contract;
   
                            this.vehicleService.find(this.sinister.vehicleId).subscribe((vehicle: VehiculeAssure) => {
                                this.contratAssurance.marqueLibelle = vehicle.marqueLibelle;
                                this.contratAssurance.modeleLibelle = vehicle.modeleLibelle;
                                this.contratAssurance.datePCirculation = vehicle.datePCirculation;
                                this.vehiculeAssure = vehicle;
                                this.sinisterService.findViewSinistersPecByVehicleRegistration(this.vehiculeAssure.id).subscribe((res) => {
                      
                                    this.viewsinisterPec = res;
                                    // calculation of number intervention remaining
            
                                    this.rerender();

            
                                });
                            this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((insured: Assure) => {
                                this.insured = insured;
                                if (this.insured.company) {
                                    this.insured.fullName = this.insured.raisonSociale;
                                } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                                    this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
                                } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                                    this.insured.fullName = this.insured.prenom;
                                } else {
                                    this.insured.fullName = this.insured.nom;
                                }

                                if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                                    this.findGouvernoratOfVille(this.insured.delegationId);
                                }

                                this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((assureRes: Assure) => {
                                    this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                    this.contratAssurance.adressePhysique = assureRes.adresse;
        
                                });
                            });
                        });
                        });
                        
                    }
                    this.dtTrigger.next();
               }, (error: ResponseWrapper) => this.onError(error.json));
            }
            
                if (params['annulPrest']) {
                    const id = params['annulPrest'];

                    this.annulPrest=true;

                    this.sinisterService.find(id).subscribe((result: Sinister) => {
                        this.sinister = result;
                       this.sinisterPec = this.sinister.sinisterPec;
                       

                       console.log("id du sinister "+ this.sinister.id);

                        if (this.sinister.contractId !== null && this.sinister.contractId !== undefined) {
                            this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                                this.contratAssurance = contract;
                               
                                this.vehicleService.find(this.sinister.vehicleId).subscribe((vehicle: VehiculeAssure) => {
                                    this.contratAssurance.marqueLibelle = vehicle.marqueLibelle;
                                    this.contratAssurance.modeleLibelle = vehicle.modeleLibelle;
                                    this.contratAssurance.datePCirculation = vehicle.datePCirculation;
                                    this.vehiculeAssure = vehicle;
                            
                                this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((insured: Assure) => {
                                    this.insured = insured;
                                    if (this.insured.company) {
                                        this.insured.fullName = this.insured.raisonSociale;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                                        this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                                        this.insured.fullName = this.insured.prenom;
                                    } else {
                                        this.insured.fullName = this.insured.nom;
                                    }
    
                                    if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                                        this.findGouvernoratOfVille(this.insured.delegationId);
                                    }
                                    this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((assureRes: Assure) => {
                                        this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                        this.contratAssurance.adressePhysique = assureRes.adresse;
            
                                    });
                                });
                            });
                        });
                        }
                        this.dtTrigger.next();
                   }, (error: ResponseWrapper) => this.onError(error.json));
                }

                if (params['refusPrest']) {
                    const id = params['refusPrest'];
                    this.refusPrest=true;
                    this.sinisterService.find(id).subscribe((result: Sinister) => {
                        this.sinister = result;

                        this.sinisterPec = this.sinister.sinisterPec;

                        console.log("id du sinister "+ this.sinister.id);

                        if (this.sinister.contractId !== null && this.sinister.contractId !== undefined) {
                            this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                                this.contratAssurance = contract;
                                
                                this.vehicleService.find(this.sinister.vehicleId).subscribe((vehicle: VehiculeAssure) => {
                                    this.contratAssurance.marqueLibelle = vehicle.marqueLibelle;
                                    this.contratAssurance.modeleLibelle = vehicle.modeleLibelle;
                                    this.contratAssurance.datePCirculation = vehicle.datePCirculation;
                                    this.vehiculeAssure = vehicle;
                                
                                this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((insured: Assure) => {
                                    this.insured = insured;
                                    if (this.insured.company) {
                                        this.insured.fullName = this.insured.raisonSociale;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                                        this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                                        this.insured.fullName = this.insured.prenom;
                                    } else {
                                        this.insured.fullName = this.insured.nom;
                                    }
    
                                    if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                                        this.findGouvernoratOfVille(this.insured.delegationId);
                                    }
                                    this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((assureRes: Assure) => {
                                        this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                        this.contratAssurance.adressePhysique = assureRes.adresse;
            
                                    });
                                });
                                });
                            });
                        }
                        this.dtTrigger.next();
                   }, (error: ResponseWrapper) => this.onError(error.json));
                }

                if (params['confirmAnnul']) {
                    const id = params['confirmAnnul'];
                    this.confirmAnnul=true;
                    this.sinisterService.find(id).subscribe((result: Sinister) => {
                        this.sinister = result;

                        this.sinisterPec = this.sinister.sinisterPec;

                        console.log("id du sinister "+ this.sinister.id);

                        if (this.sinister.contractId !== null && this.sinister.contractId !== undefined) {
                            this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                                this.contratAssurance = contract;
                              
                                this.vehicleService.find(this.sinister.vehicleId).subscribe((vehicle: VehiculeAssure) => {
                                    this.contratAssurance.marqueLibelle = vehicle.marqueLibelle;
                                    this.contratAssurance.modeleLibelle = vehicle.modeleLibelle;
                                    this.contratAssurance.datePCirculation = vehicle.datePCirculation;
                                    this.vehiculeAssure = vehicle;
                                
                                this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((insured: Assure) => {
                                    this.insured = insured;
                                    if (this.insured.company) {
                                        this.insured.fullName = this.insured.raisonSociale;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                                        this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                                        this.insured.fullName = this.insured.prenom;
                                    } else {
                                        this.insured.fullName = this.insured.nom;
                                    }
    
                                    if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                                        this.findGouvernoratOfVille(this.insured.delegationId);
                                    }
                                    this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((assureRes: Assure) => {
                                        this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                        this.contratAssurance.adressePhysique = assureRes.adresse;
            
                                    });
                                });
                            });
                            });
                        }
                        this.dtTrigger.next();
                   }, (error: ResponseWrapper) => this.onError(error.json));
                }

                if (params['confirmRefus']) {
                    const id = params['confirmRefus'];
                    this.confirmRefus=true;
                    this.sinisterService.find(id).subscribe((result: Sinister) => {
                        this.sinister = result;

                        this.sinisterPec = this.sinister.sinisterPec;

                        console.log("id du sinister "+ this.sinister.id);

                        if (this.sinister.contractId !== null && this.sinister.contractId !== undefined) {
                            this.contractService.find(this.sinister.contractId).subscribe((contract: ContratAssurance) => {
                                this.contratAssurance = contract;
                              
                                this.vehicleService.find(this.sinister.vehicleId).subscribe((vehicle: VehiculeAssure) => {
                                    this.contratAssurance.marqueLibelle = vehicle.marqueLibelle;
                                    this.contratAssurance.modeleLibelle = vehicle.modeleLibelle;
                                    this.contratAssurance.datePCirculation = vehicle.datePCirculation;
                                    this.vehiculeAssure = vehicle;
                              
                                this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((insured: Assure) => {
                                    this.insured = insured;
                                    if (this.insured.company) {
                                        this.insured.fullName = this.insured.raisonSociale;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined && this.insured.nom !== null && this.insured.nom !== undefined) {
                                        this.insured.fullName = this.insured.prenom + ' ' + this.insured.nom;
                                    } else if (this.insured.prenom !== null && this.insured.prenom !== undefined) {
                                        this.insured.fullName = this.insured.prenom;
                                    } else {
                                        this.insured.fullName = this.insured.nom;
                                    }
    
                                    if (this.insured.delegationId !== null && this.insured.delegationId !== undefined) {
                                        this.findGouvernoratOfVille(this.insured.delegationId);
                                    }
                                    this.insuredService.find(this.vehiculeAssure.insuredId).subscribe((assureRes: Assure) => {
                                        this.assure = assureRes.company ? assureRes.raisonSociale : (assureRes.prenom + ' ' + assureRes.nom);
                                        this.contratAssurance.adressePhysique = assureRes.adresse;
            
                                    });
                                });
                                });
                            });
                        }
                        this.dtTrigger.next();
                   }, (error: ResponseWrapper) => this.onError(error.json));
                }
                
        });
    }
    getMotif(){
        this.annulPrestMotif = true;
        this.reasonService.query().subscribe((res) => {
            this.reasons = res.json;});
    }
    getMotifRefus(){
        this.refusPrestMotif = true;
        this.reasonService.query().subscribe((res) => {
            this.reasons = res.json;});
    }
    save(){
    console.log("Annulation sinisterPec by sinisterId"+ this.sinisterPec.sinisterId);
        this.sinisterPec.stepId = 9 ;
        this.sinisterPec.decision = 'CANCELED' ;
       this.sinisterPec.motifsDecisionId = this.reason.id;
       console.log("reason id is "+ this.reason.id + this.sinisterPec.motifsDecisionId );
       console.log("sinisterPec id is = " +this.sinisterPec.id);
        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res)=>{this.sinisterPec = res;
            console.log("mission done"); 
            
        console.log("Annulation reason "+ this.reason.id, "sinPec motif "+ this.sinisterPec.motifsDecisionId ); 

   });
   }
   saveRefus(){
    console.log("Refus sinisterPec by sinisterId"+ this.sinisterPec.sinisterId);
    this.sinisterPec.stepId = 10 ;
    this.sinisterPec.decision = 'REFUSED' ;
    this.sinisterPec.motifsDecisionId = this.reason.id;
  
    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res)=>{this.sinisterPec = res;
        console.log("mission done");  
        console.log("Refus reason "+ this.reason.id, "sinPec motif "+ this.sinisterPec.motifsDecisionId );

});
}
confirmerAnnul(){
    this.AnnulButton = true;
    console.log("here is working");

}

confirmerRefus(){
    this.RefusButton = true;
    console.log("here is working");

}
Confirmer(){
    this.confirmRefus = false;
    this.sinisterPec.approvPec ='APPROVE';
    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res)=>{this.sinisterPec = res;
        console.log(" confirmée");  });
}

NonConfirmer(){
    this.confirmation = true;
    this.confirmRefus = false;
    this.sinisterPec.decision = null;
    this.sinisterPec.stepId = 3 ;
    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res)=>{this.sinisterPec = res;
        console.log("non confirmée");  });
}
Confirmation(){
    //this.sinisterPec.observation = this.Observ;
    //console.log("confirmée"  +this.sinisterPec.observation);
    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res)=>{this.sinisterPec = res;
        console.log("Annulation confirmée");  });
    
}
   private onError(error) {
        this.alertService.error(error, null);
    }

    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: Delegation) => {
            this.sysGouvernoratService.find(res.governorateId).subscribe((subRes: Governorate) => {
                this.insured.governorateLabel = subRes.label;
            });
        });
    }
    openPrestationView(prestationId: number) {
        this.ngbModalRef = this.siniterPrestationTugPopupService.openViewPrestationModal(SinisterPrestationViewComponent as Component, prestationId);
        this.ngbModalRef.result.then((result) => {
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            this.ngbModalRef = null;
        });
    }
    openPecView(pecId: number) {
        this.ngbModalRef = this.sinisterPecPopupService.openHistoryDetailsSinisterPec(HistoryPopupDetailsPec as Component, pecId);
        this.ngbModalRef.result.then((result) => {
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            this.ngbModalRef = null;
        });
    }
    ngAfterViewInit(): void {
        this.dtTrigger.next();
      
    }
    rerender(): void {
        this.datatableElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }
    ngOnDestroy(): void {
    }


}
