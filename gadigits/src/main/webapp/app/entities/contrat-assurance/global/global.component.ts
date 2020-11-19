import { Component, OnInit, ViewChild } from '@angular/core';
import { Assitances, Recherche, SinisterService, PriseEnCharges, Search } from '../../sinister';
import { Subscription, Subject } from 'rxjs/Rx';
import { DataTableDirective } from 'angular-datatables';
import { JhiParseLinks, JhiAlertService, JhiEventManager, JhiPaginationUtil } from 'ng-jhipster';
import { Principal, ResponseWrapper } from '../../../shared';
import { Router } from '@angular/router';
import { PaginationConfig } from '../../../blocks/config/uib-pagination.config';
import { NgProgress } from 'ngx-progressbar';
import { GaDatatable } from '../../../constants/app.constants';
import { Dossiers } from '../../sinister/dossiers.model';
import { SinisterPecPopupService } from '../../sinister-pec';
import { HistoryDossierComponent } from '../history-dossier/history-dossier.component';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';


@Component({
    selector: 'jhi-global',
    templateUrl: './global.component.html',
    styles: []
})
export class GlobalComponent implements OnInit {

    dossiers: Dossiers[] = [];
    dtTrigger: Subject<Dossiers> = new Subject();
    dtOptions: any = {};
    error: any;

    constructor(
        private alertService: JhiAlertService,
        private principal: Principal,
        private sinisterService: SinisterService,
        public ngProgress: NgProgress,
    ) {
    }
    ngOnInit() {

        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
    }

    loadAll() {
        this.ngProgress.start();
        this.sinisterService.queryDossiers().subscribe(
            (res: ResponseWrapper) => {
                this.ngProgress.done();
                this.dossiers = res.json;

                this.dossiers.forEach(element => {
                    const opdate = new Date(element.incidentDate);
                    var month = new Array();
                    var mois = (opdate.getMonth());
                    month[0] = "Janvier";
                    month[1] = "Février";
                    month[2] = "Mars";
                    month[3] = "Avril";
                    month[4] = "Mai";
                    month[5] = "Juin";
                    month[6] = "Juillet";
                    month[7] = "août";
                    month[8] = "septembre";
                    month[9] = "Octobre";
                    month[10] = "Novembre";
                    month[11] = "Décembre";
                    element.incidentMois = month[mois];
                    element.nomPrenomRaison = element.isCompany ? element.raisonSociale : (element.prenom + ' ' + element.nom);
                });
                this.dtTrigger.next();

            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }



    private onError(error) {
        this.alertService.error(this.error.message, null, null);
    }


    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
    ngOnDestroy(): void {
    }

}