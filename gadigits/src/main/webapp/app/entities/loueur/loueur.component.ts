import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Loueur } from './loueur.model';
import { LoueurService } from './loueur.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { GaDatatable } from '../../constants/app.constants';
import { PermissionAccess, UserExtraService } from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoueurPopupService } from './loueur-popup.service';
import { LoueurBloqueDialogComponent } from './loueur-bloque-dialog.component';
import { DataTableDirective } from 'angular-datatables';

@Component({
    selector: 'jhi-loueur',
    templateUrl: './loueur.component.html'
})
export class LoueurComponent implements OnInit, OnDestroy, AfterViewInit {

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    private ngbModalRef: NgbModalRef;
    loueurs: Loueur[] = [];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    dtTrigger: Subject<Loueur> = new Subject();
    permissionToAccess : PermissionAccess = new PermissionAccess();
    dtOptions: any = {};



    constructor(
        private loueurService: LoueurService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private loueurPopupService : LoueurPopupService,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private userExtraService : UserExtraService

    ) {
        
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }

    loadAll() {
    
        this.loueurService.query().subscribe(
            (res: ResponseWrapper) => {
                this.loueurs = res.json;
               // this.dtTrigger.next(); // Actualize datatables
               this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

   

   
    ngOnInit() {   
        this.dtOptions = GaDatatable.defaultDtOptions; 
        this.loadAll();
        
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(107,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });

    }

    ngOnDestroy() {
    }

 
    BloquerLoueur(loueurId: number) {
        this.ngbModalRef = this.loueurPopupService.openLoueurModal(LoueurBloqueDialogComponent as Component,loueurId );
        this.ngbModalRef.result.then((result: Boolean) => {
            if (result !== null && result !== undefined) {
                //this.sinisterPrestation.affectedTugId = result.refTugId;
                this.loadAll();
            }
            this.ngbModalRef = null;
        }, (reason) => {
            // TODO: print error message
            this.ngbModalRef = null;
        });

    }
    

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
