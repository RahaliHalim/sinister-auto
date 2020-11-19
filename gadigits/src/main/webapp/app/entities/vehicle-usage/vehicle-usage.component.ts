import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ConfirmationDialogService, Principal, ResponseWrapper, AlertUtil } from '../../shared';
import { GaDatatable, Global } from './../../constants/app.constants';
import { VehicleUsage } from './vehicle-usage.model';
import { VehicleUsageService } from './vehicle-usage.service';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { DataTableDirective } from 'angular-datatables';
import { VatRate, VatRateService } from '../vat-rate';
import { Partner, PartnerService } from '../partner';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService, HistoryPopupDetail } from '../history';

@Component({
    selector: 'jhi-vehicle-usage',
    templateUrl: './vehicle-usage.component.html'
})
export class VehicleUsageComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    permissionToAccess : PermissionAccess = new PermissionAccess();
    vehicleUsages: VehicleUsage[];
    vehicleUsage: VehicleUsage = new VehicleUsage();
    vehicleUsageCheck: VehicleUsage;
    refTvas: VatRate[];
    partners : Partner[];
    private ngbModalRef: NgbModalRef;


    isSaving: boolean;
    currentAccount: any;
    dtOptions: any = {};
    dtTrigger: Subject<VehicleUsage> = new Subject();
    existLabel = true;
    textPattern = Global.textPattern;

    constructor(
        private vehicleUsageService: VehicleUsageService,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private userExtraService : UserExtraService,
        private refTvaService: VatRateService,
        private partnerService: PartnerService,
        private historyPopupService: HistoryPopupService

    ) {
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

    loadAll() {
        this.vehicleUsageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.vehicleUsages = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.vehicleUsage.label = this.vehicleUsage.label.trim();
    }
   

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.vehicleUsage.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.vehicleUsageService.update(
                                this.vehicleUsage
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.vehicleUsageService.create(
                                this.vehicleUsage
                            )
                        );
                    }
                }
            })
    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"UsageVehicule");
         this.ngbModalRef.result.then((result: any) => {
             if (result !== null && result !== undefined) {
             }
             this.ngbModalRef = null;
         }, (reason) => {
             // TODO: print error message
             console.log('______________________________________________________2');
             this.ngbModalRef = null;
         });
     }

    edit(id: number) {
        this.vehicleUsageService.find(id).subscribe(vehicleUsage => {
            this.vehicleUsage = vehicleUsage;
        });
    }

    cancel() {
        this.vehicleUsage = new VehicleUsage();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                if(confirmed) {
                    this.vehicleUsageService.delete(id).subscribe((response) => {
                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(73,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });

        this.refTvaService.query().subscribe((res: ResponseWrapper) => { this.refTvas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.partnerService.findAllCompanies().subscribe((res: ResponseWrapper) => { this.partners = res.json; });

    }

    ngOnDestroy() {
    }

    trackId(index: number, item: VehicleUsage) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<VehicleUsage>) {
        result.subscribe((res: VehicleUsage) => this.onSaveSuccess(res),       
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: VehicleUsage) {
        this.isSaving = false;
        this.cancel();
        this.loadAll();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.isSaving = false;
        this.alertUtil.addError(error.message);
    }
}
