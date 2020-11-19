import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Expert, GarantieImplique } from './expert.model';
import { RefModeReglement, RefModeReglementService } from '../ref-mode-reglement';
import { ExpertPopupService } from './expert-popup.service';
import { ExpertService } from './expert.service';
import { VisAVis } from '../vis-a-vis';
import { Reglement } from '../reglement';
import { RefModeGestionService, RefModeGestion } from './../ref-mode-gestion';
import { ResponseWrapper } from '../../shared';
import { PartnerService } from './../partner/partner.service';
import { Partner } from './../partner/partner.model';
import { Governorate } from '../governorate/governorate.model';
import { GovernorateService } from '../governorate/governorate.service';
import { Delegation, DelegationService } from '../delegation';
import { SysActionUtilisateur, SysActionUtilisateurService } from '../sys-action-utilisateur';
import { RefMotif, RefMotifService } from '../ref-motif';
import { Subject } from 'rxjs';
import { Journal } from '../journal';
import { GaDatatable, Global } from '../../constants/app.constants';
import { expertPopupRoute } from './expert.route';
import { ConfirmationDialogService } from '../../shared';
@Component({
    selector: 'jhi-expert-dialog',
    templateUrl: './expert-dialog.component.html'
})
export class ExpertDialogComponent implements OnInit {
    delegation: Delegation;
    expert: Expert = new Expert();
    garantieImplique: GarantieImplique = new GarantieImplique();
    garantieImpliques: GarantieImplique[] = [];
    isSaving: boolean;
    dropdownList = [];
    editGarantieImplique: boolean = false;
    editGarantie: boolean = false;
    grantTimingList = [];
    dropdownSettings = {};
    dropdownSettingsForGarantieImplique = {};
    govs: boolean = false;
    formGarantie: boolean = true;
    serviceTypeDropdownSettings = {};
    selectedItems = [];
    refMotifOption: any;
    reparateur: any;
    isExpNameFTUSAUnique = true;
    governorates: any;
    governorate: Governorate;
    listeGouvernorats: Governorate[] = [];
    gouvernorats: Governorate[] = [];
    clients: Partner[];
    client: Partner;
    sinisterTypes: RefModeGestion[] = [];
    dtTriggerGarantieImplique: Subject<GarantieImplique> = new Subject();
    dtOptions: any = {};
    SysAction: any;
    gov: boolean = true;
    delegations: Delegation[] = [];
    villes: Delegation[] = [];
    villesAux: Delegation[] = [];
    partners: Partner[];
    textPattern = Global.textPattern;
    constructor(
        private alertService: JhiAlertService,
        private route: ActivatedRoute,
        private expertService: ExpertService,
        private governorateService: GovernorateService,
        private refModeReglementService: RefModeReglementService,
        private eventManager: JhiEventManager,
        private partnerService: PartnerService,
        private refMotifService: RefMotifService,
        private confirmationDialogService: ConfirmationDialogService,
        private delegationService: DelegationService,
        private refModeGestionService: RefModeGestionService,
        private sysActionUtilisateurService: SysActionUtilisateurService,
        private clientService: PartnerService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.listeGouvernorats = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.expert.governorate == null) {
            this.expert.governorate = new Governorate()
        }
        this.dropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'label',
            selectAllText: 'Select tous',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 6,
            allowSearchFilter: true
        };
        this.serviceTypeDropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'libelle',
            selectAllText: 'Select tous',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 6,
            allowSearchFilter: true
        };
        this.dropdownSettingsForGarantieImplique = {
            singleSelection: false,
            idField: 'id',
            textField: 'companyName',
            selectAllText: 'Select tous',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 6,
            allowSearchFilter: true
        };
        this.villes = this.delegations;
        this.initExpert();
        this.refModeGestionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sinisterTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.partnerService.findAllCompanies().subscribe(
            (res: ResponseWrapper) => {
                this.clients = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    initExpert() {
        this.route.params.subscribe((params) => {
            if (params && params['id'] !== undefined) {
                this.expertService
                    .find(params['id'])
                    .subscribe((expert: Expert) => {
                        this.expert = expert;
                        const dateDebutBlocage = new Date(this.expert.debutBlocage);
                        const dateFinBlocage = new Date(this.expert.finBlocage);
                        if (this.expert.dateEffetConvention != null && this.expert.dateEffetConvention != undefined) {
                            const dateDebutConventiion = new Date(this.expert.dateEffetConvention);
                            this.expert.dateEffetConvention = {
                                year: dateDebutConventiion.getFullYear(),
                                month: dateDebutConventiion.getMonth() + 1,
                                day: dateDebutConventiion.getDate()
                            };
                        }
                        if (this.expert.dateFinConvention != null && this.expert.dateFinConvention != undefined) {
                            const dateFinConvention = new Date(this.expert.dateFinConvention);
                            this.expert.dateFinConvention = {
                                year: dateFinConvention.getFullYear(),
                                month: dateFinConvention.getMonth() + 1,
                                day: dateFinConvention.getDate()
                            };
                        }
                        if (expert.garantieImpliques != null) {

                            this.clients = [];
                            this.clientService.findAllCompanies().subscribe(
                                (res: ResponseWrapper) => {
                                    this.clients = res.json;

                                    this.garantieImpliques.forEach((element) => {
                                        this.clients.forEach((item, index) => {
                                            if (item.id === element.partnerId) this.clients.splice(index, 1);
                                        });
                                    });
                                },
                                (res: ResponseWrapper) => this.onError(res.json)
                            );
                            this.formGarantie = false;
                            expert.garantieImpliques.forEach(garantieImplique => {
                                garantieImplique.listRefCompagnie = ' ';
                                garantieImplique.listRefModeGestion = ' ';
                                garantieImplique.refModeGestions.forEach(refModeGestion => {
                                    garantieImplique.listRefModeGestion += '/' + refModeGestion.libelle;
                                });
                                garantieImplique.listRefCompagnie = garantieImplique.partnerName;

                            });
                            this.garantieImpliques = expert.garantieImpliques;
                            this.dtTriggerGarantieImplique.next();
                            // Reload garantieImpliques list
                        }
                        if (expert.debutBlocage) {
                            this.expert.debutBlocage = {
                                year: dateDebutBlocage.getFullYear(),
                                month: dateDebutBlocage.getMonth() + 1,
                                day: dateDebutBlocage.getDate()
                            };
                        }
                        if (expert.finBlocage) {
                            this.expert.finBlocage = {
                                year: dateFinBlocage.getFullYear(),
                                month: dateFinBlocage.getMonth() + 1,
                                day: dateFinBlocage.getDate()
                            };
                        }

                        if (expert.listeVilles) {
                            this.expert.listeVilles = expert.listeVilles;
                            this.villes = [];
                            this.expert.gouvernorats.forEach(gouvernorat => {
                                this.delegationService.findByGovernorate(gouvernorat.id).subscribe((subRes1: Delegation[]) => {
                                    this.villes = this.villes.concat(subRes1);
                                });
                            });
                        }

                        if (this.expert.delegationId != null) {
                            this.findGouvernoratOfVille(this.expert.delegationId);
                            this.gov = false;
                            this.fetchDelegationsByGovernorate(this.expert.governorate.id);
                        }

                    });
            }
        });
    }



    findByExpNameFTUSA() {
        if (this.expert.numeroFTUSA !== null && this.expert.numeroFTUSA !== undefined && this.expert.numeroFTUSA !== '' && this.expert.raisonSociale !== null && this.expert.raisonSociale !== undefined && this.expert.numeroFTUSA !== '') {
            this.expertService.findByExpNameFTUSA(this.expert.numeroFTUSA, this.expert.raisonSociale).subscribe((subRes: Expert) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.expert.id) {
                    this.isExpNameFTUSAUnique = false;
                    this.alertService.error('auxiliumApp.expert.uniqueFTUSA', null, null);
                } else {
                    this.isExpNameFTUSAUnique = true;
                }
            });
        }
    }
    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregister ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.isSaving = true;
                    this.expert.garantieImpliques = this.garantieImpliques;
                    if (this.expert.id !== undefined) {
                        this.subscribeperToSaveResponse(
                            this.expertService.update(this.expert));
                    } else {
                        this.subscribeperToSaveResponse(
                            this.expertService.create(this.expert));
                    }
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }
    private subscribeperToSaveResponse(result: Observable<Expert>) {
        result.subscribe((res: Expert) =>
            this.onRepSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onRepSaveSuccess(result: Expert) {
        this.eventManager.broadcast({ name: 'expertListModification', content: 'OK' });
        this.router.navigate(['../../expert'])
        this.isSaving = false;
    }


    // Agent profile
    onCompanySelect(companyId: number) {
        this.sinisterTypes = [];
        // Get list of agent by compagnie
        if (companyId != null) {
            this.clientService.find(companyId).subscribe((res: Partner) => { this.garantieImplique.partnerName = res.companyName })
            this.refModeGestionService.findModesGestionByClient(companyId).subscribe(
                (res: ResponseWrapper) => {
                    this.sinisterTypes = res.json;
                    var cache = {};
                    this.sinisterTypes = this.sinisterTypes.filter(function (elem) {
                        return cache[elem.libelle] ? 0 : cache[elem.libelle] = 1;
                    })
                },

                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
    }
    addGrantieImplique(id: number) {

        this.partners = this.clients;
        this.clients = [];
        this.partners.forEach((item) => {
            if (item.id != id) this.clients.push(item);
        });

        this.garantieImplique.listRefModeGestion = ' ';
        this.garantieImplique.listRefCompagnie = ' ';
        this.garantieImplique.listRefCompagnie = this.garantieImplique.partnerName;

        if (this.garantieImplique.refModeGestions) {
            this.garantieImplique.refModeGestions.forEach(refModeGestion => {
                this.garantieImplique.listRefModeGestion += '/' + refModeGestion.libelle;
            });
        }
        if (this.editGarantieImplique == false) {
            this.garantieImpliques.push(this.garantieImplique);
        }
        this.garantieImplique = new GarantieImplique();
        this.garantieImplique.refModeGestions = [];
        this.editGarantieImplique = false
        this.formGarantie = false;

    }

    deleteGrantieImplique(garantieImpliqueTODelete) {
        this.garantieImpliques.forEach((item, index) => {
            if (item === garantieImpliqueTODelete) this.garantieImpliques.splice(index, 1);
        });
        this.clients = [];
        this.clientService.findAllCompanies().subscribe(
            (res: ResponseWrapper) => {
                this.clients = res.json;
                this.garantieImpliques.forEach((element) => {
                    this.clients.forEach((item, index) => {
                        if (item.id === element.partnerId) this.clients.splice(index, 1);
                    });
                });
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    editGrantieImplique(garantieImplique) {
        this.clientService.find(garantieImplique.partnerId).subscribe((res: Partner) => {
            this.clients.push(res);
            this.formGarantie = true;
            this.editGarantieImplique = true;
            this.garantieImplique = garantieImplique;
        })
    }

    newformGaranties() {
        this.formGarantie = true;
        this.editGarantieImplique = false;
        this.garantieImplique = new GarantieImplique();
    }
    onItemSelect(item: any) {
        this.villes = [];
        this.expert.gouvernorats.forEach(gouvernorat => {
            this.delegationService.findByGovernorate(gouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.villes = this.villes.concat(subRes1);
            });
        });
        this.govs = true;
    }
    onItemDeSelect(items: any) {
        this.villes = [];
        this.expert.gouvernorats.forEach(gouvernorat => {
            this.delegationService.findByGovernorate(gouvernorat.id).subscribe((subRes1: Delegation[]) => {
                this.villes = this.villes.concat(subRes1);
            });
        });
        if (this.expert.gouvernorats.length > 0) {
            this.govs = true;
        } else this.govs = false;


    }
    onSelectAll(items: any) {
        this.villes = [];
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.villes = res.json }, (res: ResponseWrapper) => this.onError(res.json));
        this.govs = true;
    }

    onItemDeSelectAll(items: any) {
        this.villes = [];
        this.govs = false;
    }
    trimServiceTypeNumber() {
        this.expert.raisonSociale = this.expert.raisonSociale.trim();
        this.expert.nom = this.expert.nom.trim();
        this.expert.prenom = this.expert.prenom.trim();
    }
    findGouvernoratOfVille(idVille) {
        this.delegationService.find(idVille).subscribe((res: Delegation) => {
            this.delegation = res;
            this.governorateService.find(this.delegation.governorateId).subscribe((subRes: Governorate) => {
                this.expert.governorate = subRes
            })
        }
        )
    }
    fetchDelegationsByGovernorate(id) {
        this.governorateService.find(id).subscribe((subRes: Governorate) => {
            this.governorate = subRes;
            this.delegationService.findByGovernorate(this.governorate.id).subscribe((subRes1: Delegation[]) => {
                this.delegations = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.expert.delegationId = subRes1[0].id;
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

    trackGrantiesId(index: number, item: RefModeGestion) {
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

}
@Component({
    selector: 'jhi-expert-popup',
    template: ''
})
export class ExpertPopupComponent implements OnInit, OnDestroy {
    routeSub: any;
    constructor(
        private route: ActivatedRoute,
        private expertPopupService: ExpertPopupService
    ) { }
    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.expertPopupService
                    .open(ExpertDialogComponent as Component, params['id']);
            } else {
                this.expertPopupService
                    .open(ExpertDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
