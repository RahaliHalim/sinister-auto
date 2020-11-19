import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Search } from '../../sinister';
import { SuiviAnnulation } from './suiviAnnulation.model';
import { ReportService } from '../report.service';
import { GaDatatable } from '../../../constants/app.constants';
import { ResponseWrapper } from '../../../shared';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { ContratAssuranceService } from '../../contrat-assurance';
import { GovernorateService, Governorate } from '../../governorate';
import { PartnerService, Partner } from '../../partner';
import { ListCharge } from '../../contrat-assurance/souscription-ga/listCharge.model';
import { ReparateurService, Reparateur } from '../../reparateur';
import { ExpertService, Expert } from '../../expert';
import { JhiAlertService } from 'ng-jhipster';
import { RefModeGestionService, RefModeGestion } from '../../ref-mode-gestion';
import { SinisterPecService } from '../../sinister-pec';
import { RefStepPec } from '../../sinister-pec/refStepPec.model';
import { RaisonPec } from '../../raison-pec/raison-pec.model';
import { RaisonPecService } from '../../raison-pec/raison-pec.service';
@Component({
  selector: 'jhi-suivi-annulation',
  templateUrl: './suivi-annulation.component.html',
  styles: []
})
export class SuiviAnnulationComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  dtOptions: any = {};
  dtTrigger: Subject<SuiviAnnulation> = new Subject;

  search: Search = new Search();
  suiviAnnulation: SuiviAnnulation[]
  listCharge: ListCharge[];
  governorate: Governorate[];
  partners: Partner[];
  reparators: Reparateur[] = [];
  listExperts: Expert[];
  suiviAnnulationOrig: SuiviAnnulation[];
  modeGestion: RefModeGestion[];
  stepPec: RefStepPec[];
  motifsApec: RaisonPec[];

  constructor(private reportService: ReportService,
    private contratAssuranceService: ContratAssuranceService,
    private goverService: GovernorateService,
    private partnerService: PartnerService,
    private expertService: ExpertService,
    private raisonPecService: RaisonPecService,

    private reparateurService: ReparateurService,
    private alertService: JhiAlertService,
    private refModeGestionService: RefModeGestionService,
    private sinisterPecService: SinisterPecService


  ) { }

  ngOnInit() {
    this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
    this.dtOptions.scrollX = true;



    this.contratAssuranceService.findAllListCharge().subscribe(
      (res: ResponseWrapper) => {
        this.listCharge = res.json;
      }
    );
    this.raisonPecService.query().subscribe((res: ResponseWrapper) => {
      this.motifsApec = res.json;
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

    this.reparateurService.query().subscribe((res: ResponseWrapper) => {
      this.reparators = res.json;
    });

    this.expertService.query().subscribe(
      (res: ResponseWrapper) => {

        this.listExperts = res.json;
      });

    this.sinisterPecService.findStepPecByNumber().subscribe(
      (res: ResponseWrapper) => {

        this.stepPec = res.json;
      });



    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      }
    )
    this.reportService.querySuiviAnnulation().subscribe(
      (res: ResponseWrapper) => {

        this.suiviAnnulation = res.json;
        this.rerender();
      });



    this.search = new Search();


  }


  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }


  filter() {
    this.loadAll();
  }

  changeMode(value1) {


    this.refModeGestionService.findModesGestionByClient(value1).subscribe(
      (res: ResponseWrapper) => {
        this.modeGestion = res.json;

      },
      (res: ResponseWrapper) => this.onError(res.json)
    );

 
  

}


  loadAll() {

    this.reportService.findSuiviAnnulation(this.search)
      .subscribe(
        (res: SuiviAnnulation[]) => {
          this.suiviAnnulation = res;
          this.suiviAnnulationOrig = res;
          this.suiviAnnulation = this.suiviAnnulationOrig.filter(this.filterArray.bind(this));

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





    const idStepPecFlt = this.search.idStepPec != undefined ? this.search.idStepPec == element.etatDossierId : true;



    return idStepPecFlt && partnerFlt && zoneFlt && modeGestionIdFlt && serviceFlt && expertFlt && idZoneReparateurFlt && reparateurIdFlt && chargeIdFlt && motifAnnulationIdFlt;
  }


  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy();
      this.dtTrigger.next();
    });
  }
  private onError(error) {
    this.alertService.error(error.message, null, null);
  }

  clear() {
    this.modeGestion = [];

    this.reportService.querySuiviAnnulation().subscribe(
      (res: ResponseWrapper) => {

        this.suiviAnnulation = res.json;
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
