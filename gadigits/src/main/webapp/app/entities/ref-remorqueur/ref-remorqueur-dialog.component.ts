import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Response } from '@angular/http';
import { Observable, Subscription, Subject } from 'rxjs/Rx';
import { Contact, ContactService } from '../contact';

import { VisAVis } from './../vis-a-vis/vis-a-vis.model';
import { Camion, CamionService } from '../camion';
import { VatRate, VatRateService } from '../vat-rate';
import { RefModeReglement, RefModeReglementService } from '../ref-mode-reglement';
import { PersonneMorale } from '../personne-morale/personne-morale.model';;
import { PersonneMoraleService } from '../personne-morale/personne-morale.service';
import { RefRemorqueurService, RefRemorqueurPopupService, RefRemorqueur } from '../ref-remorqueur';
import { Principal, ResponseWrapper } from '../../shared';
import { TugPricing, RefTarifService, RefTarif } from '../ref-tarif';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { TarifLine } from '../ref-tarif/tarif-line.model';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { RefMotif, RefMotifService } from '../ref-motif';
import { Journal } from '../journal';
import { ConfirmationDialogService } from '../../shared';
import { RefTypeServiceService } from '../ref-type-service/ref-type-service.service';
import { GaDatatable, SettlementMode, Global } from '../../constants/app.constants';
import { RefTypeService } from '../ref-type-service/ref-type-service.model';
import { CircleMarkerOptions } from 'leaflet';
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { RaisonAssistanceService } from '../raison-assistance';
@Component({
    selector: 'jhi-ref-remorqueur-dialog',
    templateUrl: './ref-remorqueur-dialog.component.html'
})
export class RefRemorqueurDialogComponent implements OnInit {
    bloque = false;
    list = '';
    list1: number[] = [];
    disabledMotif = false;
    disabledReclamation = true;
    ajoutCamion = false;
    ajoutContact = false;
    visAVisGeneral: VisAVis = new VisAVis();
    currentAccount: any;
    refTvas: VatRate[];
    nbr = 0;
    disabld = false;
    refModeReglements: RefModeReglement[];
    sysgouvernorats: Governorate[];
    reftypeservices: RefTypeService[];
    sysGouvernorat: Governorate;
    personneMorale: PersonneMorale = new PersonneMorale();
    refRemorqueur: RefRemorqueur = new RefRemorqueur();
    visAVis: VisAVis = new VisAVis();
    isSaving: boolean;

    camion: Camion = new Camion();
    camionModif: Camion = new Camion();
    disabled = true;
    refMotifOption: RefMotif;
    isCompanyLoaded = true;
    disabledRmq = true;
    disabledForm = true;
    disabledForm1 = true;
    disabledto = false;
    disabledcontact = true;
    sysvilles: Delegation[];
    tarifLines: TarifLine[];
    priceUrbanPlanServiceOptions: RefTarif[];
    priceUrbanMobilityOptions: RefTarif[];
    priceReparationOptions: RefTarif[];
    priceExtractionOptions: RefTarif[];
    priceKlmLongDistancePlOptions: RefTarif[];
    priceKlmShortDistanceOptions: RefTarif[];
    priceKlmShortDistancePlOptions: RefTarif[];
    priceUrbanPlanServicePlOptions: RefTarif[];
    priceKlmLongDistanceOption: RefTarif[];
    line: TarifLine;
    sysVille: Delegation;
    listContacts: VisAVis[] = [];
    listContact: VisAVis[] = [];
    listCamions: Camion[] = [];
    listCamion: Camion[] = [];
    camions: Camion[] = [];
    eventSubscriber: Subscription;
    nom: String;
    contratAssurance: any;
    tugPricing: TugPricing = new TugPricing();
    assure: any;
    refCompagnie: any;
    test: any;
    links: any;
    totalItems: any;
    ajout = true;
    edit = false;
    ajout1 = true;
    idRmq: number
    idPm: number;
    idPersonne: number;
    number = 0;
    idRemorqueur: number;
    idRemorq: number;
    dtOptions: any = {};
    SysAction: any;
    refMotif = new RefMotif();
    motifs: RefMotif[];
    idMotif: number;
    refModeReglement: RefModeReglement;
    dropdownList = [];
    selectedItems = [];
    dropdownSettings = {};
    listFeaturesRemorqueur: any;
    bloquerRemorqueur = false;
    creervisavis = true;
    isTugNameUnique = true;
    mandatoryRibFlag = false;
    errorMessage = '';
    textPattern = Global.textPattern;

    constructor(
        private alertService: JhiAlertService,
        private personneMoraleService: PersonneMoraleService,
        // private contactService: ContactService,
        private camionService: CamionService,
        private refMotifService: RefMotifService,
        private refRemorqueurService: RefRemorqueurService,
        private refTarifService: RefTarifService,
        private sysVilleService: DelegationService,
        private sysGouvernoratService: GovernorateService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        public principal: Principal,
        private router: Router,
        private refModeReglementService: RefModeReglementService,
        private refTvaService: VatRateService,
        private refTypeServiceService: RefTypeServiceService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private confirmationDialogService: ConfirmationDialogService,
        private raisonAssistanceService: RaisonAssistanceService
    ) {

    }
    ngOnInit() {
        this.visAVisGeneral.isGerant = true;
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.disabledForm = false;
        this.disabledForm1 = false;
        this.refTvaService.query().subscribe((res: ResponseWrapper) => { this.refTvas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refModeReglementService.query().subscribe((res: ResponseWrapper) => { this.refModeReglements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.isSaving = false;
        this.sysVilleService.query().subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => { this.sysgouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.personneMorale.gouvernorat == null) {
            this.personneMorale.gouvernorat = new Governorate();
        }
        if (this.personneMorale.villeId != null) {
            this.findGouvernoratOfVille(this.personneMorale.villeId);
        }
        this.initRefRemorqueur();
        this.initPersonneMorale();
        this.refTypeServiceService.query()
            .subscribe((res: ResponseWrapper) => { this.reftypeservices = res.json; this.SysAction = this.reftypeservices }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.queryline()
            .subscribe((res: ResponseWrapper) => { this.tarifLines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1234)
            .subscribe((res: ResponseWrapper) => { this.priceUrbanPlanServiceOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1235)
            .subscribe((res: ResponseWrapper) => { this.priceKlmShortDistanceOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1236)
            .subscribe((res: ResponseWrapper) => { this.priceKlmLongDistanceOption = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1240)
            .subscribe((res: ResponseWrapper) => { this.priceUrbanPlanServicePlOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1241)
            .subscribe((res: ResponseWrapper) => { this.priceKlmShortDistancePlOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1242)
            .subscribe((res: ResponseWrapper) => { this.priceKlmLongDistancePlOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1237)
            .subscribe((res: ResponseWrapper) => { this.priceExtractionOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1238)
            .subscribe((res: ResponseWrapper) => { this.priceReparationOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refTarifService.findByTarifLine(1239)
            .subscribe((res: ResponseWrapper) => { this.priceUrbanMobilityOptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.raisonAssistanceService.findMotifsByOperation(2).subscribe((subRes: ResponseWrapper) => {
            this.motifs = subRes.json;
            console.log("iciiiiiiiiiiii motifs" + this.motifs.length);
        })
        this.dropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'nom',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 6,
            allowSearchFilter: true
        };

    }

    findTugByName(pname: string) {
        console.log('_______________________________________________________________');
        if (this.personneMorale.raisonSociale !== null && this.personneMorale.raisonSociale !== undefined && this.personneMorale.raisonSociale !== '' && this.personneMorale.matriculeFiscale !== null && this.personneMorale.matriculeFiscale !== undefined && this.personneMorale.matriculeFiscale !== '' && this.personneMorale.registreCommerce !== null && this.personneMorale.registreCommerce !== undefined && this.personneMorale.registreCommerce !== '') {
            this.refRemorqueurService.findByTugName(this.personneMorale).subscribe((subRes: RefRemorqueur) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.refRemorqueur.id) {
                    this.isTugNameUnique = false;
                    this.alertService.error('auxiliumApp.refRemorqueur.unique', null, null);
                } else {
                    this.isTugNameUnique = true;
                }
            });
        }
    }

    initPersonneMorale() {
        this.route.params.subscribe((params) => {
            if (params['idPm']) {
                this.disabledcontact = false;
                this.personneMoraleService.find(params['idPm']).subscribe((subRes: PersonneMorale) => {
                    this.personneMorale = subRes;
                    if (this.personneMorale.villeId != null) {
                        this.findGouvernoratOfVille(this.personneMorale.villeId)
                    }

                }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            } else {
                if (this.personneMorale.gouvernorat == null) {
                    this.personneMorale.gouvernorat = new Governorate()
                }
            }
        });
    }

    handleClickMotif() {
        this.disabledMotif = true;
        this.disabledReclamation = false;
    }
    handleClickReclamation() {
        this.disabledMotif = false;
        this.disabledReclamation = true;
    }
    addForm() {
        this.disabledForm = true;
    }

    addForm1() {
        this.disabledForm1 = true
        this.ajout1 = true;
        this.visAVis = new VisAVis();
    }
    etatRib(id) {
        if (SettlementMode.VIREMENT === id || SettlementMode.TRAITE === id) {
            this.mandatoryRibFlag = true;
        } else {
            this.mandatoryRibFlag = false;
        }
    }
    initRefRemorqueur() {
        this.route.params.subscribe((params) => {
            if (params['idRmq']) {
                this.camionService.findByRefRemorqueur(params['idRmq']).subscribe((res: ResponseWrapper) => {
                    this.listCamions = res.json
                    this.listCamions.forEach(camion => {
                        camion.serviceTypesStr = '';
                        camion.serviceTypes.forEach((serviceType) => {
                            camion.serviceTypesStr += serviceType.nom + ', ';
                        });
                        camion.serviceTypesStr = camion.serviceTypesStr.substring(0, camion.serviceTypesStr.length - 2);
                        this.listCamion.push(camion);
                    });
                    this.listCamions = this.listCamion;
                });
                this.refRemorqueurService.findjournal(params['idRmq']).subscribe((res: Journal) => {
                    res.motifs.forEach(element => {
                        this.refMotifService.find(element.id).subscribe((res: RefMotif) => {
                            this.refMotif = res;
                            this.idMotif = res.id;
                            this.disabledMotif = true;
                            this.disabledReclamation = false;
                        });
                    });
                });
                this.refTarifService.findTugPricing(params['idRmq']).subscribe((subRes: TugPricing) => {
                    this.tugPricing = subRes;
                    this.tugPricing.priceKlmShortDistance = subRes.priceKlmShortDistance
                    const dateDebut = new Date(this.tugPricing.dateEffectiveAgreement);
                    const dateFin = new Date(this.tugPricing.dateEndAgreement);
                    if (this.tugPricing.dateEffectiveAgreement) {
                        this.tugPricing.dateEffectiveAgreement = {
                            year: dateDebut.getFullYear(),
                            month: dateDebut.getMonth() + 1,
                            day: dateDebut.getDate()
                        };
                    }
                    if (this.tugPricing.dateEndAgreement) {
                        this.tugPricing.dateEndAgreement = {
                            year: dateFin.getFullYear(),
                            month: dateFin.getMonth() + 1,
                            day: dateFin.getDate()
                        };
                    }
                });
                this.disabledcontact = false;
                this.refRemorqueurService.find(params['idRmq']).subscribe((subRes: RefRemorqueur) => {
                    this.refRemorqueur = subRes;
                    if (this.refRemorqueur.id != null && this.refRemorqueur.id !== undefined && this.refRemorqueur.visAViss !== null) {
                        this.listContact = this.refRemorqueur.visAViss;
                        this.listContact.forEach((element) => {
                            if (element.isGerant !== true) {
                                this.listContacts.push(element);
                            }
                            else this.visAVisGeneral = element;
                        });
                    }
                    this.refRemorqueur.isBloque = subRes.isBloque;
                    this.personneMorale.id = this.refRemorqueur.societeId;
                    this.personneMoraleService.find(this.refRemorqueur.societeId).subscribe((subRes: PersonneMorale) => {
                        this.personneMorale = subRes;
                        this.personneMorale.id = this.refRemorqueur.societeId;
                    })
                }, (subRes: ResponseWrapper) => this.onError(subRes.json));
            }
        });
    }
    trackRefGarantieContractuelleById(index: number, item: Contact) {
        return item.id;
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

    trimRegistrationNumber() {
        const str = this.camion.immatriculation.replace(/\s/g, "");
        this.camion.immatriculation = str.toUpperCase();
    }

    addCamion() {
        let isDuplicate = false;
        this.trimRegistrationNumber();
        if (this.listCamions && this.listCamions.length > 0) {
            for (let k = 0; k < this.listCamions.length; k++) {
                if (this.camion.immatriculation === this.listCamions[k].immatriculation) {
                    isDuplicate = true;
                    break;
                }
            }
        }

        if (isDuplicate) {
            this.errorMessage = 'Immatriculation ' + this.camion.immatriculation + ' existe déjà';
        } else {
            if (this.camion.immatriculation !== null && this.camion.immatriculation !== undefined && this.camion.immatriculation !== '') {
                this.camionService.findByImmatriculation(this.camion.immatriculation).subscribe((subRes: Camion) => {
                    if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.camion.id) {
                        isDuplicate = true;
                        this.errorMessage = 'Immatriculation ' + this.camion.immatriculation + ' existe déjà';
                    } else {
                        isDuplicate = false;
                        if (this.camion.id) {
                            this.camion = new Camion();
                        } else {
                            this.camion.serviceTypesStr = '';
                            this.camion.serviceTypes.forEach((serviceType) => {
                                this.camion.serviceTypesStr += serviceType.nom + ', ';
                            });
                            this.camion.serviceTypesStr = this.camion.serviceTypesStr.substring(0, this.camion.serviceTypesStr.length - 2);

                            this.listCamions.push(this.camion);
                        }
                        this.camion = new Camion();
                        this.ajoutCamion = true;
                        this.disabledForm = false;
                        this.selectedItems = [];
                        this.errorMessage = '';
                    }
                });
            }
        }
    }

    ModifCamion() {
        let isDuplicate = false;
        this.trimRegistrationNumber();
        if (this.listCamions && this.listCamions.length > 0) {
            for (let k = 0; k < this.listCamions.length; k++) {
                if (this.camion.immatriculation === this.listCamions[k].immatriculation) {
                    isDuplicate = true;
                    break;
                }
            }
        }

        if (isDuplicate = true && (this.camion.id == null && this.camion.id == undefined)) {
            this.errorMessage = 'Immatriculation ' + this.camion.immatriculation + ' existe déjà';
        } else {
            if (this.camion.immatriculation !== null && this.camion.immatriculation !== undefined && this.camion.immatriculation !== '') {
                this.camionService.findByImmatriculation(this.camion.immatriculation).subscribe((subRes: Camion) => {
                    if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.camion.id) {
                        isDuplicate = true;
                        this.errorMessage = 'Immatriculation ' + this.camion.immatriculation + ' existe déjà';
                    } else {
                        isDuplicate = false;
                        this.listCamions.forEach((item, index) => {
                            if (item === this.camionModif) this.listCamions.splice(index, 1);
                        });

                        this.camion.serviceTypesStr = '';
                        this.camion.serviceTypes.forEach((serviceType) => {
                            this.camion.serviceTypesStr += serviceType.nom + ', ';
                        });
                        this.camion.serviceTypesStr = this.camion.serviceTypesStr.substring(0, this.camion.serviceTypesStr.length - 2);
                        this.listCamions.push(this.camion);
                        this.camion = new Camion();
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
    deleteCamion(camionDelete) {
        this.listCamions.forEach((item, index) => {
            if (item === camionDelete) {
                this.list1.push(item.id);
                this.listCamions.splice(index, 1);
            }
        });
        this.camion = new Camion();
        this.ajout = true;
        this.edit = false;
        this.selectedItems = [];
        this.disabledForm = false;
        this.errorMessage = '';
    }
    editCamion(camionEdit: Camion) {
        this.disabledForm = true;
        this.camion = camionEdit;
        this.edit = true;
        this.ajout = false;
        this.camionModif = camionEdit;
    }
    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de Màj ce Remorqueur ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.isSaving = true;
                if (this.refRemorqueur.id !== undefined) {
                    this.subscribeperToSaveResponse(this.personneMoraleService.update(this.personneMorale));
                } else {
                    this.subscribeperToSaveResponse(this.personneMoraleService.create(this.personneMorale));
                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    private subscribeperToSaveResponse(result: Observable<PersonneMorale>) {
        result.subscribe((res: PersonneMorale) =>
            this.onPerSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onPerSaveSuccess(result: PersonneMorale) {
        this.personneMorale.id = result.id;
        this.idPersonne = result.id;
        this.refRemorqueur.societeId = result.id;
        // save remorqueur
        if (this.refRemorqueur.id !== undefined) {
            /*if (this.listContacts.length > 0) {
                this.refRemorqueur.telephone = this.listContacts[0].premTelephone;
            } else {*/
            this.refRemorqueur.telephone = this.visAVisGeneral.premTelephone;
            //}
            this.listContacts.push(this.visAVisGeneral);
            this.refRemorqueur.visAViss = this.listContacts;
            this.subscribermqToSaveResponse(this.refRemorqueurService.update(this.refRemorqueur));
        } else {
            /*if (this.listContacts.length > 0) {
                this.refRemorqueur.telephone = this.listContacts[0].premTelephone;
            } else {*/
            this.refRemorqueur.telephone = this.visAVisGeneral.premTelephone;
            //}
            this.listContacts.push(this.visAVisGeneral);
            this.refRemorqueur.visAViss = this.listContacts;
            this.subscribermqToSaveResponse(this.refRemorqueurService.create(this.refRemorqueur));
        }
    }

    private saveTug() {
        this.tugPricing.tugId = this.refRemorqueur.id;
        if (this.tugPricing.id !== undefined) {

            this.subscribeToSaveResponse(
                this.refTarifService.updatePricing(this.tugPricing));
        } else {
            this.subscribeToSaveResponse(
                this.refTarifService.createPricing(this.tugPricing));
        }
    }

    private subscribermqToSaveResponse(result: Observable<RefRemorqueur>) {
        result.subscribe((res: RefRemorqueur) =>
            this.onRmqSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onRmqSaveSuccess(result: RefRemorqueur) {
        this.refRemorqueur.id = result.id;
        if (this.refRemorqueur.isBloque === true) {
            this.refMotifService.find(this.idMotif).subscribe((res: RefMotif) => {
                const motifss: number[] = [];
                motifss.push(res.id);
                this.refRemorqueurService.bloquerMotif(this.refRemorqueur.id, motifss).subscribe((res: RefRemorqueur) => { });
            });
        }
        this.refRemorqueurService.id = result.id;
        this.personneMorale.id = result.societeId;
        this.personneMoraleService.id = result.societeId;
        this.isSaving = false;
        this.saveTug();

        this.list1.forEach((element) => {
            if (element !== null && element !== undefined) {
                this.camionService.delete(element).subscribe((response) => { });
            }
        });

        console.log('______________________________________________________________________');
        for (let i = 0; i < this.listCamions.length; i++) {
            this.listCamions[i].refTugId = this.refRemorqueur.id;
            if (this.listCamions[i].id !== undefined) {
                this.camionService.update(this.listCamions[i]).subscribe((subRes: Camion) => {
                    this.camion = subRes;
                });
            } else {
                this.camionService.create(this.listCamions[i]).subscribe((subRes: Camion) => {
                    this.camion = subRes;
                });
            }
        }
    }

    private subscribeToSaveResponse(result: Observable<TugPricing>) {
        result.subscribe((res: RefRemorqueur) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: TugPricing) {
        this.idRemorq = result.id;
        this.router.navigate(['../ref-remorqueur']);

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

    trackPersonneMoraleById(index: number, item: PersonneMorale) {
        return item.id;
    }
    trackMotifsById(index: number, item: RefMotif) {
        return item.id;
    }
    trackSysVilleById(index: number, item: Delegation) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: Governorate) {
        return item.id;
    }

    trackContactsId(index: number, item: VisAVis) {
        return item.id
    }
    trackCamionsId(index: number, item: Camion) {
        return item.id
    }

    listVillesByGouvernoratLieu(id) {
        if (id) this.disabled = false;
        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernorat = subRes;
            this.sysVilleService.findByGovernorate(this.sysGouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.sysvilles = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.personneMorale.villeId = subRes1[0].id;
                }

            });
        });
    }
    findGouvernoratOfVille(idVille) {
        this.sysVilleService.find(idVille).subscribe((res: Delegation) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.governorateId).subscribe((subRes: Governorate) => {
                this.personneMorale.gouvernorat = subRes
            })
        }
        )
    }
}
@Component({
    selector: 'jhi-ref-remorqueur-popup',
    template: ''
})
export class RefRemorqueurPopupComponent implements OnInit, OnDestroy {
    routeSub: any;
    listContacts: VisAVis[];
    listCamions: Camion[];
    refCompagnie: any;
    contact: any;
    camion: any;
    constructor(
        private route: ActivatedRoute,
        private refRemorqueurPopupService: RefRemorqueurPopupService

    ) {
    }
    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['idCpg']) {
                this.refRemorqueurPopupService
                    .open(RefRemorqueurDialogComponent as Component, params['idCpg']);
            } else {
                this.refRemorqueurPopupService
                    .open(RefRemorqueurDialogComponent as Component);
            }
        });


    }
    ngOnDestroy() {
        this.routeSub.unsubscribe();


    }
}
