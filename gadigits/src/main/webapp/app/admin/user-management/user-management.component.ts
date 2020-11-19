import { Component, OnInit, OnDestroy, ViewContainerRef, ViewChild, AfterViewInit } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { DomSanitizer } from '@angular/platform-browser';
import { Principal, User, UserService, ResponseWrapper, ConfirmationDialogService, AlertUtil } from '../../shared';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Rx';
import { UserProfileService, UserProfile } from '../../entities/user-profile';
import { UserExtraService, UserExtra, ViewUser } from '../../entities/user-extra';
import { GaDatatable } from './../../constants/app.constants';
import { DataTableDirective } from 'angular-datatables';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupDetail, HistoryPopupService } from '../../entities/history';

@Component({
    selector: 'jhi-user-mgmt',
    templateUrl: './user-management.component.html'
})
export class UserMgmtComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    currentAccount: any;
    users: User[] = [];
    usersExtra: ViewUser[] = [];
    error: any;
    success: any;

    authorities: any[] = [];
    private ngbModalRef: NgbModalRef;

    auth: any;
    isSaving: boolean;
    eventSubscriber: Subscription;

    dtOptions: any = {};
    dtTrigger: Subject<User> = new Subject();


    constructor(
        private userService: UserService,
        private userExtraService: UserExtraService,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private principal: Principal,
        private eventManager: JhiEventManager,
        private sanitizer: DomSanitizer,
        private confirmationDialogService: ConfirmationDialogService,
        private historyPopupService: HistoryPopupService,
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

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.isSaving = false;

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;

        });
    }

    activate(id: number) {
        this.confirmationDialogService
            .confirm('Confirmation', 'Êtes-vous sûrs de vouloir activer cet utilisateur?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.userService.activateUser(id).subscribe((response) => {
                        this.isSaving = false;
                        this.loadAll();
                    });
                }
            });
    }
    disable(id: number) {
        this.confirmationDialogService
            .confirm('Confirmation', 'Êtes-vous sûrs de vouloir désactiver cet utilisateur?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.userService.disableUser(id).subscribe((response) => {
                        this.isSaving = false;
                        this.loadAll();
                    });
                }
            });
    }

    /**
     * Export user in excel format
     * @param id : the user id
     */
    excelExport(user: UserExtra) {
        if(user.id == null || user.id === undefined) {
            console.log("Erreur lors de la génération");
            
        } else { // OK
            this.userService.exportUserInExcel(user.id).subscribe((blob: Blob) => {
                let fileName="Utilisateur_" + user.firstName + "_" + user.lastName + ".xlsx";
                if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                    window.navigator.msSaveOrOpenBlob(blob, fileName);
                } else {
                    var a = document.createElement('a');
                    a.href = URL.createObjectURL(blob);
                    a.download = fileName;
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                }
            },
            err => {
              alert("Error while downloading. File Not Found on the Server");
            }); 
        }
    }


    private onError(error) {
        this.isSaving = false;
        this.alertUtil.addError(error.message);
    }

    ngOnDestroy() {
        //this.routeData.unsubscribe();
        if (this.eventSubscriber !== null && this.eventSubscriber !== undefined)
            this.eventManager.destroy(this.eventSubscriber);
    }


    loadAll() {
        this.userExtraService.findAllUsers().subscribe(
            (res: ResponseWrapper) => {
                this.usersExtra = res.json;
                this.rerender(); // Actualize datatables
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"User");
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

    trackIdentity(index, item: User) {
        return item.id;
    }
}
