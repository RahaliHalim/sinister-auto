import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Loueur } from './loueur.model';
import { LoueurService } from './loueur.service';
import { RefModeReglementService } from '../ref-mode-reglement/ref-mode-reglement.service';
import { RefModeReglement } from '../ref-mode-reglement/ref-mode-reglement.model';
import { VisAVis } from '../vis-a-vis';
import { VehiculeLoueur, VehiculeLoueurService } from '../vehicule-loueur';

import { Delegation, DelegationService } from '../delegation';
import { Governorate, GovernorateService } from '../governorate';
import { ConfirmationDialogService, ResponseWrapper } from '../../shared';
import { GaDatatable, SettlementMode, Global } from '../../constants/app.constants';
import { VehicleBrandModel, VehicleBrandModelService } from '../vehicle-brand-model';
import { VehicleBrand } from '../vehicle-brand/vehicle-brand.model';
import { VehicleBrandService } from '../vehicle-brand/vehicle-brand.service';
import { RefMotif } from '../ref-motif/ref-motif.model';
import { RaisonAssistanceService } from '../raison-assistance';

@Component({
    selector: 'jhi-loueur-dialog',
    templateUrl: './loueur-dialog.component.html'
})
export class LoueurDialogComponent implements OnInit {

    loueur: Loueur = new Loueur();
    visAVis: VisAVis = new VisAVis();
    listContacts: VisAVis[] = [];
    listContact: VisAVis[] = [];
    isSaving: boolean;
    dateEffetConventionDp: any;
    dateFinConventionDp: any;
    refModeReglement: RefModeReglement;
    refModeReglements: RefModeReglement[];
    creervisavis = true;
    disabledForm = true;
    refMotifOption: RefMotif;
    disabledForm1 = true;
    ajout = true;
    edit = false;
    ajout1 = true;
    dtOptions: any = {};
    visAVisGeneral: VisAVis = new VisAVis();
    ajoutContact = false;
    disabled = true;
    sysgouvernorats: Governorate[];
    sysGouvernorat: Governorate;
    sysvilles: Delegation[];
    sysVille: Delegation;
    listVehicules: VehiculeLoueur[] = [];
    listVehicule: VehiculeLoueur[] = [];
    vehicules: VehiculeLoueur[] = [];
    vehicule: VehiculeLoueur = new VehiculeLoueur();
    vehiculeModif: VehiculeLoueur = new VehiculeLoueur();
    vehicleBrandModels: VehicleBrandModel[];
    vehicleBrands: VehicleBrand[];
    refMarque: VehicleBrand;
    refModel: VehicleBrandModel;
    errorMessage = '';
    ajoutVehicule = false;
    selectedItems = [];
    list1: number[] = [];
    disabledMotif = false;
    disabledReclamation = true;
    refMotif = new RefMotif();
    motifs: RefMotif[];
    disabledcontact = true;
    mandatoryRibFlag = false;
    textPattern = Global.textPattern;


    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private loueurService: LoueurService,
        private vehiculeLoueurService: VehiculeLoueurService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private refModeReglementService: RefModeReglementService,
        private sysVilleService: DelegationService,
        private sysGouvernoratService: GovernorateService,
        private confirmationDialogService: ConfirmationDialogService,
        private vehicleBrandModelService: VehicleBrandModelService,
        private vehicleBrandService: VehicleBrandService,
        private raisonAssistanceService: RaisonAssistanceService,
        private router: Router

    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        this.visAVisGeneral.isGerant = true;
        this.disabledForm = false;
        this.disabledForm1 = false;
        this.refModeReglementService.query().subscribe((res: ResponseWrapper) => { this.refModeReglements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysVilleService.query().subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleBrandService.query().subscribe((res: ResponseWrapper) => { this.vehicleBrands = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.raisonAssistanceService.findMotifsByOperation(3).subscribe((subRes: ResponseWrapper) => {
            this.motifs = subRes.json;
        })
        this.vehicleBrandModelService.query().subscribe((subRes: ResponseWrapper) => {
            this.vehicleBrandModels = subRes.json;
        
        });
        
        this.initLoueur();
    }


    private onError(error) {
        this.alertService.error(error.message, null, null);

    }

    addForm() {
        this.disabledForm = true;
    }

    addForm1() {
        this.disabledForm1 = true
        this.ajout1 = true;
        this.visAVis = new VisAVis();
    }
    addContact() {
        if (this.visAVis.id) {
            this.visAVis = new VisAVis();
        } else {

            this.listContacts.push(this.visAVis);
        }
        this.visAVis = new VisAVis();
        this.ajoutContact = true;
        this.disabledForm1 = false;
    }

    modifContact() {
        this.visAVis = new VisAVis();
        this.ajoutContact = true;
        this.disabledForm1 = false;
    }

    editContact(ContactEdit) {
        this.disabledForm1 = true;
        this.visAVis = ContactEdit;
        this.ajout1 = false;

    }

    deleteContact(contactDelete) {
        this.listContacts.forEach((item, index) => {
            if (item === contactDelete) this.listContacts.splice(index, 1);
        });
    }

    listVillesByGouvernoratLieu(id) {
        if (id) this.disabled = false;
        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernorat = subRes;
            this.sysVilleService.findByGovernorate(this.sysGouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.sysvilles = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.loueur.delegationId = subRes1[0].id;
                }

            });
        });
    }
    listModelByMarque(id) {
        if (id !== null && id !== undefined) {
            this.vehicleBrandService.find(id).subscribe((subRes: VehicleBrand) => {
                this.refMarque = subRes;
                this.vehicule.marqueLabel = this.refMarque.label;
                this.vehicleBrandModelService.findByBrand(this.refMarque.id).subscribe((subRes1: ResponseWrapper) => {
                    this.vehicleBrandModels = subRes1.json;
                    if (this.vehicleBrandModels && this.vehicleBrandModels.length > 0) {
                        this.vehicule.modeleId = this.vehicleBrandModels[0].id;
                        this.vehicule.modeleLabel = this.vehicleBrandModels[0].label;
                    }
                });
            });
        }
    }

    trimRegistrationNumber() {
        const str = this.vehicule.immatriculation.replace(/\s/g, "");
        this.vehicule.immatriculation = str.toUpperCase();
    }

    addVehicule() {
        let isDuplicate = false;
        this.trimRegistrationNumber();
        if (this.listVehicules && this.listVehicules.length > 0) {
            for (let k = 0; k < this.listVehicules.length; k++) {
                if (this.vehicule.immatriculation === this.listVehicules[k].immatriculation) {
                    isDuplicate = true;
                    break;
                }
            }
        }

        if (isDuplicate) {
            this.errorMessage = 'Immatriculation ' + this.vehicule.immatriculation + ' existe déjà';
        } else {
            if (this.vehicule.immatriculation !== null && this.vehicule.immatriculation !== undefined && this.vehicule.immatriculation !== '') {
                this.vehiculeLoueurService.findByImmatriculation(this.vehicule.immatriculation).subscribe((subRes: VehiculeLoueur) => {
                    if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.vehicule.id) {
                        isDuplicate = true;
                        this.errorMessage = 'Immatriculation ' + this.vehicule.immatriculation + ' existe déjà';
                    } else {
                        isDuplicate = false;
                        if (this.vehicule.id) {
                            this.vehicule = new VehiculeLoueur();
                        } else {
                            this.listVehicules.push(this.vehicule);
                            // this.camion.serviceTypesStr = '';
                            // this.camion.serviceTypes.forEach((serviceType) => {
                            //     this.camion.serviceTypesStr += serviceType.nom + ', ';
                            // });
                            // this.camion.serviceTypesStr = this.camion.serviceTypesStr.substring(0, this.camion.serviceTypesStr.length - 2);

                            // this.listCamions.push(this.camion);
                        }
                        this.vehicule = new VehiculeLoueur();
                        this.ajoutVehicule = true;
                        this.disabledForm = false;
                        this.selectedItems = [];
                        this.errorMessage = '';
                    }
                });
            }
        }
    }
    ModifVehicule() {
        let isDuplicate = false;
        this.trimRegistrationNumber();
        let nmbrImmatriculation = 0;
        if (this.listVehicules && this.listVehicules.length > 0) {
            for (let k = 0; k < this.listVehicules.length; k++) {
                if  (this.vehicule.immatriculation === this.listVehicules[k].immatriculation ) {
                    console.log ("teeeeeeeeeest1");
                    nmbrImmatriculation++;
                    if(nmbrImmatriculation == 2 ){
                        isDuplicate = true;
                        break;
                    }
                }
            }
        }

        if (isDuplicate == true && (this.vehicule.id == null && this.vehicule.id == undefined)) {
            
            this.errorMessage = 'Immatriculation ' + this.vehicule.immatriculation + ' existe déjà';
        } 
        else {
            if (this.vehicule.immatriculation !== null && this.vehicule.immatriculation !== undefined && this.vehicule.immatriculation !== '') {
                this.vehiculeLoueurService.findByImmatriculation(this.vehicule.immatriculation).subscribe((subRes: VehiculeLoueur) => {
                    if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.vehicule.id) {
                        isDuplicate = true;
                        this.errorMessage = 'Immatriculation ' + this.vehicule.immatriculation + ' existe déjà';
                    } else {
                        isDuplicate = false;
                        // this.listVehicules.forEach((item, index) => {
                        //     if (item === this.vehiculeModif) this.listVehicules.splice(index, 1);
                        // });


                        //this.listVehicules.push(this.vehicule);
                        this.vehicule = new VehiculeLoueur();
                        this.ajout = true;
                        this.edit = false;
                        this.selectedItems = [];
                        this.disabledForm = false;
                        this.errorMessage = '';
                    }
                });
            }
        }
    }

    deleteVehicule(vehiculeDelete) {
        this.listVehicules.forEach((item, index) => {
            if (item === vehiculeDelete) {
                this.list1.push(item.id);
                this.listVehicules.splice(index, 1);
            }
        });
        this.vehicule = new VehiculeLoueur();
        this.ajout = true;
        this.edit = false;
        this.selectedItems = [];
        this.disabledForm = false;
        this.errorMessage = '';
    }


    editVehicule(vehiculeEdit: VehiculeLoueur) {
        this.disabledForm = true;
        this.vehicule = vehiculeEdit;
        this.edit = true;
        this.ajout = false;
        this.vehiculeModif = vehiculeEdit;
    }


    etatRib(id) {
        if (SettlementMode.VIREMENT === id || SettlementMode.TRAITE === id) {
            this.mandatoryRibFlag = true;
        } else {
            this.mandatoryRibFlag = false;
        }
    }

    calculatePrixJour(totalPrix: number) {
        this.vehicule.prixJour = null;
        if (totalPrix != undefined && totalPrix != null) { this.vehicule.prixJour = totalPrix / 30; }
    }


    handleClickMotif() {
        this.disabledMotif = true;
        this.disabledReclamation = false;
    }

    handleClickReclamation() {
        this.disabledMotif = false;
        this.disabledReclamation = true;
    }

    initLoueur() {
        this.route.params.subscribe((params) => {
            if (params['idLoueur']) {
                // this.vehiculeLoueurService.findByLoueur(params['id']).subscribe((res: ResponseWrapper) => {
                //     this.listVehicules = res.json

                // });
                this.disabledcontact = false;
                this.loueurService.find(params['idLoueur']).subscribe((subRes: Loueur) => {
                    this.loueur = subRes;

                    
                    const dateDebut = new Date(this.loueur.dateEffetConvention);
                    const dateFin = new Date(this.loueur.dateFinConvention);
                    if (this.loueur.dateEffetConvention) {
                        this.loueur.dateEffetConvention = {
                            year: dateDebut.getFullYear(),
                            month: dateDebut.getMonth() + 1,
                            day: dateDebut.getDate()
                        };
                    }
                    if (this.loueur.dateFinConvention) {
                        this.loueur.dateFinConvention = {
                            year: dateFin.getFullYear(),
                            month: dateFin.getMonth() + 1,
                            day: dateFin.getDate()
                        };
                    }

                  if (this.loueur.id != null && this.loueur.id !== undefined && this.loueur.vehicules !== null) {
                    this.listVehicules = this.loueur.vehicules;
                  }

                    if (this.loueur.id != null && this.loueur.id !== undefined && this.loueur.visAViss !== null) {
                        this.listContact = this.loueur.visAViss;
                        this.listContact.forEach((element) => {
                            if (element.isGerant !== true) {
                                this.listContacts.push(element);
                            }
                            else this.visAVisGeneral = element;
                        });
                    }
                    this.loueur.blocage = subRes.blocage;

                }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            }
        });
    }
    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de sauvegarder ce Loueur ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.isSaving = true;
                if (this.loueur.id !== undefined) {

                    this.loueur.telephone = this.visAVisGeneral.premTelephone;
                    this.listContacts.push(this.visAVisGeneral);
                    this.loueur.visAViss = this.listContacts;
                    this.loueur.vehicules = this.listVehicules;
                    this.loueurService.update(this.loueur).subscribe((res: Loueur) => {
                        this.loueur = res;
                        this.router.navigate(['../../loueur']);

                    });

                } else {

                    this.loueur.telephone = this.visAVisGeneral.premTelephone;
                    this.listContacts.push(this.visAVisGeneral);
                    this.loueur.visAViss = this.listContacts;
                    this.loueur.vehicules = this.listVehicules;
                    this.loueurService.create(this.loueur).subscribe((res: Loueur) => {
                        this.loueur = res;
                        this.router.navigate(['../../loueur']);

                    });

                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

}
