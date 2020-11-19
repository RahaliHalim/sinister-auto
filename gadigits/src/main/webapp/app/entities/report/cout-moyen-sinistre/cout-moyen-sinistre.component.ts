import { Component, OnInit, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
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
import { PaiementReparation } from '../paiement-reparation/paiement-reparation.model';
import { Subject } from 'rxjs';
import { VehicleBrand, VehicleBrandService } from '../../vehicle-brand';
import { GaDatatable } from '../../../constants/app.constants';
import { VehicleUsage, VehicleUsageService } from '../../vehicle-usage';

@Component({
  selector: 'jhi-cout-moyen-sinistre',
  templateUrl: './cout-moyen-sinistre.component.html',
  styles: []
})
export class CoutMoyenSinistreComponent implements OnInit, OnDestroy, AfterViewInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;


  dtOptions: any = {};
  dtTrigger: Subject<PaiementReparation> = new Subject;


  listCharge: ListCharge[];
  paiementReparation: PaiementReparation[];
  paiementReparationOrg: PaiementReparation[];
  usages: VehicleUsage[];


  listExperts: Expert[];
  reparators: Reparateur[] = [];
  modeGestion: RefModeGestion[];
  partners: Partner[];
  vehicleBrands: VehicleBrand[];

  search: Search = new Search();
  governorate: Governorate[];

  constructor(private reportService: ReportService, private governorateService: GovernorateService,
    private contratAssuranceService: ContratAssuranceService,
    private vehicleBrandService: VehicleBrandService,
    private vehicleUsageService: VehicleUsageService,


    private expertService: ExpertService,
    private reparateurService: ReparateurService,

    private refModeGestionService: RefModeGestionService,
    private partnerService: PartnerService,

    private alertService: JhiAlertService
  ) { }


  ngOnInit() {
    this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
    this.dtOptions.scrollX = true;
    /*   this.reportService.queryPaiementReparation().subscribe(
         (res : ResponseWrapper) =>{
 
          this.paiementReparation = res.json;
        
          });
           */


    this.vehicleBrandService.query().subscribe(
      (res: ResponseWrapper) => {

        this.vehicleBrands = res.json;

      });

    this.vehicleUsageService.query().subscribe(
      (res: ResponseWrapper) => {
        this.usages = res.json;
      },
      (res: ResponseWrapper) => this.onError(res.json)
    );

    this.expertService.query().subscribe(
      (res: ResponseWrapper) => {

        this.listExperts = res.json;
      });
    this.contratAssuranceService.findAllListCharge().subscribe(
      (res: ResponseWrapper) => {
        this.listCharge = res.json;
      }
    );
    this.governorateService.query().subscribe((res: ResponseWrapper) => {
      this.governorate = res.json;
    }, (res: ResponseWrapper) => this.onError(res.json));

    this.reparateurService.query().subscribe((res: ResponseWrapper) => {
      this.reparators = res.json;
    });
    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      });


    this.partnerService.query().subscribe(
      (res: ResponseWrapper) => {
        this.partners = res.json;
      }
    );
    this.loadAll();

  }

  ngAfterViewInit(): void {
    this.dtTrigger.next();
  }


  changeMode(value1) {


    this.refModeGestionService.findModesGestionByClient(value1).subscribe(
      (res: ResponseWrapper) => {
        this.modeGestion = res.json;

      },
      (res: ResponseWrapper) => this.onError(res.json)
    );




  }

  filter() {
    this.loadAll();
  }

  loadAll() {

    this.reportService.findPaiementReparation(this.search)
      .subscribe(
        (res: PaiementReparation[]) => {
          this.paiementReparation = res;

          var cache = {};
          this.paiementReparation = this.paiementReparation.filter(function (elem) {
            return cache[elem.referenceGa] ? 0 : cache[elem.referenceGa] = 1;
          });

          let totalengagementGA = 0;
          for (var i = 0; i < res.length; i++) {
            if (res[i].engagementGA === null || res[i].engagementGA === undefined) {
            } else { totalengagementGA = totalengagementGA + res[i].engagementGA; }
          }
          let totalreparation = 0;
          for (var i = 0; i < res.length; i++) {
            if (res[i].total === null || res[i].total === undefined) {
            } else { totalreparation = totalreparation + res[i].total; }
          }
          let totalHeureRemplacement = 0;
          for (var i = 0; i < res.length; i++) {
            if (res[i].nbreHeuresRemplacement === null || res[i].nbreHeuresRemplacement === undefined) {
            } else { totalHeureRemplacement = totalHeureRemplacement + res[i].nbreHeuresRemplacement; }
          }
          let totalHeureReparation = 0;
          for (var i = 0; i < res.length; i++) {
            if (res[i].nbreHeuresReparation === null || res[i].nbreHeuresReparation === undefined) {
            } else { totalHeureReparation = totalHeureReparation + res[i].nbreHeuresReparation; }
          }
          let totalHeurePeinture = 0;
          for (var i = 0; i < res.length; i++) {
            if (res[i].nbreHeuresPeinture === null || res[i].nbreHeuresPeinture === undefined) {
            } else { totalHeurePeinture = totalHeurePeinture + res[i].nbreHeuresPeinture; }
          }




          this.paiementReparation.forEach(element => {
            element.ageVehicule = this.getAge(new Date(element.ageVehicule), new Date());
            element.cngFr = "";
            if (element.cng == true) { element.cngFr = "Oui "; }
            else if (element.cng == false) { element.cngFr = "Non "; }

          });
          this.paiementReparationOrg = res;
          this.paiementReparation = this.paiementReparationOrg.filter(this.filterArray.bind(this));

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
                return ' Cout moyen sinistre : ' + (totalengagementGA / res.length).toFixed(3) + '   ' +
                  '  Cout moyen sinistre réparation : ' + (totalreparation / res.length).toFixed(3) + '   ' +
                  '  Cout moyen Heure remplacement : ' + (totalHeureRemplacement / res.length).toFixed(3) + '   ' +
                  '  Cout moyen Heure réparation  : ' + (totalHeureReparation / res.length).toFixed(3) + '   ' +
                  '  Cout moyen Heure Peinture : ' + (totalHeurePeinture / res.length).toFixed(3);


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

  getAge(date_1, date_2) {

    //convert to UTC
    let date2_UTC = new Date(Date.UTC(date_2.getUTCFullYear(), date_2.getUTCMonth(), date_2.getUTCDate()));
    let date1_UTC = new Date(Date.UTC(date_1.getUTCFullYear(), date_1.getUTCMonth(), date_1.getUTCDate()));


    let yAppendix, mAppendix, dAppendix;


    //--------------------------------------------------------------
    let days = date2_UTC.getDate() - date1_UTC.getDate();
    if (days < 0) {

      date2_UTC.setMonth(date2_UTC.getMonth() - 1);
      days += this.DaysInMonth(date2_UTC);
    }
    //--------------------------------------------------------------
    let months = date2_UTC.getMonth() - date1_UTC.getMonth();
    if (months < 0) {
      date2_UTC.setFullYear(date2_UTC.getFullYear() - 1);
      months += 12;
    }
    //--------------------------------------------------------------
    let years = date2_UTC.getFullYear() - date1_UTC.getFullYear();




    if (years > 1) yAppendix = " ans";
    else yAppendix = " ans";
    if (months > 1) mAppendix = " mois";
    else mAppendix = " mois";
    if (days > 1) dAppendix = " jours";
    else dAppendix = " jours";


    return years + yAppendix + ", " + months + mAppendix + ", et " + days + dAppendix;
  }

  DaysInMonth(date2_UTC) {
    let monthStart: any = new Date(date2_UTC.getFullYear(), date2_UTC.getMonth(), 1);
    let monthEnd: any = new Date(date2_UTC.getFullYear(), date2_UTC.getMonth() + 1, 1);
    let monthLength = (monthEnd - monthStart) / (1000 * 60 * 60 * 24);
    return monthLength;
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
    const cngFlt = this.search.cng != undefined ? this.search.cng == element.cng : true;
    const marqueFlt = this.search.marqueId != undefined ? this.search.marqueId == element.marqueId : true;
    let engagementFlt = true;

    if (this.search.minInt != undefined && this.search.maxInt != undefined) {
      engagementFlt = element.engagementGA != undefined ? this.search.minInt <= element.engagementGA && element.engagementGA <= this.search.maxInt : false;

    }
    const idStepPecFlt = this.search.idStepPec != undefined ? this.search.idStepPec == element.etatDossierId : true;



    return engagementFlt && marqueFlt && cngFlt && idStepPecFlt && partnerFlt && zoneFlt && modeGestionIdFlt && serviceFlt && expertFlt && idZoneReparateurFlt && reparateurIdFlt && chargeIdFlt && motifAnnulationIdFlt;
  }


  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.destroy();
      this.dtTrigger.next();
    });
  }


  clear() {
    this.modeGestion = [];

    this.search = new Search();
    this.loadAll();

    this.refModeGestionService.query().subscribe(

      (res: ResponseWrapper) => {

        this.modeGestion = res.json;
      }
    )


  }


  private onError(error) {
    this.alertService.error(error.message, null, null);
  }
  ngOnDestroy() {
  }

}
