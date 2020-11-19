import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild } from '@angular/core';
import { Search } from '../../sinister';
import { Governorate, GovernorateService } from '../../governorate';
import { Contrat } from '../contrat.model';
import { Partner, PartnerService } from '../../partner';
import { ResponseWrapper } from '../../../shared';
import { RefTypeService, RefTypeServiceService } from '../../ref-type-service';
import { SouscritpionGa } from './souscriptionGa.model';
import { GaDatatable } from '../../../constants/app.constants';
import { ContratAssuranceService } from '../contrat-assurance.service';
import { Subject, Subscription } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { UserExtra, UserExtraService } from '../../user-extra';
import { ListCharge } from './listCharge.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { HttpClient } from '@angular/common/http';




class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}


@Component({
  selector: 'jhi-souscription-ga',
  templateUrl: './souscription-ga.component.html',
  styles: []
})

export class SouscriptionGaComponent implements  OnInit , OnDestroy, AfterViewInit{


  @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    eventSubscriber: Subscription;

  dtOptions: any = {};
  dtTrigger : Subject<SouscritpionGa> = new Subject;
  souscriptionOrg : SouscritpionGa[];
  souscription : SouscritpionGa[];
  partners : Partner[];
  refService : RefTypeService[];
  user : UserExtra[];

  listCharge : ListCharge[];
  search: Search = new Search();
  governorate : Governorate [];
  constructor(    private partnerService: PartnerService,
    private goverService : GovernorateService,    
    private refTypeServiceService: RefTypeServiceService,
    private contratAssuranceService : ContratAssuranceService,
    private userService : UserExtraService,
    private alertService: JhiAlertService,
    private http: HttpClient,  private eventManager: JhiEventManager

    ) { }

  ngOnInit() {

      this.LoadAllSouscription();
      this.contratAssuranceService.findAllListCharge().subscribe(
       (res: ResponseWrapper) => {
      this.listCharge = res.json;
      }
    );


    this.goverService.query().subscribe(
    (res: ResponseWrapper) => {
      this.governorate = res.json;
     }
    );


    this.partnerService.query().subscribe(
      (res: ResponseWrapper) => {
          this.partners = res.json;
      }
      );

      this.refTypeServiceService.query().subscribe(
    (res: ResponseWrapper) => {
        this.refService = res.json;
    },
    );


  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
}

      LoadAllSouscription(){

     const that = this;
       this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      serverSide: true,
      processing: true,            
      ajax: (dataTablesParameters: any, callback) => {
        that.http
          .post<DataTablesResponse>(
            'api/view/souscriptionGa/page',
            dataTablesParameters, {},
          ).subscribe(resp => {
            that.souscription = resp.data;

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
                                    that.contratAssuranceService.exportCaPoliciesToExcel(dt.search()).subscribe((blob: Blob) => {
                                        let fileName="souscription-ga.xlsx";
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


        rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
          dtInstance.destroy();
            this.dtTrigger.next();
           });
         }

          loadAll() {  
            this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
            this.dtOptions.scrollX = true;
         this.contratAssuranceService.querySouscriptionSearch(this.search)
        .subscribe(
      (res:SouscritpionGa[]) => { 
        this.souscription = res;
        this.souscriptionOrg = res;
        this.souscription = this.souscriptionOrg.filter(this.filterArray.bind(this));
      
        this.dtOptions.buttons = [
          {
              extend: 'print',
              text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
          },
          {
              extend: 'csvHtml5',
              text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
              fieldSeparator: ';'
          },
          {
              extend: 'excelHtml5',
              text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>',
              autoFilter: true
          },
          {
              extend: 'pdfHtml5',
              text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
              orientation: 'landscape',
              pageSize: 'LEGAL'
          }
      ];
      
      this.rerender();
      
      
      },
      (res: ResponseWrapper) => this.onError(res.json));
      
      



  } 


filterArray(element, index, array) {
  console.log('___________________________________________________________');
  
  const zoneFlt = this.search.zoneId != undefined ? this.search.zoneId == element.zoneId : true;
  const serviceFlt = this.search.serviceId != undefined ? this.search.serviceId == element.serviceId : true;
  const compagnieIdFlt = this.search.compagnieId != undefined ? this.search.compagnieId == element.compagnieId : true;
  const chargeIdFlt = this.search.chargeId != undefined ? this.search.chargeId == element.chargeId : true;

  
  return  zoneFlt && serviceFlt && compagnieIdFlt && chargeIdFlt;
}






  private onError(error) {
    this.alertService.error(error.message, null, null);
  }
  

  clear(){
    this.search = new Search();
  }

  filter(){
    this.souscription = null ;
    this.loadAll();
    

}

ngOnDestroy() {
  if(this.eventSubscriber !== null && this.eventSubscriber !== undefined)
  this.eventManager.destroy(this.eventSubscriber);
}

}
