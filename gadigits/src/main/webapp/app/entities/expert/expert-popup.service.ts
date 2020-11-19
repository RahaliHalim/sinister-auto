import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Expert } from './expert.model';
import { ExpertService } from './expert.service';

@Injectable()
export class ExpertPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private expertService: ExpertService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.expertService.find(id).subscribe((expert) => {
                    if (expert.dateEffetConvention) {
                        expert.dateEffetConvention = {
                            year: expert.dateEffetConvention.getFullYear(),
                            month: expert.dateEffetConvention.getMonth() + 1,
                            day: expert.dateEffetConvention.getDate()
                        };
                    }
                    if (expert.dateFinConvention) {
                        expert.dateFinConvention = {
                            year: expert.dateFinConvention.getFullYear(),
                            month: expert.dateFinConvention.getMonth() + 1,
                            day: expert.dateFinConvention.getDate()
                        };
                    }
                    if (expert.debutBlocage) {
                        expert.debutBlocage = {
                            year: expert.debutBlocage.getFullYear(),
                            month: expert.debutBlocage.getMonth() + 1,
                            day: expert.debutBlocage.getDate()
                        };
                    }
                    if (expert.finBlocage) {
                        expert.finBlocage = {
                            year: expert.finBlocage.getFullYear(),
                            month: expert.finBlocage.getMonth() + 1,
                            day: expert.finBlocage.getDate()
                        };
                    }
                    this.ngbModalRef = this.expertModalRef(component, expert);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.expertModalRef(component, new Expert());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    expertModalRef(component: Component, expert: Expert): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.expert = expert;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
