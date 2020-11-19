import { OnInit, Component } from "@angular/core";
import { CalculationRules } from "./calculation-rules.model";
import { CalculationRulesService } from "./calculation-rules.service";
import { Subject } from "rxjs";
import { GaDatatable } from "../../constants/app.constants";
import { Principal } from "../../shared/auth/principal.service";
import { UserExtraService } from "../user-extra/user-extra.service";
import { PermissionAccess } from "../user-extra/permission-access.model";


@Component({
    selector: "jhi-calculation-rules",
    templateUrl: "./calculation-rules.component.html"
})
export class CalculationRulesComponent implements OnInit{

    partnerRules: CalculationRules[] = [];
    dtTrigger: Subject<CalculationRules> = new Subject();
    permissionToAccess: PermissionAccess = new PermissionAccess();
    dtOptions: any = {};
    currentAccount: any;
    constructor( 
        private calculationRulesService: CalculationRulesService,
        private userExtraService: UserExtraService,
        private principal: Principal,

    )
    {}
   ngOnInit(){
        this.dtOptions = GaDatatable.defaultDtOptions;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.findFunctionnalityEntityByUser(93, this.currentAccount.id).subscribe(res => {
                this.permissionToAccess = res;
            });
        });
    }
    // Get all partner rules
    loadAll() {
    this.calculationRulesService.query().subscribe((res) => {
        this.partnerRules = res.json;
        this.dtTrigger.next();
    });
    
    }

}