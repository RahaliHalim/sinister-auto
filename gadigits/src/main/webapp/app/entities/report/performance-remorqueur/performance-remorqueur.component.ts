import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Search } from '../../sinister';
import { Governorate, GovernorateService } from '../../governorate';
import { DataTableDirective } from 'angular-datatables';
import { ResponseWrapper } from '../../../shared';
import { JhiAlertService } from 'ng-jhipster';
import { Expert, ExpertService } from '../../expert';
import { Reparateur, ReparateurService } from '../../reparateur';
import { ListCharge } from '../../contrat-assurance/souscription-ga/listCharge.model';
import { ContratAssuranceService } from '../../contrat-assurance';
import { RefModeGestion, RefModeGestionService } from '../../ref-mode-gestion';
import { Partner, PartnerService } from '../../partner';
import { RefRemorqueur, RefRemorqueurService } from '../../ref-remorqueur';
import { PerformanceRemorqueur } from './performance-remorqueur.model';
import { Subject } from 'rxjs';
import { ReportService } from '../report.service';
import { GaDatatable } from '../../../constants/app.constants';
import { RefCompagnieService } from '../../ref-compagnie';
@Component({
  selector: 'jhi-performance-remorqueur',
  templateUrl: './performance-remorqueur.component.html',
  styles: []
})
export class PerformanceRemorqueurComponent implements OnInit, AfterViewInit {
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: any = {};
  dtTrigger: Subject<PerformanceRemorqueur> = new Subject();

  performance : PerformanceRemorqueur[];
  performanceOrg : PerformanceRemorqueur[];
  partners : Partner[]; 
  tugs: RefRemorqueur[];

  search: Search = new Search();
  governorate: Governorate[]; 
    constructor(        private governorateService: GovernorateService,
     
      private partnerService: RefCompagnieService,
      private tugService: RefRemorqueurService,
      private  reportService : ReportService,
      private alertService: JhiAlertService) {
    
   }

  ngOnInit() {
  
    this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
    this.dtOptions.scrollX = true;

    this.reportService.queryPerformance().subscribe(
      (res : ResponseWrapper) =>{
   
       this.performance = res.json;
      });


    this.governorateService.query().subscribe((res: ResponseWrapper) => {
      this.governorate = res.json;
  }, (res: ResponseWrapper) => this.onError(res.json));


  this.partnerService.getAgreementCompany().subscribe(
    (res: ResponseWrapper) => {
        this.partners = res.json;
    }
);
this.tugService.query().subscribe(
  (res: ResponseWrapper) => {
      this.tugs = res.json;
  },
  (res: ResponseWrapper) => this.onError(res.json)
);
  }


  private onError(error) {
    this.alertService.error(error.message, null, null);
}

filter() {
  this.loadAll();
}


ngAfterViewInit(): void {
  this.dtTrigger.next();
}


loadAll(){

  this.reportService.findPerformance(this.search)
  .subscribe(
  (res:PerformanceRemorqueur[]) => { 
    this.performance = res;
    this.performanceOrg = res;
    this.performance = this.performanceOrg.filter(this.filterArray.bind(this));
  
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
          autoFilter: true,
       
          }
      ,
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
    
    const partnerFlt = this.search.compagnieId != undefined ? this.search.compagnieId == element.compagnieId : true;
    const zoneFlt = this.search.zoneId != undefined ? this.search.zoneId == element.zoneId : true;
    const remorqueurFlt = this.search.tugId != undefined ? this.search.tugId == element.remorqueurId : true;

    


    const idStepPecFlt = this.search.idStepPec != undefined ? this.search.idStepPec == element.etatDossierId : true;


    
    return idStepPecFlt && partnerFlt && zoneFlt && remorqueurFlt ;
  }


  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
        dtInstance.destroy();
        this.dtTrigger.next();
    });
  }


  clear(){
    this.reportService.queryPerformance().subscribe(
      (res : ResponseWrapper) =>{
   
       this.performance = res.json;
      });

  
    this.search = new Search();


  }

}
