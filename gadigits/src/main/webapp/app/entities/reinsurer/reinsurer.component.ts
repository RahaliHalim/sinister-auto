import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { Reinsurer } from './reinsurer.model';
import { ReinsurerService } from './reinsurer.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';

@Component({
    selector: 'jhi-reinsurer',
    templateUrl: './reinsurer.component.html'
})
export class ReinsurerComponent implements OnInit, OnDestroy {
    reinsurers: Reinsurer[];
    reinsurer: Reinsurer = new Reinsurer();
    currentAccount: any;
    eventSubscriber: Subscription;

    dtOptions: any = {};
    dtTrigger: Subject<Reinsurer> = new Subject();

    constructor(
        private reinsurerService: ReinsurerService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.reinsurerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reinsurers = res.json;
                this.dtTrigger.next();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReinsurers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    save() {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir sauvegarder vos modifications ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                console.log( 'User confirmed:', confirmed );
                if ( confirmed ) {
                    if (this.reinsurer.id !== undefined) {
                        this.subscribeToSaveResponse(this.reinsurerService.update(this.reinsurer));
                    } else {
                        this.subscribeToSaveResponse(this.reinsurerService.create(this.reinsurer));
                    }
                }
            })
            .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );

    }

    edit(id) {
        this.reinsurerService.find(id).subscribe((reinsurer) => {
            this.reinsurer = reinsurer;
        });

    }

    cancel() {
        this.reinsurer = new Reinsurer();
    }

    public delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer le réassureur ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                console.log( 'User confirmed:', confirmed );
                if(confirmed) {
                    this.reinsurerService.delete(id).subscribe((response) => {

                        console.log( 'User confirmed delete:', id );

                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
            .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
    }

    private subscribeToSaveResponse(result: Observable<Reinsurer>) {
        result.subscribe((res: Reinsurer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Reinsurer) {
        this.eventManager.broadcast({ name: 'reinsurerListModification', content: 'OK'});
        this.loadAll();
        this.reinsurer = new Reinsurer();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    trackId(index: number, item: Reinsurer) {
        return item.id;
    }
    registerChangeInReinsurers() {
        this.eventSubscriber = this.eventManager.subscribe('reinsurerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
