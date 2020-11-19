import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GroupDetailsService, Group, GroupService } from '../../shared';
import { GroupModalService } from './group-modal.service';

@Component({
    selector: 'jhi-group-mgmt-delete-dialog',
    templateUrl: './group-delete.component.html'
})
export class GroupMgmtDeleteComponent {

    group: Group;

    constructor(
        private groupDetailsService: GroupDetailsService,
        private groupService: GroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id) {
        
        this.groupService.deleteById(id).subscribe((response) => {
            this.eventManager.broadcast({ name: 'groupListModification',
                content: 'Deleted a group'});
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-delete-dialog',
    template: ''
})
export class GroupDeleteComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groupModalService: GroupModalService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.groupModalService.open(GroupMgmtDeleteComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
