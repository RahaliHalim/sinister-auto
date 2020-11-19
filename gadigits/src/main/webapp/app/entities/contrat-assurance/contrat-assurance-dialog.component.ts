import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ContratAssurance } from './contrat-assurance.model';
import { ContratAssuranceService } from './contrat-assurance.service';
import { RefTypeContrat, RefTypeContratService } from '../ref-type-contrat';
import { RefNatureContrat, RefNatureContratService } from '../ref-nature-contrat';
import { Agency, AgencyService } from '../agency';
import { VehiculeAssure, VehiculeAssureService } from '../vehicule-assure';
import { Assure, AssureService } from '../assure';
import { RefFractionnement, RefFractionnementService } from '../ref-fractionnement';
import { RefPack, RefPackService } from '../ref-pack';
import { ResponseWrapper, User, AlertUtil } from '../../shared';
import { VehicleBrandModel, VehicleBrandModelService } from '../vehicle-brand-model';
import { VehicleBrand, VehicleBrandService } from '../vehicle-brand';
import { VehicleEnergy, VehicleEnergyService } from '../vehicle-energy';
import { VehicleUsage, VehicleUsageService } from '../vehicle-usage';
import { GaDatatable } from '../../constants/app.constants';
import { ConfirmationDialogService } from '../../shared';
import { PolicyStatus, PolicyStatusService } from '../policy-status';
import { PolicyReceiptStatus, PolicyReceiptStatusService } from '../policy-receipt-status';
import { AmendmentType, AmendmentTypeService } from '../amendment-type';
import { PartnerService } from './../partner/partner.service';
import { Partner } from './../partner/partner.model';
import { Delegation } from './../delegation/delegation.model';
import { Governorate } from './../governorate/governorate.model';
import { DelegationService } from './../delegation/delegation.service';
import { GovernorateService } from './../governorate/governorate.service';

@Component({
    selector: 'jhi-contrat-assurance-dialog',
    templateUrl: './contrat-assurance-dialog.component.html'
})
export class ContratAssuranceDialogComponent implements OnInit {
    contratAssurance: ContratAssurance = new ContratAssurance();
    vehiculeAssure: VehiculeAssure = new VehiculeAssure();
    vehiculeassures: VehiculeAssure[] = [];

    // Referential
    governorates: Governorate[];
    delegations: Delegation[];
    vehicleBrandModels: VehicleBrandModel[];
    vehicleBrands: VehicleBrand[];
    vehicleEnergies: VehicleEnergy[];
    vehicleUsages: VehicleUsage[];
    policyStatuss: PolicyStatus[];
    policyReceiptStatuss: PolicyReceiptStatus[];
    amendmentTypes: AmendmentType[];
    partners: Partner[];

    dtOptions: any = {};
    dtTrigger: Subject<VehiculeAssure> = new Subject();

    contractId: number;
    assureIsActive: Boolean = false;
    vehiculeIsActive = false;
    isConcessionaire = false;
    modeleVoiture: String;
    isEditing: Boolean;
    isPolicyNumberUnique = true;
    client: Partner;
    assure: Assure = new Assure();

    immatriculationVehicule: String;


    isSaving: boolean;
    isInsuredLoaded = false;
    isInsuredCarLoaded = false;
    vehiculesIsActive = false;
    isVehiculeModeEdit = false;
    isVehiculeModecreer = false;
    contratSinister = false;
    selectedValue: any;
    reftypecontrats: RefTypeContrat[];
    refnaturecontrats: RefNatureContrat[];
    refagences: Agency[];

    reffractionnements: RefFractionnement[];
    refpacks: RefPack[];

    debutValiditeDp: any;
    datePCirculationDp: any;
    finValiditeDp: any;
    isShown: Boolean = true;
    currentAccount: any;
    cli: any;

    clt: any;
    listeClient: any;
    checked: any[] = [];
    isAgentGeneral = false;
    isCompany = false;
    nbr = 0;
    nbrVehPattern = '^[0-9]*$';
    floatPattern = '^[0-9]*\\.?[0-9]*$';
    alphaPattern = '^[A-Za-z0-9]*$';
    amountPattern = '^[0-9]+(\.[0-9]{1,3})?';

    vehiculeAssuress: VehiculeAssure[] = [];

    vehiculeassur = new VehiculeAssure();

    vehiculeAssureDeleted = new VehiculeAssure();
    refMarque: VehicleBrand;
    refModel: VehicleBrandModel;
    refagence: Agency = new Agency();
    modifContrat = false;
    verifAssure = 0;
    exist = false;


    constructor(
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private contratAssuranceService: ContratAssuranceService,
        private refTypeContratService: RefTypeContratService,
        private refNatureContratService: RefNatureContratService,
        private refAgenceService: AgencyService,
        private vehicleUsageService: VehicleUsageService,
        private vehiculeAssureService: VehiculeAssureService,
        private assureService: AssureService,
        private refFractionnementService: RefFractionnementService,
        private refPackService: RefPackService,
        private eventManager: JhiEventManager,
        private vehicleBrandModelService: VehicleBrandModelService,
        private vehicleBrandService: VehicleBrandService,
        private vehicleEnergyService: VehicleEnergyService,
        private route: ActivatedRoute,
        private clientService: PartnerService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private confirmationDialogService: ConfirmationDialogService,
        private partnerService: PartnerService,
        private policyStatusService: PolicyStatusService,
        private policyReceiptStatusService: PolicyReceiptStatusService,
        private amendmentTypeService: AmendmentTypeService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.isSaving = false;

        this.dtOptions = GaDatatable.onlyPagingDtOptions;

        this.partnerService.query().subscribe((res: ResponseWrapper) => { this.partners = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.governorateService.query().subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.refTypeContratService.query().subscribe((res: ResponseWrapper) => { this.reftypecontrats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refNatureContratService.query().subscribe((res: ResponseWrapper) => { this.refnaturecontrats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refFractionnementService.query().subscribe((res: ResponseWrapper) => { this.reffractionnements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.vehicleBrandService.query().subscribe((res: ResponseWrapper) => { this.vehicleBrands = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleEnergyService.query().subscribe((res: ResponseWrapper) => { this.vehicleEnergies = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.vehicleUsageService.query().subscribe((res: ResponseWrapper) => { this.vehicleUsages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.policyStatusService.query().subscribe((res: ResponseWrapper) => { this.policyStatuss = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.policyReceiptStatusService.query().subscribe((res: ResponseWrapper) => { this.policyReceiptStatuss = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.amendmentTypeService.query().subscribe((res: ResponseWrapper) => { this.amendmentTypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.initContrat();
    }

    /**
     * Init contract
     */
    initContrat() {
        this.route.params.subscribe((params) => {
            if (params['idc']) { // Update from sinister
                this.contratSinister = true;
                this.contractId = params['idc'];
            } else if (params['id']) { // Update from contract list
                this.contractId = params['id'];
            }
            if (this.contractId) { // Update case
                this.contratAssuranceService.find(this.contractId).subscribe((subRes: ContratAssurance) => {
                    this.contratAssurance = subRes;
                    this.modifContrat = true;
                    const dateDebut = new Date(this.contratAssurance.debutValidite);
                    const dateFin = new Date(this.contratAssurance.finValidite);

                    if (this.contratAssurance.debutValidite) {
                        this.contratAssurance.debutValidite = {
                            year: dateDebut.getFullYear(),
                            month: dateDebut.getMonth() + 1,
                            day: dateDebut.getDate()
                        };
                    }
                    if (this.contratAssurance.finValidite) {
                        this.contratAssurance.finValidite = {
                            year: dateFin.getFullYear(),
                            month: dateFin.getMonth() + 1,
                            day: dateFin.getDate()
                        };
                    }
                    if (this.contratAssurance.deadlineDate) {
                        const deadline = new Date(this.contratAssurance.deadlineDate);
                        this.contratAssurance.deadlineDate = { year: deadline.getFullYear(), month: deadline.getMonth() + 1, day: deadline.getDate() };
                    }
                    if (this.contratAssurance.amendmentEffectiveDate) {
                        const amendmentEffective = new Date(this.contratAssurance.amendmentEffectiveDate);
                        this.contratAssurance.amendmentEffectiveDate = { year: amendmentEffective.getFullYear(), month: amendmentEffective.getMonth() + 1, day: amendmentEffective.getDate() };
                    }
                    if (this.contratAssurance.receiptValidityDate) {
                        const receiptValidity = new Date(this.contratAssurance.receiptValidityDate);
                        this.contratAssurance.receiptValidityDate = { year: receiptValidity.getFullYear(), month: receiptValidity.getMonth() + 1, day: receiptValidity.getDate() };
                    }

                    // Initialize agency list
                    if (this.contratAssurance.clientId !== null && this.contratAssurance.clientId !== undefined) {
                        this.listAgenceByCompagnie(this.contratAssurance.clientId);
                    }
                    // Initialize vehicle list
                    for (let i = 0; i < this.contratAssurance.vehicules.length; i++) {
                        if (this.contratAssurance.vehicules[i].datePCirculation !== null && this.contratAssurance.vehicules[i].datePCirculation !== undefined) {
                            const datePCirculation = new Date(this.contratAssurance.vehicules[i].datePCirculation);
                            this.contratAssurance.vehicules[i].datePCirculation = {
                                year: datePCirculation.getFullYear(),
                                month: datePCirculation.getMonth() + 1,
                                day: datePCirculation.getDate()
                            };
                        }
                        this.contratAssurance.vehicules[i].deleted = false;
                    }
                    if (this.contratAssurance.typeId === 2 && this.contratAssurance.vehicules && this.contratAssurance.vehicules.length > 0) { // personnal contract
                        this.vehiculeassur = this.contratAssurance.vehicules[0];
                        if (this.vehiculeassur.marqueId !== undefined) {
                            this.listModelByMarqueEdit(this.vehiculeassur.marqueId);
                        }
                        if (this.vehiculeassur.insured.delegationId !== null && this.vehiculeassur.insured.delegationId !== undefined) {
                            this.delegationService.find(this.vehiculeassur.insured.delegationId).subscribe((delegation: Delegation) => {
                                this.governorateService.find(delegation.governorateId).subscribe((governorate: Governorate) => {
                                    this.vehiculeassur.insured.governorateId = governorate.id;
                                    this.fetchDelegationsByGovernorate(this.vehiculeassur.insured.governorateId);
                                });
                            });
                        }


                    }
                    this.vehiculeAssuress = this.contratAssurance.vehicules;
                    this.dtTrigger.next(); // Actualize datatables

                }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            } else if (params['immatriculation']) {
                this.vehiculeassur.immatriculationVehicule = params['immatriculation'];
                this.modifContrat = false;

            } else if (params['immatriculationPec']) {
                this.vehiculeassur.immatriculationVehicule = params['immatriculationPec'];
                this.modifContrat = false;

            } else {
                this.modifContrat = false;
            }
        });
    }
    changeAssujettie(etat) {
        if (etat == false) {
            this.vehiculeAssure.assujettieTVA = false;
        } else {
            this.vehiculeAssure.assujettieTVA = true;
        }
    }
    /**
     * Get city list by gouvernorat
     * @param id
     */
    fetchDelegationsByGovernorate(id) {
        this.delegationService.findByGovernorate(id).subscribe((delegations: Delegation[]) => {
            this.delegations = delegations;
            if (delegations && delegations.length > 0) {
                this.vehiculeassur.insured.delegationId = delegations[0].id;
            }
        });
    }

    selectClient() {
        this.selectedValue = this.contratAssurance.clientId;
    }

    trimRegistrationNumber() {
        const str = this.vehiculeassur.immatriculationVehicule.replace(/\s/g, "");
        this.vehiculeassur.immatriculationVehicule = str.toUpperCase();
        /*if (this.vehiculeassur.immatriculationVehicule !== null && this.vehiculeassur.immatriculationVehicule !== undefined && this.vehiculeassur.immatriculationVehicule !== ''
            && this.contratAssurance.clientId !== null && this.contratAssurance.clientId !== undefined) {
            let vinTmp = undefined;
            if (this.vehiculeAssuress !== null && this.vehiculeAssuress !== undefined && this.vehiculeAssuress.length > 0) {   // Type contrat flotte          
                vinTmp = this.vehiculeAssuress.find(x => x.immatriculationVehicule === this.vehiculeassur.immatriculationVehicule);
                if (vinTmp != undefined && (this.vehiculeassur.id !== vinTmp.id)) {
                    this.alertUtil.addError('auxiliumApp.contratAssurance.uniqueVin');
                    this.vehiculeassur.immatriculationVehicule = undefined;
                }
            }
            if (vinTmp === null || vinTmp === undefined) {
                this.contratAssuranceService.findByImmatriculation(this.vehiculeassur.immatriculationVehicule).subscribe((contratAssurancee) => {
                    if (contratAssurancee && this.contratAssurance.id != contratAssurancee.id) {
                        //this.alertUtil.addError('auxiliumApp.contratAssurance.uniqueVin');
                        //this.vehiculeassur.immatriculationVehicule = undefined;
                    }
                });
            }
        }*/
    }

    findVehiculeByCompagnyNameAndImmatriculation() {
        const str = this.vehiculeassur.immatriculationVehicule.replace(/\s/g, "");
        this.vehiculeassur.immatriculationVehicule = str.toUpperCase();
        if (this.vehiculeassur.immatriculationVehicule !== null && this.vehiculeassur.immatriculationVehicule !== undefined && this.vehiculeassur.immatriculationVehicule !== ''
            && this.contratAssurance.clientId !== null && this.contratAssurance.clientId !== undefined) {
            this.vehiculeAssureService.findVehiculeByCompagnyIdAndImmatriculation(this.contratAssurance.clientId, this.vehiculeassur.immatriculationVehicule).subscribe((vehiculeRes: VehiculeAssure) => {
                if (vehiculeRes.id !== null && vehiculeRes.id !== undefined) {
                    this.alertUtil.addError('auxiliumApp.contratAssurance.uniqueVin');
                    this.vehiculeassur.immatriculationVehicule = undefined;
                }
            });
        }
    }

    /**
     * Change client actions
     * @param id new selected client id
     */
    changeClient(id) {
        // TODO : is it usefull
        this.selectedValue = this.contratAssurance.clientId;
        // Find agency by selected companies
        this.clientService.find(id).subscribe((clientRes: Partner) => { // Find selected client object
            this.client = clientRes;
            this.isConcessionaire = false;
            if (this.client.id == null) {
                this.isConcessionaire = true;
                this.refagences = null;
            } else {
                this.refAgenceService.findAllByPartner(this.client.id).subscribe((subRes1: ResponseWrapper) => {
                    this.refagences = subRes1.json;
                });
            }
        });
    }

    /**
     * Get list Agence by Client
     * @param id new selected client id
     */
    listAgenceByCompagnie(id) {
        this.clientService.find(id).subscribe((subRes: Partner) => {
            this.client = subRes;
            this.isConcessionaire = false;
            if (this.client.id == null) {
                this.isConcessionaire = true;
                this.refagences = null;
            } else {
                this.refAgenceService.findAllByPartner(this.client.id).subscribe((subRes1: ResponseWrapper) => {
                    this.refagences = subRes1.json;
                });
            }
        });
        this.refpacks = [];
        this.refPackService.findByPartner(id).subscribe((res: ResponseWrapper) => {
            this.refpacks = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
    }

    /**
     * Init vehicule
     */
    initVehicule() {
        if (!this.contratAssurance.id) {
            this.isInsuredCarLoaded = true;
            this.route.params.subscribe((params) => {
                if (params['immatriculation']) {
                    this.vehiculeAssure.immatriculationVehicule = params['immatriculation'];
                }
                if (params['immatriculationPec']) {
                    this.vehiculeAssure.immatriculationVehicule = params['immatriculationPec'];
                }
            });
        }
    }

    findPolicyByNumber(policy: ContratAssurance) {
        if (policy !== null && policy !== undefined && policy.numeroContrat !== null && policy.numeroContrat !== undefined && policy.numeroContrat !== '') {
            this.contratAssuranceService.findByPolicyNumber(policy).subscribe((subRes: ContratAssurance) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.contratAssurance.id) {
                    this.isPolicyNumberUnique = false;
                    this.alertUtil.addError('auxiliumApp.contratAssurance.unique');
                } else {
                    this.isPolicyNumberUnique = true;
                }
            });
        }
    }

    /**
     * Init insured person
     */
    initAssure() {
        if (!this.contratAssurance.assureId) { // creation mode
            this.assure = new Assure();
            this.isInsuredLoaded = true;
            this.route.params.subscribe((params) => {
                if (params['id']) {
                    this.assure.id = params['id'];
                }
            });
        } else {
            this.isEditing = true;
            this.assureService.find(this.contratAssurance.assureId).subscribe((subRes: Assure) => {
                this.assure = subRes;
                if (this.assure.delegationId !== null && this.assure.delegationId !== undefined) {
                    this.delegationService.find(this.assure.delegationId).subscribe((delegation: Delegation) => {
                        this.governorateService.find(delegation.governorateId).subscribe((governorate: Governorate) => {
                            this.assure.governorateId = governorate.id;
                            this.fetchDelegationsByGovernorate(this.assure.governorateId);
                        });
                    });
                }
                this.isInsuredLoaded = true;
            }, (subRes: ResponseWrapper) => this.onError(subRes.json));
        }

    }

    creer() {
        this.isVehiculeModecreer = true;
    }
    clear() {
        this.vehiculeassur = new VehiculeAssure();
    }


    addVehicule() {
        if (this.vehiculeassur.id) {
            this.vehiculeassur = new VehiculeAssure();
        } else {
            this.vehiculeAssuress.push(this.vehiculeassur);

            this.vehiculeassur = new VehiculeAssure();

            this.nbr = this.vehiculeAssuress.length;
        }
    }

    deleteVehicule(vehiculeassur) {
        vehiculeassur.deleted = true;
        /*
        this.vehiculeAssuress.forEach((item, index) => {
            if (item === vehiculeassur) {
                this.vehiculeAssuress.splice(index, 1);
            }
        });*/
    }

    editVehicule(vehiculeassur) {
        this.vehiculeassur = vehiculeassur;
        console.log(this.vehiculeassur);
        if (this.vehiculeassur.insured.delegationId !== null && this.vehiculeassur.insured.delegationId !== undefined) {
            this.delegationService.find(this.vehiculeassur.insured.delegationId).subscribe((delegation: Delegation) => {
                this.governorateService.find(delegation.governorateId).subscribe((governorate: Governorate) => {
                    this.vehiculeassur.insured.governorateId = governorate.id;
                    this.fetchDelegationsByGovernorate(this.vehiculeassur.insured.governorateId);
                });
            });
        }
        if (this.vehiculeassur.marqueId !== undefined) {
            this.listModelByMarqueEdit(this.vehiculeassur.marqueId);
        }
        this.isVehiculeModeEdit = true;
    }

    updateVehicle() {
        this.vehiculeassur = new VehiculeAssure();
        this.isVehiculeModeEdit = false;
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.contratAssurance.id !== undefined) { // Update
                        this.contratAssurance.vehicules = [];
                        this.contratAssuranceService.update(this.contratAssurance).subscribe((subRes: ContratAssurance) => {
                            if (this.contratAssurance.typeId === 1) { // Type contrat flotte
                                if (this.vehiculeAssuress !== null && this.vehiculeAssuress !== undefined && this.vehiculeAssuress.length > 0) {   // Type contrat flotte
                                    for (let i = 0; i < this.vehiculeAssuress.length; i++) {
                                        this.vehiculeAssuress[i].contratId = this.contratAssurance.id;
                                        if (this.vehiculeAssuress[i].id !== undefined) {
                                            if (this.vehiculeAssuress[i].deleted) {
                                                // Delete
                                                this.vehiculeAssureService.delete(this.vehiculeAssuress[i].id).subscribe(() => {
                                                    console.log('update vehicle successful');
                                                });
                                            } else {
                                                this.vehiculeAssureService.update(this.vehiculeAssuress[i]).subscribe(() => {
                                                    this.assureService.update(this.vehiculeAssuress[i].insured).subscribe((assure: Assure) => {
                                                    });
                                                    console.log('update vehicle successful');
                                                });
                                            }
                                        } else if (!this.vehiculeAssuress[i].deleted) {
                                            this.assureService.create(this.vehiculeAssuress[i].insured).subscribe((assure: Assure) => {
                                                this.vehiculeAssuress[i].insuredId = assure.id;
                                                this.vehiculeAssuress[i].insured = assure;
                                                this.vehiculeAssureService.create(this.vehiculeAssuress[i]).subscribe((subRes: VehiculeAssure) => {
                                                    this.vehiculeAssure = subRes;
                                                });
                                            });
                                        }
                                    }
                                }
                            } else { // Type particulier
                                this.vehiculeassur.contratId = this.contratAssurance.id;
                                this.assureService.update(this.vehiculeassur.insured).subscribe((assure: Assure) => {
                                    this.vehiculeAssureService.update(this.vehiculeassur).subscribe((subRes: VehiculeAssure) => {
                                        this.vehiculeassur = subRes;
                                        const datePCirculation = new Date(this.vehiculeassur.datePCirculation);
                                        if (this.vehiculeassur.datePCirculation) {
                                            this.vehiculeassur.datePCirculation = {
                                                year: datePCirculation.getFullYear(),
                                                month: datePCirculation.getMonth() + 1,
                                                day: datePCirculation.getDate()
                                            };
                                        }
                                    });
                                });
                            }
                            // Redirect to right page
                            this.route.params.subscribe((params) => {
                                if (params['immmat']) {
                                    if (this.vehiculeAssuress.length > 0) {
                                        for (let i = 0; i < this.vehiculeAssuress.length; i++) {
                                            if (this.vehiculeAssuress[i].immatriculationVehicule === params['immmat']) {
                                                this.exist = true;
                                            } else {
                                                this.exist = false;
                                            }
                                        }
                                    } else {
                                        if (this.contratAssurance.immatriculationVehicule === params['immmat']) {
                                            this.exist = true;
                                        } else {
                                            this.exist = false;
                                        }
                                    }
                                    if (this.exist) {
                                        this.router.navigate(['../../sinister-new/idcontrat/' + this.contratAssurance.id + '/immatriculation/' + params['immmat']])
                                    } else {
                                        this.router.navigate(['../../sinister-new']);
                                    }
                                } else {
                                    this.router.navigate(['../../contrat-assurance']);
                                }
                            });

                        });
                    } else { // Create
                        // Save contract
                        this.contratAssuranceService.create(this.contratAssurance).subscribe((subRes: ContratAssurance) => {
                            this.contratAssurance = subRes;
                            const dateDebut = new Date(this.contratAssurance.debutValidite);
                            const dateFin = new Date(this.contratAssurance.finValidite);
                            if (this.contratAssurance.debutValidite) {
                                this.contratAssurance.debutValidite = {
                                    year: dateDebut.getFullYear(),
                                    month: dateDebut.getMonth() + 1,
                                    day: dateDebut.getDate()
                                };
                            }
                            if (this.contratAssurance.finValidite) {
                                this.contratAssurance.finValidite = {
                                    year: dateFin.getFullYear(),
                                    month: dateFin.getMonth() + 1,
                                    day: dateFin.getDate()
                                };
                            }
                            if (this.vehiculeAssuress !== null && this.vehiculeAssuress !== undefined && this.vehiculeAssuress.length > 0) {   // Type contrat flotte
                                for (let i = 0; i < this.vehiculeAssuress.length; i++) {
                                    this.vehiculeAssuress[i].contratId = this.contratAssurance.id;
                                    if (!this.vehiculeAssuress[i].deleted) {
                                        this.assureService.create(this.vehiculeAssuress[i].insured).subscribe((assure: Assure) => {
                                            this.vehiculeAssuress[i].insuredId = assure.id;
                                            this.vehiculeAssuress[i].insured = assure;
                                            this.vehiculeAssureService.create(this.vehiculeAssuress[i]).subscribe((subRes: VehiculeAssure) => {
                                                this.vehiculeAssure = subRes;
                                                this.redirectToRightInterface();
                                            });
                                        });
                                    }
                                }
                            }

                            if (this.contratAssurance.typeId != 1) {  // type contrat individuel
                                this.vehiculeassur.contratId = this.contratAssurance.id;
                                this.assureService.create(this.vehiculeassur.insured).subscribe((assure: Assure) => {
                                    this.vehiculeassur.insuredId = assure.id;
                                    this.vehiculeassur.insured = assure;
                                    this.vehiculeAssureService.create(this.vehiculeassur).subscribe((subRes: VehiculeAssure) => {
                                        this.vehiculeassur = subRes;
                                        const datePCirculation = new Date(this.vehiculeassur.datePCirculation);
                                        if (this.vehiculeassur.datePCirculation) {
                                            this.vehiculeassur.datePCirculation = {
                                                year: datePCirculation.getFullYear(),
                                                month: datePCirculation.getMonth() + 1,
                                                day: datePCirculation.getDate()
                                            };
                                        }
                                        this.redirectToRightInterface();

                                    });
                                });
                            }


                        });

                    }
                }
            })
            .catch(() => {
                console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)');
            });

    }

    redirectToRightInterface() {
        this.route.params.subscribe((params) => {
            if (params['immatriculation']) {
                if (this.contratAssurance.typeId == 1) {
                    for (let i = 0; i < this.vehiculeAssuress.length; i++) {
                        if (this.vehiculeAssuress[i].immatriculationVehicule === params['immatriculation']) {
                            this.exist = true;
                        } else {
                            this.exist = false;
                        }
                    }
                } else {
                    if (this.vehiculeassur.immatriculationVehicule === params['immatriculation']) {
                        this.exist = true;
                    } else {
                        this.exist = false;
                    }
                }
                if (this.exist) {
                    this.router.navigate(['../../sinister-new/idcontrat/' + this.contratAssurance.id + '/immatriculation/' + params['immatriculation']])
                } else {
                    this.router.navigate(['../../sinister-new']);
                }
            } else if (params['immatriculationPec']) {
                this.router.navigate(['../../sinister-pec-agent-new-c/' + params['immatriculationPec']]);
            } else {
                this.router.navigate(['../../contrat-assurance']);
            }
        });
    }

    private subscribeContratToSaveResponse(result: Observable<Assure>) {
        result.subscribe((res: Assure) =>
            this.onassureSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onassureSaveSuccess(result: Assure) {
        this.isSaving = true;
        this.contratAssurance.assureId = result.id;
        // Save contract
        this.contratAssuranceService.create(this.contratAssurance).subscribe((subRes: ContratAssurance) => {
            this.contratAssurance = subRes;
            const dateDebut = new Date(this.contratAssurance.debutValidite);
            const dateFin = new Date(this.contratAssurance.finValidite);
            if (this.contratAssurance.debutValidite) {
                this.contratAssurance.debutValidite = {
                    year: dateDebut.getFullYear(),
                    month: dateDebut.getMonth() + 1,
                    day: dateDebut.getDate()
                };
            }
            if (this.contratAssurance.finValidite) {
                this.contratAssurance.finValidite = {
                    year: dateFin.getFullYear(),
                    month: dateFin.getMonth() + 1,
                    day: dateFin.getDate()
                };
            }
            if (this.vehiculeAssuress !== null && this.vehiculeAssuress !== undefined && this.vehiculeAssuress.length > 0) {   // Type contrat flotte
                for (let i = 0; i < this.vehiculeAssuress.length; i++) {
                    this.vehiculeAssuress[i].contratId = this.contratAssurance.id;
                    if (!this.vehiculeAssuress[i].deleted) {
                        this.vehiculeAssureService.create(this.vehiculeAssuress[i]).subscribe((subRes: VehiculeAssure) => {
                            this.vehiculeAssure = subRes;
                        });
                    }
                }
            }

            if (!this.isVehiculeModeEdit) {  // type contrat individuel
                this.vehiculeassur.contratId = this.contratAssurance.id;
                this.vehiculeAssureService.create(this.vehiculeassur).subscribe((subRes: VehiculeAssure) => {
                    console.log("___________________vehicle", this.vehiculeassur);
                    this.vehiculeassur = subRes;
                    const datePCirculation = new Date(this.vehiculeassur.datePCirculation);
                    if (this.vehiculeassur.datePCirculation) {
                        this.vehiculeassur.datePCirculation = {
                            year: datePCirculation.getFullYear(),
                            month: datePCirculation.getMonth() + 1,
                            day: datePCirculation.getDate()
                        };
                    }
                });
            }

            this.route.params.subscribe((params) => {
                if (params['immatriculation']) {
                    if (this.contratAssurance.typeId == 1) {
                        for (let i = 0; i < this.vehiculeAssuress.length; i++) {
                            if (this.vehiculeAssuress[i].immatriculationVehicule === params['immatriculation']) {
                                this.exist = true;
                            } else {
                                this.exist = false;
                            }
                        }
                    } else {
                        if (this.vehiculeassur.immatriculationVehicule === params['immatriculation']) {
                            this.exist = true;
                        } else {
                            this.exist = false;
                        }
                    }
                    if (this.exist) {
                        this.router.navigate(['../../sinister-new/idcontrat/' + this.contratAssurance.id + '/immatriculation/' + params['immatriculation']])
                    } else {
                        this.router.navigate(['../../sinister-new']);
                    }
                } else if (params['immatriculationPec']) {
                    this.router.navigate(['../../sinister-pec-agent-new-c/' + params['immatriculationPec']]);
                } else {
                    this.router.navigate(['../../contrat-assurance']);
                }
            });
        });
    }

    /**
     * List car model by brand
     * @param id car brand id
     */
    trackRefModelById(index: number, item: VehicleBrandModel) {
        return item.id;
    }

    trackMarqueById(index: number, item: VehicleBrand) {
        return item.id;
    }

    listModelByMarque(id) {
        if (id !== null && id !== undefined) {
            this.vehicleBrandService.find(id).subscribe((subRes: VehicleBrand) => {
                this.refMarque = subRes;
                this.vehicleBrandModelService.findByBrand(this.refMarque.id).subscribe((subRes1: ResponseWrapper) => {
                    this.vehicleBrandModels = subRes1.json;
                    if (this.vehicleBrandModels && this.vehicleBrandModels.length > 0) {
                        this.vehiculeassur.modele = this.vehicleBrandModels[0];
                        this.vehiculeassur.modeleId = this.vehicleBrandModels[0].id;
                        this.vehiculeassur.modeleLibelle = this.vehicleBrandModels[0].label;
                    }
                });
            });
        }
    }

    listModelByMarqueEdit(id) {
        if (id !== null && id !== undefined) {
            this.vehicleBrandService.find(id).subscribe((subRes: VehicleBrand) => {
                this.refMarque = subRes;
                this.vehicleBrandModelService.findByBrand(this.refMarque.id).subscribe((subRes1: ResponseWrapper) => {
                    this.vehicleBrandModels = subRes1.json;
                    if (this.vehicleBrandModels && this.vehicleBrandModels.length > 0) {
                        //this.vehiculeassur.modele = this.vehicleBrandModels[0];
                        //this.vehiculeassur.modeleId = this.vehicleBrandModels[0].id;
                        ///this.vehiculeassur.modeleLibelle = this.vehicleBrandModels[0].label;
                    }
                });
            });
        }
    }




    changeInsuredNature(isPp) {
        this.vehiculeassur.insured.company = isPp;
    }

    /**
     * Find car brand from it model
     * @param idModel car model id
     */
    findMarqueOfMode(idModel) {
        this.vehicleBrandModelService.find(idModel).subscribe((res: VehicleBrandModel) => {
            this.refModel = res;
            this.vehicleBrandService.find(this.refModel.brandId).subscribe((subRes: VehicleBrand) => {
                this.vehiculeAssure.marque = subRes
                this.refMarque = subRes;
                this.vehiculeAssureService.findModelByMarque(this.refMarque.id).subscribe((subRes1: VehicleBrandModel[]) => {
                    this.vehicleBrandModels = subRes1;
                });
            });
        });
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
        if (error)
            this.alertUtil.addError(error.message);
    }

    trackRefTypeContratById(index: number, item: RefTypeContrat) {
        return item.id;
    }

    trackRefNatureContratById(index: number, item: RefNatureContrat) {
        return item.id;
    }

    trackRefAgenceById(index: number, item: Agency) {
        return item.id;
    }

    trackVehicleUsageById(index: number, item: VehicleUsage) {
        return item.id;
    }

    trackVehiculeAssureById(index: number, item: VehiculeAssure) {
        return item.id;
    }

    trackAssureById(index: number, item: Assure) {
        return item.id;
    }

    trackRefFractionnementById(index: number, item: RefFractionnement) {
        return item.id;
    }

    trackRefPackById(index: number, item: RefPack) {
        return item.id;
    }

    trackClientById(index: number, item: Partner) {
        return item.id;
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
    toggleVehicule() {
        this.vehiculeIsActive = !this.vehiculeIsActive;
    }
    toggleAssure() {
        this.assureIsActive = !this.assureIsActive;
    }
    changeNatureAssure(Shown: Boolean) {
        if ((Shown === true) || (Shown === false)) {
            this.isShown = Shown;
        }
    }

    verifRequiredAssure(ver) {
        this.verifAssure = ver;
    }

    formatDate(date) {
        let ret = '';
        if (date !== null || date !== undefined) {
            ret += date.day < 10 ? '0' + date.day : date.day; ret += '/';
            ret += date.month < 10 ? '0' + date.month : date.month; ret += '/';
            ret += '' + date.year;
        }
        return ret;
    }
}
