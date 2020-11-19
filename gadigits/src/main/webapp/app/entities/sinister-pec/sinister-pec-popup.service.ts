import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SinisterPec } from './sinister-pec.model';
import { SinisterPecService } from './sinister-pec.service';
import { Sinister } from '../sinister/sinister.model';
import { ContratAssurance } from '../contrat-assurance';
import { Attachment } from '../attachments';
import { VehiculeAssure } from '../vehicule-assure';
import { VehiclePiece } from '../vehicle-piece/vehicle-piece.model';
import { ViewSinisterPec } from '../view-sinister-pec';

@Injectable()
export class SinisterPecPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sinisterPecService: SinisterPecService

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
                this.sinisterPecService.find(id).subscribe((sinisterPec) => {
                    if (sinisterPec.declarationDate) {
                        sinisterPec.declarationDate = {
                            year: sinisterPec.declarationDate.getFullYear(),
                            month: sinisterPec.declarationDate.getMonth() + 1,
                            day: sinisterPec.declarationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.sinisterPecModalRef(component, sinisterPec);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sinisterPecModalRef(component, new SinisterPec());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sinisterPecModalRef(component: Component, sinisterPec: SinisterPec): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sinisterPec = sinisterPec;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }

    openDecisionPecModal(component: Component, pec?: SinisterPec): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, { size: 'sm', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sinisterPec = pec;
        return this.ngbModalRef;
    }

    openDecisionSinisterModal(component: Component, pec?: Sinister, contract?: ContratAssurance, constAttachment?: Attachment, carteGriseAttachment?: Attachment, acteCessionAttachment?: Attachment, constatFiles?: File, carteGriseFiles?: File, acteCessionFiles?: File, listModeByCnvByUser?: any[], vehicule?: VehiculeAssure, updateConstat?: boolean, updateCarteGrise?: boolean, updateActeDeCession?: boolean, piecesAttachments?: Attachment[], updatePieceJointe1?: boolean): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, { size: 'sm', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sinister = pec;
        this.ngbModalRef.componentInstance.contratAssurance = contract;
        this.ngbModalRef.componentInstance.constatAttachment = constAttachment;
        this.ngbModalRef.componentInstance.carteGriseAttachment = carteGriseAttachment;
        this.ngbModalRef.componentInstance.acteCessionAttachment = acteCessionAttachment;
        this.ngbModalRef.componentInstance.constatFiles = constatFiles;
        this.ngbModalRef.componentInstance.carteGriseFiles = carteGriseFiles;
        this.ngbModalRef.componentInstance.acteCessionFiles = acteCessionFiles;
        this.ngbModalRef.componentInstance.listModeByCnvByUser = listModeByCnvByUser;
        this.ngbModalRef.componentInstance.vehicule = vehicule;
        this.ngbModalRef.componentInstance.updateConstat = updateConstat;
        this.ngbModalRef.componentInstance.updateCarteGrise = updateCarteGrise;
        this.ngbModalRef.componentInstance.updateActeDeCession = updateActeDeCession;
        this.ngbModalRef.componentInstance.piecesAttachments = piecesAttachments;
        this.ngbModalRef.componentInstance.updatePieceJointe1 = updatePieceJointe1;
        return this.ngbModalRef;
    }

    openBonSortieModal(component: Component, pec?: ViewSinisterPec): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sinisterPec = pec;
        return this.ngbModalRef;
    }

    openHistoryModal(component: Component): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, {size: 'lg' , backdrop: 'static' });
        return this.ngbModalRef;
    }
    openHistoryModalSinisterPrestation(component: Component, idSinisterPrestation: number, entityNamePrestation: string): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, {size: 'lg' , windowClass: 'modal-xl', backdrop: 'static' });
        this.ngbModalRef.componentInstance.idSinisterPrestation = idSinisterPrestation;
        this.ngbModalRef.componentInstance.entityNamePrestation = entityNamePrestation;
        return this.ngbModalRef;
    }

    openHistoryModalSinisterPec(component: Component, entityName: string, entityId: number): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, {size: 'lg' , windowClass: 'modal-xxl', backdrop: 'static' });
        this.ngbModalRef.componentInstance.entityName = entityName;
        this.ngbModalRef.componentInstance.entityId = entityId;
        return this.ngbModalRef;
    }

    openHistoryDetailsSinisterPec(component: Component, idSinisterPec: number): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, { size: 'lg', windowClass: 'modal-xxl', backdrop: 'static' });
        this.ngbModalRef.componentInstance.idSinisterPec = idSinisterPec;
        return this.ngbModalRef;
    }
    openHistoryModalSinister(component: Component, entityName: string, entityId: number): NgbModalRef {
        
        this.ngbModalRef = this.modalService.open(component, {size: 'lg' , backdrop: 'static' });
        this.ngbModalRef.componentInstance.entityName = entityName;
        this.ngbModalRef.componentInstance.entityId = entityId;
        return this.ngbModalRef;
    }
    
    openAddPieceModal(component: Component, vehiclePiece: VehiclePiece): NgbModalRef {
        this.ngbModalRef = this.modalService.open(component, { size: 'lg' , windowClass: 'modal-md', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vehiclePiece = vehiclePiece;
        return this.ngbModalRef;
    }

}
