import {
    Component,
    ViewChild,
    OnInit,
    OnDestroy,
    AfterViewInit,
} from "@angular/core";
import { Observable } from "rxjs/Rx";
import { Subject } from "rxjs";
import { JhiEventManager, JhiAlertService } from "ng-jhipster";
import {
    ConfirmationDialogService,
    Principal,
    ResponseWrapper,
    AlertUtil,
} from "../../shared";
import { GaDatatable, Global } from "./../../constants/app.constants";
import { VehicleBrand } from "./vehicle-brand.model";
import { VehicleBrandService } from "./vehicle-brand.service";
import { UserExtraService, PermissionAccess } from "../user-extra";
import { DataTableDirective } from "angular-datatables";
import { NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { HistoryPopupService } from "../history";
import { HistoryPopupDetail } from "../history/history-popup-detail";

@Component({
    selector: "jhi-vehicle-brand",
    templateUrl: "./vehicle-brand.component.html",
})
export class VehicleBrandComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    vehicleBrands: VehicleBrand[];
    vehicleBrand: VehicleBrand = new VehicleBrand();
    vehicleBrandCheck: VehicleBrand;
    private ngbModalRef: NgbModalRef;

    isSaving: boolean;
    currentAccount: any;
    dtOptions: any = {};
    dtTrigger: Subject<VehicleBrand> = new Subject();
    existLabel = true;
    textPattern = Global.textPattern;

    constructor(
        private vehicleBrandService: VehicleBrandService,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private userExtraService: UserExtraService,
        private historyPopupService: HistoryPopupService
    ) {}

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
        this.vehicleBrandService.query().subscribe(
            (res: ResponseWrapper) => {
                this.vehicleBrands = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.vehicleBrand.label = this.vehicleBrand.label.trim();
    }
    /**
     * Find vehicle brand by label
     * @param label
     */
    findBrandByLabel(label: string) {
        this.existLabel = false;
        if (label != null && label != undefined && label != "") {
            this.vehicleBrandService
                .findByLabel(label)
                .subscribe((vehicleBrand) => {
                    this.vehicleBrandCheck = vehicleBrand;
                    if (
                        this.vehicleBrandCheck.id != null &&
                        this.vehicleBrandCheck.id != undefined
                    ) {
                        if (
                            this.vehicleBrandCheck.label
                                .replace(/\s/g, "")
                                .toLowerCase() ==
                            label.replace(/\s/g, "").toLowerCase()
                        ) {
                            this.existLabel = true;
                            this.alertUtil.addError(
                                "auxiliumApp.vehicleBrand.exist"
                            );
                        }
                    } else {
                        this.existLabel = false;
                    }
                });
        }
    }

    save() {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir enregistrer ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.vehicleBrand.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.vehicleBrandService.update(this.vehicleBrand)
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.vehicleBrandService.create(this.vehicleBrand)
                        );
                    }
                }
            });
    }

    edit(id: number) {
        this.vehicleBrandService.find(id).subscribe((vehicleBrand) => {
            this.vehicleBrand = vehicleBrand;
        });
    }

    cancel() {
        this.vehicleBrand = new VehicleBrand();
    }

    delete(id) {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer cet enregistrement ?",
                "Oui",
                "Non",
                "lg"
            )
            .then((confirmed) => {
                if (confirmed) {
                    this.vehicleBrandService
                        .delete(id)
                        .subscribe((response) => {
                            // Refresh refpricing list
                            this.loadAll();
                        });
                }
            });
    }

    selectHistory(id) {
        console.log("premier logggggg" + id);
        this.ngbModalRef = this.historyPopupService.openHist(
            HistoryPopupDetail as Component,
            id,
            "Marque"
        );
        this.ngbModalRef.result.then(
            (result: any) => {
                if (result !== null && result !== undefined) {
                }
                this.ngbModalRef = null;
            },
            (reason) => {
                // TODO: print error message
                console.log(
                    "______________________________________________________2"
                );
                this.ngbModalRef = null;
            }
        );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService
                .findFunctionnalityEntityByUser(71, this.currentAccount.id)
                .subscribe((res) => {
                    this.permissionToAccess = res;
                });
        });
    }

    ngOnDestroy() {}

    trackId(index: number, item: VehicleBrand) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<VehicleBrand>) {
        result.subscribe(
            (res: VehicleBrand) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: VehicleBrand) {
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
