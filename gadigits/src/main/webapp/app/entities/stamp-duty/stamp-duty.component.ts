import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Observable } from 'rxjs/Rx';
import { JhiAlertService } from 'ng-jhipster';
import { StampDuty } from './stamp-duty.model';
import { StampDutyService } from './stamp-duty.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history';
import { HistoryPopupDetail } from '../history/history-popup-detail';

@Component({
    selector: 'jhi-stamp-duty',
    templateUrl: './stamp-duty.component.html'
})
export class StampDutyComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    currentAccount: any;
    permissionToAccess : PermissionAccess = new PermissionAccess();
    stampdutyList: StampDuty[];
    stampduty: StampDuty = new StampDuty();
    error: any;
    success: any;
    amountPattern = '^[0-9]+(\.[0-9]{1,3})?';
    datePattern = '(0[1-9]|[1-2][0-9]|3[0-1])\/(0[1-9]|1[0-2])\/[0-9]{4}';
    minDate = { year: 1900, month: 1, day: 1 };
    dtOptions: any = {};
    dtTrigger: Subject<StampDuty> = new Subject();
    private ngbModalRef: NgbModalRef;

    constructor(
        private stampDutyService: StampDutyService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService : UserExtraService,
        private historyPopupService: HistoryPopupService,
    ) {
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    trimLabel() {
        this.stampduty.amount = this.stampduty.amount != undefined ? +('' + this.stampduty.amount).trim() : undefined;
    }

    loadAll() {
        this.stampDutyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.stampdutyList = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(87,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }

    ngOnDestroy() {
        
    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"DroitTimbre");
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

    save() {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir sauvegarder ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                console.log( 'User confirmed:', confirmed );
                if ( confirmed ) {
                    if (this.stampduty.id !== undefined) {
                        this.subscribeToSaveResponse(
                        this.stampDutyService.update(this.stampduty));
                    } else {
                        this.subscribeToSaveResponse(
                        this.stampDutyService.create(this.stampduty));
                    }

                }
            })
            .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );

    }

    edit(id) {
        this.stampDutyService.find(id).subscribe((stampduty) => {
            this.stampduty = stampduty;
            if (stampduty.startDate) {
                this.stampduty.startDate = {
                    year: stampduty.startDate.getFullYear(),
                    month: stampduty.startDate.getMonth() + 1,
                    day: stampduty.startDate.getDate()
                };
            }

        });

    }

    cancel() {
        this.stampduty = new StampDuty();
    }

    public delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer le jour férié?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                console.log( 'User confirmed:', confirmed );
                if(confirmed) {
                    this.stampDutyService.delete(id).subscribe((response) => {

                        console.log( 'User confirmed delete:', id );

                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
            .catch(() => console.log( 'User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)' ) );
    }

    private subscribeToSaveResponse(result: Observable<StampDuty>) {
        result.subscribe((res: StampDuty) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: StampDuty) {
        this.stampduty = new StampDuty();
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
        this.alertService.error(error.message, null, null);
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
          // Destroy the table first
          dtInstance.destroy();
          // Call the dtTrigger to rerender again
          this.dtTrigger.next();
        });
    }
}
