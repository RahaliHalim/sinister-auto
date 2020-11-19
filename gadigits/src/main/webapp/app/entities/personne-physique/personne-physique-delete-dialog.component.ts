import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonnePhysique } from './personne-physique.model';
import { PersonnePhysiquePopupService } from './personne-physique-popup.service';
import { PersonnePhysiqueService } from './personne-physique.service';

@Component({
    selector: 'jhi-personne-physique-delete-dialog',
    templateUrl: './personne-physique-delete-dialog.component.html'
})
export class PersonnePhysiqueDeleteDialogComponent {

    personnePhysique: PersonnePhysique;

    constructor(
        private personnePhysiqueService: PersonnePhysiqueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personnePhysiqueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personnePhysiqueListModification',
                content: 'Deleted an personnePhysique'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personne-physique-delete-popup',
    template: ''
})
export class PersonnePhysiqueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personnePhysiquePopupService: PersonnePhysiquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personnePhysiquePopupService
                .open(PersonnePhysiqueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
