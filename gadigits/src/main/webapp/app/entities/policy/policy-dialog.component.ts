import { RefPack } from './../ref-pack/ref-pack.model';
import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Policy } from './policy.model';
import { PolicyService } from './policy.service';
import { PolicyType, PolicyTypeService } from '../policy-type';
import { PolicyNature, PolicyNatureService } from '../policy-nature';
import { Periodicity, PeriodicityService } from '../periodicity';
import { Partner, PartnerService } from '../partner';
import { Agency, AgencyService } from '../agency';
import { PolicyHolder, PolicyHolderService } from '../policy-holder';
import { ResponseWrapper } from '../../shared';
import { RefPackService } from '../ref-pack';

@Component({
    selector: "jhi-policy-dialog",
    templateUrl: "./policy-dialog.component.html"
})
export class PolicyDialogComponent implements OnInit {
    policy: Policy = new Policy();
    isSaving: boolean;

    policytypes: PolicyType[];

    policynatures: PolicyNature[];

    periodicities: Periodicity[];

    packs: RefPack[];

    partners: Partner[];

    agencies: Agency[];

    policyholders: PolicyHolder[];
    startDateDp: any;
    endDateDp: any;
    isEditMode = false;

    constructor(
        private alertService: JhiAlertService,
        private policyService: PolicyService,
        private policyTypeService: PolicyTypeService,
        private policyNatureService: PolicyNatureService,
        private periodicityService: PeriodicityService,
        private partnerService: PartnerService,
        private agencyService: AgencyService,
        private policyHolderService: PolicyHolderService,
        private packService: RefPackService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {

        this.isSaving = false;
        this.policyTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.policytypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.policyNatureService.query().subscribe(
            (res: ResponseWrapper) => {
                this.policynatures = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.periodicityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.periodicities = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.partnerService.findAllCompanies().subscribe(
            (res: ResponseWrapper) => {
                this.partners = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.policyHolderService.query().subscribe(
            (res: ResponseWrapper) => {
                this.policyholders = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    selectPartner(partnerId: number) {
        this.agencyService.findAllByPartner(partnerId).subscribe((res: ResponseWrapper) => { this.agencies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.packService.findByServiceType(partnerId).subscribe(
            (res: ResponseWrapper) => {
                this.packs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    save() {
        this.isSaving = true;
        if (this.policy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.policyService.update(this.policy)
            );
        } else {
            this.subscribeToSaveResponse(
                this.policyService.create(this.policy)
            );
        }
    }

    private subscribeToSaveResponse(result: Observable<Policy>) {
        result.subscribe(
            (res: Policy) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: Policy) {
        this.eventManager.broadcast({
            name: "policyListModification",
            content: "OK"
        });
        this.isSaving = false;
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
        console.log(
            "________________________________________________________77777"
        );
        this.alertService.error(error.message, null, null);
    }

    trackPolicyTypeById(index: number, item: PolicyType) {
        return item.id;
    }

    trackPolicyNatureById(index: number, item: PolicyNature) {
        return item.id;
    }

    trackPeriodicityById(index: number, item: Periodicity) {
        return item.id;
    }

    trackPartnerById(index: number, item: Partner) {
        return item.id;
    }

    trackAgencyById(index: number, item: Agency) {
        return item.id;
    }

    trackPolicyHolderById(index: number, item: PolicyHolder) {
        return item.id;
    }
}
