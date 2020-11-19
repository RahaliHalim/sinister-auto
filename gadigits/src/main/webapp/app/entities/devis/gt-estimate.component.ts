import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Principal } from '../../shared';
import { SinisterPecService } from '../sinister-pec';
import { Router } from '@angular/router';



@Component({
    selector: 'jhi-gt-estimate',
    templateUrl: './gt-estimate.component.html'
})
export class GTEstimateComponent implements OnInit, OnDestroy {

    estimationId: any;
    sinPecId: any;
    constructor(
        private principal: Principal,
        private route: ActivatedRoute,
        private sinisterPecService: SinisterPecService,
        private router: Router

    ) {
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if (params['idEstimate']) {
                this.estimationId = params['idEstimate'];
                this.sinPecId = params['idSinPec'];
            }
            if (this.estimationId !== null && this.estimationId !== undefined && this.sinPecId !== null && this.sinPecId !== undefined) {
                this.sinisterPecService.saveEstimation(this.estimationId, this.sinPecId).subscribe((res) => {
                });
            }
        });
    }

    test() {
        this.router.navigate(['/devis-new/' + this.sinPecId]);
    }

    ngOnDestroy() {

    }

} 
