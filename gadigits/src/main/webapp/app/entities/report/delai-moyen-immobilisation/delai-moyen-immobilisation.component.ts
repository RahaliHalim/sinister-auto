import { Component, OnInit, ViewChild } from '@angular/core';
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
import { ReportService } from '../report.service';
import { DelaiMoyenImmobilisation } from './delai-moyen.model';
import { GaDatatable } from '../../../constants/app.constants';
import { Subject } from 'rxjs';
@Component({
  selector: 'jhi-delai-moyen-immobilisation',
  templateUrl: './delai-moyen-immobilisation.component.html',
  styles: []
})
export class DelaiMoyenImmobilisationComponent implements OnInit {


  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: any = {};
  dtTrigger: Subject<DelaiMoyenImmobilisation> = new Subject;

  listCharge: ListCharge[];
  delaiMoyImmobilisation: DelaiMoyenImmobilisation[];
  delaiMoyImmobilisationOrg: DelaiMoyenImmobilisation[];

  modeGestion: RefModeGestion[];
  partners: Partner[];

  search: Search = new Search();
  governorate: Governorate[];

  constructor(private reportService: ReportService, private governorateService: GovernorateService,
    private contratAssuranceService: ContratAssuranceService,

    private expertService: ExpertService,
    private reparateurService: ReparateurService,

    private refModeGestionService: RefModeGestionService,
    private partnerService: PartnerService,

    private alertService: JhiAlertService
  ) { }
  ngOnInit() {
    this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
    this.dtOptions.scrollX = true;
    this.reportService.queryDelaiMoyImmobilisation().subscribe(
      (res: ResponseWrapper) => {

        this.delaiMoyImmobilisation = res.json;
        this.rerender();

      });



    this.contratAssuranceService.findAllListCharge().subscribe(
      (res: ResponseWrapper) => {
        this.listCharge = res.json;
      }
    );
    this.governorateService.query().subscribe((res: ResponseWrapper) => {
      this.governorate = res.json;
    }, (res: ResponseWrapper) => this.onError(res.json));


    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      });


    this.partnerService.query().subscribe(
      (res: ResponseWrapper) => {
        this.partners = res.json;
      }
    );
    // this.loadAll()
  }

  changeMode(value1) {


    this.refModeGestionService.findModesGestionByClient(value1).subscribe(
      (res: ResponseWrapper) => {
        this.modeGestion = res.json;

      },
      (res: ResponseWrapper) => this.onError(res.json)
    );

 
  

}

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }


  filter() {
    this.loadAll();
  }

  loadAll() {

    this.reportService.findDelaiMoyImmobilisation(this.search)
      .subscribe(
        (res: DelaiMoyenImmobilisation[]) => {
          this.delaiMoyImmobilisation = res;
          this.delaiMoyImmobilisationOrg = res;
          this.delaiMoyImmobilisation = this.delaiMoyImmobilisationOrg.filter(this.filterArray.bind(this));

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
    const modeGestionIdFlt = this.search.modeGestionId != undefined ? this.search.modeGestionId == element.modeGestionId : true;

    const serviceFlt = this.search.serviceId != undefined ? this.search.serviceId == element.serviceId : true;
    const expertFlt = this.search.expertId != undefined ? this.search.expertId == element.expertId : true;
    const idZoneReparateurFlt = this.search.idZoneReparateur != undefined ? this.search.idZoneReparateur == element.villeId : true;
    const reparateurIdFlt = this.search.reparateurId != undefined ? this.search.reparateurId == element.reparateurId : true;
    const chargeIdFlt = this.search.chargeId != undefined ? this.search.chargeId == element.chargeId : true;
    const motifAnnulationIdFlt = this.search.motifAnnulationId != undefined ? this.search.motifAnnulationId == element.motifAnnulationId : true;
    const cngFlt = this.search.typeChoc != undefined ? this.search.typeChoc == element.typeChocBoolean : true;





    const idStepPecFlt = this.search.idStepPec != undefined ? this.search.idStepPec == element.etatDossierId : true;



    return cngFlt && idStepPecFlt && partnerFlt && zoneFlt && modeGestionIdFlt && serviceFlt && expertFlt && idZoneReparateurFlt && reparateurIdFlt && chargeIdFlt && motifAnnulationIdFlt;
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
  clear(search) {
    this.modeGestion = [];

    search.modeGestionId = undefined;
    search.compagnieId = undefined;
    search.typeChoc = undefined;
    search.chargeId = undefined;
    search.zoneId = undefined;
    search.startDate = undefined;
    search.endDate = undefined;

    this.reportService.queryDelaiMoyImmobilisation().subscribe(
      (res: ResponseWrapper) => {

        this.delaiMoyImmobilisation = res.json;
        this.rerender();

      });

    this.search = new Search();
  
    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      }
    )
  }

}


