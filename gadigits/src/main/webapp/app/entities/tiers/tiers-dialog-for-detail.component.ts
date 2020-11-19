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
    selector: 'jhi-tiers-dialog-for-detail',
    templateUrl: './tiers-dialog-for-detail.component.html'
})
export class TiersDialogForDetailComponent implements OnInit {

    @Input() tiers: Tiers = new Tiers();
    isSaving: boolean;
    debutValiditeDp: any;
    finValiditeDp: any;
    compagnies: any;

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
        .subscribe((res: ResponseWrapper) => { this.compagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    save() {
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
 