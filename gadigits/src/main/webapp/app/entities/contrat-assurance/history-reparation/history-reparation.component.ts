import { Component, OnInit, Input } from '@angular/core';
import { GaDatatable } from '../../../constants/app.constants';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { History, HistoryService } from '../../history';
import { Principal, ResponseWrapper } from '../../../shared';
import { Observable, Subject } from 'rxjs/Rx';
import { SinisterPecService, SinisterPec } from '../../sinister-pec';
import { saveAs } from 'file-saver/FileSaver';

@Component({
  selector: 'jhi-history-reparation',
  templateUrl: './history-reparation.component.html',
  styles: []
})
export class HistoryReparationComponent implements OnInit {

  histories: History[];
  sortedHistories: History[] = [];

  @Input() entityName: string;
  @Input() entityId: number;

  dtOptions: any = {};
  dtTrigger: Subject<History> = new Subject();

  constructor(
    public activeModal: NgbActiveModal,
    private historyService: HistoryService,
    private sinisterPecService: SinisterPecService
  ) { }
  ngOnInit() {
    console.log(this.entityName);
    console.log(this.entityId);

    this.dtOptions = GaDatatable.defaultDtOptions;
    this.historyService.findHistoriesPecByEntity(this.entityId, this.entityName).subscribe((res: ResponseWrapper) => {
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
          else if (hist.nameAttribute == "codeBareme") { hist.translateNameAttribute = "Le code Bareme" }
          else if (hist.nameAttribute == "companyName") { hist.translateNameAttribute = "Le nom de la compagnie" }
          else if (hist.nameAttribute == "agentName") { hist.translateNameAttribute = "Le nom de l'agent" }
          else if (hist.nameAttribute == "immatriculationVehicle") { hist.translateNameAttribute = "L'immatriculation'" }
          else if (hist.nameAttribute == "incidentDate") { hist.translateNameAttribute = "La date de création" }
          else if (hist.nameAttribute == "insuredSurName") { hist.translateNameAttribute = "Le prénom de l'asssuré" }
          else if (hist.nameAttribute == "insuredName") { hist.translateNameAttribute = "Le nom de l'assuré" }
          else if (hist.nameAttribute == "labelModeGestion") { hist.translateNameAttribute = "Le mode de gestion" }
          else if (hist.nameAttribute == "stepLabel") { hist.translateNameAttribute = "L'État" }

          else if (hist.nameAttribute == "expertName") { hist.translateNameAttribute = "Le nom de l'expert" }
          //else if (hist.nameAttribute == "reference") { hist.translateNameAttribute = "La référence" }
          else if (hist.nameAttribute == "agenceName") { hist.translateNameAttribute = "Le nom de l'agence" }
          else if (hist.nameAttribute == "reparateurName") { hist.translateNameAttribute = "Le nom du réparateur" }

       //   else if (hist.nameAttribute == "motifsReopenedId") { hist.translateNameAttribute = "L'Id de motif ré-ouvert'" }
          else if (hist.nameAttribute == "vehicleReceiptDate") { hist.translateNameAttribute = "La date de la réception du véhicule" }
          else if (hist.nameAttribute == "labelPosGa") { hist.translateNameAttribute = "La position GA" }
          else if (hist.nameAttribute == "receptionVehicule") { hist.translateNameAttribute = "Réception du véhicule" }
          else if (hist.nameAttribute == "ttcAmount") { hist.translateNameAttribute = "Le montant de la réparation" }
          else if (hist.nameAttribute == "dateRDVReparation") { hist.translateNameAttribute = "La date du rendez-vous pour la réparation" }
          else if (hist.nameAttribute == "expertDecision") { hist.translateNameAttribute = "La décision de l'expert" }


          //  else if (hist.nameAttribute == "primaryQuotationId") { hist.translateNameAttribute = "L'Id de la première cotation" }
          else if (hist.nameAttribute == "conditionVehicle") { hist.translateNameAttribute = "L'État de véhicule" }
          else if (hist.nameAttribute == "repairableVehicle") { hist.translateNameAttribute = "Le véhicule réparable" }
          else if (hist.nameAttribute == "preliminaryReport") { hist.translateNameAttribute = "Le constat préliminaire" }
          else if (hist.nameAttribute == "immobilizedVehicle") { hist.translateNameAttribute = "Le véhicule immobilisé" }



          //     else if (hist.nameAttribute == "id") { hist.translateNameAttribute = "L'Id " }
          else if (hist.nameAttribute == "companyReference") { hist.translateNameAttribute = "La référence de la compagnie" }
          else if (hist.nameAttribute == "declarationDate") { hist.translateNameAttribute = "La date de déclaration" }



          else if (hist.nameAttribute == "agentLastName") { hist.translateNameAttribute = "Le prénom de l'agent" }

          else if (hist.nameAttribute == "decision") { hist.translateNameAttribute = "La décision" }
          else if (hist.nameAttribute == "dateCreation") { hist.translateNameAttribute = "La date de création" }
          else if (hist.nameAttribute == "approvPec") { hist.translateNameAttribute = "La prestation" }
          else if (hist.nameAttribute == "sinisterPecId") { hist.translateNameAttribute = "L'Id de sinister PEC" }

          else if (hist.nameAttribute == "dateModification") { hist.translateNameAttribute = "La date de modification" }
          else if (hist.nameAttribute == "generation_bon_sortie_date") { hist.translateNameAttribute = "La date de génération de bon de sortie" }

          else if (hist.nameAttribute == "contractNumber") { hist.translateNameAttribute = "Le numpéro de contrat" }
          else if (hist.nameAttribute == "referenceSinister") { hist.translateNameAttribute = "La référence de sinistre" }
          else if (hist.nameAttribute == "assignedTofirstName") { hist.translateNameAttribute = "Le nom de chargé " }
          else if (hist.nameAttribute == "assignedTolastName") { hist.translateNameAttribute = "Le prénom de chargé" }
          else if (hist.nameAttribute == "governorateLabel") { hist.translateNameAttribute = "Le lieu de sinistre" }
          else if (hist.nameAttribute == "governorateRepLabel") { hist.translateNameAttribute = "Le lieu de réparation" }
          else if (hist.nameAttribute == "assujettieTVA") { hist.translateNameAttribute = "Assujettie à la TVA" }
         else if (hist.nameAttribute == "motifsReopenedLabel") { hist.translateNameAttribute = "Le motif de ré-ouverture" }
         else if (hist.nameAttribute == "motifsDecisionLabel") { hist.translateNameAttribute = "Le motif" }
         else if (hist.nameAttribute == "reasonCanceledLabel") { hist.translateNameAttribute = "Le motif d'annulation" }
         else if (hist.nameAttribute == "reasonRefusedLabel") { hist.translateNameAttribute = "Le motif de refus" }
         else if (hist.nameAttribute == "lightShock") { hist.translateNameAttribute = "Le choc léger : " }
         else if (hist.nameAttribute == "conditionVehicle") { hist.translateNameAttribute = "Etat véhicule" }
         else if (hist.nameAttribute == "repairableVehicle") { hist.translateNameAttribute = "Voiture réparable" }
         else if (hist.nameAttribute == "concordanceReport") { hist.translateNameAttribute = "La concordance des circonstances de l'accidents avec les dégâts déclarés" }
         else if (hist.nameAttribute == "immobilizedVehicle") { hist.translateNameAttribute = "Véhicule immobilisé" }

         else { hist.translateNameAttribute = "undefined" }
          
          

          if (hist.lastValue == 'true') { hist.lastValue = "Oui"; }
          if (hist.lastValue == 'false') { hist.lastValue = "Non"; }
          if (hist.newValue == 'false') { hist.newValue = "Non"; }
          if (hist.newValue == 'true') { hist.newValue = "Oui"; }
          if (hist.lastValue == 'null') { hist.lastValue = "vide"; }
          if (hist.newValue == 'null') { hist.newValue = "vide"; }

          if (hist.newValue == 'ACCEPTED') { hist.newValue = "Acceptée"; }
          if (hist.newValue == 'ACC_WITH_RESRV') { hist.newValue = "Acceptée avec réserves"; }
          if (hist.newValue == 'ACC_WITH_CHANGE_STATUS') { hist.newValue = "Acceptée avec changement de statut"; }
          if (hist.newValue == 'CANCELED') { hist.newValue = "annulée"; }
          if (hist.newValue == 'REFUSED') { hist.newValue = "refusée"; }
          if (hist.newValue == 'REOPENED') { hist.newValue = "ré-ouverte"; }

          if (hist.lastValue == 'ACCEPTED') { hist.lastValue = "Acceptée"; }
          if (hist.lastValue == 'ACC_WITH_RESRV') { hist.lastValue = "vide"; }
          if (hist.lastValue == 'ACC_WITH_CHANGE_STATUS') { hist.lastValue = "Acceptée avec changement de statut"; }
          if (hist.lastValue == 'CANCELED') { hist.lastValue = "annulée"; }
          if (hist.lastValue == 'REFUSED') { hist.lastValue = "refusée"; }
          if (hist.lastValue == 'REOPENED') { hist.lastValue = "ré-ouverte"; }

          if (hist.newValue == 'APPROVE') { hist.newValue = "approuvée"; }

          if (hist.lastValue == 'APPROVE') { hist.lastValue = "approuvée"; }
          if (hist.newValue == 'APPROVE_WITH_MODIFICATION') { hist.newValue = "approuvée avec modification"; }

          if (hist.lastValue == 'APPROVE_WITH_MODIFICATION') { hist.lastValue = "approuvée avec modification"; }
          if (hist.lastValue == '') { hist.lastValue = "vide"; }
          if (hist.newValue == '') { hist.newValue = "vide"; }



        });




        element.changeValues.forEach(changeValues => {



          if (changeValues.nameAttribute !== 'id' && changeValues.nameAttribute !== 'conditionVehicle' 
          && changeValues.nameAttribute !== 'observationExpert' &&
            changeValues.nameAttribute !== 'repairableVehicle' &&
            changeValues.nameAttribute !== 'concordanceReport' &&
            changeValues.nameAttribute !== 'preliminaryReport' &&
            changeValues.translateNameAttribute !== 'undefined' &&
            changeValues.nameAttribute !== 'primaryQuotationId' 
          && changeValues.newValue !== 'vide' &&
           changeValues.nameAttribute !== 'receptionVehicule' 
         && changeValues.lastValue !== 'vide' &&

           changeValues.nameAttribute !== 'vehicleId' && changeValues.nameAttribute !== 'creationUserId' 
            && changeValues.nameAttribute !== 'locationId' && changeValues.nameAttribute !== 'expertId' 
            && changeValues.nameAttribute !== 'reasonRefusedId' && changeValues.nameAttribute !== 'reasonCanceledId' 
            && changeValues.nameAttribute !== 'quotationMPId' && changeValues.nameAttribute !== 'reasonCancelAffectedRepId' 
            && changeValues.nameAttribute !== 'modeModifId' && changeValues.nameAttribute !== 'reasonCancelExpertId' 
           && changeValues.nameAttribute !== 'motifsDecisionId' 
            && changeValues.nameAttribute !== 'userId' 
            && changeValues.nameAttribute !== 'valeurNeuf' && changeValues.nameAttribute !== 'insuredCapital' 
            && changeValues.nameAttribute !== 'phone' && changeValues.nameAttribute !== 'isAssujettieTva' 
            && changeValues.nameAttribute !== 'marketValue' && changeValues.nameAttribute !== 'partnerId' 
            && changeValues.nameAttribute !== 'sinisterId' && changeValues.nameAttribute !== 'baremeId' 
            && changeValues.nameAttribute !== 'assigned_date' && changeValues.nameAttribute !== 'vehicle_receipt_date' 
            && changeValues.nameAttribute !== 'vehicle_receipt_date' && changeValues.nameAttribute !== 'driver_license_number' 
            && changeValues.nameAttribute !== 'driver_name' && changeValues.nameAttribute !== 'driver_or_insured' 
            && changeValues.nameAttribute !== 'type_franchise' && changeValues.nameAttribute !== 'franchise' 
            && changeValues.nameAttribute !== 'remaining_capital' && changeValues.nameAttribute !== 'insured_capital' 
            && changeValues.nameAttribute !== 'responsability_rate' && changeValues.nameAttribute !== 'vehicle_number' 
            && changeValues.nameAttribute !== 'responsabiliteX' && changeValues.nameAttribute !== 'disassemblyRequest' 
             && changeValues.nameAttribute !== 'changeModificationPrix' 
            && changeValues.nameAttribute !== 'activeModificationPrix' && changeValues.nameAttribute !== 'modificationPrix' 
            && changeValues.nameAttribute !== 'assignedToId' && changeValues.nameAttribute !== 'gouvernoratRepId' 
            && changeValues.nameAttribute !== 'villeRepId' && changeValues.nameAttribute !== 'delegationId' 
            && changeValues.nameAttribute !== 'posGaId' && changeValues.nameAttribute !== 'modeId' 
            && changeValues.nameAttribute !== 'driverOrInsured' && changeValues.nameAttribute !== 'oldStepModifSinPec' 
            && changeValues.nameAttribute !== 'incidentGovernorateId' && changeValues.nameAttribute !== 'incidentLocationId' 
            && changeValues.nameAttribute !== 'destinationGovernorateId' && changeValues.nameAttribute !== 'destinationLocationId' 
            && changeValues.nameAttribute !== 'statusId' && changeValues.nameAttribute !== 'affectedTugId' 
            && changeValues.nameAttribute !== 'affectedTruckId' && changeValues.nameAttribute !== 'reopenGroundsId' 
            && changeValues.nameAttribute !== 'notEligibleGroundsId' && changeValues.nameAttribute !== 'cancelGroundsId' 
            && changeValues.nameAttribute !== 'serviceTypeId' && changeValues.nameAttribute !== 'stepId') {
            element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé(e) de " + changeValues.lastValue + " à " + changeValues.newValue + '\n';

          } else if (changeValues.lastValue == 'vide' && changeValues.translateNameAttribute !== "undefined") {
            element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est " + changeValues.newValue + '\n';

          }
        });
      });

      this.histories.forEach(element => {
        if (element.actionLabel !== null && element.actionLabel !== undefined) {
            this.sortedHistories.push(element);
        }

    });
      this.sortedHistories = this.sortedHistories.sort((val2, val1) => { return new Date(val2.operationDate).getTime() - new Date(val1.operationDate).getTime() });
      /*this.histories.sort(function (a, b) {
        return b.operationDate - a.operationDate;
      });*/
      //this.dtTrigger.next();
    });

  }





  dataURItoBlobAtt(dataURI, extention, type) {
    const byteString = window.atob(dataURI);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], { type: type + '/' + extention });
    return blob;
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }







}
