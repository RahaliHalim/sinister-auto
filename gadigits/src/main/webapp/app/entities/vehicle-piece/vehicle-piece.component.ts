import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';

import { JhiEventManager, JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { GaDatatable, Global } from './../../constants/app.constants';
import { VehiclePiece } from './vehicle-piece.model';
import { VehiclePieceService } from './vehicle-piece.service';
import { VehiclePieceType } from '../vehicle-piece-type/vehicle-piece-type.model';
import { VehiclePieceTypeService } from '../vehicle-piece-type/vehicle-piece-type.service';
import { DataTableDirective } from 'angular-datatables';
import { ConfirmationDialogService, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vehicle-piece',
    templateUrl: './vehicle-piece.component.html'
})
export class VehiclePieceComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    vehiclePieces: VehiclePiece[];
    vehiclePiece: VehiclePiece = new VehiclePiece();
    currentAccount: any;
    vehiclePieceTypes: VehiclePieceType[];
    textPattern = Global.textPattern;
    isSaving: boolean;
    dtOptions: any = {};
    dtTrigger: Subject<VehiclePiece> = new Subject();

    constructor(
        private vehiclePieceService: VehiclePieceService,
        private vehiclePieceTypeService: VehiclePieceTypeService,
        private confirmationDialogService: ConfirmationDialogService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
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

    trimLabel() {
        this.vehiclePiece.label = this.vehiclePiece.label.trim();
    }

    loadAll() {
        this.vehiclePieceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.vehiclePieces = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.vehiclePiece.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.vehiclePieceService.update(
                                this.vehiclePiece
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.vehiclePieceService.create(
                                this.vehiclePiece
                            )
                        );
                    }
                }
            })
    }

    edit(id: number) {
        this.vehiclePieceService.find(id).subscribe(vehiclePiece => {
            this.vehiclePiece = vehiclePiece;
        });
    }

    cancel() {
        this.vehiclePiece = new VehiclePiece();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                if(confirmed) {
                    this.vehiclePieceService.delete(id).subscribe((response) => {
                        this.loadAll();
                    });
                }
            })
    }


    ngOnInit() {
        this.isSaving = false;
        this.vehiclePieceTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.vehiclePieceTypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
    }

    private subscribeToSaveResponse(result: Observable<VehiclePiece>) {
        result.subscribe(
            (res: VehiclePiece) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: VehiclePiece) {
        this.isSaving = false;
        this.cancel();
        this.loadAll();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    private onError(error) {
        this.isSaving = false;
        this.alertService.error(error.message, null, null);
    }
}
