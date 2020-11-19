import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { Response } from '@angular/http';
import { GaDatatable } from '../../constants/app.constants';
import { Observable, Subject } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { Principal, ResponseWrapper } from '../../shared';
import { DomSanitizer } from '@angular/platform-browser';
import { AgencyPopupService } from './agency-popup.service';
import { Input } from '@angular/core';
import { saveAs } from 'file-saver/FileSaver';
import { DatePipe } from '@angular/common'
import { Agency } from './agency.model';
import { History, HistoryService } from '../history';
import { AgencyService } from './agency.service';
import { Subscription } from 'rxjs/Rx';
@Component({
    selector: 'jhi-ref-pack-popup-detail',
    templateUrl: './agenceConcessHistoryPopup.html'
})
export class AgenceConcessPopupDetail implements OnInit {
    @Input() id: number;
    @Input() entityName: string;

    dtOptions: any = {};
    histories: History[];
    private subscription: Subscription;
    dtTrigger: Subject<Agency> = new Subject();


    constructor(
        public activeModal: NgbActiveModal,
        private historyService: HistoryService,
    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.load(this.id, this.entityName);
    }

    load(id, entityName) {
        this.historyService.findHistoriesByEntity(id, entityName).subscribe((res: ResponseWrapper) => {
            this.histories = res.json;
            this.histories.forEach(element => {
                console.log("test Date" + element.operationDate);
                const opdate = new Date(element.operationDate);
                element.descriptiveOfChange = '';
                element.operationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' + opdate.getHours() + ':' + opdate.getMinutes() + ':' + opdate.getSeconds());

                element.changeValues.forEach(hist => {
                    if (hist.nameAttribute == "partnerCompanyName") { hist.translateNameAttribute = "Compagnie d'assurance" }
                    else if (hist.nameAttribute == "code") { hist.translateNameAttribute = "Code Agence" }
                    else if (hist.nameAttribute == "name") { hist.translateNameAttribute = "Nom agence" }
                    else if (hist.nameAttribute == "phone") { hist.translateNameAttribute = "Téléphone" }
                    else if (hist.nameAttribute == "mobile") { hist.translateNameAttribute = "Mobile" }
                    else if (hist.nameAttribute == "fax") { hist.translateNameAttribute = "Fax" }
                    else if (hist.nameAttribute == "email") { hist.translateNameAttribute = "Email" }
                    else if (hist.nameAttribute == "regionLabel") { hist.translateNameAttribute = "Region" }
                    else if (hist.nameAttribute == "governorateLabel") { hist.translateNameAttribute = "Gouvernorat" }
                    else if (hist.nameAttribute == "delegationLabel") { hist.translateNameAttribute = "Ville"; }
                    else if (hist.nameAttribute == "address") { hist.translateNameAttribute = "Adresse" }
                    else if (hist.nameAttribute == "zipcode") { hist.translateNameAttribute = "Code Postale" }
                    else if (hist.nameAttribute == "type") { hist.translateNameAttribute = "Type" }
                    else if (hist.nameAttribute == "category") { hist.translateNameAttribute = "Catégorie" }
                    else if (hist.nameAttribute == "nom") { hist.translateNameAttribute = "Nom" }
                    else if (hist.nameAttribute == "prenom") { hist.translateNameAttribute = "Prenom" }
                    else if (hist.nameAttribute == "deuxiemeMail") { hist.translateNameAttribute = "Deuxième Email" }
                    else if (hist.nameAttribute == "typeAgence") { hist.translateNameAttribute = "Type Agence" }
                    else if (hist.nameAttribute == "companyName") { hist.translateNameAttribute = "Raison Sociale" }
                    else if (hist.nameAttribute == "tradeRegister") { hist.translateNameAttribute = "Registre de Commerce" }
                    else if (hist.nameAttribute == "taxIdentificationNumber") { hist.translateNameAttribute = "Matricule Fiscale" }
                    else if (hist.nameAttribute == "typeAgence") { hist.translateNameAttribute = "Type Agence" }
                    else if (hist.nameAttribute == "premTelephone") { hist.translateNameAttribute = "Télephone" }
                    else if (hist.nameAttribute == "premMail") { hist.translateNameAttribute = "Email" }
                    else if (hist.nameAttribute == "foreignCompany") { hist.translateNameAttribute = "Compagnie étrangère" }
                    else if (hist.nameAttribute == "active") { hist.translateNameAttribute = "Bloqué" }
                    else if (hist.nameAttribute == "attachmentName") { hist.translateNameAttribute = "L'attachment2" }
                    else if (hist.nameAttribute == "attachmentOriginalName") { hist.translateNameAttribute = "L'attachment3" }

                    if (hist.lastValue == 'null') { hist.lastValue = "vide"; console.log("test --------- " + hist.lastValue); }
                    if (hist.newValue == 'null') { hist.newValue = "vide"; console.log("test --------- " + hist.lastValue); }
                    if (hist.lastValue == 'true') { hist.lastValue = "Oui"; console.log("test --------- " + hist.lastValue); }
                    if (hist.lastValue == 'false') { hist.lastValue = "Non"; console.log("test --------- " + hist.lastValue); }
                    if (hist.newValue == 'false') { hist.newValue = "Non"; console.log("test --------- " + hist.lastValue); }
                    if (hist.newValue == 'true') { hist.newValue = "Oui"; console.log("test --------- " + hist.lastValue); }
                });
                element.changeValues.forEach(changeValues => {
                    if (changeValues.nameAttribute !== 'partnerId' && changeValues.nameAttribute !== 'governorateId' && changeValues.nameAttribute !== 'delegationId' && changeValues.nameAttribute !== 'regionId' && changeValues.nameAttribute !== 'dateCreation' && changeValues.nameAttribute !== 'id' && changeValues.nameAttribute !== 'isGerant') {
                        element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + changeValues.lastValue + " en " + changeValues.newValue + ' \n';
                    }
                });
            });
            this.histories.sort((a, b) => a.operationDate.rendered.localeCompare(b.operationDate.rendered));
            this.dtTrigger.next();

        });
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }



    onError(error: any): any {
        throw new Error("Method not implemented.");
    }

    trackId(index: number, item: History) {
        return item.id;
    }

}

@Component({
    selector: 'jhi-ref-agence-pop',
    template: ''
})
export class AgenceConcessPopupComponent implements OnInit, OnDestroy {
    routeSub: any;
    agency: Agency;
    dtOptions: any = {};

    constructor(
        //private refBaremePopupDetailService: RefBaremePopupDetailService,
        public principal: Principal,
        private route: ActivatedRoute,
        private agencyPopupService: AgencyPopupService,
    ) { }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.routeSub = this.route.params.subscribe((params) => {
            /* if ( params['id'] ) {
                 this.agencyPopupService
                     .open(AgenceConcessPopupDetail as Component, params['id']);
             } else {*/
            this.agencyPopupService
                .open(AgenceConcessPopupDetail as Component, this.agency);
            //}
        });
    }


    onError(json: any): void {
        throw new Error("Method not implemented.");
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

}