import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { Subject } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';
import { Statement } from './statement.model';
import { StatementService } from './statement.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { DataTableDirective } from 'angular-datatables';
import { GaDatatable } from './../../constants/app.constants';

@Component({
    selector: 'jhi-invoice',
    templateUrl: './invoice.component.html'
})
export class InvoiceComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    statements: Statement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    dtOptions: any = {};
    dtTrigger: Subject<Statement> = new Subject();

    constructor(
        private statementService: StatementService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private sanitizer: DomSanitizer,
        private principal: Principal,
        private confirmationDialogService: ConfirmationDialogService
    ) {
    }

    ngAfterViewInit(): void {
        this.dtTrigger.next();
    }

    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.dtTrigger.next();
        });
    }

    loadAll() {
        this.statementService.query().subscribe(
            (res: ResponseWrapper) => {
                this.statements = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    showPdf(statement) {
        const linkSource = 'data:application/pdf;base64,' + statement.attachment64;
        return this.sanitizer.bypassSecurityTrustUrl(linkSource);
    }

    /**
     * Get generated statement pdf
     * @param id : the statement id
     */
    getStatementPdf(statement: Statement) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir confirmer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (statement.id == null || statement.id === undefined) {
                    console.log("Erreur lors de la génération");

                } else { // OK
                    statement.step = 1;
                    this.statementService.getStatementPdf(statement.id).subscribe((blob: Blob) => {
                        let companyName = statement.tugLabel.split(' ').join('_');
                        let fileName = "Bordereau_" + companyName + ".pdf";
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
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    /**
     * Get generated statement pdf
     * @param id : the statement id
     */
    generateStatementPdf(statement: Statement) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir confirmer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                if (statement.id == null || statement.id === undefined) {
                    console.log("Erreur lors de la génération");

                } else { // OK
                    statement.step = 1;
                    this.statementService.generateStatementPdf(statement.id).subscribe((blob: Blob) => {
                        let companyName = statement.tugLabel.split(' ').join('_');
                        let fileName = "Bordereau_" + companyName + ".pdf";
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
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    validate(statement: Statement) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir confirmer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.statementService.validate(statement.id).subscribe((result: Statement) => {
                    statement.step = 2;
                    this.loadAll();
                },
                    err => {
                        alert('Error while validating statement');
                    });
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));

    }

    invalidate(statement: Statement) {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir confirmer ?', 'Oui', 'Non', 'lg').then((confirmed) => {
            console.log('User confirmed:', confirmed);
            if (confirmed) {
                this.statementService.invalidate(statement.id).subscribe((result: Statement) => {
                    statement.step = 3;
                },
                    err => {
                        alert('Error while validating statement');
                    });
            }
        })
            .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
