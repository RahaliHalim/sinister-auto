import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { Search } from '../../sinister';
import { Partner, PartnerService } from '../../partner';
import { ListCharge } from '../../contrat-assurance/souscription-ga/listCharge.model';
import { Governorate, GovernorateService } from '../../governorate';
import { ResponseWrapper } from '../../../shared';
import { ContratAssuranceService } from '../../contrat-assurance';
import { RefTypeServiceService, RefTypeService } from '../../ref-type-service';
import { ReparateurService, Reparateur } from '../../reparateur';
import { ExpertService } from '../../expert';
import { Expert } from '../../../entities/expert/expert.model';
import { Bonification } from './bonification.model';
import { ReportService } from '../report.service';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { GaDatatable } from '../../../constants/app.constants';
import { JhiAlertService } from 'ng-jhipster';


@Component({
  selector: 'jhi-bonification-depenses',
  templateUrl: './bonification-depenses.component.html',
  styles: []
})
export class BonificationDepensesComponent implements OnInit, OnDestroy, AfterViewInit {


  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  search: Search = new Search();
  partners: Partner[];
  listCharge: ListCharge[];
  governorate: Governorate[];
  refService: RefTypeService[];
  listExperts: Expert[];
  bonification: Bonification[];
  bonificationOrig: Bonification[];
  reparators: Reparateur[] = [];

  dtOptions: any = {};
  dtTrigger: Subject<Bonification> = new Subject;


  constructor(private contratAssuranceService: ContratAssuranceService,
    private goverService: GovernorateService,
    private partnerService: PartnerService,
    private expertService: ExpertService,
    private reparateurService: ReparateurService,
    private refTypeServiceService: RefTypeServiceService,
    private reportService: ReportService,
    private alertService: JhiAlertService

  ) { }

  ngOnInit() {

    this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
    this.dtOptions.scrollX = true;
    this.bonification = null;
    this.bonificationOrig = null;
    this.search = new Search();
    /*  this.reportService.queryBonification().subscribe(
     (res : ResponseWrapper) =>{
  
      this.bonification = res.json;
     }); */

    this.expertService.query().subscribe(
      (res: ResponseWrapper) => {

        this.listExperts = res.json;
      });

    this.contratAssuranceService.findAllListCharge().subscribe(
      (res: ResponseWrapper) => {
        this.listCharge = res.json;
      }
    );

    this.reparateurService.query().subscribe((res: ResponseWrapper) => {
      this.reparators = res.json;
    });

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
    this.clear();
    // this.loadAll();
  }


  filter() {
    this.loadAll();
  }


  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }


  loadAll() {
    this.reportService.findBonification(this.search)
      .subscribe(
        (res: Bonification[]) => {
          this.bonification = res;
          this.bonificationOrig = res;
          this.bonification = this.bonificationOrig.filter(this.filterArray.bind(this));

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

    const serviceFlt = this.search.serviceId != undefined ? this.search.serviceId == element.serviceId : true;
    const expertFlt = this.search.expertId != undefined ? this.search.expertId == element.expertId : true;
    const idZoneReparateurFlt = this.search.idZoneReparateur != undefined ? this.search.idZoneReparateur == element.idZoneReparateur : true;
    const reparateurIdFlt = this.search.reparateurId != undefined ? this.search.reparateurId == element.reparateurId : true;
    const chargeIdFlt = this.search.chargeId != undefined ? this.search.chargeId == element.chargeId : true;

    return partnerFlt && zoneFlt && serviceFlt && expertFlt && idZoneReparateurFlt && reparateurIdFlt && chargeIdFlt;
  }

  private onError(error) {
    this.alertService.error(error.message, null, null);
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy();
      this.dtTrigger.next();
    });
  }

  clear() {
    this.reportService.queryBonification().subscribe(
      (res: ResponseWrapper) => {

        this.bonification = res.json;
        this.rerender();

      }
    );
    this.search = new Search();

  }

  ngOnDestroy() {
  }

}
