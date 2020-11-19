import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RefBareme } from './ref-bareme.model';
import { RefBaremeService } from './ref-bareme.service';
import { Http, Response } from '@angular/http';
import { JhiDateUtils } from 'ng-jhipster';
import { Observable, BehaviorSubject } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { DomSanitizer } from '@angular/platform-browser';




@Injectable()
export class RefBaremePopupDetailService {
    private ngbModalRef: NgbModalRef;
    private listbaremUrl = 'api/ref-baremes-without-croquis';
    private listbaremUrlWithCroquis = 'api/ref-baremes';

    private idBSource = new BehaviorSubject(0);
    currentmessage = this.idBSource.asObservable();

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private refBaremeService: RefBaremeService,
        private http: Http,
        private dateUtils: JhiDateUtils


    ) {
        this.ngbModalRef = null;
    }
    changeMessage(newID: number) {
        this.idBSource.next(newID);
    }

    listBareme(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.listbaremUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.debutValidite = this.dateUtils
            .convertLocalDateFromServer(entity.debutValidite);
        entity.finValidite = this.dateUtils
            .convertLocalDateFromServer(entity.finValidite);
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.refBaremeService.find(id).subscribe((refBareme) => {
                    this.ngbModalRef = this.refBaremeModalRef(component, refBareme);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.refBaremeModalRef(component, new RefBareme());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }


    refBaremeModalRef(component: Component, refBareme: RefBareme): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.refBareme = refBareme;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
