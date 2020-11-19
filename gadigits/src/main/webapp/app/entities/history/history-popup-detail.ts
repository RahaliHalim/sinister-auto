import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { Response } from '@angular/http';
import { GaDatatable } from '../../constants/app.constants';
import { Observable, Subject } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiParseLinks, JhiDateUtils } from 'ng-jhipster';
import { Principal, ResponseWrapper } from '../../shared';
import { DomSanitizer } from '@angular/platform-browser';
import { HistoryPopupService } from './history-popup.service';
import { Input } from '@angular/core';
import { saveAs } from 'file-saver/FileSaver';
import { DatePipe } from '@angular/common'
import { History, HistoryService } from '.';
import { Subscription } from 'rxjs/Rx';
import { Agency } from '../agency';
@Component({
    selector: 'History-popup',
    templateUrl: './history-popup-detail.html'
})
export class HistoryPopupDetail implements OnInit {
    @Input() idEntity: number;
    @Input() nameEntity: string;

    dtOptions: any = {};
    histories: History[];
    historiesSorted: History[];
    motComplet: string[] = [];
    pieceJointeHistory = false;
    dtTrigger: Subject<Agency> = new Subject();
    m = 0;
    n = 0;


    constructor(
        public activeModal: NgbActiveModal,
        private historyService: HistoryService,
        private dateUtils: JhiDateUtils

    ) {
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.load(this.idEntity, this.nameEntity);
    }

    load(id, nameEntity) {
        this.historyService.findHistoriesByEntity(id, nameEntity).subscribe((res: ResponseWrapper) => {
            this.histories = res.json;
            this.histories.forEach(element => {
                const opdate = new Date(element.operationDate);
                element.descriptiveOfChange = '';
                element.operationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' + opdate.getHours() + ':' + opdate.getMinutes() + ':' + opdate.getSeconds());
                element.changeValues.forEach(hist => {
                    if (hist.nameAttribute == "label") { hist.translateNameAttribute = "Libellé" }
                    else if (hist.nameAttribute == "brandLabel") { hist.translateNameAttribute = "Marque" }
                    else if (hist.nameAttribute == "amount") { hist.translateNameAttribute = "Droit timbre" }
                    else if (hist.nameAttribute == "startDate") { hist.translateNameAttribute = "Date début" }
                    else if (hist.nameAttribute == "statusLabel") { hist.translateNameAttribute = "Etat prestation" }
                    else if (hist.nameAttribute == "operationLabel") { hist.translateNameAttribute = "Opération" }
                    else if (hist.nameAttribute == "pecStatusChangeMatrixLabel") { hist.translateNameAttribute = "Matrice de modification de statuts" }
                    else if (hist.nameAttribute == "statusPecLabel") { hist.translateNameAttribute = "Etat prestation" }
                    else if (hist.nameAttribute == "responsible") { hist.translateNameAttribute = "à la charge de " }
                    else if (hist.nameAttribute == "vatRate") { hist.translateNameAttribute = "Taux TVA" }
                    else if (hist.nameAttribute == "effectiveDate") { hist.translateNameAttribute = "Date effet" }

                    // history reparateur
                    else if (hist.nameAttribute == "raisonSociale") { hist.translateNameAttribute = "Raison Sociale" }
                    else if (hist.nameAttribute == "registreCommerce") { hist.translateNameAttribute = "Registre de Commerce" }
                    else if (hist.nameAttribute == "matriculeFiscale") { hist.translateNameAttribute = "Matricule Fiscale" }
                    else if (hist.nameAttribute == "nomPerVisVis") { hist.translateNameAttribute = "Nom de peremier vis à vis" }
                    else if (hist.nameAttribute == "prenomPerVisVis") { hist.translateNameAttribute = "Prénom de peremier vis à vis" }
                    else if (hist.nameAttribute == "telPerVisVis") { hist.translateNameAttribute = "telephone  de premier visà is" }
                    else if (hist.nameAttribute == "faxPerVisVis") { hist.translateNameAttribute = "fax de Permier Vis à Vis" }
                    else if (hist.nameAttribute == "emailPerVisVis") { hist.translateNameAttribute = "email de premier Vis à Vis  " }
                    else if (hist.nameAttribute == "isConventionne") { hist.translateNameAttribute = "Conventionné" }
                    else if (hist.nameAttribute == "isCng") { hist.translateNameAttribute = "CNG" }
                    else if (hist.nameAttribute == "isGaEstimate") { hist.translateNameAttribute = "Ga Estimate" }
                    else if (hist.nameAttribute == "dateEffetConvention") { hist.translateNameAttribute = "date Effet Convention" }
                    else if (hist.nameAttribute == "dateFinConvention") { hist.translateNameAttribute = "date Fin Convention" }
                    else if (hist.nameAttribute == "isFour") { hist.translateNameAttribute = "Four" }
                    else if (hist.nameAttribute == "isMarbre") { hist.translateNameAttribute = "Marbre" }
                    else if (hist.nameAttribute == "isBloque") { hist.translateNameAttribute = "Bloqué " }
                    else if (hist.nameAttribute == "dateDebutBlocage") { hist.translateNameAttribute = "date Debut Blocage" }
                    else if (hist.nameAttribute == "dateFinBlocage") { hist.translateNameAttribute = "date Fin Blocage" }
                    else if (hist.nameAttribute == "montantIP") { hist.translateNameAttribute = "montant ingrédiant de pienture" }
                    else if (hist.nameAttribute == "hydro") { hist.translateNameAttribute = "hydro" }
                    else if (hist.nameAttribute == "solvant") { hist.translateNameAttribute = "solvant" }
                    else if (hist.nameAttribute == "libelleGouvernorat") { hist.translateNameAttribute = "Gouvernorat" }
                    else if (hist.nameAttribute == "villeLibelle") { hist.translateNameAttribute = "ville" }
                    else if (hist.nameAttribute == "rib") { hist.translateNameAttribute = "RIB " }
                    else if (hist.nameAttribute == "notation") { hist.translateNameAttribute = "notation" }
                    else if (hist.nameAttribute == "observation") { hist.translateNameAttribute = "observation" }
                    else if (hist.nameAttribute == "isagentOfficiel") { hist.translateNameAttribute = "Agent Officiel" }
                    else if (hist.nameAttribute == "capacite") { hist.translateNameAttribute = "Capacite" }
                    else if (hist.nameAttribute == "adresse") { hist.translateNameAttribute = "adresse" }
                    else if (hist.nameAttribute == "garantieImpliques") { hist.translateNameAttribute = "Garantie Impliques" }
                    else if (hist.nameAttribute == "orientations") { hist.translateNameAttribute = "orientations" }
                    else if (hist.nameAttribute == "specialitePrincipales") { hist.translateNameAttribute = "Specialite Principales" }
                    else if (hist.nameAttribute == "specialiteSecondaires") { hist.translateNameAttribute = "specialite Secondaires" }
                    else if (hist.nameAttribute == "visAViss") { hist.translateNameAttribute = "vis à Vis" }
                    else if (hist.nameAttribute == "partnerName") { hist.translateNameAttribute = "compagnie" }
                    else if (hist.nameAttribute == "compagnie") { hist.translateNameAttribute = "compagnie" }

                    else if (hist.nameAttribute == "remiseMarque") { hist.translateNameAttribute = "remise sur la marque" }
                    else if (hist.nameAttribute == "refAgeVehiculeValeur") { hist.translateNameAttribute = "Age de Vehicule" }
                    else if (hist.nameAttribute == "marqueType") { hist.translateNameAttribute = "marque principale" }
                    else if (hist.nameAttribute == "valeur") { hist.translateNameAttribute = "age Véhicule" }
                    else if (hist.nameAttribute == "label") { hist.translateNameAttribute = "libelle " }
                    else if (hist.nameAttribute == "libelle") { hist.translateNameAttribute = "Mode de Gestion" }
                    else if (hist.nameAttribute == "tauxHoraireMOPeintur") { hist.translateNameAttribute = "taux Horaire MO Peinture" }
                    else if (hist.nameAttribute == "tauxHoraireMORemplacement") { hist.translateNameAttribute = "taux Horaire MO Remplacement" }
                    else if (hist.nameAttribute == "tauxHoraireMOReparation") { hist.translateNameAttribute = "Taux Horaire MO Reparation" }
                    else if (hist.nameAttribute == "tauxHorairesReparationHauteTechnicite") { hist.translateNameAttribute = "taux Horaires Reparation Haute Technicite" }
                    else if (hist.nameAttribute == "montagePareBriseAvecColle") { hist.translateNameAttribute = "montage Pare Brise" }
                    else if (hist.nameAttribute == "montagePareBriseAvecJoint") { hist.translateNameAttribute = "montage Pare Brise Avec Joint" }
                    else if (hist.nameAttribute == "montageVitreDePorte") { hist.translateNameAttribute = "montage Vitre De Porte " }
                    else if (hist.nameAttribute == "montageVoletDairAvecColleOuJoint") { hist.translateNameAttribute = "montage Volet Dair Avec Colle Ou Joint" }
                    else if (hist.nameAttribute == "tauxHoraireMOPeinturRestourne") { hist.translateNameAttribute = "taux Horaire MO Peinture Restourne" }
                    else if (hist.nameAttribute == "tauxHoraireMORemplacementRestourne") { hist.translateNameAttribute = "taux Horaire MO Remplacement Restourne" }
                    else if (hist.nameAttribute == "tauxHorairesReparationHauteTechniciteRestourne") { hist.translateNameAttribute = "taux Horaires Reparation Haute Technicite Restourne " }
                    else if (hist.nameAttribute == "montagePareBriseAvecColleRestourne") { hist.translateNameAttribute = "montage Pare Brise Restourne" }
                    else if (hist.nameAttribute == "montagePareBriseAvecJointRestourne") { hist.translateNameAttribute = "montage Pare Brise Avec Joint Restourne" }
                    else if (hist.nameAttribute == "montageVitreDePorteRestourne") { hist.translateNameAttribute = "montage Vitre De Porte Restourne" }
                    else if (hist.nameAttribute == "montageVoletDairAvecColleOuJointRestourne") { hist.translateNameAttribute = "montage Volet D'air Avec Colle Ou Joint Restourne" }
                    else if (hist.nameAttribute == "tauxHoraireMOReparationRestourne") { hist.translateNameAttribute = "taux Horaire MO Reparation Restourne" }
                    else if (hist.nameAttribute == "restourneMontageLunetteAriereAvecColle") { hist.translateNameAttribute = "restourne Montage Lunette Ariere Avec Colle" }
                    else if (hist.nameAttribute == "montageLunetteAriereAvecColle") { hist.translateNameAttribute = "montage Lunette Ariere Avec Colle" }
                    else if (hist.nameAttribute == "restourneMontageLunetteAriereAvecjoint") { hist.translateNameAttribute = "restourne Montage Lunette Ariere Avec Joint" }
                    else if (hist.nameAttribute == "montageLunetteAriereAvecjoint") { hist.translateNameAttribute = "montage Lunette Ariere Avec Joint" }
                    else if (hist.nameAttribute == "petiteFourniture") { hist.translateNameAttribute = "petite Fourniture" }
                    // translation expert
                    else if (hist.nameAttribute == "nom") { hist.translateNameAttribute = "nom" }
                    else if (hist.nameAttribute == "prenom") { hist.translateNameAttribute = "prénom" }
                    else if (hist.nameAttribute == "email") { hist.translateNameAttribute = "libelle " }
                    else if (hist.nameAttribute == "telephone") { hist.translateNameAttribute = "télephone" }
                    else if (hist.nameAttribute == "fax") { hist.translateNameAttribute = "Fax" }
                    else if (hist.nameAttribute == "mobile") { hist.translateNameAttribute = "mobile" }
                    else if (hist.nameAttribute == "mobile2") { hist.translateNameAttribute = " deuxiéme mobile " }
                    else if (hist.nameAttribute == "numeroFTUSA") { hist.translateNameAttribute = "numéro FTUSA" }
                    else if (hist.nameAttribute == "centreExpertise") { hist.translateNameAttribute = "Centre Expertise" }
                    else if (hist.nameAttribute == "blocage") { hist.translateNameAttribute = "Bloqué" }
                    else if (hist.nameAttribute == "eng") { hist.translateNameAttribute = "ENG " }
                    else if (hist.nameAttribute == "isActive") { hist.translateNameAttribute = "Activé" }
                    else if (hist.nameAttribute == "active") { hist.translateNameAttribute = "L'etat d'activation" }
                    else if (hist.nameAttribute == "debutBlocage") { hist.translateNameAttribute = "debut du Blocage" }
                    else if (hist.nameAttribute == "finBlocage") { hist.translateNameAttribute = "fin du Blocage" }
                    else if (hist.nameAttribute == "dateEffetConvention") { hist.translateNameAttribute = "date d'effet du convention " }
                    else if (hist.nameAttribute == "dateFinConvention") { hist.translateNameAttribute = "date fin du convention" }
                    else if (hist.nameAttribute == "delegationLabel") { hist.translateNameAttribute = "marque du  Vehicule" }
                    else if (hist.nameAttribute == "governorateLabel") { hist.translateNameAttribute = "governorat" }
                    else if (hist.nameAttribute == "adresse") { hist.translateNameAttribute = "Adresse " }
                    else if (hist.nameAttribute == "listeVilles") { hist.translateNameAttribute = "liste  des villes" }
                    else if (hist.nameAttribute == "Liste VisAViss") { hist.translateNameAttribute = "Vis à Vis" }
                    else if (hist.nameAttribute == "Liste gouvernorats") { hist.translateNameAttribute = "Liste gouvernorats" }
                    else if (hist.nameAttribute == "Liste Villes") { hist.translateNameAttribute = "Liste Villes" }
                    else if (hist.nameAttribute == "partnerName") { hist.translateNameAttribute = "compagnie" }
                    else if (hist.nameAttribute == "refModeGestionsString") { hist.translateNameAttribute = " Liste Mode Gestions" }
                    else if (hist.nameAttribute == "Liste Specialite Secondaires") { hist.translateNameAttribute = "Liste Specialite Secondaires" }
                    else if (hist.nameAttribute == "Liste Specialite Principales") { hist.translateNameAttribute = "Liste Specialite Principales" }
                    else if (hist.nameAttribute == "Liste orientations") { hist.translateNameAttribute = "Liste orientations" }
                    else if (hist.nameAttribute == "Liste Garanties Impliques") { hist.translateNameAttribute = "Liste Garanties Impliques" }
                    else if (hist.nameAttribute == "Liste Partner Modes") { hist.translateNameAttribute = "Liste Partner Modes" }
                    else if (hist.nameAttribute == "listModeGestions") { hist.translateNameAttribute = " Liste Mode Gestions" }
                    else if (hist.nameAttribute == "refMarquesString") { hist.translateNameAttribute = " Liste Marques" }
                    else if (hist.nameAttribute == "description") { hist.translateNameAttribute = " Description" }
                    else if (hist.nameAttribute == "responsabiliteY") { hist.translateNameAttribute = " Responsabilite Y" }
                    else if (hist.nameAttribute == "responsabiliteX") { hist.translateNameAttribute = " Responsabilite X" }
                    else if (hist.nameAttribute == "code") { hist.translateNameAttribute = " Code" }
                    else if (hist.nameAttribute == "title") { hist.translateNameAttribute = "Title" }
                    else if (hist.nameAttribute == "firstName") { hist.translateNameAttribute = "Prénom" }
                    else if (hist.nameAttribute == "lastName") { hist.translateNameAttribute = "Nom" }
                    else if (hist.nameAttribute == "email") { hist.translateNameAttribute = "Email" }
                    else if (hist.nameAttribute == "profileName") { hist.translateNameAttribute = "Profils" }
                    else if (hist.nameAttribute == "originalName") { this.pieceJointeHistory = true; }

                    else if (hist.nameAttribute == "debutValidite") { hist.translateNameAttribute = "Début de Validité" }
                    else if (hist.nameAttribute == "deadlineDate") { hist.translateNameAttribute = "Date d'échéance" }
                    else if (hist.nameAttribute == "receiptValidityDate") { hist.translateNameAttribute = "Validité quittance" }
                    else if (hist.nameAttribute == "finValidite") { hist.translateNameAttribute = "Fin de validité" }
                    else if (hist.nameAttribute == "amendmentEffectiveDate") { hist.translateNameAttribute = "Date d'effet de l'avenant" }
                    else if (hist.nameAttribute== "numeroContrat") { hist.translateNameAttribute = "Numéro du contrat" }
                    else if (hist.nameAttribute == "nomCompagnie") { hist.translateNameAttribute = "Compagnie" }
                    else if (hist.nameAttribute == "agenceNom") { hist.translateNameAttribute = "Agence" }
                    else if (hist.nameAttribute == "fractionnementLibelle") {hist.translateNameAttribute = "Fractionnement" }
                    else if (hist.nameAttribute == "commentaire") { hist.translateNameAttribute = "Commentaire" }
                    else if (hist.nameAttribute == "typeLabel") { hist.translateNameAttribute = "Type" }
                    else if (hist.nameAttribute == "natureLabel") { hist.translateNameAttribute = "Nature" }
                    else if (hist.nameAttribute == "statusLabel") { hist.translateNameAttribute = "Statut" }
                    else if (hist.nameAttribute == "receiptStatusLabel") { hist.translateNameAttribute = "Statut quittance" }
                    else if (hist.nameAttribute == "franchiseTypeNewValue") { hist.translateNameAttribute = "Montant de la prime" }
                    else if (hist.nameAttribute == "franchiseTypeDcCapital") { hist.translateNameAttribute = "Type d'avenant" }


                    else { hist.translateNameAttribute = "undefined" }

                    if (hist.lastValue == 'true') { hist.lastValue = "Oui"; }
                    if (hist.lastValue == 'false') { hist.lastValue = "Non"; }
                    if (hist.newValue == 'false') { hist.newValue = "Non"; }
                    if (hist.newValue == 'true') { hist.newValue = "Oui"; }
                    if (hist.lastValue == 'null') { hist.lastValue = "vide"; }
                    if (hist.newValue == 'null') { hist.newValue = "vide"; }
                });
                this.motComplet = [];
                element.changeValues.forEach(changeValues => {

                    if (changeValues.nameAttribute == 'Liste VisAViss' || changeValues.nameAttribute == 'Liste Specialite Secondaires' || changeValues.nameAttribute == 'Liste Specialite Principales' || changeValues.nameAttribute == 'Liste orientations' || changeValues.nameAttribute == 'Liste Garanties Impliques' || changeValues.nameAttribute == 'Liste gouvernorats' || changeValues.nameAttribute == 'Liste Villes' || changeValues.nameAttribute == 'Liste Partner Modes') {
                        if (!changeValues.newValue.includes('changed to')) {
                            var words = changeValues.newValue.split('DTO');
                            for (let index = 1; index < words.length; index++) {
                                const mot = words[index];
                                var mots = mot.split(',');
                                if (mot.includes('removed')) {
                                    switch (changeValues.nameAttribute) {
                                        case 'Liste gouvernorats':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt.substr(8, elementtt.length - 2) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Villes':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt.substr(8, elementtt.length - 2) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste VisAViss':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('nom') && !elementtt.includes('null')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt.substr(6, elementtt.length - 1) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Specialite Secondaires':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt.substr(8, elementtt.length - 1) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Specialite Principales':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt.substr(8, elementtt.length - 1) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste orientations':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('remiseMarque')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt + '\n';
                                                }
                                            })
                                            break;
                                        case 'Liste Garanties Impliques':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('partnerName')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + elementtt + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Partner Modes':
                                            this.m = 0;
                                            let el = '';
                                            mots.forEach(elementtt => {
                                                this.m = this.m + 1;
                                                el = el + ' ' + elementtt;

                                                //if (elementtt.includes('modeId') && elementtt.includes('partnerId')) {
                                                if (this.m == mots.length) {
                                                    let strOut = el.substr(el.indexOf('partnerId'), 25);
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " supprission : " + strOut + '\n';
                                                }

                                                //}
                                            });
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                else if (mot.includes('added')) {

                                    switch (changeValues.nameAttribute) {
                                        case 'Liste gouvernorats':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt.substr(8, elementtt.length - 2) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Villes':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt.substr(8, elementtt.length - 2) + '\n';
                                                }
                                            });

                                            break;
                                        case 'Liste VisAViss':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('nom') && !elementtt.includes('null') && !elementtt.includes('prenom')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt.substr(5, elementtt.length - 1) + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Specialite Secondaires':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt.substr(8, elementtt.length - 1) + '\n';
                                                }
                                            });

                                            break;
                                        case 'Liste Specialite Principales':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('label')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt.substr(8, elementtt.length - 1) + '\n';
                                                }
                                            });

                                            break;
                                        case 'Liste orientations':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('remiseMarque')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Garanties Impliques':
                                            mots.forEach(elementtt => {
                                                if (elementtt.includes('partnerName')) {
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + elementtt + '\n';
                                                }
                                            });
                                            break;
                                        case 'Liste Partner Modes':
                                            this.n = 0;
                                            let el = '';
                                            mots.forEach(elementtt => {
                                                this.n = this.n + 1;
                                                el = el + ' ' + elementtt;
                                                //if (elementtt.includes('modeId') && elementtt.includes('partnerId')) {
                                                if (this.n == mots.length) {
                                                    let strOut = el.substr(el.indexOf('partnerId'), 25);
                                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + strOut + '\n';
                                                }

                                                // }
                                            });
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                /*if (changeValues.nameAttribute == 'Liste Partner Modes') {

                                    if (mot.includes('added') || mot.includes('removed')) {
                                        this.n = 0;
                                        let el = '';
                                        mots.forEach(elementtt => {
                                            this.n = this.n + 1;
                                            el = el + ' ' + elementtt;
                                            if (this.n == mots.length) {
                                                console.log('test------------------------------');
                                                let strOut = el.substr(el.indexOf('partnerId'), 25);
                                                if (strOut !== undefined && strOut !== null) {
                                                    this.motComplet.push(strOut);
                                                    var cache = {};
                                                    this.motComplet = this.motComplet.filter(function (elem) {
                                                        return cache[elem] ? 0 : cache[elem] = 1;
                                                    });
                                                }

                                                console.log(strOut);
                                            }

                                            //if (elementtt.includes('modeId') && elementtt.includes('partnerId')) {

                                            //element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " ajout : " + strOut + '\n';
                                            //}

                                        });
                                    }

                                }*/
                            }
                        }
                        else {
                            var words = changeValues.newValue.split('changed to');
                            var olds = words[0].split(',');
                            var news = words[1].split(',');
                            var oldValue = ' ';
                            var newValue = ' ';
                            olds.forEach(old => {
                                if (old.includes('label')) {
                                    oldValue = old.substr(8, old.length - 1);

                                }
                            });
                            news.forEach(newval => {
                                if (newval.includes('label')) {
                                    newValue = newval.substr(8, newval.length - 1);

                                }
                            });
                            switch (changeValues.nameAttribute) {
                                case 'Liste gouvernorats':
                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + oldValue + " en " + newValue + '\n';
                                    break;
                                case 'Liste Villes': element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + oldValue + " en " + newValue + '\n';
                                    break;
                                case 'Liste Specialite Secondaires':
                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + oldValue + " en " + newValue + '\n';
                                    break;
                                case 'Liste Specialite Principales':
                                    element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + oldValue + " en " + newValue + '\n';
                                    break;
                                default:
                                    break;
                            }

                        }

                    }
                    else if (changeValues.nameAttribute !== 'description' && changeValues.nameAttribute !== 'regionId' && changeValues.nameAttribute !== 'longitude' && changeValues.nameAttribute !== 'latitude' && changeValues.nameAttribute !== 'governorateId' && changeValues.nameAttribute !== 'addedGageo' && changeValues.nameAttribute !== 'id' && changeValues.nameAttribute !== 'reparateurId' && changeValues.nameAttribute !== 'partnerId' && changeValues.nameAttribute !== 'refAgeVehiculeId' && changeValues.nameAttribute !== 'reglementId' && changeValues.nameAttribute !== 'delegationId' && changeValues.nameAttribute !== 'gouvernoratId' && changeValues.nameAttribute !== 'ExpertId' && changeValues.nameAttribute !== 'villeId' && changeValues.nameAttribute !== 'brandId' && changeValues.nameAttribute !== 'endDate' && changeValues.nameAttribute !== 'creationUserId' && changeValues.nameAttribute !== 'operationId' && changeValues.nameAttribute !== 'statusId' && changeValues.nameAttribute !== 'creationDate' && changeValues.nameAttribute !== 'code' && changeValues.nameAttribute !== 'pecStatusChangeMatrixId' && changeValues.nameAttribute !== 'statusPecId' && changeValues.nameAttribute !== 'stepLabel' && changeValues.nameAttribute !== 'referencedId' && changeValues.nameAttribute !== 'chemin' && changeValues.nameAttribute !== 'pieceJointeId' && changeValues.nameAttribute !== 'attachment64' && changeValues.nameAttribute !== 'attachmentName' && changeValues.nameAttribute !== 'name' && this.nameEntity !== 'RefBareme' && changeValues.translateNameAttribute !== 'undefined') {
                        element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + changeValues.lastValue + " en " + changeValues.newValue + '\n';
                    } else if (changeValues.nameAttribute !== 'chemin' && changeValues.nameAttribute !== 'pieceJointeId' && changeValues.nameAttribute !== 'attachment64' && changeValues.nameAttribute !== 'attachmentName' && changeValues.nameAttribute !== 'name' && changeValues.nameAttribute !== 'id' && changeValues.nameAttribute !== 'originalName' && changeValues.translateNameAttribute !== 'undefined') {
                        element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.translateNameAttribute + " est changé de " + changeValues.lastValue + " en " + changeValues.newValue + '\n';
                    }
                    if (this.pieceJointeHistory) {
                        this.pieceJointeHistory = false;
                        element.descriptiveOfChange = element.descriptiveOfChange + '\n' + "Modification du croquis" + '\n';
                    }
                });
            });

            

            /*this.motComplet.forEach(elemen => {
                console.log('yes--------------------------');
                console.log(elemen);
            });*/
            this.histories.sort((a, b) => a.operationDate.rendered.localeCompare(b.operationDate.rendered));
            this.dtTrigger.next();

        });
    }



    clear() {
        this.activeModal.dismiss('cancel');
    }



    onError(error: any): any {
        throw new Error("Method not implemented.");
    }

    trackId(index: number, item: History) {
        return item.id;
    }

}

@Component({
    selector: 'jhi-ref-agence-pop',
    template: ''
})
export class HistoryPopupComponent implements OnInit, OnDestroy {
    routeSub: any;
    agency: any;
    dtOptions: any = {};

    constructor(
        //private refBaremePopupDetailService: RefBaremePopupDetailService,
        public principal: Principal,
        private route: ActivatedRoute,
        private historyPopupService: HistoryPopupService,
    ) { }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.routeSub = this.route.params.subscribe((params) => {
            /* if ( params['id'] ) {
                 this.agencyPopupService
                     .open(AgenceConcessPopupDetail as Component, params['id']);
             } else {*/
            /*this.historyPopupService
                .open(HistoryPopupDetail as Component, this.agency);*/
            //}
        });
    }


    onError(json: any): void {
        throw new Error("Method not implemented.");
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

}