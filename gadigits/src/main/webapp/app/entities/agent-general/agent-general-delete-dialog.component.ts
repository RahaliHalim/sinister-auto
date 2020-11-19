import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AgentGeneral } from './agent-general.model';
import { AgentGeneralPopupService } from './agent-general-popup.service';
import { AgentGeneralService } from './agent-general.service';

@Component({
    selector: 'jhi-agent-general-delete-dialog',
    templateUrl: './agent-general-delete-dialog.component.html'
})
export class AgentGeneralDeleteDialogComponent {

    agentGeneral: AgentGeneral;

    constructor(
        private agentGeneralService: AgentGeneralService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agentGeneralService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'agentGeneralListModification',
                content: 'Deleted an agentGeneral'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-agent-general-delete-popup',
    template: ''
})
export class AgentGeneralDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agentGeneralPopupService: AgentGeneralPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.agentGeneralPopupService
                .open(AgentGeneralDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
