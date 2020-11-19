import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { DomSanitizer } from '@angular/platform-browser';
import { Principal, User, UserService, ResponseWrapper, ConfirmationDialogService, AlertUtil } from '../../shared';
import { ReparateurService } from '../../entities/reparateur/reparateur.service';
import { ExpertService } from '../../entities/expert/expert.service';
import { AgentGeneralService } from '../../entities/agent-general/agent-general.service';
import { RefModeGestionService } from '../../entities/ref-mode-gestion/ref-mode-gestion.service';
import { RefModeGestion } from '../../entities/ref-mode-gestion/ref-mode-gestion.model';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Rx';
import { UserReslationService } from '../../shared/auth/user-relation.service';
import { UserRelation } from '../../shared/auth/user-relation.model';
import { ProfileAccessService, ProfileAccess, EntityProfileAccess, UserAccessWork } from '../../entities/profile-access';
import { UserProfileService, UserProfile } from '../../entities/user-profile';
import { UserExtraService, UserExtra, ViewUser } from '../../entities/user-extra';
import { GaDatatable } from './../../constants/app.constants';
import { BusinessEntity, BusinessEntityService } from '../../entities/business-entity';
import { Partner } from '../../entities/partner/partner.model';
import { ConventionService } from '../../entities/convention/convention.service';
import { PartnerModeMapping } from '../../entities/user-partner-mode/partner-mode-mapping.model';
import { UserPartnerMode } from '../../entities/user-partner-mode/user-partner-mode.model';
import { PartnerService } from '../../entities/partner/partner.service';
import { Reparateur } from '../../entities/reparateur/reparateur.model';
import { Expert } from '../../entities/expert/expert.model';
import { AgencyService } from '../../entities/agency/agency.service';
import { Agency } from '../../entities/agency/agency.model';
import { UserPartnerModeService } from '../../entities/user-partner-mode/user-partner-mode.service';
import { DataTableDirective } from 'angular-datatables';

@Component({
    selector: 'jhi-user-mgmt-dialog',
    templateUrl: './user-management-dialog.component.html'
})
export class UserMgmtDialogComponent implements OnInit, OnDestroy {
    currentAccount: any;
    error: any;
    success: any;

    selectedRole: any;
    authorities: any[] = [];

    selectedProfil: any;
    auth: any;
    userSaved: User;
    isSaving: boolean;
    isUpdating = false;
    eventSubscriber: Subscription;

    DropdownSettings = {};
    profilesSelected: UserProfile[] = [];
    profiles: UserProfile[] = [];
    profile: UserProfile = new UserProfile();

    // Added by issam
    user: User = new User();
    userExtra: UserExtra = new UserExtra();
    userProfilesSettings = {};
    userProfiles: UserProfile[] = [];
    selectedProfiles: UserProfile[] = [];
    profileAccesss: ProfileAccess[] = [];
    entityProfileAccesss: EntityProfileAccess[] = [];
    entityProfileAccess: EntityProfileAccess = new EntityProfileAccess();
    userAccessWork: UserAccessWork = new UserAccessWork();
    profileAccess: ProfileAccess = new ProfileAccess();
    // Added by Ridha
    selectedProfilesShow: any = {};
    selectedBrokers: Agency[] = [];
    
    userPartnerMode: UserPartnerMode = new UserPartnerMode();
    listUserPartnerMode: UserPartnerMode[] = [];
    DropdownSettingsCompagnie = {};
    DropdownSettingsMode = {};
    DropdownSettingsCourtier = {};
    
    pecResponsibles: UserExtra[] = [];
    brokers: any[] = [];

    reparators: Reparateur[] = [];
    experts: Expert[] = [];
    companies: Partner[] = [];
    generalAgents: any[] = [];
    selectedCompanyIds: number[] = [];
    compagnie: Partner = new Partner();
    mgmtModes: PartnerModeMapping[] = [];
    selectedMgmtModes: PartnerModeMapping[] = [];
    selectedCompanies: Partner[] = [];
    prestationDirectors: UserExtra[] = [];

    dtOptions: any = {};
    dtTrigger: Subject<User> = new Subject();


    constructor(
        private userService: UserService,
        private userExtraService: UserExtraService,
        private userProfileService: UserProfileService,
        private profileAccessService: ProfileAccessService,
        private reparateurService: ReparateurService,
        private expertService: ExpertService,
        private refModeGestionService: RefModeGestionService,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer,
        private confirmationDialogService: ConfirmationDialogService,
        private conventionService: ConventionService,
        private partnerService: PartnerService,
        private agencyService: AgencyService,
        private userPartnerModeService: UserPartnerModeService,
        private route: ActivatedRoute,
        private router: Router
    ) {

    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if (params['id']) { // Update
                this.getUserId(+params['id']);
            } else { // create
                this.user.userExtra = this.userExtra;
            }
        });
        
        this.DropdownSettings = {
            singleSelection: false,
            idField: "id",
            textField: "libelle",
            enableCheckAll: true,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };
        this.userProfilesSettings = {
            singleSelection: false,
            idField: "id",
            textField: "label",
            enableCheckAll: false,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };
        // Added by Ridha
        this.DropdownSettingsCompagnie = {
            singleSelection: false,
            idField: "id",
            textField: "companyName",
            enableCheckAll: false,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };
        this.DropdownSettingsMode = {
            singleSelection: false,
            idField: "partnerMode",
            textField: "labelPartnerMode",
            enableCheckAll: false,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };
        this.DropdownSettingsCourtier = {
            singleSelection: false,
            idField: "id",
            textField: "name",
            enableCheckAll: false,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };
        this.selectedProfilesShow = {
            directeurPrestation: false,
            responsablePEC: false,
            chargeClient: false,
            courtier: false,
            compagnie: false,
            agentAssurance: false,
            expert: false,
            reparateur: false
        }
        this.isSaving = false;

        this.principal.identity().then((account) => {
            this.currentAccount = account;

        });

        this.userProfileService.query().subscribe((res: ResponseWrapper) => { this.userProfiles = res.json; });
        this.profileAccessService.query().subscribe((res: ResponseWrapper) => { this.profileAccesss = res.json; });
    }

    /**
    *  When profile selected
    * @param profileId 
    */
    onProfilSelect(profileId: number) {
        console.log("profileId idd----" + profileId);
        this.selectedProfilesShow.directeurPrestation = false,
        this.selectedProfilesShow.responsablePEC = false,
        this.selectedProfilesShow.chargeClient = false,
        this.selectedProfilesShow.courtier = false,
        this.selectedProfilesShow.compagnie = false,
        this.selectedProfilesShow.agentAssurance = false,
        this.selectedProfilesShow.expert = false,
        this.selectedProfilesShow.expertiseCenter = false,
        this.selectedProfilesShow.reparateur = false,

        this.listUserPartnerMode = [];
        
        this.selectedBrokers = [];
        this.user.userExtra.personId = undefined;
        this.mgmtModes = [];
        this.selectedMgmtModes = [];
        this.selectedCompanyIds = [];
        this.compagnie = new Partner();
        this.generalAgents = [];
        this.selectedCompanies = [];
        this.prestationDirectors = [];
        this.companies = [];

        switch (profileId) {
            case 4: this.selectedProfilesShow.directeurPrestation = true;
                // Get list of compagnies
                this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                    this.companies = res.json;
                });
                break;
            case 5: this.selectedProfilesShow.responsablePEC = true;
                this.userExtraService.findByProfil(4).subscribe((res: UserExtra[]) => {
                    this.prestationDirectors = res;
                });
                break;
            case 6: 
            case 7:     
            case 8: this.selectedProfilesShow.chargeClient = true;
                this.userExtraService.findByProfil(5).subscribe((res: UserExtra[]) => {
                    this.pecResponsibles = res;
                });
                break;
            case 23: this.selectedProfilesShow.courtier = true;
                // Get list of compagnies
                this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                    this.companies = res.json;
                });
                break;
            case 25: this.selectedProfilesShow.compagnie = true;
                // Get list of compagnies
                this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                    this.companies = res.json;
                });

                break;
            case 24: this.selectedProfilesShow.agentAssurance = true;
                // Get list of compagnies
                this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                    this.companies = res.json;
                });
                break;
            case 26: this.selectedProfilesShow.expertiseCenter = true;
                // Get list of compagnies
                this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                    this.companies = res.json;
                });
                break;
            case 27: this.selectedProfilesShow.expert = true;
                // Get list of experts
                this.expertService.query().subscribe((res: ResponseWrapper) => {
                    this.experts = res.json;
                });
                break;
            case 28: this.selectedProfilesShow.reparateur = true;
                // Get list of reparateurs
                this.reparateurService.query().subscribe((res: ResponseWrapper) => {
                    this.reparators = res.json;
                });
                break;
            }
    }

    // Agent profile
    onCompanySelect(companyId: number) {
        this.generalAgents = [];
        this.mgmtModes = [];
        this.selectedMgmtModes = [];
        this.selectedCompanyIds = [];
        this.user.userExtra.personId = undefined;
        
        this.listUserPartnerMode = [];        
        
        // Get list of agent by compagnie
        if (companyId != null) {
            // Get company agents
            this.agencyService.findAgencyByPartnerAndType(companyId, "Agent").subscribe((res: ResponseWrapper) => {
                this.generalAgents = res.json;
                this.selectedCompanyIds.push(companyId);
                // Get company mgmt modes
                if (this.generalAgents.length > 0 && this.selectedCompanyIds.length > 0) {
                    this.conventionService.findPackByPartner(this.selectedCompanyIds).subscribe((res) => {
                        this.mgmtModes = res;

                        var cache = {};
                        this.mgmtModes = this.mgmtModes.filter(function (elem) {
                            return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                        })
                    });
                }
            });
        }
    }

    onMultiCompanySelect(companyItem: any) {
        this.selectedCompanyIds = [];
        this.mgmtModes = [];
        let refreshMgmtModes = [];
        this.user.userExtra.personId = undefined;
        switch (this.user.userExtra.profileId) {
            case 4 : // Directeur Prestation
                for (let i = 0; i < this.selectedCompanies.length; i++) {
                    this.selectedCompanyIds.push(this.selectedCompanies[i].id);
                }
                if (this.selectedCompanyIds.length > 0) {
                    this.conventionService.findPackByPartner(this.selectedCompanyIds).subscribe((res) => {
                        this.mgmtModes = res;
                        var cache = {};
                        this.mgmtModes = this.mgmtModes.filter(function (elem) {
                            return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                        })

                        for(let i = 0; i < this.selectedMgmtModes.length; i++) {
                            for(let j = 0; j < this.mgmtModes.length; j++) {
                                if (this.mgmtModes[j].partnerMode === this.selectedMgmtModes[i].partnerMode) {
                                    refreshMgmtModes.push(this.selectedMgmtModes[i]);
                                    break;
                                }
                            }
                        }
                        this.selectedMgmtModes = refreshMgmtModes;
                    });
                } else {
                    this.selectedMgmtModes = [];
                }
            break;
            case 5 : // Responsable prestation
            case 6 : // Charge client
            case 7 : // Charge client
            case 8 : // Charge client
                for (let i = 0; i < this.selectedCompanies.length; i++) {
                    this.selectedCompanyIds.push(this.selectedCompanies[i].id);
                }
                if (this.selectedCompanyIds.length > 0) {
                    this.userExtraService.findModeByUserAndPartner(this.user.userExtra.userBossId, this.selectedCompanyIds).subscribe((res) => {
                        this.mgmtModes = res;
                        var cache = {};
                        this.mgmtModes = this.mgmtModes.filter(function (elem) {
                            return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                        })

                        for(let i = 0; i < this.selectedMgmtModes.length; i++) {
                            for(let j = 0; j < this.mgmtModes.length; j++) {
                                if (this.mgmtModes[j].partnerMode === this.selectedMgmtModes[i].partnerMode) {
                                    refreshMgmtModes.push(this.selectedMgmtModes[i]);
                                    break;
                                }
                            }
                        }
                        this.selectedMgmtModes = refreshMgmtModes;
                    });
                } else {
                    this.selectedMgmtModes = [];
                }
            break;
            case 23 : 
                for (let i = 0; i < this.selectedCompanies.length; i++) {
                    this.selectedCompanyIds.push(this.selectedCompanies[i].id);
                }

                if (this.selectedCompanyIds.length > 0) {
                    this.agencyService.findCourtierByPartnerAndType(this.selectedCompanyIds, "Courtier").subscribe((res: ResponseWrapper) => {
                        this.brokers = res.json;
                        if (this.brokers.length > 0) {
                            this.conventionService.findPackByPartner(this.selectedCompanyIds).subscribe((res) => {
                                this.mgmtModes = res;
                                var cache = {};
                                this.mgmtModes = this.mgmtModes.filter(function (elem) {
                                    return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                })

                                for(let i = 0; i < this.selectedMgmtModes.length; i++) {
                                    for(let j = 0; j < this.mgmtModes.length; j++) {
                                        if (this.mgmtModes[j].partnerMode === this.selectedMgmtModes[i].partnerMode) {
                                            refreshMgmtModes.push(this.selectedMgmtModes[i]);
                                            break;
                                        }
                                    }
                                }
                                this.selectedMgmtModes = refreshMgmtModes;
                            });
                        }
                    });
                } else {
                    this.selectedMgmtModes = [];
                }
            break;
        }
    }

    onPrestationDirectorSelect(prestationDirectorId: number) {
        // Get list of directors compagnies 
        this.companies = [];
        this.userExtraService.findCompagnieByUser(prestationDirectorId).subscribe((res: Partner[]) => {
            this.companies = res;
        });
    }

    onPecResponsible(pecResponsibleId: number) {
        // Get list of responsable compagnies 
        this.userExtraService.findCompagnieByUser(pecResponsibleId).subscribe((res: Partner[]) => {
            this.companies = res;
        });
    }

    /*****************************************************************************************************************************/
    onItemSelect(item: UserProfile) {
        // A profile added
        this.userAccessWork.profiles = this.selectedProfiles;
        this.userAccessWork.id = item.id;
        this.userAccessWork.addFlag = true;
        this.profileAccessService.treatEntityAccessByProfile(this.userAccessWork).subscribe((res: UserAccessWork) => {
            this.userAccessWork = res;
        });
    }

    onItemDeSelect(item: UserProfile) {
        // A profile added
        this.userAccessWork.profiles = this.selectedProfiles;
        this.userAccessWork.id = item.id;
        this.userAccessWork.addFlag = false;
        this.profileAccessService.treatEntityAccessByProfile(this.userAccessWork).subscribe((res: UserAccessWork) => {
            this.userAccessWork = res;
        });

    }

    /**
     * get role for selected user
     * @param id 
     */
    getUserId(id) {
        this.clear();
        this.user.userExtra = new UserExtra();
        this.userService.find(id).subscribe((subRes) => {
            this.user = subRes;
            this.selectedRole = this.user.authorities[0];
            this.userExtraService.findByUser(id).subscribe((subRes: UserExtra) => {
                this.user.userExtra = subRes;
                this.userAccessWork = subRes.userAccessWork;
                this.selectedProfiles = this.userAccessWork.profiles;
                console.log(this.user.userExtra);
                this.userAccessWork.entityProfileAccesss.forEach(entityProfileAccess => {
                  
                    entityProfileAccess.profileAccesss.forEach(profileAccess => {
                        if(profileAccess.profileIds== null || profileAccess.profileIds== undefined)
                            profileAccess.profileIds=[];
                        profileAccess.profileIds.push(profileAccess.profileId);

                    });
                });
                // Edit user relation
                const personId = this.user.userExtra.personId;
                this.onProfilSelect(this.user.userExtra.profileId);
                this.user.userExtra.personId = personId;
                if (this.user.userExtra.userPartnerModes.length > 0) {
                    for (let i = 0; i < this.user.userExtra.userPartnerModes.length; i++) {
                        this.selectedCompanyIds.push(this.user.userExtra.userPartnerModes[i].partnerId);
                    }
                }

                console.log(this.user.userExtra);
                const profileId = this.user.userExtra.profileId;
                switch (profileId) {
                    case 4: this.selectedProfilesShow.directeurPrestation = true;
                        console.log("directeur prestation*-*-*-");
                        this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                            this.companies = res.json;
                            this.userExtraService.findCompagnieByUser(this.user.userExtra.id).subscribe((res: Partner[]) => {
                                this.selectedCompanies = res;
                                this.conventionService.findPackByPartner(this.selectedCompanyIds).subscribe((res) => {
                                    this.mgmtModes = res;
                                    var cache = {};
                                    this.mgmtModes = this.mgmtModes.filter(function (elem) {
                                        return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                    })
                                    this.userExtraService.findModeByUserAndPartner(this.user.userExtra.id, this.selectedCompanyIds).subscribe((res: PartnerModeMapping[]) => {
                                        this.selectedMgmtModes = res;
                                    });
                                });
                            });
                        });
                    break;
                    case 5: this.selectedProfilesShow.responsablePEC = true;
                        this.userExtraService.findCompagnieByUser(this.user.userExtra.userBossId).subscribe((res: Partner[]) => {
                            this.companies = res;
                            this.userExtraService.findCompagnieByUser(this.user.userExtra.id).subscribe((res: Partner[]) => {
                                this.selectedCompanies = res;
                                this.userExtraService.findModeByUserAndPartner(this.user.userExtra.userBossId, this.selectedCompanyIds).subscribe((res) => {
                                    this.mgmtModes = res;
                                    var cache = {};
                                    this.mgmtModes = this.mgmtModes.filter(function (elem) {
                                        return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                    })
                                    this.userExtraService.findModeByUserAndPartner(this.user.userExtra.id, this.selectedCompanyIds).subscribe((res: PartnerModeMapping[]) => {
                                        this.selectedMgmtModes = res;
                                    });
                                });
                            });
                        });
                    break;  
                    case 6:
                    case 7:
                    case 8: this.selectedProfilesShow.chargeClient = true;
                        this.userExtraService.findCompagnieByUser(this.user.userExtra.userBossId).subscribe((res: Partner[]) => {
                            this.companies = res;
                            this.userExtraService.findCompagnieByUser(this.user.userExtra.id).subscribe((res: Partner[]) => {
                                this.selectedCompanies = res;
                                this.userExtraService.findModeByUserAndPartner(this.user.userExtra.userBossId, this.selectedCompanyIds).subscribe((res) => {
                                    this.mgmtModes = res;
                                    var cache = {};
                                    this.mgmtModes = this.mgmtModes.filter(function (elem) {
                                        return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                    })
                                    this.userExtraService.findModeByUserAndPartner(this.user.userExtra.id, this.selectedCompanyIds).subscribe((res: PartnerModeMapping[]) => {
                                        this.selectedMgmtModes = res;
                                    });
                                });
                            });
                        });
                    break;
                    case 23: this.selectedProfilesShow.courtier = true;
                        var cache = {};
                        this.selectedCompanyIds = this.selectedCompanyIds.filter(function (elem) {
                            return cache[elem] ? 0 : cache[elem] = 1;
                        })
                        this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                            this.companies = res.json;
                            this.userExtraService.findCompagnieByUser(this.user.userExtra.id).subscribe((res: Partner[]) => {
                                this.selectedCompanies = res;
                                this.agencyService.findCourtierByPartnerAndType(this.selectedCompanyIds, "Courtier").subscribe((res: ResponseWrapper) => {
                                    this.brokers = res.json;
                                    this.userPartnerModeService.findAgencyByUser(this.user.userExtra.id).subscribe((res: Agency[]) => {
                                        this.selectedBrokers = res;
                                        this.conventionService.findPackByPartner(this.selectedCompanyIds).subscribe((res) => {
                                            this.mgmtModes = res;
                                            var cache = {};
                                            this.mgmtModes = this.mgmtModes.filter(function (elem) {
                                                return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                            })
                                            this.userExtraService.findModeByUserAndPartner(this.user.userExtra.id, this.selectedCompanyIds).subscribe((res: PartnerModeMapping[]) => {
                                                this.selectedMgmtModes = res;
                                                var cache = {};
                                                this.selectedMgmtModes = this.selectedMgmtModes.filter(function (elem) {
                                                    return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                                })
                                            });
                                        });
                                    });
                                });
                            });
                        });
                        break;
                    case 24: this.selectedProfilesShow.agentAssurance = true;
                        // Get list of compagnies
                        console.log("agent général*-*-*-");
                        this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                            this.companies = res.json;
                            this.compagnie.id = this.user.userExtra.userPartnerModes[0].partnerId;
                            this.agencyService.findAgencyByPartnerAndType(this.compagnie.id, "Agent").subscribe((res: ResponseWrapper) => {
                                this.generalAgents = res.json;
                                this.user.userExtra.personId = this.user.userExtra.personId;
                                this.conventionService.findPackByPartner(this.selectedCompanyIds).subscribe((res) => {
                                    this.mgmtModes = res;
                                    var cache = {};
                                    this.mgmtModes = this.mgmtModes.filter(function (elem) {
                                        return cache[elem.partnerMode] ? 0 : cache[elem.partnerMode] = 1;
                                    })
                                    this.userExtraService.findModeByUserAndPartner(this.user.userExtra.id, this.selectedCompanyIds).subscribe((res: PartnerModeMapping[]) => {
                                        this.selectedMgmtModes = res;
                                    });
                                });
                            });
                        });
                    break;
                }
            });
        });
        this.isUpdating = true;
    }

    /**
     * save user
     */
    save() {
        // Save user and functionnality
        this.confirmationDialogService
            .confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    this.user.userExtra.userAccessWork = this.userAccessWork;
                    // Treat partners modes
                    this.listUserPartnerMode = [];
                    if(this.selectedMgmtModes && this.selectedMgmtModes.length > 0) {
                        for (let i = 0; i < this.selectedMgmtModes.length; i++) {
                            if(this.selectedProfilesShow.courtier) {
                                this.userPartnerMode = new UserPartnerMode();
                                this.userPartnerMode.courtierId = this.user.userExtra.personId;
                                this.userPartnerMode.partnerId = parseInt(this.selectedMgmtModes[i].partnerMode[0]);
                                if (this.selectedMgmtModes[i].partnerMode[2] != null && this.selectedMgmtModes[i].partnerMode[2] != undefined) {
                                    this.userPartnerMode.modeId = parseInt(this.selectedMgmtModes[i].partnerMode[1].concat(this.selectedMgmtModes[i].partnerMode[2]));
                                } else {
                                    this.userPartnerMode.modeId = parseInt(this.selectedMgmtModes[i].partnerMode[1]);
                                }
                                this.listUserPartnerMode.push(this.userPartnerMode);
                            } else {
                                this.userPartnerMode = new UserPartnerMode();
                                this.userPartnerMode.partnerId = parseInt(this.selectedMgmtModes[i].partnerMode[0]);
                                if (this.selectedMgmtModes[i].partnerMode[2] != null && this.selectedMgmtModes[i].partnerMode[2] != undefined) {
                                    this.userPartnerMode.modeId = parseInt(this.selectedMgmtModes[i].partnerMode[1].concat(this.selectedMgmtModes[i].partnerMode[2]));
                                } else {
                                    this.userPartnerMode.modeId = parseInt(this.selectedMgmtModes[i].partnerMode[1]);
                                }
                                this.listUserPartnerMode.push(this.userPartnerMode);
                            }
                        }
                    }

                    this.user.userExtra.userPartnerModes = this.listUserPartnerMode;

                    if (this.user.id !== null && this.user.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.userService.update(
                                this.user
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.userService.create(
                                this.user
                            )
                        );
                    }
                }

            });
    }


    private subscribeToSaveResponse(result) {
        result.subscribe(
            (res: Response) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.router.navigate(['../../user-management'])
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.isSaving = false;
        this.alertUtil.addError(error.message);
    }

    clear() {
        this.isUpdating = false;
        this.user = new User();
        this.userAccessWork = new UserAccessWork();
        this.selectedProfiles = [];
        this.selectedCompanies = [];
        this.selectedBrokers = [];
        this.prestationDirectors = [];
        this.pecResponsibles = [];
        this.brokers = [];
        this.reparators = [];
        this.experts = [];
        this.user.userExtra = new UserExtra();

        this.listUserPartnerMode = [];        
        this.user.userExtra.personId = undefined;
        this.mgmtModes = [];
        this.selectedMgmtModes = [];
        this.selectedCompanyIds = [];
        this.compagnie = new Partner();
        this.companies = [];
        this.generalAgents = [];
        this.selectedProfilesShow = {
            directeurPrestation: false,
            responsablePEC: false,
            chargeClient: false,
            courtier: false,
            compagnie: false,
            agentAssurance: false,
            expert: false,
            reparateur: false
        }

    }
    ngOnDestroy() {
        //this.routeData.unsubscribe();
        if (this.eventSubscriber !== null && this.eventSubscriber !== undefined)
            this.eventManager.destroy(this.eventSubscriber);
    }

    trackIdentity(index, item: User) {
        return item.id;
    }
}
