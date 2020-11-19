import { Component, OnInit, ViewChild, OnDestroy, AfterViewInit } from '@angular/core';
import { RefModeGestion, RefModeGestionService } from '../../ref-mode-gestion';
import { Partner, PartnerService } from '../../partner';
import { ListCharge } from '../../contrat-assurance/souscription-ga/listCharge.model';
import { Governorate, GovernorateService } from '../../governorate';
import { Agency, AgencyService } from '../../agency';
import { ContratAssuranceService } from '../../contrat-assurance';
import { ResponseWrapper } from '../../../shared';
import { SuiviDossiers } from './suiviDossiers.model';
import { GaDatatable, EtatMotifs } from '../../../constants/app.constants';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { Search } from '../../sinister';
import { StatusPec, StatusPecService } from '../../status-pec';
import { JhiAlertService } from 'ng-jhipster';
import { RefCompagnieService } from '../../ref-compagnie';
import { SinisterPecService } from '../../sinister-pec';
import { RefStepPec } from '../../sinister-pec/refStepPec.model';

@Component({
  selector: 'jhi-suivi-nbre-dossiers',
  templateUrl: './suivi-nbre-dossiers.component.html',
  styles: []
})
export class SuiviNbreDossiersComponent implements OnInit, OnDestroy, AfterViewInit {



  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  etat: EtatMotifs[];
  modeGestion: RefModeGestion[];
  partners: Partner[];
  listCharge: ListCharge[];
  governorate: Governorate[];
  agent: Agency[];
  dossiers: SuiviDossiers[];
  dossiersOrg: SuiviDossiers[];

  search: Search = new Search();
  RefStep: RefStepPec[];

  dtOptions: any = {};
  dtTrigger: Subject<SuiviDossiers> = new Subject;


  constructor(private partnerService: RefCompagnieService,
    private goverService: GovernorateService,
    private agencyService: AgencyService,
    private contratAssuranceService: ContratAssuranceService,
    private refModeGestionService: RefModeGestionService,
    private statutsPecService: StatusPecService,
    private alertService: JhiAlertService,
    private sinisterPecService: SinisterPecService,
    private agenceAssuranceService: AgencyService







  ) { }

  ngOnInit() {

    this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
    this.dtOptions.scrollX = true;
    this.search = new Search();



    // this.contratAssuranceService.querySuiviDossiers().subscribe(
    //   (res: ResponseWrapper) => {
    //     this.dossiers = res.json;
    //     this.rerender();

    //   }
    // );


    this.sinisterPecService.findStepPecByNumber().subscribe(
      (res: ResponseWrapper) => {

        this.RefStep = res.json;
      });
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



    this.partnerService.getAgreementCompany().subscribe(
      (res: ResponseWrapper) => {
        this.partners = res.json;
      }
    );

    this.agencyService.query().subscribe(

      (res: ResponseWrapper) => {

        this.agent = res.json;

      }

    )


    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      }
    )

    this.loadAll();

  }

  changeMode(value1, value2) {

    if (value2 == null) {
      this.refModeGestionService.findModesGestionByClient(value1).subscribe(
        (res: ResponseWrapper) => {
          this.modeGestion = res.json;
        },
        (res: ResponseWrapper) => this.onError(res.json)
      );

      this.agenceAssuranceService.findAllByPartnerWithoutFiltrage(value1).subscribe(
        (res: ResponseWrapper) => {
          this.agent = res.json;

        });
    }


    if (value1 == null) {

      this.agenceAssuranceService.findAllByZone(value2).subscribe(
        (res: ResponseWrapper) => {
          this.agent = res.json;

        });
    }
    else {

      this.refModeGestionService.findModesGestionByClient(value1).subscribe(
        (res: ResponseWrapper) => {
          this.modeGestion = res.json;

        },
        (res: ResponseWrapper) => this.onError(res.json)
      );

      this.agenceAssuranceService.findAllByPartnerAndZone(value1, value2).subscribe(
        (res: ResponseWrapper) => {
          this.agent = res.json;

        });
    }

  }

  clear() {


    this.agent = [];

    this.modeGestion = [];
    this.search = new Search();
    this.loadAll();
    /*this.contratAssuranceService.querySuiviDossiers().subscribe(
      (res: ResponseWrapper) => {
        this.dossiers = res.json;
        this.rerender();

      }
    );*/
    this.search = new Search();
    this.agencyService.query().subscribe(

      (res: ResponseWrapper) => {

        this.agent = res.json;
      }

    )


    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      }
    )

  }


  filter() {

    this.loadAll();



  }





  loadAll() {
    this.contratAssuranceService.querySuiviDossiersSearch(this.search)
      .subscribe(

        (res: SuiviDossiers[]) => {

          this.dossiers = res;
          let nbreBonDeSortie = 0;
          for (var i = 0; i < res.length; i++) {
            if (res[i].dateBonSortie === null || res[i].dateBonSortie === undefined) {
            } else { nbreBonDeSortie += 1; }
          }


          this.dossiersOrg = res;
          this.dossiers = this.dossiersOrg.filter(this.filterArray.bind(this));
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
              messageBottom: function () {
                return 'Nombre dossiers sinistres ouverts : ' + (res ? res.length : 0) + ' prestation(s) \r\n' +
                  ' \n Nombre de dossiers avec Bon de sortie : ' + nbreBonDeSortie + ' prestation(s)'
              }, customize: function (xlsx) {
                var sheet = xlsx.xl.worksheets['sheet1.xml'];
                $('row:first c', sheet).attr('s', '49');
                // jQuery selector to add a border
                $('row c*', sheet).attr('s', '25');
              }

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
    const agentIdFlt = this.search.agentId != undefined ? this.search.agentId == element.agentId : true;
    const idEtatDossierFlt = this.search.idEtapePrestation != undefined ? this.search.idEtapePrestation == element.idEtatDossier : true;
    const chargeIdFlt = this.search.chargeId != undefined ? this.search.chargeId == element.chargeId : true;

    return partnerFlt && zoneFlt && modeGestionIdFlt && idEtatDossierFlt && agentIdFlt && chargeIdFlt;
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

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }

  ngOnDestroy() {
  }
}
