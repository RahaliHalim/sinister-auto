import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subject } from "rxjs";
import { Convention } from './convention.model';
import { ConventionService } from './convention.service';
import { Principal, ResponseWrapper, ConfirmationDialogService } from '../../shared';
import { RefPack, RefPackService } from './../ref-pack';
import { RefTypeServiceService } from './../ref-type-service/ref-type-service.service';
import { RefTypeService, ServiceTypePacks } from './../ref-type-service/ref-type-service.model';
import { GaDatatable } from './../../constants/app.constants';
import { BusinessEntity } from './../../constants/access.constants';
import { UserExtraService, PermissionAccess} from '../user-extra';

@Component({
    selector: "jhi-convention",
    templateUrl: "./convention.component.html"
})
export class ConventionComponent implements OnInit, OnDestroy {
    currentAccount: any;
    conventions: Convention[] = [];
    convention: Convention = new Convention();
    services: RefTypeService[];
    serviceTypePacks: ServiceTypePacks = new ServiceTypePacks();
    packs: RefPack[];
    conventionServiceTypePacks: ServiceTypePacks[];
    permissionToAccess : PermissionAccess = new PermissionAccess();

    error: any;
    success: any;
    generate = true;
    packslist: RefPack[] = [];
    pack = new RefPack();
    eventSubscriber: Subscription;

    dtOptions: any = {};
    dtTrigger: Subject<Convention> = new Subject();

    constructor(
        private conventionService: ConventionService,
        private alertService: JhiAlertService,
        private principal: Principal,
        private refTypeServiceService: RefTypeServiceService,
        private refPackService: RefPackService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private confirmationDialogService: ConfirmationDialogService,
        private userExtraService : UserExtraService
    ) {}

    loadAll() {
        this.conventionService.query().subscribe((res: ResponseWrapper) => {
            this.conventions = res.json;
            if (this.conventions && this.conventions.length > 0) {
                this.conventions.forEach((convention) => { // Iterate over conventions
                    convention.packsString = '';
                    if (convention.packs && convention.packs.length > 0) {
                        convention.packs.forEach((pack) => { // Iterate over packs
                            convention.packsString += pack.label + ', ';
                        });
                    }
                    if (convention.conventionAmendments && convention.conventionAmendments.length > 0) {
                        convention.conventionAmendments.forEach((conventionAmendments) => { // Iterate over packs
                            convention.packsString += conventionAmendments.refPack.label + ', ';
                        });
                    }

                    convention.packsString = convention.packsString.substring(0, convention.packsString.length - 2);
                });
            }
            this.dtTrigger.next();
    }, (res: ResponseWrapper) => this.onError(res.json));

    }


    ngOnInit() {
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.refTypeServiceService.query().subscribe((res: ResponseWrapper) => { this.services = res.json; },(res: ResponseWrapper) => this.onError(res.json));

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(BusinessEntity.CONVENTION, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
        this.registerChangeInConvention();

    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    findServiceTypePacks(id: number) {
        this.packs = [];
        this.refPackService.findByServiceType(id).subscribe((res: ResponseWrapper) => { this.packs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }



    edit(id) {
        this.conventionService.find(id).subscribe(convention => {
            this.convention = convention;
            if (convention.startDate) {
                this.convention.startDate = {
                    year: convention.startDate.getFullYear(),
                    month: convention.startDate.getMonth() + 1,
                    day: convention.startDate.getDate()
                };
            }
            if (convention.endDate) {
                this.convention.endDate = {
                    year: convention.endDate.getFullYear(),
                    month: convention.endDate.getMonth() + 1,
                    day: convention.endDate.getDate()
                };
            }
        });
    }

    cancel() {
        this.convention = new Convention();
    }

    public delete(id) {
        this.confirmationDialogService
            .confirm(
                "Confirmation",
                "Êtes-vous sûrs de vouloir supprimer cette convention ?","Oui", "Non",
                "lg")
            .then(confirmed => {
                console.log("User confirmed:", confirmed);
                if (confirmed) {
                    this.conventionService.delete(id).subscribe(response => {
                        console.log("User confirmed delete:", id);

                        // Refresh refpricing list
                        this.loadAll(); }); } })
            .catch(() =>
                console.log(
                    "User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)"
                )
            );
    }

    private subscribeToSaveResponse(result: Observable<Convention>) {
        result.subscribe(
            (res: Convention) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    private onSaveSuccess(result: Convention) {
        this.eventManager.broadcast({
            name: "conventionListModification",
            content: "OK"
        });
        this.loadAll();
        this.convention = new Convention();
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

    registerChangeInConvention() {
        this.eventSubscriber = this.eventManager.subscribe(
            "conventionListModification",
            response => this.loadAll()
        );
    }

    private onSuccess(data, headers) {
        this.conventions = data;

    }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
