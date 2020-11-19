import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { FeatureService } from './feature.service';
import { FeatureModalService } from './feature-modal.service';
import { Features } from './feature.model';

@Component({
    selector: 'jhi-feature-mgmt-delete-dialog',
    templateUrl: './feature-delete.component.html'
})
export class FeatureMgmtDeleteComponent {

    feature: Features;
    constructor(
        private featureService: FeatureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id) {
       
        this.featureService.deleteById(id).subscribe((response) => {
            this.eventManager.broadcast({ name: 'featuresListModification',
                content: 'Deleted a fonctionalite'});
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-feature-delete-dialog',
    template: ''
})
export class FeatureDeleteComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private featureModalService: FeatureModalService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            
            this.modalRef = this.featureModalService.open(FeatureMgmtDeleteComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
