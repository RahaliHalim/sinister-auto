import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tiers } from './tiers.model';
import { TiersPopupService } from './tiers-popup.service';
import { TiersService } from './tiers.service';
import { RefCompagnieService, RefCompagnie } from '../ref-compagnie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tiers-dialog',
    templateUrl: './tiers-dialog.component.html'
})
export class TiersDialogComponent implements OnInit {

    @Input() tiers: Tiers = new Tiers();
    isSaving: boolean;
    debutValiditeDp: any;
    finValiditeDp: any;
    compagnies: any;
    minDate = {year: 1980, month: 1, day: 1};
    maxDate = {year: 2030, month: 1, day: 1};

    constructor(
        private alertService: JhiAlertService,
        private tiersService: TiersService,
        private refCompagnieService: RefCompagnieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refCompagnieService.query()
        .subscribe((res: ResponseWrapper) => { this.compagnies = res.json; console.log(this.compagnies); }, (res: ResponseWrapper) => this.onError(res.json));
    }

    save() {

        console.log("save tiers composent chield----------------");
        this.isSaving = true;
        if (this.tiers.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tiersService.update(this.tiers));
        } else {
           
                this.subscribeToSaveResponse(
                    this.tiersService.create(this.tiers));
            
        }
    }

    trackcompagnieOptionById(index: number, item: RefCompagnie) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<Tiers>) {
        result.subscribe((res: Tiers) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Tiers) {
        this.eventManager.broadcast({ name: 'tiersListModification', content: 'OK'});
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
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-tiers-popup',
    template: ''
})
export class TiersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tiersPopupService: TiersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tiersPopupService
                    .open(TiersDialogComponent as Component, params['id']);
            } else {
                this.tiersPopupService
                    .open(TiersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
