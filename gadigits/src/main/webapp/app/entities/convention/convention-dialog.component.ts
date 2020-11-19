import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { RefPack, SinisterTypeSetting, ApecSettings, Operator } from './../ref-pack/ref-pack.model';
import { RefExclusion } from './../ref-pack/ref-exclusion.model';
import { Convention } from './convention.model';
import { RefPackService } from './../ref-pack/ref-pack.service';
import { ConventionService } from './convention.service';
import { RefCompagnie } from './../ref-compagnie';
import { ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { RefNatureContrat } from './../ref-nature-contrat';
import { VehicleUsage } from './../vehicle-usage';
import { RefTypeServiceService } from './../ref-type-service/ref-type-service.service';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { VehicleUsageService } from './../vehicle-usage/vehicle-usage.service';
import { ReinsurerService } from './../reinsurer/reinsurer.service';
import { Reinsurer } from './../reinsurer/reinsurer.model';
import { RefModeGestionService, RefModeGestion } from './../ref-mode-gestion';
import { GaDatatable } from '../../constants/app.constants';
import { saveAs } from 'file-saver/FileSaver';
import { PartnerService } from './../partner/partner.service';
import { Partner } from './../partner/partner.model';

@Component({
    selector: "jhi-convention-dialog",
    templateUrl: "./convention-dialog.component.html"
})
export class ConventionDialogComponent implements OnInit {
    convention: Convention = new Convention(); // Principal entity
    refPack: RefPack = new RefPack(); // RefPack in edition or creation
    conventionId: number; // Id of the current convention
    isCreateMode = true;
    // For packs list
    dtOptions: any = {};
    dtTrigger: Subject<RefPack> = new Subject();
    // For pack and convention edition
    partners: Partner[];
    refPackEdit: RefPack = new RefPack();
    reinsurers: Reinsurer[];
    typeServices: RefTypeService[];
    refExclusionss: RefExclusion[];
    currentExclusion: RefExclusion;
    typeService: RefTypeService;
    usages: VehicleUsage[];
    sinisterTypes: RefModeGestion[];
    grantTimingList = [];
    isSaving: boolean;
    dropdownList = [];
    refExclusions: RefExclusion[]= [];
    selectedItems = [];
    dropdownSettings = {};
    dropdownSettingsUsages = {};
    serviceTypeDropdownSettings = {};
    disabledForm1 = false;
    packSettings = {
        mileage: false,
        interventionNumber: false,
        vrDaysNumber: false,
        ceilingToConsume: false,
        combinable: false,
        homeService: false,
        usages: false,
        modeGestions: false,
        exclusions: false,
        grantTimingList: false,
        price: false,
        companyPart: false,
        reinsurerPart: false,
        partnerPart: false,
        reinsurer: false
    };
    cumule = false;
    usage = false;
    tarif = false;
    timing = false;
    ceiling = false;
    sinster = false;
    selectedFiles: FileList;
    currentFileUpload: File;
    signedAgreementUrl: any;
    signedAgreementPreview = false;

    constructor(
        private alertService: JhiAlertService,
        private refPackService: RefPackService,
        private conventionService: ConventionService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
        private refTypeServiceService: RefTypeServiceService,
        private partnerService: PartnerService,
        private reinsurerService: ReinsurerService,
        private vehicleUsageService: VehicleUsageService,
        private refModeGestionService: RefModeGestionService,
        private confirmationDialogService: ConfirmationDialogService,
        private sanitizer: DomSanitizer
    ) {}

    ngOnInit() {
        this.disabledForm1 = false;
        this.isSaving = false;
        this.dtOptions = GaDatatable.defaultDtOptions;
        // this.refPack.convention = this.convention;
        this.route.params.subscribe((params) => {
            if (params && params['id'] !== undefined) {
                this.conventionId = + params['id']; // (+) converts string 'id' to a number
                this.conventionService
                    .find(this.conventionId)
                    .subscribe((convention: Convention) => {
                        this.convention = convention;
                        if (convention.startDate) {
                            this.convention.startDate = {
                                year: convention.startDate.getFullYear(),
                                month: convention.startDate.getMonth() + 1,
                                day: convention.startDate.getDate()
                            };
                        }
                        if (convention.endDate) {
                            this.convention.endDate = {
                                year: convention.endDate.getFullYear(),
                                month: convention.endDate.getMonth() + 1,
                                day: convention.endDate.getDate()
                            };
                        }
                        this.dtTrigger.next(); // Reload packs list
                    });
            }
        });

        this.reinsurerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reinsurers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.partnerService.query().subscribe((res: ResponseWrapper) => { this.partners = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.vehicleUsageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.usages = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.refTypeServiceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.typeServices = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.refPackService.queryExclusion().subscribe(
            (res: ResponseWrapper) => {
                this.refExclusionss = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.refModeGestionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sinisterTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.grantTimingList = [
            { id: "APEC", libelle: "APEC" },
            { id: "DPEC", libelle: "DPEC" }
        ];


        this.selectedItems = [];
        this.dropdownSettings = {
            singleSelection: false,
            idField: "id",
            textField: "libelle",
            enableCheckAll: false,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };
        this.dropdownSettingsUsages= {
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

        this.serviceTypeDropdownSettings = {
            singleSelection: false,
            idField: "id",
            textField: "nom",
            enableCheckAll: false,
            selectAllText: "Tous",
            unSelectAllText: "Tous",
            itemsShowLimit: 3,
            allowSearchFilter: true,
            searchPlaceholderText: "Rechercher"
        };

    }

    addExclusion() {
        console.log('_________________________________________ ');
        if(this.currentExclusion) {
            if(!this.refPack.exclusions) {
                this.refPack.exclusions = [];
            }
            this.refPack.exclusions.push(this.currentExclusion);
        }
    }

    deleteExclusion(exclusion) {
        console.log('_________________________________________ ');
        exclusion.deleted = true;
    }

    addForm1(){
        this.disabledForm1 = true;
        this.refPack = new RefPack();
        this.packSettings = {
            mileage: false,
            interventionNumber: false,
            vrDaysNumber: false,
            ceilingToConsume: false,
            combinable: false,
            homeService: false,
            usages: false,
            modeGestions: false,
            exclusions: false,
            grantTimingList: false,
            price: false,
            companyPart: false,
            reinsurerPart: false,
            partnerPart: false,
            reinsurer: false
        };

    }

    printFields() {
        this.packSettings = {
            mileage: false,
            interventionNumber: false,
            vrDaysNumber: false,
            ceilingToConsume: false,
            combinable: false,
            homeService: false,
            usages: false,
            modeGestions: false,
            exclusions: false,
            grantTimingList: false,
            price: false,
            companyPart: false,
            reinsurerPart: false,
            partnerPart: false,
            reinsurer: false
        };
        if (this.refPack.serviceTypes && this.refPack.serviceTypes.length > 0) {
            for (let i = 0; i < this.refPack.serviceTypes.length; i++) {
                const serviceTypeId = this.refPack.serviceTypes[i].id;
                switch (serviceTypeId) {
                    case 1:
                        this.packSettings.usages = true;
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        this.packSettings.usages = true;
                        this.packSettings.interventionNumber = true;
                        this.packSettings.mileage = true;
                        this.packSettings.combinable = true;
                        this.packSettings.price = true;
                        this.packSettings.exclusions = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    case 7:
                        this.packSettings.usages = true;
                        this.packSettings.price = true;
                        this.packSettings.exclusions = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    case 8:
                    case 9:
                    case 10:
                        this.packSettings.usages = true;
                        this.packSettings.price = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    case 11:
                        this.packSettings.usages = true;
                        this.packSettings.modeGestions = true;
                        this.packSettings.exclusions = true;
                        this.packSettings.price = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    case 12:
                        this.packSettings.usages = true;
                        this.packSettings.grantTimingList = true;
                        this.packSettings.homeService = true;
                        this.packSettings.vrDaysNumber = true;
                        this.packSettings.combinable = true;
                        this.packSettings.exclusions = true;
                        this.packSettings.price = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    case 13:
                        this.packSettings.usages = true;
                        this.packSettings.ceilingToConsume = true;
                        this.packSettings.exclusions = true;
                        this.packSettings.price = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    case 14:
                        this.packSettings.price = true;
                        this.packSettings.companyPart = true;
                        this.packSettings.reinsurerPart = true;
                        this.packSettings.partnerPart = true;
                        break;
                    default: console.log('Service type inconnu.');
                }
            }
            // populate exclusions list
            this.refExclusions = [];
            this.refExclusions = this.refExclusionss;
        }
    }

    onChangeReinsurerPart() {
        console.log("______________________________________________________________________");
        if(this.refPack.reinsurerPart !== null && this.refPack.reinsurerPart !== undefined) {
            this.packSettings.reinsurer = true;
        }
    }
    selectFile(event) {
        this.selectedFiles = event.target.files;
        const reader = new FileReader();
        reader.readAsDataURL(event.target.files[0]); // read file as data url
        reader.onload = () => { // called once readAsDataURL is completed
            this.signedAgreementPreview = true;
            this.signedAgreementUrl = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result.toString());
            console.log("iccciii loggg  file"+this.signedAgreementUrl);
            console.log("iccciii loggg  file"+reader.readAsDataURL(event.target.files[0]));
        }
    }
    downloadFile() {
        if (this.selectedFiles && this.selectedFiles.length > 0) {
            this.currentFileUpload = this.selectedFiles.item(0);
            console.log("iccciii loggg  downlod file"+this.currentFileUpload);
        }
        saveAs(this.currentFileUpload);
    }
    bloquePack(refPack) {
          if (!refPack.blocked) {
            refPack.blocked = true;
          }
         if (refPack.blocked) {
            refPack.blocked = false;
          }
      }
      deletePack(refPack) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer le pack ?', 'Oui', 'Non', 'lg' )
        .then(( confirmed ) => {
        console.log( 'User confirmed:', confirmed );
            if ( confirmed ) {
        this.convention.packs.forEach( (item, index) => {
            if(item === refPack) this.convention.packs.splice(index, 1);
          });
          this.dtTrigger.next();
         }})
        .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
     }
    onItemSelect(item: any) {
        this.printFields();
    }
    onItemDeSelect(item: any) {
        this.printFields();
    }

    onSinisterTypeDeSelect(item: RefModeGestion) {
        console.log(item);
        for (let i = 0; i < this.refPack.sinisterTypeSettings.length; i++) {
            const setting = this.refPack.sinisterTypeSettings[i];
            if (setting.sinisterTypeId === item.id) {
                this.refPack.sinisterTypeSettings.splice(i, 1);
                break;
            }
        }

        for (let i = 0; i < this.refPack.apecSettings.length; i++) {
            const apecSetting = this.refPack.apecSettings[i];
            if (apecSetting.mgntModeId === item.id) {
                this.refPack.apecSettings.splice(i, 1);
                break;
            }
        }        
    }

    onSinisterTypeSelect(item: RefModeGestion) {
        if (!this.refPack.sinisterTypeSettings) {
            this.refPack.sinisterTypeSettings = [];
        }
        // Add the new setting
        const setting = new SinisterTypeSetting();
        setting.sinisterTypeId = item.id;
        setting.label = item.libelle;
        setting.sinisterTypeLabel = item.libelle;
        this.refPack.sinisterTypeSettings.push(setting);

        if (!this.refPack.apecSettings) {
            this.refPack.apecSettings = [];
        }
        // Add the new setting
        const apecSettings = new ApecSettings();
        apecSettings.mgntModeId = item.id;
        apecSettings.mgntModeLabel = item.libelle;
        this.refPack.apecSettings.push(apecSettings);
    }

    /**
     * Save the current convention
     */
    save() {

        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir enregistrer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then(confirmed => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.isSaving = true;
                    if (this.convention.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.conventionService.update(
                                this.convention
                            )
                        );
                    } else {
                        if (
                            this.selectedFiles &&
                            this.selectedFiles.length > 0
                        ) {
                            this.currentFileUpload = this.selectedFiles.item(
                                0
                            );
                        }
                        this.subscribeToSaveResponse(
                            this.conventionService.create(
                                this.currentFileUpload,
                                this.convention
                            )
                        );
                    }
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    trackRefCompagnieById(index: number, item: RefCompagnie) {
        return item.id;
    }
    trackRefNatureContratById(index: number, item: RefNatureContrat) {
        return item.id;
    }
    trackUsageById(index: number, item: VehicleUsage) {
        return item.id;
    }

    /**
     * Cancel current operation on pack
     */
    cancel() {
        this.convention = new Convention();
        this.isCreateMode = true;
    }
    cancelPack() {
        this.disabledForm1 = false;
        this.refPack = new RefPack();
        this.refPackEdit = new RefPack();
        this.printFields();
    }
    /**
     * Edit a pack
     * @param refPack : the pack to edit
     */
    edit(refPack: RefPack) {
        this.disabledForm1 = true;
        this.refPack = refPack;
        this.refPackEdit = refPack;
        this.isCreateMode = false;
        this.printFields();
    }

    saveRefPack() {
        //this.refPack.convention = this.convention;
        if(this.refPack.exclusions && this.refPack.exclusions.length > 0) {
            let excls = [];
            for (let i = 0; i < this.refPack.exclusions.length; i++) {
                if(!this.refPack.exclusions[i].deleted) {
                    excls.push(this.refPack.exclusions[i]);
                }
            }
            this.refPack.exclusions = excls;
        }

        if (this.isCreateMode) {
            if (this.convention.packs === undefined) {
                this.convention.packs = [];
            }
            this.convention.packs.push(this.refPack);
            this.refPack = new RefPack();
            this.dtTrigger.next();
            this.disabledForm1 = false;

        } else {
            this.convention.packs.forEach( (item, index) => {
                if(item === this.refPackEdit) this.convention.packs.splice(index,1);
              });
            this.convention.packs.push(this.refPack);
            this.dtTrigger.next();
            this.isCreateMode = true;
            this.refPack = new RefPack();
            this.disabledForm1 = false;
        }
    }
    private subscribeToSaveResponse(result: Observable<Convention>) {
        result.subscribe(
            (res: Convention) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );

    }

    private onSaveSuccess(result: Convention) {
        this.eventManager.broadcast({
            name: "conventionListModification",
            content: "OK"
        });


        this.isSaving = false;
        this.router.navigate(["/convention"]);

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
}
