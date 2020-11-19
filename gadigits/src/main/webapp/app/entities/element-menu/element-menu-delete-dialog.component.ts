import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ElementMenu } from './element-menu.model';
import { ElementMenuPopupService } from './element-menu-popup.service';
import { ElementMenuService } from './element-menu.service';

@Component({
    selector: 'jhi-element-menu-delete-dialog',
    templateUrl: './element-menu-delete-dialog.component.html'
})
export class ElementMenuDeleteDialogComponent {

    elementMenu: ElementMenu;

    constructor(
        private elementMenuService: ElementMenuService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.elementMenuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'elementMenuListModification',
                content: 'Deleted an elementMenu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-element-menu-delete-popup',
    template: ''
})
export class ElementMenuDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elementMenuPopupService: ElementMenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.elementMenuPopupService
                .open(ElementMenuDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
