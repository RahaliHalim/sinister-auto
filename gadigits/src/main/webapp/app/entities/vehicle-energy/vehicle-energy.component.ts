import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { Subject } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { ConfirmationDialogService, Principal, ResponseWrapper, AlertUtil } from '../../shared';
import { GaDatatable, Global } from './../../constants/app.constants';
import { VehicleEnergy } from './vehicle-energy.model';
import { VehicleEnergyService } from './vehicle-energy.service';
import { DataTableDirective } from 'angular-datatables';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HistoryPopupDetail } from '../history/history-popup-detail';
import { HistoryPopupService } from '../history';

@Component({
    selector: 'jhi-vehicle-energy',
    templateUrl: './vehicle-energy.component.html'
})
export class VehicleEnergyComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    vehicleEnergies: VehicleEnergy[];
    vehicleEnergy: VehicleEnergy = new VehicleEnergy();
    vehicleEnergyVehicleCheck: VehicleEnergy;
    isSaving: boolean;
    currentAccount: any;
    textPattern = Global.textPattern;
    private ngbModalRef: NgbModalRef;
    
    dtOptions: any = {};
    dtTrigger: Subject<VehicleEnergy> = new Subject();
    existLabel = true;
    permissionToAccess : PermissionAccess = new PermissionAccess();

    constructor(
        private vehicleEnergyService: VehicleEnergyService,
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
        this.vehicleEnergyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.vehicleEnergies = res.json;
                this.rerender();
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    trimLabel() {
        this.vehicleEnergy.label = this.vehicleEnergy.label.trim();
    }

    /**
     * Find vehicle energy by label
     * @param label 
     */
    findEnergyVehicleByLabel(label: string) {
        this.existLabel = false;
        if(label != null && label != undefined && label != ''){
          this.vehicleEnergyService.findByLabel(label).subscribe(vehicleEnergy => {
              this.vehicleEnergyVehicleCheck = vehicleEnergy;
              if(this.vehicleEnergyVehicleCheck.id != null && this.vehicleEnergyVehicleCheck.id != undefined){
                  if(this.vehicleEnergyVehicleCheck.label.replace(/\s/g, "").toLowerCase() == label.replace(/\s/g, "").toLowerCase()){
                    
                  this.existLabel = true;
                  this.alertUtil.addError("auxiliumApp.vehicleEnergy.exist");
                  }
              }else{
                  this.existLabel = false;
              }
          });
        }
      }

      selectHistory(id) {
        console.log("premier logggggg"+id);
         this.ngbModalRef = this.historyPopupService.openHist(HistoryPopupDetail as Component, id,"VehicleEnergie");
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

    save() {
        this.confirmationDialogService.confirm('Confirmation', 'Êtes-vous sûrs de vouloir enregistrer ?', 'Oui', 'Non', 'lg')
            .then(confirmed => {
                if (confirmed) {
                    this.isSaving = true;
                    if (this.vehicleEnergy.id !== undefined) {
                        this.subscribeToSaveResponse(
                            this.vehicleEnergyService.update(
                                this.vehicleEnergy
                            )
                        );
                    } else {
                        this.subscribeToSaveResponse(
                            this.vehicleEnergyService.create(
                                this.vehicleEnergy
                            )
                        );
                    }
                }
            })
    }

    edit(id: number) {
        this.vehicleEnergyService.find(id).subscribe(vehicleEnergy => {
            this.vehicleEnergy = vehicleEnergy;
        });
    }

    cancel() {
        this.vehicleEnergy = new VehicleEnergy();
    }

    delete(id) {
        this.confirmationDialogService.confirm( 'Confirmation', 'Êtes-vous sûrs de vouloir supprimer cet enregistrement ?', 'Oui', 'Non', 'lg' )
            .then(( confirmed ) => {
                if(confirmed) {
                    this.vehicleEnergyService.delete(id).subscribe((response) => {

                        console.log( 'User confirmed delete:', id );

                        // Refresh refpricing list
                        this.loadAll();
                    });
                }
            })
    }

    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(70,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }

    ngOnDestroy() {
    }

    trackId(index: number, item: VehicleEnergy) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<VehicleEnergy>) {
        result.subscribe(
            (res: VehicleEnergy) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: VehicleEnergy) {
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
