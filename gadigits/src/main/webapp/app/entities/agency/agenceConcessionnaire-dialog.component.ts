import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute , Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ConfirmationDialogService } from '../../shared';

import { Agency } from './agency.model';
import { AgencyPopupService } from './agency-popup.service';
import { AgencyService } from './agency.service';
import { Partner, PartnerService } from '../partner';
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { Region, RegionService } from '../region';
import { ResponseWrapper } from '../../shared';
import { TypeAgence } from '../../constants/app.constants';
import { GaDatatable, Global } from './../../constants/app.constants';

@Component({
    selector: 'jhi-agency-dialog',
    templateUrl: './agenceConcessionnaire-dialog.component.html'
})
export class AgenceConcessionnaireDialogComponent implements OnInit {

    agency: Agency = new Agency();
    isSaving: boolean;
    gov: boolean = true;
    partners: Partner[] = [];
    governorate: Governorate;
    governorates: Governorate[] = [];

    delegations: Delegation[] = [];
    delegation: Delegation;
    typeAgences : TypeAgence[] = [
        {id: 1, libelle: "CET"},
        {id: 2, libelle: "TDA"},
        {id: 3, libelle: "CCS"},
        {id: 4, libelle: "CTN"}];
        typeAgence : TypeAgence;
        edit: boolean = false;
        create: boolean = false;
        isAgenceConcessNameUnique: boolean = true;
        isPartUnique: boolean = true;
        isEmailUnique: boolean = true;
        textPattern = Global.textPattern;

        textPatternNotAlphNul = '^[a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ\/]+( [a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ\/\s\(\)\-]+)*$';
        

    constructor(
        private route: ActivatedRoute,
        private alertService: JhiAlertService,
        private router: Router,
        private agencyService: AgencyService,
        private partnerService: PartnerService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private confirmationDialogService: ConfirmationDialogService,
        private regionService: RegionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.initAgency();
        this.partnerService.findAllDealers()
            .subscribe((res: ResponseWrapper) => { this.partners = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    /*clear() {
        this.activeModal.dismiss('cancel');
    }*/

    initAgency() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agencyService
                    .find(params['id'])
                    .subscribe((agency: Agency) => {
                        this.agency = agency;
                        this.gov = false;
                        if (this.agency.delegationId != null) {
                            this.findGouvernoratOfVille(this.agency.delegationId);
                            this.gov = false;
                        }
                    });
            }
        });
    }
    

    save() {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg' ).then(( confirmed ) => {
            console.log( 'User confirmed:', confirmed );
            if (confirmed) {
                this.isSaving = true;
                this.agency.genre = 2;
                if (this.agency.id !== undefined) {
                    this.subscribeToSaveResponse(
                        this.agencyService.update(this.agency));
                        this.edit = true;
                } else {
                    this.subscribeToSaveResponse(
                        this.agencyService.create(this.agency));
                        this.create = true ;
                }
            }
        })
        .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
}

    private subscribeToSaveResponse(result: Observable<Agency>) {
        result.subscribe((res: Agency) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Agency) {
        this.eventManager.broadcast({ name: 'agenceConcessionnaireListModification', content: 'OK'});
        this.router.navigate(['../agenceConcessionnaire']);
        this.isSaving = false;
        if(this.edit){
            this.alertService.success('auxiliumApp.agency.updatedConcess',null,null);
        }
        if(this.create){
            this.alertService.success('auxiliumApp.agency.createdConcess',null,null);
        }
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

    trimServiceTypeNumber() {
        this.agency.nom = this.agency.nom.trim();
        this.agency.prenom = this.agency.prenom.trim();
        this.agency.name = this.agency.name.trim();
    }

    fetchDelegationsByGovernorate(id) {
        this.governorateService.find(id).subscribe((subRes: Governorate) => {
            this.governorate = subRes;
            this.delegationService.findByGovernorate(this.governorate.id).subscribe((subRes1: Delegation[]) => {
                this.delegations = subRes1;
                if (subRes1 && subRes1.length > 0) {
                    this.agency.delegationId = subRes1[0].id;
                }

            });
        });
        this.gov = false;
    }

    findByAgenceConcessNameConcessName() {
        console.log('_______________________________________________________________');
        if (this.agency.name !== null && this.agency.name !== undefined && this.agency.name !== '' && this.agency.partnerId !== null && this.agency.partnerId !== undefined ) {
            this.agencyService.findByAgenceConcessionnaireNameConcessName(this.agency.name,this.agency.partnerId).subscribe((subRes: Partner) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.agency.id) {
                    this.isPartUnique = false;
                    this.alertService.error('auxiliumApp.agency.uniqueName', null, null);
                } else {
                    this.isPartUnique = true;
                }
            });
        }
    }

    findByAgenceConcessEmailUnique() {
        console.log('_______________________________________________________________');
        if (this.agency.email !== null && this.agency.email !== undefined && this.agency.email !== '' && this.agency.deuxiemeMail !== null && this.agency.deuxiemeMail !== undefined && this.agency.deuxiemeMail !== '' ) {

                if (this.agency.email === this.agency.deuxiemeMail) {
                    this.isEmailUnique = false;
                    this.alertService.error('auxiliumApp.agency.emailUnique', null, null);
                } else {
                    this.isEmailUnique = true;
                }
        }
    }

    findGouvernoratOfVille(id) {
        this.delegationService.find(id).subscribe((res: Delegation) => {
            this.delegation = res;
            this.governorateService.find(this.delegation.governorateId).subscribe((subRes: Governorate) => {
                this.governorate = subRes
            })
        }
        )
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPartnerById(index: number, item: Partner) {
        return item.id;
    }

    trackTypeAgenceById(index: number, item: TypeAgence) {
        return item.libelle;
    }

    trackGovernorateById(index: number, item: Governorate) {
        return item.id;
    }

    trackDelegationById(index: number, item: Delegation) {
        return item.id;
    }

    trackRegionById(index: number, item: Region) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-agency-popup',
    template: ''
})
export class AgenceConcessionnairePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencyPopupService: AgencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agencyPopupService
                    .open(AgenceConcessionnaireDialogComponent as Component, params['id']);
            } else {
                this.agencyPopupService
                    .open(AgenceConcessionnaireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
