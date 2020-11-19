import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserCellule } from './user-cellule.model';
import { UserCellulePopupService } from './user-cellule-popup.service';
import { UserCelluleService } from './user-cellule.service';
import { User, UserService } from '../../shared';
import { Cellule, CelluleService } from '../cellule';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-cellule-dialog',
    templateUrl: './user-cellule-dialog.component.html'
})
export class UserCelluleDialogComponent implements OnInit {

    userCellule: UserCellule;
    isSaving: boolean;

    users: User[];

    cellules: Cellule[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private userCelluleService: UserCelluleService,
        private userService: UserService,
        private celluleService: CelluleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.celluleService.query()
            .subscribe((res: ResponseWrapper) => { this.cellules = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userCellule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userCelluleService.update(this.userCellule));
        } else {
            this.subscribeToSaveResponse(
                this.userCelluleService.create(this.userCellule));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserCellule>) {
        result.subscribe((res: UserCellule) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserCellule) {
        this.eventManager.broadcast({ name: 'userCelluleListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCelluleById(index: number, item: Cellule) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-cellule-popup',
    template: ''
})
export class UserCellulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userCellulePopupService: UserCellulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userCellulePopupService
                    .open(UserCelluleDialogComponent as Component, params['id']);
            } else {
                this.userCellulePopupService
                    .open(UserCelluleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
