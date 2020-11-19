import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Client } from './client.model';
import { ClientPopupService } from './client-popup.service';
import { ClientService } from './client.service';
import { RefCompagnie, RefCompagnieService } from '../ref-compagnie';
import { Produit, ProduitService } from '../produit';
import { RefPack, RefPackService } from '../ref-pack';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-client-dialog',
    templateUrl: './client-dialog.component.html'
})
export class ClientDialogComponent implements OnInit {

    client: Client;
    isSaving: boolean;
    compagnies: RefCompagnie[];
    produits: Produit[];
    refpacks: RefPack[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private clientService: ClientService,
        private refCompagnieService: RefCompagnieService,
        private produitService: ProduitService,
        private refPackService: RefPackService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.refCompagnieService
            .query({filter: 'client-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.client.compagnieId) {
                    this.compagnies = res.json;
                } else {
                    this.refCompagnieService
                        .find(this.client.compagnieId)
                        .subscribe((subRes: RefCompagnie) => {
                            this.compagnies = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.produitService.query()
            .subscribe((res: ResponseWrapper) => { this.produits = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refPackService.query()
            .subscribe((res: ResponseWrapper) => { this.refpacks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.refCompagnieService.getUnblockedCompany()
            .subscribe((res: ResponseWrapper) => { this.compagnies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.client.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientService.update(this.client));
        } else {
            this.subscribeToSaveResponse(
                this.clientService.create(this.client));
        }
    }

    private subscribeToSaveResponse(result: Observable<Client>) {
        result.subscribe((res: Client) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Client) {
        this.eventManager.broadcast({ name: 'clientListModification', content: 'OK'});
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

    trackRefCompagnieById(index: number, item: RefCompagnie) {
        return item.id;
    }

    trackProduitById(index: number, item: Produit) {
        return item.id;
    }

    trackRefPackById(index: number, item: RefPack) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-client-popup',
    template: ''
})
export class ClientPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPopupService: ClientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clientPopupService
                    .open(ClientDialogComponent as Component, params['id']);
            } else {
                this.clientPopupService
                    .open(ClientDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
