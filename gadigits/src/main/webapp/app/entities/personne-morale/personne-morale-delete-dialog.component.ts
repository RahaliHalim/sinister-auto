import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonneMorale } from './personne-morale.model';
import { PersonneMoralePopupService } from './personne-morale-popup.service';
import { PersonneMoraleService } from './personne-morale.service';

@Component({
    selector: 'jhi-personne-morale-delete-dialog',
    templateUrl: './personne-morale-delete-dialog.component.html'
})
export class PersonneMoraleDeleteDialogComponent {

    personneMorale: PersonneMorale;

    constructor(
        private personneMoraleService: PersonneMoraleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personneMoraleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personneMoraleListModification',
                content: 'Deleted an personneMorale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personne-morale-delete-popup',
    template: ''
})
export class PersonneMoraleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personneMoralePopupService: PersonneMoralePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personneMoralePopupService
                .open(PersonneMoraleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
