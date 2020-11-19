import { Component, OnInit, OnDestroy } from '@angular/core';
import {  FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Camion } from './camion.model';
import { CamionPopupService } from './camion-popup.service';
import { CamionService } from './camion.service';
import { RefTypeServiceService } from '../ref-type-service/ref-type-service.service';
import { RefTypeService} from '../ref-type-service/ref-type-service.model';
import {  RefRemorqueurService } from '../ref-remorqueur';
import { ResponseWrapper } from '../../shared';
import 'rxjs/add/operator/map';
@Component({
    selector: 'jhi-camion-dialog',
    templateUrl: './camion-dialog.component.html'
})
export class CamionDialogComponent implements OnInit {
    camion: Camion= new Camion();
    isSaving: boolean;
    reftypeservice: RefTypeService;
    selected: any;
    serviceForm: FormGroup;
    idRmq:any;
    list : String = "";
    listCamions: Camion[];
    savedCamion: any;
    serviceSelects: string[];
    reftypeservicess: RefTypeService[];
    reftypeservices: RefTypeService[];
    SysAction: any;
    selectedMotifs: any;
    value: any;
    checked: any[] = [];
    dropdownList = [];
    selectedItems = [];
    dropdownSettings = {};
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private camionService: CamionService,
        private refRemorqueurService: RefRemorqueurService,
        private refTypeServiceService: RefTypeServiceService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute
    ) {
    }
    ngOnInit() {
        this.camion.refTugId = this.refRemorqueurService.id;
        this.isSaving = false;
        this.refTypeServiceService.query()
        .subscribe((res: ResponseWrapper) => {this.reftypeservices = res.json;this.SysAction =this.reftypeservices}, (res: ResponseWrapper) => this.onError(res.json));
        if ( this.camion.id) {
                this.camionService.find( this.camion.id).subscribe((subRes: Camion) => {
                    this.camion = subRes;
                }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                this.refTypeServiceService.query()
                 .subscribe((res: ResponseWrapper) => {this.reftypeservices = res.json;
                    const listeService = this.camion.serviceTypesStr;
                        const services = listeService.split(",");
                        console.log("ici sevices"+services);
                        var i = 0;
                        this.selectedItems = [{ item_id: 1, item_text: 'Mumbai' }];
                        services.forEach(service => {
                            this.reftypeservices.forEach(element => {
                                if (service == element.nom){
                                    this.selectedItems[i]=element;
                                    i=i+1;
                                }
                             });
                            });
                     });
      }
      this.dropdownSettings = {
        singleSelection: false,
        idField: 'id',
        textField: 'nom',
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        itemsShowLimit: 6,
        allowSearchFilter: true
      };
     }
     onItemSelect (item:any) {
        console.log(item);
      }
      onSelectAll (items: any) {
        console.log(items);
      }
    save() {
        this.isSaving = true;
        if (this.camion.id !== undefined) {
            for (let i = 0; i < this.selectedItems.length; i++) {
                this.list=this.list +(this.selectedItems[i].nom)+",";
            }
            this.camion.serviceTypesStr = '' ; //this. list;
            this.subscribeperToSaveResponse(
                this.camionService.update(this.camion));
                this.camionService.findByRefRemorqueur(this.camion.refTugId)
                .subscribe((res: ResponseWrapper) => { this.listCamions = res.json; });
                this.activeModal.dismiss(true);
        }
        else {
            for (let i = 0; i < this.selectedItems.length; i++) {
                this.list=this.list +(this.selectedItems[i].nom)+",";
            }
            this.camion.serviceTypesStr = ''; //this. list;
            this.subscribeperToSaveResponse(
            this.camionService.create(this.camion));
            this.camionService.findByRefRemorqueur(this.camion.refTugId)
            .subscribe((res: ResponseWrapper) => { this.listCamions = res.json; });
             this.activeModal.dismiss(true);
        }
    }
    private subscribeperToSaveResponse(result: Observable<Camion>) {
        result.subscribe((res: Camion) =>
            this.onPerSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    trackRefTypeServiceById(index: number, item: RefTypeService) {
        return item.id;
    }

    private onPerSaveSuccess(result:Camion) {
        this.eventManager.broadcast({ name: 'camionListModification', content: 'OK'});
        this.isSaving = false;
        this.camionService.findByRefRemorqueur(result.refTugId)
        .subscribe((res: ResponseWrapper) => { this.listCamions = res.json; });

    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-camion-popup',
    template: ''
})
export class CamionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private camionPopupService: CamionPopupService
    ) {

    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.camionPopupService
                    .open(CamionDialogComponent as Component, params['id']);
            }
            else {
                this.camionPopupService
                    .open(CamionDialogComponent as Component);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
