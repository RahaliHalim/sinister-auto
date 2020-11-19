import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SysActionUtilisateur } from './sys-action-utilisateur.model';
import { SysActionUtilisateurPopupService } from './sys-action-utilisateur-popup.service';
import { SysActionUtilisateurService } from './sys-action-utilisateur.service';

@Component({
    selector: 'jhi-sys-action-utilisateur-delete-dialog',
    templateUrl: './sys-action-utilisateur-delete-dialog.component.html'
})
export class SysActionUtilisateurDeleteDialogComponent {

    sysActionUtilisateur: SysActionUtilisateur;

    constructor(
        private sysActionUtilisateurService: SysActionUtilisateurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sysActionUtilisateurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sysActionUtilisateurListModification',
                content: 'Deleted an sysActionUtilisateur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sys-action-utilisateur-delete-popup',
    template: ''
})
export class SysActionUtilisateurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sysActionUtilisateurPopupService: SysActionUtilisateurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sysActionUtilisateurPopupService
                .open(SysActionUtilisateurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
