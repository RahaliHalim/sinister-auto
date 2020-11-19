import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { ConventionPackPopupService } from './convention-pack-popup.service';
import { RefPack, SinisterTypeSetting, ApecSettings } from './../ref-pack/ref-pack.model';
import { RefExclusion } from './../ref-pack/ref-exclusion.model';
import { ConventionAmendment } from './convention-amendment.model';
import { RefPackService } from './../ref-pack/ref-pack.service';
import { ConventionService } from './convention.service';
import { ConventionAmendmentService } from './convention-amendment.service';
import { RefPackPopupDetail } from './ref-pack-popup-detail';
import { ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { RefNatureContrat } from './../ref-nature-contrat';
import { VehicleUsage, VehicleUsageService } from './../vehicle-usage';
import { RefTypeServiceService } from './../ref-type-service/ref-type-service.service';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { Partner, PartnerService } from './../partner';
import { ReinsurerService } from './../reinsurer/reinsurer.service';
import { Reinsurer } from './../reinsurer/reinsurer.model';
import { RefModeGestionService, RefModeGestion } from './../ref-mode-gestion';
import { GaDatatable } from '../../constants/app.constants';
import { saveAs } from 'file-saver/FileSaver';
import { Convention } from './convention.model';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiDateUtils } from 'ng-jhipster';

@Component({
    selector: 'jhi-convention-amendment',
    templateUrl: './convention-amendment.component.html'
})
export class ConventionAmendmentComponent implements OnInit {
    convention: Convention = new Convention(); // Principal entity
    conventionAmendment: ConventionAmendment = new ConventionAmendment(); // Principal entity
    refPack: RefPack = new RefPack();
    // RefPack in edition or creation
    conventionId: number; // Id of the current conventionAmendment
    isCreateMode = true;
    private ngbModalRef: NgbModalRef;
    // For packs list
    dtOptions: any = {};
    dtTrigger: Subject<RefPack> = new Subject();
    dtTriggerConventionAmendment: Subject<ConventionAmendment> = new Subject();
    // For pack and conventionAmendment edition
    partners: Partner[];
    conventionAmendmentEdit: ConventionAmendment = new ConventionAmendment();
    reinsurers: Reinsurer[];
    typeServices: RefTypeService[];
    refPackEdit: RefPack = new RefPack();
    conventionAmendments: ConventionAmendment[] = [];
    refExclusionss: RefExclusion[];
    currentExclusion: RefExclusion;
    typeService: RefTypeService;
    usages: VehicleUsage[];
    sinisterTypes: RefModeGestion[];
    grantTimingList = [];
    isSaving: boolean;
    dropdownList = [];
    refExclusions: RefExclusion[] = [];
    selectedItems = [];
    dropdownSettings = {};
    serviceTypeDropdownSettings = {};
    dropdownSettingsUsages = {};
    disabledForm1 = false;
    disabledForm2 = false;
    disabledFormAmendment = false;
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
        private conventionAmendmentService: ConventionAmendmentService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private router: Router,
        private conventionPackPopupService: ConventionPackPopupService,
        private refTypeServiceService: RefTypeServiceService,
        private partnerService: PartnerService,
        private reinsurerService: ReinsurerService,
        private refUsageContratService: VehicleUsageService,
        private refModeGestionService: RefModeGestionService,
        private dateUtils: JhiDateUtils,
        private confirmationDialogService: ConfirmationDialogService,
        private sanitizer: DomSanitizer
    ) { }

    ngOnInit() {
        this.disabledForm1 = false;
        this.isSaving = false;
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.route.params.subscribe((params) => {
            if (params && params['id'] !== undefined) {
                this.conventionId = + params['id']; // (+) converts string 'id' to a number
                this.conventionAmendment.conventionId = this.conventionId;
                this.conventionService.find(this.conventionId).subscribe((convention: Convention) => {
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
                    if (this.convention.conventionAmendments !== undefined) {
                        if (this.convention.conventionAmendments !== null) {
                            if (this.convention.conventionAmendments.length !== 0) {
                                this.conventionAmendments = this.convention.conventionAmendments;
                                for (let index = 0; index < this.convention.conventionAmendments.length; index++) {
                                    const avenant = this.convention.conventionAmendments[index];
                                    avenant.startDate = this.dateUtils.convertLocalDateFromServer(avenant.startDate);
                                    if (avenant.startDate) {
                                        this.conventionAmendments[index].startDate = {
                                            year: avenant.startDate.getFullYear(),
                                            month: avenant.startDate.getMonth() + 1,
                                            day: avenant.startDate.getDate()
                                        };
                                    }
                                    avenant.endDate = this.dateUtils.convertLocalDateFromServer(avenant.endDate);
                                    if (avenant.endDate) {
                                        this.conventionAmendments[index].endDate = {
                                            year: avenant.endDate.getFullYear(),
                                            month: avenant.endDate.getMonth() + 1,
                                            day: avenant.endDate.getDate()
                                        };
                                    }
                                }
                            }
                        }
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

        this.partnerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.refUsageContratService.query().subscribe(
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
            { id: 'APEC', libelle: 'APEC' },
            { id: 'DPEC', libelle: 'DPEC' }
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
        this.dropdownSettingsUsages = {
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
        if (this.currentExclusion) {
            if (!this.refPack.exclusions) {
                this.refPack.exclusions = [];
            }
            this.refPack.exclusions.push(this.currentExclusion);
        }
    }
    addExclusionToAmd() {
        console.log('_________________________________________ ');
        if (this.currentExclusion) {
            if (!this.conventionAmendment.refPack.exclusions) {
                this.conventionAmendment.refPack.exclusions = [];
            }
            this.conventionAmendment.refPack.exclusions.push(this.currentExclusion);
        }
    }

    deleteExclusion(exclusion) {
        console.log('_________________________________________ ');
        exclusion.deleted = true;
    }

    addForm1() {
        this.disabledForm1 = true;
        this.conventionAmendment = new ConventionAmendment();
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
    printFields(refPack: RefPack) {
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
        if (refPack.serviceTypes && refPack.serviceTypes.length > 0) {
            for (let i = 0; i < refPack.serviceTypes.length; i++) {
                const serviceTypeId = refPack.serviceTypes[i].id;
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
    onChangeReinsurerPart(refPack: RefPack) {
        if (this.conventionAmendment.refPack.reinsurerPart !== null && this.conventionAmendment.refPack.reinsurerPart !== undefined) {
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
        }
    }

    downloadFile() {
        if (this.selectedFiles && this.selectedFiles.length > 0) {
            this.currentFileUpload = this.selectedFiles.item(0);
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
    deletePackAvenant(avenant) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes vous s de vouloir supprimer  avenant ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.conventionAmendments.forEach((item, index) => {
                        if (item === avenant) this.conventionAmendments.splice(index, 1);
                    });
                    this.dtTrigger.next();
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }
    deletePack(refPack) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer le pack ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.convention.packs.forEach((item, index) => {
                        if (item === refPack) this.convention.packs.splice(index, 1);
                    });
                    this.dtTrigger.next();
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }
    onItemSelect(item: any) {
        this.printFields(this.conventionAmendment.refPack);
    }
    onItemDeSelect(item: any) {
        this.printFields(this.conventionAmendment.refPack);
    }

    onSinisterTypeDeSelect(item: RefModeGestion) {
        console.log(item);
        for (let i = 0; i < this.conventionAmendment.refPack.sinisterTypeSettings.length; i++) {
            const setting = this.conventionAmendment.refPack.sinisterTypeSettings[i];
            if (setting.sinisterTypeId === item.id) {
                this.conventionAmendment.refPack.sinisterTypeSettings.splice(i, 1);
                break;
            }
        }
        for (let i = 0; i < this.conventionAmendment.refPack.apecSettings.length; i++) {
            const apecSetting = this.conventionAmendment.refPack.apecSettings[i];
            if (apecSetting.mgntModeId === item.id) {
                this.conventionAmendment.refPack.apecSettings.splice(i, 1);
                break;
            }
        }

    }

    onSinisterTypeSelect(item: RefModeGestion) {
        if (!this.conventionAmendment.refPack.sinisterTypeSettings) {
            this.conventionAmendment.refPack.sinisterTypeSettings = [];
        }
        // Add the new setting
        const setting = new SinisterTypeSetting();
        setting.sinisterTypeId = item.id;
        setting.label = item.libelle;
        setting.sinisterTypeLabel = item.libelle;

        this.conventionAmendment.refPack.sinisterTypeSettings.push(setting);

        if (!this.conventionAmendment.refPack.apecSettings) {
            this.conventionAmendment.refPack.apecSettings = [];
        }
        // Add the new setting
        const apecSettings = new ApecSettings();
        apecSettings.mgntModeId = item.id;
        apecSettings.mgntModeLabel = item.libelle;
        this.conventionAmendment.refPack.apecSettings.push(apecSettings);
    }

    /**
     * Save the current conventionAmendment
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
                    if (this.conventionAmendments && this.conventionAmendments.length > 0) {
		                /*this.conventionService.update(this.convention).subscribe((res)=> {
	                    	console.log('successful save');
	                    });*/
                        this.conventionAmendments.forEach(conventionAmendment => {
                            if (conventionAmendment.id !== undefined) {
                                this.subscribeToSaveResponse(
                                    this.conventionAmendmentService.update(
                                        conventionAmendment
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
                                    this.conventionAmendmentService.create(
                                        this.currentFileUpload,
                                        conventionAmendment
                                    )
                                );
                            }
                        });
                    } else {
	                    /*this.subscribeToSaveCnvResponse(
	                        this.conventionService.update(
	                           	this.convention
	                        )
	                    );*/
                    }


                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }
    private subscribeToSaveResponse(result: Observable<ConventionAmendment>) {
        result.subscribe(
            (res: ConventionAmendment) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );

    }
    trackRefNatureContratById(index: number, item: RefNatureContrat) {
        return item.id;
    }
    trackUsageById(index: number, item: VehicleUsage) {
        return item.id;
    }
    retour() {
        this.router.navigate(["/convention"]);
    }
    /**
     * Cancel current operation on pack
     */
    cancel() {
        this.conventionAmendment = new ConventionAmendment();
        this.conventionAmendment.conventionId = this.convention.id;
        this.isCreateMode = true;
    }
    cancelPack() {
        this.disabledForm1 = false;
        this.disabledForm2 = false;
        this.conventionAmendmentEdit = new ConventionAmendment();
        this.refPackEdit = new RefPack();
        this.printFields(this.conventionAmendment.refPack);
        this.printFields(this.refPack);
    }
    /**
     * Edit a pack
     * @param refPack : the pack to edit
     */
    editAvenant(conventionAmendment: ConventionAmendment) {
        this.conventionAmendment.conventionId = this.convention.id;

        this.disabledForm1 = true;
        this.disabledFormAmendment = true;
        this.conventionAmendmentEdit = conventionAmendment;
        this.conventionAmendment = conventionAmendment;
        this.conventionAmendment.endDate = this.convention.endDate;
        this.isCreateMode = false;
        this.printFields(this.conventionAmendment.refPack);
        this.onChangeReinsurerPart(this.conventionAmendment.refPack);
    }
    edit(refPack: RefPack) {
        this.disabledForm1 = true;

        this.disabledFormAmendment = true;
        this.conventionAmendment.conventionId = this.convention.id;
        const copy: RefPack = Object.assign({}, refPack);
        copy.sinisterTypeSettings.forEach(sinisterTypeSettings => {
            sinisterTypeSettings.id = null;
        });
        copy.apecSettings.forEach(apecSetting => {
            apecSetting.id = null;
        });
        this.conventionAmendment.refPack = copy;
        this.conventionAmendment.oldRefPackId = this.conventionAmendment.refPack.id;
        this.conventionAmendment.refPack.id = null;
        this.conventionAmendment.endDate = this.convention.endDate;
        this.isCreateMode = false;
        this.printFields(this.conventionAmendment.refPack);
        this.onChangeReinsurerPart(this.conventionAmendment.refPack);
    }

    /* tug selection for a defined service */
    selectPack(refPack) {
        this.ngbModalRef = this.conventionPackPopupService.openTugModal(RefPackPopupDetail as Component, refPack);
        this.ngbModalRef.result.then((result: RefPack) => {
            if (result !== null && result !== undefined) {
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            console.log('______________________________________________________2');
            this.ngbModalRef = null;
        });
    }
    newAvenant() {
        this.disabledForm1 = true;
        this.disabledForm2 = false;
        this.disabledFormAmendment = true;
        this.conventionAmendment = new ConventionAmendment();
        this.conventionAmendment.refPack = new RefPack();
        this.conventionAmendment.conventionId = this.convention.id;
        this.conventionAmendment.endDate = this.convention.endDate;
        this.isCreateMode = true;
        this.printFields(this.conventionAmendment.refPack);
        this.onChangeReinsurerPart(this.conventionAmendment.refPack);
    }

    saveRefPack() {
        if (this.conventionAmendment.refPack.exclusions && this.conventionAmendment.refPack.exclusions.length > 0) {
            let excls = [];
            for (let i = 0; i < this.conventionAmendment.refPack.exclusions.length; i++) {
                if (!this.conventionAmendment.refPack.exclusions[i].deleted) {
                    excls.push(this.conventionAmendment.refPack.exclusions[i]);
                }
            }
            this.conventionAmendment.refPack.exclusions = excls;
        }

        if (this.isCreateMode) {
            if (this.conventionAmendments === undefined) {
                this.conventionAmendments = [];
            }
            this.conventionAmendments.push(this.conventionAmendment);
            this.conventionAmendment = new ConventionAmendment();
            this.dtTrigger.next();
            this.disabledForm1 = false;

        } else {
            if (this.conventionAmendments !== undefined) {
                if (this.conventionAmendments !== null) {
                    if (this.conventionAmendments.length !== 0) {
                        this.conventionAmendments.forEach((item, index) => {
                            if (item === this.conventionAmendmentEdit) this.conventionAmendments.splice(index, 1);
                        });
                    }
                }
            }
            this.conventionAmendments.push(this.conventionAmendment);
            this.dtTrigger.next();
            this.isCreateMode = true;
            this.conventionAmendment = new ConventionAmendment();
            this.disabledForm1 = false;
        }
    }
    saveOnlyRefPack() {
        //this.refPack.convention = this.convention;
        this.convention.packs.forEach((item, index) => {
            if (item === this.refPackEdit) this.convention.packs.splice(index, 1);
        });
        this.convention.packs.push(this.refPack);
        this.dtTrigger.next();
        this.refPack = new RefPack();
        this.disabledForm2 = false;
    }



    private onSaveSuccess(result: ConventionAmendment) {
        this.eventManager.broadcast({
            name: "conventionListModification",
            content: "OK"
        });


        this.isSaving = false;
        this.router.navigate(["/convention"]);

    }
    private subscribeToSaveCnvResponse(result: Observable<Convention>) {
        result.subscribe(
            (res: Convention) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );

    }

    private onSaveCnvSuccess(result: Convention) {
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
