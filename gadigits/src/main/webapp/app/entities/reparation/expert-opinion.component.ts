import { Component, OnInit,Pipe, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs/Rx';
import { JhiAlertService } from 'ng-jhipster';
import { Principal, ResponseWrapper } from '../../shared';
import { GaDatatable } from '../../constants/app.constants';
import { Response } from '@angular/http';
import { ViewSinisterPec, ViewSinisterPecService } from '../view-sinister-pec';
import { UserExtraService, PermissionAccess} from '../user-extra';
import { ViewChild, ElementRef } from '@angular/core';

@Component( {
    selector: 'jhi-expert-opinion',
    templateUrl: './expert-opinion.component.html'
} )
export class ExpertOpinionComponent implements OnInit, OnDestroy {
    currentAccount: any;
    error: any;
    success: any;
    sinisterPecsBeingProcessed: ViewSinisterPec[];
    dtOptions: any = {};
    dtTrigger: Subject<ViewSinisterPec> = new Subject();
    permissionToAccess : PermissionAccess = new PermissionAccess();

    constructor(
        private alertService: JhiAlertService,
        private principal: Principal,
        private viewSinisterPecService: ViewSinisterPecService,
        private router: Router,
        private userExtraService : UserExtraService
    ) {
    }

    loadAll() {
        this.viewSinisterPecService.getAllForExpertOpinion(this.currentAccount.id).subscribe(
            ( res: ResponseWrapper ) => {
                this.sinisterPecsBeingProcessed = res.json;       
                this.dtTrigger.next(); // Actualize datatables
            }, ( res: ResponseWrapper ) => this.onError( res.json )
        );
    }
    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.dtOptions = GaDatatable.defaultDtOptions;
            this.userExtraService.findFunctionnalityEntityByUser(48,this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
                this.loadAll();    
         });
                
        });       
    }

    ngOnDestroy() {
    }

    trackId( index: number, item: ViewSinisterPec ) {
        return item.id;
    }
    
    private onError( error ) {
        this.alertService.error( error.message, null, null );
    }

    expertOpinion(id: number) {
        this.router.navigate(['/expert-opinion/', id]);
    }
}


