import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { SinisterService } from '../sinister/sinister.service';
import { Sinister } from '../sinister/sinister.model';
import { GaDatatable } from '../../constants/app.constants';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { Principal, ResponseWrapper } from '../../shared';
@Component({
    selector: 'jhi-annuler-affectation-reparateur',
    templateUrl: './prestation-pec-annuler-affect-reparation.component.html'

})
export class PrestationPECAnnulerAffectReparationComponent implements OnInit, OnDestroy {
    sinister: Sinister;
    sinisterPecsBeingAffectReparateur: ViewSinisterPec[];
    currentAccount: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    permissionToAccess : PermissionAccess = new PermissionAccess();
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    constructor(
        private principal: Principal,
        private sinisterPecService: SinisterPecService,
        private viewSinisterPecService: ViewSinisterPecService,
        private eventManager: JhiEventManager,
        private userExtraService : UserExtraService
    ) {

    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.registerChangeInSinisterPecs();


        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(27,this.currentAccount.id).subscribe(res => {
             this.permissionToAccess = res;
           });
        });
    }
    loadAll() {
        this.viewSinisterPecService.getAllAcceptedAndHasReparator().subscribe((res) => {
            this.sinisterPecsBeingAffectReparateur = res.json;
            this.dtTrigger.next();
        });
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
  
 
    trackId(index: number, item: ViewSinisterPec) {
        return item.id;
    }
    
    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }
}

