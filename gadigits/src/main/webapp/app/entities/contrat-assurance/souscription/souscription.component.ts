import { Component, OnInit, ViewChild, OnDestroy,AfterViewInit } from '@angular/core';
import { SinisterPrestation, Search, Recherche } from '../../sinister';
import { GaDatatable } from '../../../constants/app.constants';
import { Contrat } from '../contrat.model';
import { Subject, Subscription } from 'rxjs';
import { ContratAssuranceService } from '../contrat-assurance.service';
import { ResponseWrapper, Principal, Account } from '../../../shared';
import { DataTableDirective } from 'angular-datatables';
import { Governorate, GovernorateService } from '../../governorate';
import { Partner, PartnerService } from '../../partner';
import { RefPack, RefPackService } from '../../ref-pack';
import { HttpClient } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';


class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
  search: Search = new Search();
}

@Component({
  selector: 'jhi-souscription',
  templateUrl: './souscription.component.html',
  styles: []
})
export class SouscriptionComponent implements OnInit , OnDestroy, AfterViewInit{
  
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  
  eventSubscriber: Subscription;

  contrat : Contrat[];
  governorate: Governorate[];
  account: any; 
  dtOptions: any = {};
  dtTrigger : Subject<Contrat> = new Subject;
  search: Search = new Search();
  partners : Partner[];
  pack : RefPack[];
  recherche = new Recherche();
  
  constructor(
    private contratAssuranceService : ContratAssuranceService,
    private goverService : GovernorateService,        
    private partnerService: PartnerService,
    private refpackService : RefPackService,
    private principal: Principal,
    private http: HttpClient,  private eventManager: JhiEventManager,
    private alertService: JhiAlertService
  ) { }

  ngOnInit() {
    this.loadAll();
    this.goverService.query().subscribe((res: ResponseWrapper) => {
      this.governorate = res.json;
    }); 
    this.partnerService.query().subscribe((res: ResponseWrapper) => {
      this.partners = res.json;
    });
    this.refpackService.query().subscribe((res: ResponseWrapper) => {
      this.pack = res.json;
    });
  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }
  
  changeMode(value) {
    console.log("mode"+value);
    this.refpackService.findByPartner(value).subscribe((res: ResponseWrapper) => {
      this.pack = res.json;
    });
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy();
      this.dtTrigger.next();
    });
  }

  loadAll(){
    const that = this;
    this.dtOptions = {
        pagingType: 'full_numbers',
        pageLength: 10,
        serverSide: true,
        processing: true,            
        ajax: (dataTablesParameters: any, callback) => {
          that.http
            .post<DataTablesResponse>(
              'api/view/contrat/page',
              dataTablesParameters, {}
            ).subscribe(resp => {
              that.contrat = resp.data;
  
              callback({
                recordsTotal: resp.recordsTotal,
                recordsFiltered: resp.recordsFiltered,    
                data: []
              });
            });
          }, 
          columns: [{ data: 'nomAgentAssurance' }, { data: 'usageLabel' }, { data: 'naturePack' }, { data: 'zone' }],
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
                        },  buttons: [
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
                                    that.contratAssuranceService.exportSouscriptionToExcel(dt.search()).subscribe((blob: Blob) => {
                                        let fileName="indicateur-souscription.xlsx";
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
  }

  clear(){
    this.search = new Search();
  }

  
  filter(){
    this.contratAssuranceService.queryContratSearch(this.search).subscribe((res :Contrat[]) => {
        this.contrat = res;
        this.rerender();
    },
    (res: ResponseWrapper) => this.onError(res.json)
    );
  }

  ngOnDestroy() {
  }

  private onError(error) {
    this.alertService.error(error.message, null, null);
  }
}
