import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { GroupDetails, GroupDetailsService, GroupService, Group } from '../../shared';

@Injectable()
export class GroupModalService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private groupDetailsService: GroupDetailsService,
        private groupService: GroupService
    ) {}

    open(component: Component, id?: number): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.groupService.find(id).subscribe((group) => this.groupModalRef(component, group));
        } else {
            return this.groupModalRef(component, new Group());
        }
    }

    groupModalRef(component: Component, group: Group): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.group = group;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
