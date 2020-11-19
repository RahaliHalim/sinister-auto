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
import { UserExtraService, PermissionAccess } from "../user-extra";
import { DataTableDirective } from "angular-datatables";
import { NgbModalRef } from "@ng-bootstrap/ng-bootstrap";
import { HistoryPopupService } from "../history";
import { HistoryPopupDetail } from "../history/history-popup-detail";
import { NaturePanne } from "./nature-panne.model";
import { NaturePanneService } from "./nature-panne.service";

@Component({
    selector: "jhi-nature-panne",
    templateUrl: "./nature-panne.component.html",
})
export class NaturePanneComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    permissionToAccess: PermissionAccess = new PermissionAccess();
    naturePannes: NaturePanne[];
    naturePanne: NaturePanne = new NaturePanne();
    naturePanneCheck: NaturePanne;
    private ngbModalRef: NgbModalRef;

    isSaving: boolean;
    currentAccount: any;
    dtOptions: any = {};
    dtTrigger: Subject<NaturePanne> = new Subject();
    existLabel = true;
    textPattern = Global.textPattern;

    constructor(
        private naturePanneService: NaturePanneService,
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
        this.naturePanneService.query().subscribe(
            (res: ResponseWrapper) => {
                this.naturePannes = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.naturePanne.label = this.naturePanne.label.trim();
    }
    /**
     * Find vehicle brand by label
     * @param label
     */
    findBrandByLabel(label: string) {
        this.existLabel = false;
        if (label != null && label != undefined && label != "") {
            this.naturePanneService
                .findByLabel(label)
                .subscribe((NaturePanne) => {
                    this.naturePanneCheck = NaturePanne;
                    if (
                        this.naturePanneCheck.id != null &&
                        this.naturePanneCheck.id != undefined
                    ) {
                        if (
                            this.naturePanneCheck.label
                                .replace(/\s/g, "")
                                .toLowerCase() ==
                            label.replace(/\s/g, "").toLowerCase()
                        ) {
                            this.existLabel = true;
                            this.alertUtil.addError(
                                "auxiliumApp.naturePanne.exist"
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
                    if (this.naturePanne.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.naturePanneService.update(this.naturePanne)
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.naturePanneService.create(this.naturePanne)
                        );
                    }
                }
            });
    }

    edit(id: number) {
        this.naturePanneService.find(id).subscribe((NaturePanne) => {
            this.naturePanne = NaturePanne;
        });
    }

    cancel() {
        this.naturePanne = new NaturePanne();
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
                    this.naturePanneService.delete(id).subscribe((response) => {
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
            "NaturePanne"
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
                .findFunctionnalityEntityByUser(109, this.currentAccount.id)
                .subscribe((res) => {
                    this.permissionToAccess = res;
                    console.log(res);
                });
        });
    }
    activate(naturePanne: NaturePanne) {
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
                    naturePanne.active = true;
                    this.subscribeToSaveResponse(
                        this.naturePanneService.update(naturePanne)
                    );
                }
            });
    }

    disable(naturePanne: NaturePanne) {
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
                    naturePanne.active = false;
                    this.subscribeToSaveResponse(
                        this.naturePanneService.update(naturePanne)
                    );
                }
            });
    }
    ngOnDestroy() {}

    trackId(index: number, item: NaturePanne) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<NaturePanne>) {
        result.subscribe(
            (res: NaturePanne) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: NaturePanne) {
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
