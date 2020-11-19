import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PolicyHolder } from './policy-holder.model';
import { PolicyHolderPopupService } from './policy-holder-popup.service';
import { PolicyHolderService } from './policy-holder.service';
import { Governorate, GovernorateService } from '../governorate';
import { Delegation, DelegationService } from '../delegation';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-policy-holder-dialog',
    templateUrl: './policy-holder-dialog.component.html'
})
export class PolicyHolderDialogComponent implements OnInit {

    policyHolder: PolicyHolder;
    isSaving: boolean;

    governorates: Governorate[];

    delegations: Delegation[];

    users: User[];
    creationDateDp: any;
    updateDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private policyHolderService: PolicyHolderService,
        private governorateService: GovernorateService,
        private delegationService: DelegationService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.governorateService.query()
            .subscribe((res: ResponseWrapper) => { this.governorates = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.delegationService.query()
            .subscribe((res: ResponseWrapper) => { this.delegations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.policyHolder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.policyHolderService.update(this.policyHolder));
        } else {
            this.subscribeToSaveResponse(
                this.policyHolderService.create(this.policyHolder));
        }
    }

    private subscribeToSaveResponse(result: Observable<PolicyHolder>) {
        result.subscribe((res: PolicyHolder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PolicyHolder) {
        this.eventManager.broadcast({ name: 'policyHolderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
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

    trackGovernorateById(index: number, item: Governorate) {
        return item.id;
    }

    trackDelegationById(index: number, item: Delegation) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-policy-holder-popup',
    template: ''
})
export class PolicyHolderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private policyHolderPopupService: PolicyHolderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.policyHolderPopupService
                    .open(PolicyHolderDialogComponent as Component, params['id']);
            } else {
                this.policyHolderPopupService
                    .open(PolicyHolderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
