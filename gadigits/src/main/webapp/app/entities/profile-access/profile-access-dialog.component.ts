import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProfileAccess } from './profile-access.model';
import { ProfileAccessPopupService } from './profile-access-popup.service';
import { ProfileAccessService } from './profile-access.service';
import { BusinessEntity, BusinessEntityService } from '../business-entity';
import { Functionality, FunctionalityService } from '../functionality';
import { ElementMenu, ElementMenuService } from '../element-menu';
import { UserProfile, UserProfileService } from '../user-profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-profile-access-dialog',
    templateUrl: './profile-access-dialog.component.html'
})
export class ProfileAccessDialogComponent implements OnInit {

    profileAccess: ProfileAccess;
    isSaving: boolean;

    businessentities: BusinessEntity[];

    functionalities: Functionality[];

    elementmenus: ElementMenu[];

    userprofiles: UserProfile[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private profileAccessService: ProfileAccessService,
        private businessEntityService: BusinessEntityService,
        private functionalityService: FunctionalityService,
        private elementMenuService: ElementMenuService,
        private userProfileService: UserProfileService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.businessEntityService.query()
            .subscribe((res: ResponseWrapper) => { this.businessentities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.functionalityService.query()
            .subscribe((res: ResponseWrapper) => { this.functionalities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.elementMenuService.query()
            .subscribe((res: ResponseWrapper) => { this.elementmenus = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userProfileService.query()
            .subscribe((res: ResponseWrapper) => { this.userprofiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.profileAccess.id !== undefined) {
            this.subscribeToSaveResponse(
                this.profileAccessService.update(this.profileAccess));
        } else {
            this.subscribeToSaveResponse(
                this.profileAccessService.create(this.profileAccess));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProfileAccess>) {
        result.subscribe((res: ProfileAccess) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProfileAccess) {
        this.eventManager.broadcast({ name: 'profileAccessListModification', content: 'OK'});
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

    trackBusinessEntityById(index: number, item: BusinessEntity) {
        return item.id;
    }

    trackFunctionalityById(index: number, item: Functionality) {
        return item.id;
    }

    trackElementMenuById(index: number, item: ElementMenu) {
        return item.id;
    }

    trackUserProfileById(index: number, item: UserProfile) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-profile-access-popup',
    template: ''
})
export class ProfileAccessPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileAccessPopupService: ProfileAccessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.profileAccessPopupService
                    .open(ProfileAccessDialogComponent as Component, params['id']);
            } else {
                this.profileAccessPopupService
                    .open(ProfileAccessDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
