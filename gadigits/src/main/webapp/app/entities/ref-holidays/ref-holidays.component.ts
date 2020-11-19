import { GaDatatable } from '../../constants/app.constants';
import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { RefHolidays } from './ref-holidays.model';
import { RefHolidaysService } from './ref-holidays.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { DataTableDirective } from 'angular-datatables';
import { PermissionAccess, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-ref-holidays',
    templateUrl: './ref-holidays.component.html'
})
export class RefHolidaysComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    holidaysList: RefHolidays[];
    holidays: RefHolidays = new RefHolidays();
    error: any;
    success: any;

    dtOptions: any = {};
    dtTrigger: Subject<RefHolidays> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private refHolidaysService: RefHolidaysService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService: UserExtraService
    ) {
    }

    loadAll() {
        this.refHolidaysService.query().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(2, this.currentAccount.id).subscribe(res => {
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

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir sauvegarder vos modifications ?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                console.log('User confirmed:', confirmed);
                if (confirmed) {
                    this.refHolidaysService.findByLabelOrDate(this.holidays).subscribe((res) => {
                        if (res !== null && res !== undefined && res.json.length > 0) {
                            this.alertService.error('auxiliumApp.refHolidays.duplicate', null, null);
                        } else {
                            if (this.holidays.id !== undefined) {
                                this.subscribeToSaveResponse(
                                    this.refHolidaysService.update(this.holidays));
                            } else {
                                this.subscribeToSaveResponse(
                                    this.refHolidaysService.create(this.holidays));
                            }
                        }
                    });
                }
            })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    edit(id) {
        this.refHolidaysService.find(id).subscribe((holidays) => {
            this.holidays = holidays;
            if (holidays.date) {
                this.holidays.date = {
                    year: holidays.date.getFullYear(),
                    month: holidays.date.getMonth() + 1,
                    day: holidays.date.getDate()
                };
            }

        });

    }

    cancel() {
        this.holidays = new RefHolidays();
    }

    public delete(id) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir supprimer le jour férié?', 'Oui', 'Non', 'lg')
            .then((confirmed) => {
                if (confirmed) {
                    this.refHolidaysService.delete(id).subscribe((response) => {
                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
    }

    private subscribeToSaveResponse(result: Observable<RefHolidays>) {
        result.subscribe((res: RefHolidays) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RefHolidays) {
        this.loadAll();
        this.holidays = new RefHolidays();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onSuccess(data, headers) {
        this.holidaysList = data;
        this.rerender();
    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
