import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { Response } from '@angular/http';
import { GaDatatable } from '../../constants/app.constants';
import { Observable, Subject } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { RefTypeServiceService } from './../ref-type-service/ref-type-service.service';
import { RefPack } from '../ref-pack/ref-pack.model';
import { RefPackPopupService } from '../ref-pack/ref-pack-popup.service';
import { RefPackService } from '../ref-pack/ref-pack.service';
import { Principal, ResponseWrapper } from '../../shared';
import { RefTypeService } from './../ref-type-service/ref-type-service.model';
import { VehicleUsage } from './../vehicle-usage';
import { DomSanitizer } from '@angular/platform-browser';
import { ConventionPackPopupService } from './convention-pack-popup.service';
import { SinisterTypeSetting } from './../ref-pack/ref-pack.model';
import { RefExclusion } from './../ref-pack/ref-exclusion.model';
import { ConventionAmendment } from './convention-amendment.model';
import { ConventionService } from './convention.service';
import { ConventionAmendmentService } from './convention-amendment.service';
import { RefCompagnie } from './../ref-compagnie';
import { RefNatureContrat } from './../ref-nature-contrat';
import { VehicleUsageService } from './../vehicle-usage/vehicle-usage.service';
import { Client } from './../client/client.model';
import { ReinsurerService } from './../reinsurer/reinsurer.service';
import { Reinsurer } from './../reinsurer/reinsurer.model';
import { RefModeGestionService, RefModeGestion } from './../ref-mode-gestion';
import { Input } from '@angular/core';
import { saveAs } from 'file-saver/FileSaver';
import { Convention } from './convention.model';
@Component({
    selector: 'jhi-ref-pack-popup-detail',
    templateUrl: './ref-pack-popup-detail.html'
})
export class RefPackPopupDetail implements OnInit {
    @Input() refPack: RefPack;
    typeServices: RefTypeService[];
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
    refPackEdit: RefPack = new RefPack();
    reinsurers: Reinsurer[];
    refExclusionss: RefExclusion[];
    typeService: RefTypeService;
    usages: VehicleUsage[];
    sinisterTypes: RefModeGestion[];
    grantTimingList = [];
    isSaving: boolean;
    dropdownList = [];
    refExclusions: RefExclusion[] = [];
    selectedItems = [];
    usageSettings = {};
    dropdownSettings = {};
    serviceTypeDropdownSettings = {};
    constructor(
        private refTypeServiceService: RefTypeServiceService,
        public activeModal: NgbActiveModal,
        private refPackService: RefPackService,
        private router: Router,
        private alertService: JhiAlertService,
        private conventionService: ConventionService,
        private conventionAmendmentService: ConventionAmendmentService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private conventionPackPopupService: ConventionPackPopupService,
        private reinsurerService: ReinsurerService,
        private refUsageContratService: VehicleUsageService,
        private refModeGestionService: RefModeGestionService,
        private sanitizer: DomSanitizer
    ) {
    }

    ngOnInit() {
        this.refTypeServiceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.typeServices = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.reinsurerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reinsurers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

        this.refUsageContratService.query().subscribe(
            (res: ResponseWrapper) => {
                this.usages = res.json;
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
        this.usageSettings = {
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
        this.printFields();
        this.onChangeReinsurerPart();
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }
    onChangeReinsurerPart() {
        console.log("______________________________________________________________________");
        if (this.refPack.reinsurerPart !== null && this.refPack.reinsurerPart !== undefined) {
            this.packSettings.reinsurer = true;
        }
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

    onError(error: any): any {
        throw new Error("Method not implemented.");
    }
    trackUsageById(index: number, item: VehicleUsage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ref-bareme-pop',
    template: ''
})
export class RefPackPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    dtOptions: any = {};

    constructor(
        //private refBaremePopupDetailService: RefBaremePopupDetailService,
        public principal: Principal,
        private route: ActivatedRoute,
    ) { }
    ngOnInit() {

        //this.loadAll();
    }

    onError(json: any): void {
        throw new Error("Method not implemented.");
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

}

