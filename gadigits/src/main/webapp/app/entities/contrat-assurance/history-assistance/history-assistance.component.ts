import { Component, OnInit, Input, ɵConsole } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GaDatatable } from '../../../constants/app.constants';
import { Assitances, SinisterService } from '../../sinister';
import { Subject, Subscription } from 'rxjs';
import { HistoryService, History } from '../../history';
import { ResponseWrapper } from '../../../shared';
import { Attachment } from '../../attachments';
import { SinisterPecService } from '../../sinister-pec';
@Component({
  selector: 'jhi-history-assistance',
  templateUrl: './history-assistance.component.html',
  styles: []
})
export class HistoryAssistanceComponent implements OnInit {

  @Input() idSinisterPrestation: number;
  @Input() entityNamePrestation: string;

  histories: History[];
  sortedHistories: History[];
  dtOptions: any = {};
  dtTrigger: Subject<History> = new Subject();

  constructor(
    public activeModal: NgbActiveModal,
    private historyService: HistoryService,
  ) { }


  ngOnInit() {
    console.log(this.idSinisterPrestation);
    console.log(this.entityNamePrestation);

    this.dtOptions = GaDatatable.defaultDtOptions;
    this.historyService.findHistoriesByEntity(this.idSinisterPrestation, this.entityNamePrestation).subscribe((res: ResponseWrapper) => {
      this.histories = res.json;
      this.histories.forEach(element => {

        element.descriptiveOfChange = '';
        element.changeValues.forEach(hist => {
          if (hist.nameAttribute == "incidentGovernorateLabel") { hist.translateNameAttribute = "Gouvernorat de sinistre" }
          else if (hist.nameAttribute == "incidentLocationLabel") { hist.translateNameAttribute = "Ville de sinistre" }
          else if (hist.nameAttribute == "destinationGovernorateLabel") { hist.translateNameAttribute = "Gouvernorat de destination" }
          else if (hist.nameAttribute == "destinationLocationLabel") { hist.translateNameAttribute = "Ville de destination" }
          else if (hist.nameAttribute == "priceHt") { hist.translateNameAttribute = "Montant HT" }
          else if (hist.nameAttribute == "priceTtc") { hist.translateNameAttribute = "Montant TTC" }
          else if (hist.nameAttribute == "mileage") { hist.translateNameAttribute = "Kilométrage" }
          else if (hist.nameAttribute == "statusLabel") { hist.translateNameAttribute = "Etat" }
          else if (hist.nameAttribute == "tugArrivalDate") { hist.translateNameAttribute = "Date arrivée remorqueur" }
          else if (hist.nameAttribute == "insuredArrivalDate") { hist.translateNameAttribute = "Date arrivée assuré" }
          else if (hist.nameAttribute == "affectedTugLabel") { hist.translateNameAttribute = "Remorqueur" }
          else if (hist.nameAttribute == "affectedTruckLabel") { hist.translateNameAttribute = "Immatriculation camion" }
          else if (hist.nameAttribute == "closureDate") { hist.translateNameAttribute = "Date de cloture" }
          else if (hist.nameAttribute == "heavyWeights") { hist.translateNameAttribute = "Poids Lourd" }
          else if (hist.nameAttribute == "holidays") { hist.translateNameAttribute = "Férié" }
          else if (hist.nameAttribute == "night") { hist.translateNameAttribute = "Nuit" }
          else if (hist.nameAttribute == "halfPremium") { hist.translateNameAttribute = "Demi majoré" }
          else if (hist.nameAttribute == "cancelGroundsDescription") { hist.translateNameAttribute = "Motif d'annullation" }
          else if (hist.nameAttribute == "notEligibleGroundsDescription") { hist.translateNameAttribute = "Motif de non éligibilité" }
          else if (hist.nameAttribute == "serviceTypeLabel") { hist.translateNameAttribute = "Type de service" }
          else if (hist.nameAttribute == "reopenGroundsDescription") { hist.translateNameAttribute = "Motif d'ouverture" }


          if (hist.lastValue == 'true') { hist.lastValue = "Oui"; }
          if (hist.lastValue == 'false') { hist.lastValue = "Non"; }
          if (hist.newValue == 'false') { hist.newValue = "Non"; }
          if (hist.newValue == 'true') { hist.newValue = "Oui"; }
          if (hist.lastValue == 'null') { hist.lastValue = "vide"; }
          if (hist.newValue == 'null') { hist.newValue = "vide"; }





        });





        element.changeValues.forEach(changeValues => {
          if (changeValues.nameAttribute !== 'incidentGovernorateId' && changeValues.nameAttribute !== 'incidentLocationId' && changeValues.nameAttribute !== 'destinationGovernorateId' && changeValues.nameAttribute !== 'destinationLocationId' && changeValues.nameAttribute !== 'statusId' && changeValues.nameAttribute !== 'affectedTugId' && changeValues.nameAttribute !== 'affectedTruckId' && changeValues.nameAttribute !== 'reopenGroundsId' && changeValues.nameAttribute !== 'notEligibleGroundsId' && changeValues.nameAttribute !== 'cancelGroundsId' && changeValues.nameAttribute !== 'serviceTypeId') {
            element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé(e) de " + changeValues.lastValue + " à " + changeValues.newValue + '\n';

          }
        });
      });
      this.sortedHistories = this.histories.sort((val2, val1) => { return new Date(val2.operationDate).getTime() - new Date(val1.operationDate).getTime() });
      //this.dtTrigger.next();
    });
  }
  /*get sortData() {
    return this.histories.sort((a, b) => {
      return <any>new Date(b.operationDate) - <any>new Date(a.operationDate);
    });
  }*/
  clear() {
    this.activeModal.dismiss('cancel');
  }
}
