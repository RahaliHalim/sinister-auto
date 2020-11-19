import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ConfirmationDialogService, Principal, ResponseWrapper, AlertUtil } from '../../shared';
import { GaDatatable, Global } from './../../constants/app.constants';
import { VehicleBrandModel } from './vehicle-brand-model.model';
import { VehicleBrandModelService } from './vehicle-brand-model.service';
import { VehicleBrand, VehicleBrandService } from '../vehicle-brand';
import { DataTableDirective } from 'angular-datatables';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupDetail } from '../history/history-popup-detail';
import { HistoryPopupService } from '../history';

@Component({
    selector: 'jhi-vehicle-brand-model',
    templateUrl: './vehicle-brand-model.component.html'
})
export class VehicleBrandModelComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    vehicleBrandModels: VehicleBrandModel[];
    vehicleBrandModel: VehicleBrandModel = new VehicleBrandModel();
    vehicleModelCheck: VehicleBrandModel;
    vehiclebrands: VehicleBrand[];
    existLabel = true;
    isSaving: boolean;
    currentAccount: any;
    textPattern = Global.textPattern;
    permissionToAccess : PermissionAccess = new PermissionAccess();
    
    dtOptions: any = {};
    dtTrigger: Subject<VehicleBrandModel> = new Subject();
    private ngbModalRef: NgbModalRef;

    constructor(
        private vehicleBrandModelService: VehicleBrandModelService,
        private vehicleBrandService: VehicleBrandService,
        private alertService: JhiAlertService,
        private alertUtil: AlertUtil,
        private activatedRoute: ActivatedRoute,
        private confirmationDialogService: ConfirmationDialogService,
        private principal: Principal,
        private userExtraService : UserExtraService,
        private historyPopupService: HistoryPopupService,
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
        this.vehicleBrandModelService.query().subscribe(
            (res: ResponseWrapper) => {
                this.vehicleBrandModels = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.vehicleBrandModel.label = this.vehicleBrandModel.label.trim();
    }
    /**
     * Find vehicle model by label
     * @param label 
     */
    findModelByLabel(label: string) {
        this.existLabel = false;
        if(label != null && label != undefined && label != ''){
          this.vehicleBrandModelService.findByLabel(this.vehicleBrandModel).subscribe(vehicleModel => {
              this.vehicleModelCheck = vehicleModel;
              console.log("test------------------");
              if(this.vehicleModelCheck.id != null && this.vehicleModelCheck.id != undefined && this.vehicleBrandModel.id != this.vehicleModelCheck.id ){
                  console.log("test1------------------");
                  if(this.vehicleModelCheck.label.replace(/\s/g, "").toLowerCase() == label.replace(/\s/g, "").toLowerCase()){
                    this.existLabel = true;
                    this.alertUtil.addError("auxiliumApp.vehicleBrandModel.exist");
                  }
              }else{
                  this.existLabel = false;
              }
          });
        }
      }

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.vehicleBrandModel.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.vehicleBrandModelService.update(
                                this.vehicleBrandModel
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.vehicleBrandModelService.create(
                                this.vehicleBrandModel
                            )
                        );
                    }
                }
            })
    }

    selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"Modele");
         this.ngbModalRef.result.then((result: any) => {
             if (result !== null && result !== undefined) {
             }
             this.ngbModalRef = null;
         }, (reason) => {
             // TODO: print error message
             console.log('______________________________________________________2');
             this.ngbModalRef = null;
         });
     }

    edit(id: number) {
        this.vehicleBrandModelService.find(id).subscribe(vehicleBrandModel => {
            this.existLabel = false;
            this.vehicleBrandModel = vehicleBrandModel;
        });
    }

    cancel() {
        this.vehicleBrandModel = new VehicleBrandModel();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                if(confirmed) {
                    this.vehicleBrandModelService.delete(id).subscribe((response) => {
                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
    }

    ngOnInit() {
        this.isSaving = false;
        this.vehicleBrandService.query()
            .subscribe((res: ResponseWrapper) => { this.vehiclebrands = res.json; }, (res: ResponseWrapper) => this.onError(res.json));

        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(72,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }

    ngOnDestroy() {
    }

    trackId(index: number, item: VehicleBrandModel) {
        return item.id;
    }

    trackVehicleBrandById(index: number, item: VehicleBrand) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<VehicleBrandModel>) {
        result.subscribe(
            (res: VehicleBrandModel) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: VehicleBrandModel) {
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
        this.alertUtil.addError(error.message);
    }
}
