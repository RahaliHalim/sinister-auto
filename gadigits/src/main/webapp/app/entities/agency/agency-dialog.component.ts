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
import { Type, Categorie } from '../../constants/app.constants';
import { GaDatatable, Global } from './../../constants/app.constants';
@Component({
    selector: 'jhi-agency-dialog',
    templateUrl: './agency-dialog.component.html'
})
export class AgencyDialogComponent implements OnInit {

    agency: Agency = new Agency();
    isSaving: boolean;
    gov: boolean = true;
    reg: boolean = true;
    msg: string = 'Un nouveau Agent a été créé' ;
    partners: Partner[] = [];

    partnersNoEtrg: Partner[] = [];

    governorates: Governorate[] = [];
    governorate: Governorate;
    delegation: Delegation;
    delegations: Delegation[] = [];
    region: Region;
    regions: Region[] = [];

    types : Type[] = [
    {id: 1, libelle: "Agent"},
    {id: 2, libelle: "Courtier"},
    {id: 3, libelle: "Bureau direct"}];

    categories : Categorie[] = [
        {id: 1, libelle: "A"},
        {id: 2, libelle: "B"},
        {id: 3, libelle: "C"}];

    type : Type;

    categorie : Categorie;
    edit: boolean = false;
    create: boolean = false;
    isAgencyNameUnique: boolean = true;
    isAgencycodeUnique: boolean = true;
    isAgencyNamecodeUnique: boolean = true;
    textPattern = Global.textPattern;
    textPatternAlphaNum = '^[a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+( [a-z0-9A-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüý,-.\'ÿ\/]+)*$';

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
        this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => {
                this.partners = res.json;
                this.partners.forEach(element => {
                    if(!element.foreignCompany){
                        this.partnersNoEtrg.push(element);
                    }
                });
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.regionService.query()
            .subscribe((res: ResponseWrapper) => { this.regions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
                        this.reg = false;
                        if (this.agency.delegationId != null) {
                            this.findGouvernoratOfVille(this.agency.delegationId);
                            //this.reg = false;
                        }

                       /* if (this.agency.governorateId != null) {
                            this.findRegionOfGouvernorat(this.agency.governorateId);
                            this.reg = false;
                        }*/
                    });
            }
        });
    }


    save() {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg' ).then(( confirmed ) => {
            console.log( 'User confirmed:', confirmed );
            if (confirmed) {
                this.isSaving = true;
                this.agency.genre = 1;
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
        this.eventManager.broadcast({ name: 'agencyListModification', content: 'OK'});
        this.router.navigate(['../agency']);
        this.isSaving = false;
        if(this.edit){
            this.alertService.success('auxiliumApp.agency.updated',null,null);
        }
        if(this.create){
            this.alertService.success('auxiliumApp.agency.created',null,null);
        }
        //this.activeModal.dismiss(result);
    }

    trimServiceTypeNumber() {
        this.agency.code = this.agency.code.trim();
        this.agency.name = this.agency.name.trim();
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

    }

    findRegionOfGouvernorat(id) {
        this.governorateService.find(id).subscribe((res: Governorate) => {
            this.governorate = res;
            this.regionService.find(this.governorate.regionId).subscribe((subRes: Region) => {
                this.region = subRes
            })
        }
        )
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


    findAgencyByNameCode() {
        console.log('_______________________________________________________________');
        if (this.agency.partnerId !== null && this.agency.partnerId !== undefined  && this.agency.code !== null && this.agency.code !== undefined && this.agency.code !== '') {
            this.agencyService.findByAgencyByNameCode(this.agency.partnerId,this.agency.code).subscribe((subRes: Agency) => {
                if (subRes.id !== null && subRes.id !== undefined && subRes.id !== this.agency.id) {
                    this.isAgencyNameUnique = false;
                    this.alertService.error('auxiliumApp.agency.uniqueCodeAgency', null, null);
                } else {
                    this.isAgencyNameUnique = true;
                }
            });
        }
    }

    fetchGovernorateByRegion(id) {
        this.regionService.find(id).subscribe((subRes: Region) => {
            this.region = subRes;
            this.governorateService.findByRegion(this.region.id).subscribe((subRes0: Governorate[]) => {
                this.governorates = subRes0;
                if (subRes0 && subRes0.length > 0) {
                    this.agency.governorateId = subRes0[0].id;

                    this.delegationService.findByGovernorate(subRes0[0].id).subscribe((subRes1: Delegation[]) => {
                        this.delegations = subRes1;
                        if (subRes1 && subRes1.length > 0) {
                            this.agency.delegationId = subRes1[0].id;
                        }

                    });


                }

            });
        });
        this.reg = false;
    }

    trackPartnerById(index: number, item: Partner) {
        return item.id;
    }

    trackTypeById(index: number, item: Type) {
        return item.libelle;
    }

    trackCategorieById(index: number, item: Categorie) {
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
export class AgencyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencyPopupService: AgencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agencyPopupService
                    .open(AgencyDialogComponent as Component, params['id']);
            } else {
                this.agencyPopupService
                    .open(AgencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
