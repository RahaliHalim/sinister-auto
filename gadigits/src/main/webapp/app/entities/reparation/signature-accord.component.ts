import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec, SinisterPecService } from '../sinister-pec';
import { Apec, ApecService } from '../apec';
import { Quotation } from '../quotation';
import { Principal, ResponseWrapper } from '../../shared';
import { EtatAccord, QuoteStatus } from '../../constants/app.constants';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PieceJointeSignatureAccordPopupService } from './piece-jointe-signature-accord-popup.service';
import { PieceJointeSignatureAccordComponent } from './piece-jointe-signature-accord.component';
import { UserExtraService, PermissionAccess, UserExtra } from '../user-extra';
import { GaDatatable } from '../../constants/app.constants';
import { AttachmentService } from '../attachments';
import { DataTableDirective } from 'angular-datatables';
import { Authorities, PrestationPecStep } from '../../constants/app.constants';
@Component({
    selector: 'jhi-reparation-ajout-saisie-devis',
    templateUrl: './signature-accord.component.html'
})
export class SignatureAccordComponent implements OnInit, OnDestroy {


    //@ViewChild(DataTableDirective)
    //dtElement: DataTableDirective; 

    currentAccount: any;
    aPECs: SinisterPec[] = [];
    quotation: Quotation = new Quotation();
    sinisterPec: SinisterPec = new SinisterPec();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    private ngbModalRef: NgbModalRef;
    stampDuty = 0.6;

    userExtra: UserExtra = new UserExtra();
    constructor(
        private sinisterPecService: SinisterPecService,
        private apecService: ApecService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private router: Router,
        private pieceJointeSignatureAccordPopupService: PieceJointeSignatureAccordPopupService,
        private eventManager: JhiEventManager,
        private userExtraService: UserExtraService,
        private attachmentService: AttachmentService
    ) {

    }

    loadAll() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;

            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((usr: UserExtra) => {
                this.userExtra = usr;
                this.sinisterPecService.findByEtatAccord(this.currentAccount.id, EtatAccord.ACC_SIGNATURE_ACCORD).subscribe((res) => {
                    res.json.forEach(element => {
                        element.sinisterPec.tiers.forEach(tr => {
                            element.sinisterPec.immatriculationTier = tr.immatriculation;
                        });
                        element.sinisterPec.apecId = element.id;
                        if (element.sinisterPec.stepId == 109) {
                            this.aPECs.push(element.sinisterPec);
                        }
                    });
                    //this.rerender();
                    this.dtTrigger.next();
                });
            });

            this.userExtraService.findFunctionnalityEntityByUser(55, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }
    attacher(idAPec: number) {
        this.apecService.find(idAPec).subscribe((res) => {
            this.ngbModalRef = this.pieceJointeSignatureAccordPopupService.pieceJointeModalRef(PieceJointeSignatureAccordComponent as Component, res);
            this.ngbModalRef.result.then((result: any) => {
                // if (result !== null && result !== undefined) {
                console.log("result select popup------");
                console.log(result);
                this.sinisterPec = result.sinisterPec;
                this.sinisterPec.stepId = PrestationPecStep.INSTANCE_REPARATION;
                
                if (this.sinisterPec.listComplementaryQuotation.length == 0) {
                    if (this.sinisterPec.primaryQuotationId != null) {
                        if (this.sinisterPec.remainingCapital !== null) {
                            let franchise = 0;
                            if (this.sinisterPec.partnerId == 5) {
                                if (this.sinisterPec.modeId == 5) {
                                    franchise = (this.sinisterPec.remainingCapital * 5) / 100;
                                } else if (this.sinisterPec.modeId == 7) {
                                    franchise = (this.sinisterPec.remainingCapital * 10) / 100;
                                } else if (this.sinisterPec.modeId == 11) {
                                    if (this.sinisterPec.franchiseTypeDcCapital == 'Pourcentage') {
                                        franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.dcCapitalFranchise) / 100;
                                    } else if (this.sinisterPec.franchiseTypeDcCapital == 'Montant') {
                                        franchise = this.sinisterPec.dcCapitalFranchise;
                                    }
                                }
                            } else if (this.sinisterPec.partnerId == 6) {
                                if (this.sinisterPec.modeId == 5) {
                                    franchise = 30;
                                } else if (this.sinisterPec.modeId == 7) {
                                    franchise = (this.sinisterPec.remainingCapital * 10) / 100;
                                } else if (this.sinisterPec.modeId == 11) {
                                    if (this.sinisterPec.franchiseTypeDcCapital == 'Pourcentage') {
                                        franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.dcCapitalFranchise) / 100;
                                    } else if (this.sinisterPec.franchiseTypeDcCapital == 'Montant') {
                                        franchise = this.sinisterPec.dcCapitalFranchise;
                                    }
                                }
                            } else if (this.sinisterPec.partnerId == 7 || this.sinisterPec.partnerId == 4) {
                                if (this.sinisterPec.modeId == 5) {
                                    if (this.sinisterPec.franchiseTypeDcCapital == 'Pourcentage') {
                                        //franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.dcCapitalFranchise) / 100;
                                        franchise = result.estimationFranchise;
                                    } else if (this.sinisterPec.franchiseTypeDcCapital == 'Montant') {
                                        franchise = 100;
                                        if (this.sinisterPec.partnerId == 4) {
                                            franchise = this.sinisterPec.dcCapitalFranchise;
                                        }
                                    }
                                } else if (this.sinisterPec.modeId == 7) {
                                    //franchise = (result.ttc * 10) / 100;
                                    franchise = result.estimationFranchise;
                                } else if (this.sinisterPec.modeId == 11) {
                                    if (this.sinisterPec.franchiseTypeDcCapital == 'Pourcentage') {
                                        //franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.dcCapitalFranchise) / 100;
                                        franchise = result.estimationFranchise;
                                    } else if (this.sinisterPec.franchiseTypeDcCapital == 'Montant') {
                                        franchise = this.sinisterPec.dcCapitalFranchise;
                                    }
                                }
                            } else if (this.sinisterPec.partnerId == 3) {
                                if (this.sinisterPec.modeId == 5) {
                                    franchise = 0;
                                } else if (this.sinisterPec.modeId == 7) {
                                    franchise = 0;
                                }
                            } else {
                                if (this.sinisterPec.modeId == 5) {
                                    if (this.sinisterPec.franchiseTypeDcCapital == 'Pourcentage') {
                                        franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.dcCapitalFranchise) / 100;
                                    } else if (this.sinisterPec.franchiseTypeDcCapital == 'Montant') {
                                        franchise = this.sinisterPec.dcCapitalFranchise;
                                    }
                                } else if (this.sinisterPec.modeId == 7) {
                                    if (this.sinisterPec.franchiseTypeBgCapital == 'Pourcentage') {
                                        franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.bgCapitalFranchise) / 100;
                                    } else if (this.sinisterPec.franchiseTypeBgCapital == 'Montant') {
                                        franchise = this.sinisterPec.bgCapitalFranchise;
                                    }
                                } else if (this.sinisterPec.modeId == 11) {
                                    if (this.sinisterPec.franchiseTypeDcCapital == 'Pourcentage') {
                                        franchise = (this.sinisterPec.remainingCapital * this.sinisterPec.dcCapitalFranchise) / 100;
                                    } else if (this.sinisterPec.franchiseTypeDcCapital == 'Montant') {
                                        franchise = this.sinisterPec.dcCapitalFranchise;
                                    }
                                }
                            }
                            let capRest = this.sinisterPec.remainingCapital - franchise - (result.ttc - result.participationAssure);
                            if (this.sinisterPec.totaleFranchise == null || this.sinisterPec.totaleFranchise == undefined) {
                                this.sinisterPec.totaleFranchise = 0;
                            }
                            this.sinisterPec.totaleFranchise = this.sinisterPec.totaleFranchise + result.estimationFranchise;

                            if (capRest > 0) {
                                this.sinisterPec.capitalRestantAfterComp = capRest;
                            } else {
                                this.sinisterPec.capitalRestantAfterComp = 0;
                            }
                        }
                        this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                        });
                        this.aPECs.forEach((item, index) => {
                            if (item.id === result.sinisterPecId) this.aPECs.splice(index, 1);
                        });
                    }
                }
                else {
                    if (this.sinisterPec.remainingCapital != null) {
                        if (this.sinisterPec.capitalRestantAfterComp !== 0) {
                            let capRestComp = 0;
                            if (this.sinisterPec.partnerId == 7 || this.sinisterPec.partnerId == 4) {
                                capRestComp = this.sinisterPec.capitalRestantAfterComp - result.estimationFranchise - (result.ttc - this.stampDuty - result.participationAssure);
                            } else {
                                capRestComp = this.sinisterPec.capitalRestantAfterComp - (result.ttc - this.stampDuty - result.participationAssure);
                            }
                            let franMin = 0;
                            franMin = this.sinisterPec.totaleFranchise + result.estimationFranchise;
                            this.sinisterPec.totaleFranchise = franMin;

                            if (capRestComp > 0) {
                                this.sinisterPec.capitalRestantAfterComp = capRestComp;
                            } else {
                                this.sinisterPec.capitalRestantAfterComp = 0;
                            }
                        }
                    }
                    this.sinisterPecService.updateIt(this.sinisterPec).subscribe((res) => {
                    });
                    this.aPECs.forEach((item, index) => {
                        if (item.id === result.sinisterPecId) this.aPECs.splice(index, 1);
                    });
                }



                //this.modeId = result.modeId;

                // }
                this.ngbModalRef = null;
            }, (reason) => {
                // TODO: print error message
                console.log('______________________________________________________2');
                this.ngbModalRef = null;
            });
        });
    }

    getAccordPdf(sinisterPec: SinisterPec) {
        if (sinisterPec.apecId == null || sinisterPec.apecId === undefined) {
            console.log("Erreur lors de la génération");

        } else { // OK
            this.sinisterPecService.getSignatureAccordPdf(sinisterPec.apecId).subscribe((blob: Blob) => {
                let fileName = "Accord" + ".pdf";
                console.log(fileName);

                if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                    window.navigator.msSaveOrOpenBlob(blob, fileName);
                } else {
                    var a = document.createElement('a');
                    a.href = URL.createObjectURL(blob);
                    a.download = fileName;
                    a.target = '_blank';
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                }
            },
                err => {
                    alert("Error while downloading. File Not Found on the Server");
                });
        }
    }

    ngOnInit() {
        this.dtOptions = Object.assign({}, GaDatatable.defaultDtOptions);
        this.dtOptions.scrollX = true;
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            //this.dtTrigger.next();
        });
        this.registerChangeInPrestationPECS();

    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
    trackId(index: number, item: Apec) {
        return item.id;
    }
    registerChangeInPrestationPECS() {
        this.eventSubscriber = this.eventManager.subscribe('prestationPECListModification', (response) => this.loadAll());
    }
    downloadPieceFile(pieceFileAttachment: File) {
        if (pieceFileAttachment) {
            saveAs(pieceFileAttachment);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    /**
     * added from home
     */

    /*rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
      }*/

}
