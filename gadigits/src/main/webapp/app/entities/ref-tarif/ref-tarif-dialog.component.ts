import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { TarifLine } from './tarif-line.model';
import { RefTarif } from './ref-tarif.model';
import { RefTarifPopupService } from './ref-tarif-popup.service';
import { RefTarifService } from './ref-tarif.service';
import { ResponseWrapper } from '../../shared';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-ref-tarif-dialog',
    templateUrl: './ref-tarif-dialog.component.html'
})
export class RefTarifDialogComponent implements OnInit {
    refTarifs: RefTarif[];
    refTarif: RefTarif;
    isSaving: boolean;
    tariflines:TarifLine[];
    eventSubscriber: Subscription;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private refTarifService: RefTarifService,
        private eventManager: JhiEventManager,
        private router: Router,
       
    ) {

    }

    ngOnInit() {
    this.isSaving = false;
    this.refTarifService.queryline()
    .subscribe((res: ResponseWrapper) => { this.tariflines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));   
   
     }
    clear() {
        this.activeModal.dismiss('cancel');
    }
    save() {
        this.isSaving = true;
        if (this.refTarif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refTarifService.update(this.refTarif));
                this.activeModal.dismiss(true);
        } else {           
            this.subscribeToSaveResponse(              
                this.refTarifService.create(this.refTarif)); 
                this.activeModal.dismiss(true); 
        }
    }

    private subscribeToSaveResponse(result: Observable<RefTarif>) {
        result.subscribe((res: RefTarif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveSuccess(result: RefTarif) {
        this.router.navigate(['../ref-tarif']);
        this.eventManager.broadcast({ name: 'tarifListModification', content: 'OK'});
        this.isSaving = false;
       
        this.refTarifService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    
    }
    private onSuccess(data, headers) {
        this.refTarifs = data;
      
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
    selector: 'jhi-ref-tarif-popup',
    template: ''
})
export class RefTarifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private refTarifPopupService: RefTarifPopupService
    )
     {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                console.log("params id"+params['id']);
                this.refTarifPopupService
                    .open(RefTarifDialogComponent as Component, params['id']);
            } else {
                this.refTarifPopupService
                    .open(RefTarifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
