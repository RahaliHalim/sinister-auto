import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAccess } from './user-access.model';
import { UserAccessPopupService } from './user-access-popup.service';
import { UserAccessService } from './user-access.service';
import { BusinessEntity, BusinessEntityService } from '../business-entity';
import { Functionality, FunctionalityService } from '../functionality';
import { ElementMenu, ElementMenuService } from '../element-menu';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-access-dialog',
    templateUrl: './user-access-dialog.component.html'
})
export class UserAccessDialogComponent implements OnInit {

    userAccess: UserAccess;
    isSaving: boolean;

    businessentities: BusinessEntity[];

    functionalities: Functionality[];

    elementmenus: ElementMenu[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private userAccessService: UserAccessService,
        private businessEntityService: BusinessEntityService,
        private functionalityService: FunctionalityService,
        private elementMenuService: ElementMenuService,
        private userService: UserService,
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
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userAccess.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userAccessService.update(this.userAccess));
        } else {
            this.subscribeToSaveResponse(
                this.userAccessService.create(this.userAccess));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserAccess>) {
        result.subscribe((res: UserAccess) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserAccess) {
        this.eventManager.broadcast({ name: 'userAccessListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-access-popup',
    template: ''
})
export class UserAccessPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAccessPopupService: UserAccessPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userAccessPopupService
                    .open(UserAccessDialogComponent as Component, params['id']);
            } else {
                this.userAccessPopupService
                    .open(UserAccessDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
