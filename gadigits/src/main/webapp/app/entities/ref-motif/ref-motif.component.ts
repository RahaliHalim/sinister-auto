import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Journal, JournalService } from '../journal';
import { RefMotif } from './ref-motif.model';
import { Observable } from 'rxjs/Rx';
import { RefMotifService } from './ref-motif.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { Subject } from "rxjs";
import { SysActionUtilisateur } from '../sys-action-utilisateur';
import { GaDatatable } from './../../constants/app.constants';
@Component({
    selector: 'jhi-ref-motif',
    templateUrl: './ref-motif.component.html'
})
export class RefMotifComponent implements OnInit, OnDestroy {

currentAccount: any;
    refMotifs: RefMotif[];
    eventSubscriber: Subscription;
    dtOptions: any = {};
    error: any;
    success: any;
    previousPage: any;
    reverse: any;

    refMotif: RefMotif = new RefMotif ;
    isSaving: boolean;
    dtTrigger: Subject<RefMotif> = new Subject();
    journals: Journal[];
    sysactionutilisateurs: SysActionUtilisateur[];
    authorities: any[];
    editMotif :boolean =  false;
    createMotif :boolean = true;
    constructor(
        private refMotifService: RefMotifService,
        private alertService: JhiAlertService,
        public  principal: Principal,
        private eventManager: JhiEventManager,
        private confirmationDialogService: ConfirmationDialogService,
    ) {
    }
    cancel() {
        this.refMotif = new RefMotif();
    }
   loadAll() {
        this.refMotifService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        //this.dtTrigger.next();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg' )
        .then(( confirmed ) => {
        console.log( 'User confirmed:', confirmed );
            if ( confirmed ) {
        this.isSaving = true;
        if (this.refMotif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.refMotifService.update(this.refMotif));
                this.createMotif = true  ;
        } else {
            this.subscribeToSaveResponse(
                this.refMotifService.create(this.refMotif));
                this.createMotif = true  ;
        }}
        })
          .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
    }
    edit(motif) {
     this.refMotif = motif
     this.editMotif = true;
     this.createMotif = false  ;
    }




    private subscribeToSaveResponse(result: Observable<RefMotif>) {
        result.subscribe((res: RefMotif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }
    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }
    private onSaveSuccess(result: RefMotif) {
        this.eventManager.broadcast({ name: 'modifListModification', content: 'OK'});
        this.loadAll();
        this.refMotif = new RefMotif();
    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRefMotifs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RefMotif) {
        return item.id;
    }
    registerChangeInRefMotifs() {
        this.eventSubscriber = this.eventManager.subscribe('refMotifListModification', (response) => this.loadAll());
    }



    private onSuccess(data, headers) {
        this.refMotifs = data;
        this.dtTrigger.next();
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
