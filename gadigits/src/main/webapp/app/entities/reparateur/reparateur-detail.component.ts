import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Reparateur, GarantieImplique, Specialite, Orientation, RefAgeVehicule } from './reparateur.model';
import { RefModeReglement, RefModeReglementService } from '../ref-mode-reglement';
import { ReparateurPopupService } from './reparateur-popup.service';
import { ReparateurService } from './reparateur.service';
import { VisAVis } from '../vis-a-vis';
import { VehicleBrand, VehicleBrandService } from '../vehicle-brand';
import { Reglement } from '../reglement';
import { RefModeGestionService, RefModeGestion } from './../ref-mode-gestion';
import { ResponseWrapper } from '../../shared';
import { PartnerService } from './../partner/partner.service';;
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { Partner } from './../partner/partner.model';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { RefMotif, RefMotifService } from '../ref-motif';
import { Subject } from 'rxjs';
import { Journal } from '../journal';
import { ConfirmationDialogService } from '../../shared';
import { GaDatatable, SettlementMode } from '../../constants/app.constants';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';
@Component({
    selector: 'jhi-reparateur-detail',
    templateUrl: './reparateur-detail.component.html'
})
export class ReparateurDetailComponent implements OnInit {
    sysVille: Delegation;
    reparateur: Reparateur = new Reparateur();
    orientation: Orientation = new Orientation();
    refAgeVehicule: RefAgeVehicule = new RefAgeVehicule();
    orientations: Orientation[] = [];
    garantieImplique: GarantieImplique = new GarantieImplique();
    specialites: Specialite[] = [];
    visAVis: VisAVis = new VisAVis();
    gerant: VisAVis = new VisAVis();
    visAViss: VisAVis[] = [];
    garantieImpliques: GarantieImplique[] = [];
    isSaving: boolean;
    dropdownList = [];
    isExpNameFTUSAUnique = true;
    formOrientation = false;
    formGarantie = false;
    formVisAVis = true;
    ajoutOrientation: boolean = true;
    editVisaVis: boolean = false;
    editGarantieImplique: boolean = false;
    editGarantie: boolean = false;
    grantTimingList = [];
    dropdownSettings = {};
    dropdownSettingsForGarantieImplique = {};
    serviceTypeDropdownSettings = {};
    selectedItems = [];
    sysGouvernorat: Governorate;
    refModeReglements: RefModeReglement[];
    clients: Partner[];
    client: Partner;
    marquePrincipale: string = '';
    autreMarque: string = '';
    gov: boolean = true;
    nbrVehPattern = '^[0-9]*$';
    sinisterTypes: RefModeGestion[];
    refmarques: VehicleBrand[];
    refmarque: VehicleBrand = new VehicleBrand();
    refAgeVehicules: RefAgeVehicule[];
    motifs: RefMotif[];
    dtTriggerGarantieImplique: Subject<GarantieImplique> = new Subject();

    dtTriggerVisAVis: Subject<VisAVis> = new Subject();
    dtTriggerOrientation: Subject<Orientation> = new Subject();
    dtTriggerMarque: Subject<String> = new Subject();
    dtOptions: any = {};
    reglements: Reglement[];
    SysAction: any;
    idMotif: number;
    refMotif = new RefMotif();
    governorates: Governorate[];
    sysvilles: Delegation[];
    mandatoryRibFlag = false;
    mandatoryCapaciteFlag = false;
    isCommentError = false;
    refMotifOption = new RefMotif();
    private ngbModalRef: NgbModalRef;
    constructor(
        private alertService: JhiAlertService,
        private route: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,
        private reparateurService: ReparateurService,
        private sysGouvernoratService: GovernorateService,
        private refMarqueService: VehicleBrandService,
        private refModeReglementService: RefModeReglementService,
        private eventManager: JhiEventManager,
        private clientService: PartnerService,
        private refMotifService: RefMotifService,
        private delegationService: DelegationService,
        private refModeGestionService: RefModeGestionService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private historyPopupService: HistoryPopupService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;

        this.delegationService.query().subscribe((res: ResponseWrapper) => { this.sysvilles = res.json }, (res: ResponseWrapper) => this.onError(res.json));

        this.sysGouvernoratService.query().subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.reparateur.gouvernorat == null) {
            this.reparateur.gouvernorat = new Governorate();
        }
        this.refMarqueService.query()
            .subscribe((res: ResponseWrapper) => { this.refmarques = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refModeReglementService.query().
            subscribe((res: ResponseWrapper) => { this.refModeReglements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.reparateurService.queryrefAgeVehicule().
            subscribe((res: ResponseWrapper) => { this.refAgeVehicules = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.sysActionUtilisateurService.find(202)
            .subscribe((subRes: SysActionUtilisateur) => {
                this.SysAction = subRes;
                this.motifs = this.SysAction.motifs
            });
        this.dropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'libelle',
            selectAllText: 'Select tous',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 6,
            maxHeight: 179,
            badgeShowLimit: 300,
            allowSearchFilter: true
        };
        this.dropdownSettingsForGarantieImplique = {
            singleSelection: false,
            idField: 'id',
            textField: 'companyName',
            selectAllText: 'Select tous',
            unSelectAllText: 'UnSelect All',
            maxHeight: 179,
            itemsShowLimit: 6,
            allowSearchFilter: true
        };
        this.initReparateur();
        this.refModeGestionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sinisterTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.clientService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clients = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }


    initReparateur() {
        this.route.params.subscribe((params) => {
            if (params && params['id'] !== undefined) {
                this.reparateurService
                    .find(params['id'])
                    .subscribe((reparateur: Reparateur) => {
                        this.formVisAVis = false;
                        this.reparateur = reparateur;
                        const dateDebutConventiion = new Date(this.reparateur.dateEffetConvention);
                        const dateFinConvention = new Date(this.reparateur.dateFinConvention);
                        const dateDebutBlocage = new Date(this.reparateur.dateDebutBlocage);
                        const dateFinBlocage = new Date(this.reparateur.dateFinBlocage);
                        if (dateDebutConventiion) {
                            this.reparateur.dateEffetConvention = {
                                year: dateDebutConventiion.getFullYear(),
                                month: dateDebutConventiion.getMonth() + 1,
                                day: dateDebutConventiion.getDate()
                            };
                        }
                        if (dateFinConvention) {
                            this.reparateur.dateFinConvention = {
                                year: dateFinConvention.getFullYear(),
                                month: dateFinConvention.getMonth() + 1,
                                day: dateFinConvention.getDate()
                            };
                        }
                        if (reparateur.dateDebutBlocage) {
                            this.reparateur.dateDebutBlocage = {
                                year: dateDebutBlocage.getFullYear(),
                                month: dateDebutBlocage.getMonth() + 1,
                                day: dateDebutBlocage.getDate()
                            };
                        }
                        if (reparateur.dateFinBlocage) {
                            this.reparateur.dateFinBlocage = {
                                year: dateFinBlocage.getFullYear(),
                                month: dateFinBlocage.getMonth() + 1,
                                day: dateFinBlocage.getDate()
                            };
                        }
                        if (reparateur.orientations) {
                            this.orientations = reparateur.orientations;
                        }
                        if (reparateur.visAViss) {
                            reparateur.visAViss.forEach(visAVis => {
                                if (visAVis.isGerant == true) {
                                    this.gerant = visAVis;
                                    reparateur.visAViss.forEach((item, index) => {
                                        if (item === visAVis) reparateur.visAViss.splice(index, 1);
                                    });
                                }
                            });
                            this.visAViss = reparateur.visAViss;
                        }
                        if (this.reparateur.villeId != null) {
                            this.findGouvernoratOfVille(this.reparateur.villeId)
                            this.gov = false;
                        }
                        this.reparateurService.findjournal(params['id']).subscribe((res: Journal) => {
                            res.motifs.forEach(element => {
                                this.refMotifService.find(element.id).subscribe((res: RefMotif) => {
                                    this.refMotif = res;
                                    this.idMotif = res.id;
                                });
                            });
                        });
                        if (reparateur.garantieImpliques != null) {
                            reparateur.garantieImpliques.forEach(garantieImplique => {
                                garantieImplique.listRefModeGestion = '';
                                garantieImplique.listRefCompagnie = '';
                                garantieImplique.refModeGestions.forEach(refModeGestion => {
                                    garantieImplique.listRefModeGestion = garantieImplique.listRefModeGestion + '/' + refModeGestion.libelle;
                                });
                                garantieImplique.listRefCompagnie = garantieImplique.partnerName;
                            });
                            this.garantieImpliques = reparateur.garantieImpliques;
                            this.dtTriggerGarantieImplique.next();
                            if (reparateur.orientations != null) {
                                reparateur.orientations.forEach(orientation => {
                                    orientation.listMarque = '';
                                    orientation.refMarques.forEach(refMarque => {
                                        orientation.listMarque = orientation.listMarque + '/' + refMarque.label;
                                    });
                                });
                                this.orientations = reparateur.orientations;
                            }
                            // Reload garantieImpliques list
                        }
                        if (reparateur.specialitePrincipales != null) {
                            reparateur.specialitePrincipales.forEach(specialitePrincipal => {
                                this.marquePrincipale += specialitePrincipal.label + '/';
                            });
                        }
                        if (reparateur.specialiteSecondaires != null) {
                            reparateur.specialiteSecondaires.forEach(specialitePrincipal => {
                                this.autreMarque += specialitePrincipal.label + '/';
                            });
                        }
                    });
            }
        });
    }

    selectHistory(id) {
        console.log("premier logggggg" + id);
        this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id, "Reparateur");
        this.ngbModalRef.result.then((result: any) => {
            if (result !== null && result !== undefined) {
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });
    }


    addOrientation() {
        if (this.ajoutOrientation == false) {
            this.reparateurService.findRefAgeVehicule(this.orientation.refAgeVehiculeId).subscribe(
                (res: RefAgeVehicule) => {
                    this.refAgeVehicule = res;
                    this.orientation.listMarque = '';
                    this.orientation.refMarques.forEach(refMarque => {
                        this.orientation.listMarque += '/' + refMarque.label;
                    });
                    this.orientation.refAgeVehiculeValeur = this.refAgeVehicule.valeur;
                    this.orientations.push(this.orientation);
                    this.orientation = new Orientation();
                    this.orientation.refMarques = [];
                });
        }
        else {
            this.reparateurService.findRefAgeVehicule(this.orientation.refAgeVehiculeId).subscribe(
                (res: RefAgeVehicule) => {
                    this.refAgeVehicule = res;
                    this.orientation.listMarque = '';
                    this.orientation.refMarques.forEach(refMarque => {
                        this.orientation.listMarque += '/' + refMarque.label;
                    });
                    this.orientation.refAgeVehiculeValeur = this.refAgeVehicule.valeur;
                    this.orientation = new Orientation();
                    this.orientation.refMarques = [];
                });
        }
        this.formOrientation = false;
    }
    deleteOrientation(orientation) {
        this.orientations.forEach((item, index) => {
            if (item === orientation) this.orientations.splice(index, 1);
        });
    }
    editOrientation(orientation) {
        this.ajoutOrientation = true;
        this.orientation = orientation;
        this.formOrientation = true;
    }
    addFormOrientation() {
        this.formOrientation = true;
        this.ajoutOrientation = false;
        this.orientation = new VisAVis();
    }
    addVisAVis() {
        if (this.editVisaVis == false) {
            this.visAViss.push(this.visAVis);
            this.visAVis = new VisAVis();
        }
        if (this.editVisaVis == true) {
            this.visAVis = new VisAVis();
        }
        this.formVisAVis = false;
    }
    deleteVisAVis(visAVis) {
        this.visAViss.forEach((item, index) => {
            if (item === visAVis) this.visAViss.splice(index, 1);
        });
    }
    editVisAVis(visAVis) {
        this.editVisaVis = true;
        this.visAVis = visAVis;
        this.formVisAVis = true;
    }



    newformGaranties() {
        this.formGarantie = true;
        this.editGarantieImplique = false;
        this.garantieImplique = new GarantieImplique();
    }
    deleteGrantieImplique(garantieImpliqueTODelete) {
        this.garantieImpliques.forEach((item, index) => {
            if (item === garantieImpliqueTODelete) this.garantieImpliques.splice(index, 1);
        });
    }

    editGrantieImplique(garantieImplique) {
        this.editGarantieImplique = true;
        this.garantieImplique = garantieImplique;
        this.formGarantie = true;
    }

    onItemSelect(item: any) {
        console.log(item);
    }
    onSelectAll(items: any) {
        console.log(items);
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

    findGouvernoratOfVille(idVille) {
        this.delegationService.find(idVille).subscribe((res: Delegation) => {
            this.sysVille = res;
            this.sysGouvernoratService.find(this.sysVille.governorateId).subscribe((subRes: Governorate) => {
                this.reparateur.gouvernorat = subRes
            })
        }
        )
    }
    fetchDelegationsByGovernorate(id) {
        this.sysGouvernoratService.find(id).subscribe((subRes: Governorate) => {
            this.sysGouvernorat = subRes;
            this.delegationService.findByGovernorate(this.sysGouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.sysvilles = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.reparateur.villeId = subRes1[0].id;
                }

            });
        });
        this.gov = false;
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

    trimVisAVisNumber() {
        this.visAVis.nom = this.visAVis.nom.trim();
        this.visAVis.prenom = this.visAVis.prenom.trim();
    }
    trimGerantNumber() {
        this.gerant.nom = this.visAVis.nom.trim();
        this.gerant.prenom = this.visAVis.prenom.trim();
    }
    trackGrantiesId(index: number, item: RefModeGestion) {
        return item.id;
    }
    trackVehicleBrandById(index: number, item: VehicleBrand) {
        return item.id;
    }
    trackSysVilleById(index: number, item: Delegation) {
        return item.id;
    }
    trackGouvernoratById(index: number, item: Governorate) {
        return item.id;
    }
    trackGarantieImpliqueById(index: number, item: GarantieImplique) {
        return item.id;
    }
    trackAgeVehiculesById(index: number, item: RefAgeVehicule) {
        return item.id;
    }
    trackVisAVissId(index: number, item: VisAVis) {
        return item.id;
    }
    trackReglementById(index: number, item: Reglement) {
        return item.id;
    }
}
