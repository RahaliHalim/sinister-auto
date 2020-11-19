import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { AgentGeneral } from './agent-general.model';
import { AgentGeneralService } from './agent-general.service';

@Component({
    selector: 'jhi-agent-general-detail',
    templateUrl: './agent-general-detail.component.html'
})
export class AgentGeneralDetailComponent implements OnInit, OnDestroy {

    agentGeneral: AgentGeneral;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private agentGeneralService: AgentGeneralService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgentGenerals();
    }

    load(id) {
        this.agentGeneralService.find(id).subscribe((agentGeneral) => {
            this.agentGeneral = agentGeneral;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgentGenerals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agentGeneralListModification',
            (response) => this.load(this.agentGeneral.id)
        );
    }
}
