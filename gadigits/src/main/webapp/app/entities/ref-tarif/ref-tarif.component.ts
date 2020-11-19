import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Response } from '@angular/http';
import { Subscription, Subject, Observable } from 'rxjs/Rx';
import { JhiAlertService } from 'ng-jhipster';
import { RefTarif } from './ref-tarif.model';
import { RefTarifService } from './ref-tarif.service';
import { TarifLine } from './tarif-line.model';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
import { PermissionAccess, UserExtraService } from '../user-extra';

@Component({
    selector: "jhi-ref-tarif",
    templateUrl: "./ref-tarif.component.html"
})
export class RefTarifComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    refTarifs: RefTarif[]; // Pricing list
    tariflines: TarifLine[]; // Princing lines list
    error: any;
    success: any;
    dtOptions: any = {};
    dtTrigger: Subject<RefTarif> = new Subject();
    refTarif = new RefTarif();
    isSaving: boolean;
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private refTarifService: RefTarifService,
        private alertService: JhiAlertService,
        public principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService
    ) { }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;
        this.refTarifService.queryline().subscribe((res: ResponseWrapper) => { this.tariflines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(3, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }

    ngOnDestroy() {
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }

    /**
     * Load all services pricing
     */
    loadAll() {
        this.refTarifService
            .query()
            .subscribe(
                (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    /**
     * Delete a pricing element
     * @param id : the id of the pricing element to delete
     */
    public delete(id: number) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer ce tarif ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                if (confirmed) {
                    this.refTarifService.delete(id).subscribe((response) => {
                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
            .catch(() =>
                console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog'));
    }

    /**
     * Prepare edit
     * @param id : the id of the pricing element to update
     */
    edit(id: number) {
        this.refTarifService.find(id).subscribe((refTarif) => {
            this.refTarif = refTarif;
        });
    }

    /**
     * Cancel edit element
     */
    cancel() {
        this.refTarif = new RefTarif();
    }

    /**
     * Save current pricing element
     */
    save() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir enregistrer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then(confirmed => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.isSaving = true;
                    if (this.refTarif.id !== undefined) {
                        // Update
                        this.subscribeToSaveResponse(
                            this.refTarifService.update(this.refTarif)
                        );
                    } else {
                        // Create
                        this.subscribeToSaveResponse(
                            this.refTarifService.create(this.refTarif)
                        );
                    }
                }
            })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );

    }

    private subscribeToSaveResponse(result: Observable<RefTarif>) {
        result.subscribe(
            (res: RefTarif) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: RefTarif) {
        this.isSaving = false;
        this.refTarif = new RefTarif();
        this.loadAll();
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

    private onSuccess(data, headers) {
        this.refTarifs = data;
        this.rerender();
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
