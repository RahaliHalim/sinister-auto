import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { SinisterService } from '../sinister/sinister.service';
import { Sinister } from '../sinister/sinister.model';
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable, PrestationPecStep } from '../../constants/app.constants';
import { ConfirmationDialogService } from '../../shared';
import { DecisionPec } from '../../constants/app.constants';
import { Apec, ApecService } from '../apec';
import { ViewSinisterPecService, ViewSinisterPec } from '../view-sinister-pec';
import { QuotationService, Quotation } from '../quotation';
import { DetailsPiecesService } from '../details-pieces';

@Component({
    selector: 'jhi-sinister-pec-refused',
    templateUrl: './sinister-pec-modification-prestation.component.html'

})
export class SinisterPecModificationPrestation implements OnInit, OnDestroy {
    sinister: Sinister;
    sinisterPecsBeingProcessed: ViewSinisterPec[];
    sinisterPec: SinisterPec = new SinisterPec();
    currentAccount: any;
    immatriculationTier: any;
    selectedRow: Number;
    sinPecid: number;
    selectedItem: boolean = true;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    newStepReceptionVehicle: boolean = false;
    newStepChangementStatus: boolean = false;
    newStepAvisExpert: boolean = false;
    newStepMiseAJour: boolean = false;
    newStepImprimeAPEC: boolean = false;
    selectSinisterPec: boolean = false;
    beforeStep: boolean = false;
    selectOneEtape: boolean = false;
    showAlert: boolean = false;
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    apecs: Apec[];
    quotation: Quotation = new Quotation();
    apecModifPec: Apec = new Apec();
    constructor(
        private sinisterPecService: SinisterPecService,
        private sinisterService: SinisterService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        public principal: Principal,
        private confirmationDialogService: ConfirmationDialogService,
        private apecService: ApecService,
        private viewSinisterPecService: ViewSinisterPecService,
        private quotationService: QuotationService,
        private detailsPiecesService: DetailsPiecesService,
    ) {

    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.registerChangeInSinisterPecs();
    }
    loadAll() {
        this.viewSinisterPecService.getAllSinPecForModificationPrestation().subscribe((res) => {
            this.sinisterPecsBeingProcessed = res.json;
            this.dtTrigger.next();
        });
    }

    setClickedRow(index: number, refbid: number) {


        this.selectedRow = index;
        this.sinPecid = refbid
        this.selectedItem = false;
        this.selectSinisterPec = true;

    }

    changeEtape() {
        this.showAlert = false;
        if (this.newStepChangementStatus == true && this.newStepImprimeAPEC == false && this.newStepReceptionVehicle == false && this.newStepAvisExpert == false && this.newStepMiseAJour == false) {
            this.selectOneEtape = true;
        } else if (this.newStepChangementStatus == false && this.newStepImprimeAPEC == true && this.newStepReceptionVehicle == false && this.newStepAvisExpert == false && this.newStepMiseAJour == false) {
            this.selectOneEtape = true;
        } else if (this.newStepChangementStatus == false && this.newStepImprimeAPEC == false && this.newStepReceptionVehicle == true && this.newStepAvisExpert == false && this.newStepMiseAJour == false) {
            this.selectOneEtape = true;
        } else if (this.newStepChangementStatus == false && this.newStepImprimeAPEC == false && this.newStepReceptionVehicle == false && this.newStepAvisExpert == true && this.newStepMiseAJour == false) {
            this.selectOneEtape = true;
        } else if (this.newStepChangementStatus == false && this.newStepImprimeAPEC == false && this.newStepReceptionVehicle == false && this.newStepAvisExpert == false && this.newStepMiseAJour == true) {
            this.selectOneEtape = true;
        } else {
            this.selectOneEtape = false;
        }
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (this.sinPecid) {



                    this.sinisterPecService.findPrestationPec(this.sinPecid).subscribe((res: SinisterPec) => {
                        this.sinisterPec = res;
                        const dateUpdatePec = new Date(this.sinisterPec.declarationDate);
                        this.sinisterPec.declarationDate = this.dateAsYYYYMMDDHHNNSSLDT(dateUpdatePec);
                        if (this.newStepChangementStatus) {
                            this.sinisterPec.oldStepModifSinPec = this.sinisterPec.stepId;
                            this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                            this.sinisterPec.stepId = 3;
                            this.sinisterPec.debloque = true;
                            this.sinisterPec.decision = null;
                            this.beforeStep = false;
                            this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                                this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                this.selectedItem = true;
                                this.selectedRow = undefined;
                                this.sinPecid = undefined;
                                this.selectSinisterPec = false;
                            });
                        }
                        if (this.newStepReceptionVehicle) {
                            if (this.sinisterPec.primaryQuotationId !== null && this.sinisterPec.primaryQuotationId !== undefined) {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId = 25;
                                this.sinisterPec.primaryQuotation = null;
                                this.sinisterPec.dateRDVReparation = null;
                                this.sinisterPec.expertDecision = null;
                                this.sinisterPec.oldStepModifSinPec = null;
                                this.sinisterPec.oldStep = null;
                                this.sinisterPec.pieceGenerique = null;
                                this.sinisterPec.expertId = null;
                                this.beforeStep = false;
                                this.detailsPiecesService.deleteByQuery(this.sinisterPec.primaryQuotationId).subscribe((response1) => {
                                    this.detailsPiecesService.deleteByQueryMP(this.sinisterPec.id).subscribe((response2) => {
                                        this.apecService.deleteAPecDevisCompl(this.sinisterPec.id).subscribe((resDel) => {
                                            this.sinisterPec.primaryQuotationId = null;
                                            this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resMP2) => {
                                                this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                    this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                                    this.selectedItem = true;
                                                    this.selectedRow = undefined;
                                                    this.sinPecid = undefined;
                                                    this.selectSinisterPec = false;
                                                });
                                            });
                                        });

                                    });
                                });
                            } else if (this.sinisterPec.reparateurId !== null && this.sinisterPec.reparateurId !== undefined) {
                                this.sinisterPec.oldStepNw = this.sinisterPec.stepId;
                                this.sinisterPec.stepId = 25;
                                this.sinisterPec.primaryQuotation = null;
                                this.sinisterPec.dateRDVReparation = null;
                                this.sinisterPec.expertDecision = null;
                                this.sinisterPec.oldStepModifSinPec = null;
                                this.sinisterPec.oldStep = null;
                                this.sinisterPec.pieceGenerique = null;
                                this.sinisterPec.expertId = null;
                                this.beforeStep = false;

                                this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resMP2) => {
                                    this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                    this.selectedItem = true;
                                    this.selectedRow = undefined;
                                    this.sinPecid = undefined;
                                    this.selectSinisterPec = false;
                                });
                            } else {
                                this.beforeStep = true;
                            }
                        }

                        if (this.newStepImprimeAPEC) {

                            if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                                if (this.sinisterPec.primaryQuotationId != null && this.sinisterPec.primaryQuotationId != undefined) {
                                    /*this.quotationService.find(this.sinisterPec.primaryQuotationId).subscribe((res) => {  // Find devid By ID
                                        this.quotation = res;
                                    });*/
                                    this.apecService.findByQuotation(this.sinisterPec.primaryQuotationId).subscribe((res: Apec) => {
                                        this.apecModifPec = res;
                                        if (this.apecModifPec.etat == 20 || this.apecModifPec.etat == 10 || this.apecModifPec.etat == 9 || this.apecModifPec.etat == 13) {
                                            this.apecModifPec.etat = 3;
                                            this.apecService.update(this.apecModifPec).subscribe((res) => {
                                                this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                                this.selectedItem = true;
                                                this.selectedRow = undefined;
                                                this.sinPecid = undefined;
                                                this.selectSinisterPec = false;
                                            });
                                        } else {
                                            this.beforeStep = true;
                                            this.showAlert = true;
                                        }
                                    });
                                } else {
                                    this.beforeStep = true;
                                    this.showAlert = true;
                                }
                            } else {
                                this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((resCompl) => {
                                    if (resCompl.id !== undefined && resCompl.id !== null) {
                                        this.apecService.findByQuotation(resCompl.id).subscribe((res: Apec) => {
                                            this.apecModifPec = res;
                                            if (this.apecModifPec.etat == 20 || this.apecModifPec.etat == 10 || this.apecModifPec.etat == 9 || this.apecModifPec.etat == 13) {
                                                this.apecModifPec.etat = 3;
                                                this.apecService.update(this.apecModifPec).subscribe((res) => {
                                                    this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                                    this.selectedItem = true;
                                                    this.selectedRow = undefined;
                                                    this.sinPecid = undefined;
                                                    this.selectSinisterPec = false;
                                                });
                                            } else {
                                                this.beforeStep = true;
                                                this.showAlert = true;
                                            }
                                        });
                                    } else {
                                        this.beforeStep = true;
                                        this.showAlert = true;
                                    }

                                });
                            }

                        }

                        if (this.newStepAvisExpert) {
                            this.alertService.error('auxiliumApp.sinisterPec.enCoursDeDeveloppement', null, null);
                        }

                        if (this.newStepMiseAJour) {
                            if (this.sinisterPec.primaryQuotationId !== null && this.sinisterPec.primaryQuotationId !== undefined) {
                                if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                                    if (this.sinisterPec.stepId !== PrestationPecStep.ATTENTE_SAISIE_DVIS && this.sinisterPec.stepId !== PrestationPecStep.Verification
                                        && this.sinisterPec.stepId !== PrestationPecStep.CONFIRMATION_QUOTE && this.sinisterPec.stepId !== PrestationPecStep.CIRCUMSTANCE_CONFORMS_OK_FOR_DISASSEMBLY) {
                                        this.apecService.deleteApecByDevis(this.sinisterPec.primaryQuotationId).subscribe((apecDelete) => {
                                            this.sinisterPec.stepId = PrestationPecStep.UPDATE_QUOTE;
                                            this.sinisterPec.changeModificationPrix = false;
                                            this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resMP2) => {
                                                this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                                this.selectedItem = true;
                                                this.selectedRow = undefined;
                                                this.sinPecid = undefined;
                                                this.selectSinisterPec = false;
                                            });
                                        });
                                    } else {
                                        this.beforeStep = true;
                                        this.showAlert = true;
                                    }
                                } else {
                                    if (this.sinisterPec.stepId !== PrestationPecStep.Verification && this.sinisterPec.stepId !== PrestationPecStep.CONFIRMATION_QUOTE) {
                                        this.sinisterPec.stepId = PrestationPecStep.UPDATE_QUOTE;
                                        this.sinisterPec.changeModificationPrix = false;
                                        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resMP2) => {
                                            this.detailsPiecesService.deleteDetailsPiecesQuotationAnnule(this.sinisterPec.id).subscribe((response) => {
                                                this.apecService.deleteAPecDevisCompl(this.sinisterPec.id).subscribe((resDel) => {
                                                    this.sinisterPecService.fusionDevisForSinisterPec(this.sinisterPec.id, this.sinisterPec.primaryQuotationId).subscribe((fus) => {
                                                        this.detailsPiecesService.deleteByQueryMP(this.sinisterPec.id).subscribe((response) => {
                                                            this.quotationService.deleteAdditionnelQuote(this.sinisterPec.id).subscribe((resMP2) => {
                                                                this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                                                this.selectedItem = true;
                                                                this.selectedRow = undefined;
                                                                this.sinPecid = undefined;
                                                                this.selectSinisterPec = false;
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });

                                        /* this.quotationService.findQuotCompl(this.sinisterPec.id).subscribe((quot) => {
                                            this.apecService.deleteApecByDevis(quot.id).subscribe((apecDelete) => {
                                                this.sinisterPec.stepId = PrestationPecStep.UPDATE_QUOTE;
                                                this.sinisterPecService.updateIt(this.sinisterPec).subscribe((resMP2) => {
                                                    this.sinisterPecService.saveQuotationStatus(quot.id).subscribe((resQ) => {
                                                        this.alertService.success('auxiliumApp.sinisterPec.modifSinPec', null, null);
                                                        this.selectedItem = true;
                                                        this.selectedRow = undefined;
                                                        this.sinPecid = undefined;
                                                        this.selectSinisterPec = false;
                                                    });
                                                });
                                            });
                                        }); */
                                    } else {
                                        this.beforeStep = true;
                                        this.showAlert = true;
                                    }
                                }
                            } else {
                                this.beforeStep = true;
                                this.showAlert = true;
                            }
                        }

                    });




                }
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }


    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: SinisterPec) {
        return item.id;
    }
    dateAsYYYYMMDDHHNNSS(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + ' ' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    dateAsYYYYMMDDHHNNSSLDT(date): string {
        return date.getFullYear()
            + '-' + this.leftpad(date.getMonth() + 1, 2)
            + '-' + this.leftpad(date.getDate(), 2)
            + 'T' + this.leftpad(date.getHours(), 2)
            + ':' + this.leftpad(date.getMinutes(), 2)
            + ':' + this.leftpad(date.getSeconds(), 2);
    }

    leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
            + String(val)).slice(String(val).length);
    }
    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }
}