import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Subject } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { SinisterService } from '../sinister/sinister.service';
import { Sinister } from '../sinister/sinister.model';
import { GaDatatable } from '../../constants/app.constants';
import { ConfirmationDialogService, Principal, ResponseWrapper } from '../../shared';
import { UserExtraService, PermissionAccess } from '../user-extra';

@Component({
    selector: 'jhi-consultation-demand-pec',
    templateUrl: './consultation-demand-pec.component.html'

})
export class ConsultationDemandePecComponent implements OnInit, OnDestroy {
    sinister: Sinister;
    sinisterPecsBeingProcessed: SinisterPec[];
    currentAccount: any;
    eventSubscriber: Subscription;
    dtOptions: any = {};
    dtTrigger: Subject<SinisterPec> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    constructor(
        private sinisterPecService: SinisterPecService,
        private sinisterService: SinisterService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private userExtraService: UserExtraService
    ) {
        
    }
    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.registerChangeInSinisterPecs();
    }
    loadAll() {

        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(85, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
                this.sinisterPecService.queryConsulterDemandePec(this.currentAccount.id).subscribe((res) => {
                    this.sinisterPecsBeingProcessed = res.json;
                    this.sinisterPecsBeingProcessed.forEach(sinPec => {
                        const opdate = new Date(sinPec.declarationDate);
                        sinPec.declarationDate = (opdate.getFullYear() + '-' + ((opdate.getMonth() + 1)) + '-' + opdate.getDate() + ' ' +opdate.getHours() + ':' + opdate.getMinutes()+ ':' + opdate.getSeconds());
                     
                    });
                    this.dtTrigger.next();   
            });
            
        });
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
      
      leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
              + String(val)).slice(String(val).length);
      }
    registerChangeInSinisterPecs() {
        this.eventSubscriber = this.eventManager.subscribe('sinisterPecListModification', (response) => this.loadAll());
    }
}
