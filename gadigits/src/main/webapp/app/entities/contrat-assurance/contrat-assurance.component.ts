import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Principal, ResponseWrapper, AlertUtil, UserService } from '../../shared';
import { Subject } from "rxjs";
import { ViewPolicy, ViewPolicyService } from '../view-policy';
import { PermissionAccess, UserExtraService } from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupService } from '../history/history-popup.service';
import { HistoryPopupDetail } from '../history';

class DataTablesResponse {
    data: any[];
    draw: number;
    recordsFiltered: number;
    recordsTotal: number;
}
  
@Component({
    selector: 'jhi-contrat-assurance',
    templateUrl: './contrat-assurance.component.html'
})
export class ContratAssuranceComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    currentAccount: any;
    policies: ViewPolicy[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    authorities: any[];

    dtOptions: any = {};
    dtTrigger: Subject<ViewPolicy> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();

    constructor(
        private alertUtil: AlertUtil,
        public  principal: Principal,
        private userService: UserService,
        private eventManager: JhiEventManager,
        private viewPolicyService: ViewPolicyService,
        private http: HttpClient,
        private historyPopupService: HistoryPopupService,

        private userExtraService: UserExtraService
    ) {
    }

    ngOnInit() {
        console.log('_____________________________________________________');
        const that = this;
        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            serverSide: true,
            processing: true,            
            ajax: (dataTablesParameters: any, callback) => {
              that.http
                .post<DataTablesResponse>(
                  'api/view-policies/page',
                  dataTablesParameters, {}
                ).subscribe(resp => {
                  that.policies = resp.data;
      
                  callback({
                    recordsTotal: resp.recordsTotal,
                    recordsFiltered: resp.recordsFiltered,
                    data: []
                  });
                });
            }, 
            columns: [{ data: 'id' }, { data: 'policyNumber' }, { data: 'companyName' }, { data: 'agencyName' }, 
                        { data: 'registrationNumber' }, { data: 'policyHolderName' }, { data: 'startDate' }, { data: 'endDate' }],
                        dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6"p>>',

                        language: {
                            processing: 'Traitement en cours...',
                            search: 'Rechercher&nbsp;:',
                            lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
                            info: '_START_ - _END_ / _TOTAL_',
                            infoEmpty: 'La liste est vide',
                            infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
                            infoPostFix: '',
                            loadingRecords: 'Chargement en cours...',
                            zeroRecords: '...',
                            emptyTable: '...',
                            paginate: {
                                first: '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                                previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                                next: '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                                last: '<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
                            },            
                            aria: {
                                sortAscending: ': activer pour trier la colonne par ordre croissant',
                                sortDescending: ': activer pour trier la colonne par ordre décroissant'
                            }
                        },
                        // Declare the use of the extension in the dom parameter
                        //dom: 'Bfrtip',
                        // Configure the buttons
                        buttons: [
                            {
                                extend: 'print',
                                text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
                            },
                            {
                                extend: 'pdfHtml5',
                                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                                orientation: 'landscape',
                                pageSize: 'LEGAL' 
                            },
                            {
                                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>',
                                key: '1',
                                action: function (e, dt, node, config) {
                                    that.viewPolicyService.exportpoliciesToExcel(dt.search()).subscribe((blob: Blob) => {
                                        let fileName="Contrats.xlsx";
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
                        ]
                
          };        
        /*this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        this.dtOptions.serverSide = true;
        this.dtOptions.processing = true;
        this.dtOptions.ajax = (dataTablesParameters: any, callback) => {
            that.http
              .post<DataTablesResponse>(
                'http://localhost:9000/api/view-policies/page',
                dataTablesParameters, {}
              ).subscribe(resp => {
                that.policies = resp.data;
    
                callback({
                  recordsTotal: resp.recordsTotal,
                  recordsFiltered: resp.recordsFiltered,
                  data: []
                });
              });
        };*/
        //this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(34, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });


        //this.registerChangeInContratAssurances();
    }

    loadAll() {
        this.viewPolicyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.policies = res.json;
                this.dtTrigger.next(); // Actualize datatables
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"Contrat");
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

    ngOnDestroy() {
        if(this.eventSubscriber !== null && this.eventSubscriber !== undefined)
            this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ViewPolicy) {
        return item.id;
    }
    registerChangeInContratAssurances() {
        this.eventSubscriber = this.eventManager.subscribe('contratAssuranceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertUtil.addError(error.message);
    }

}
