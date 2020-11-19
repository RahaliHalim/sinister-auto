import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Global } from './../../constants/app.constants';
import { Principal, ResponseWrapper } from '../../shared';
import { VehiclePiece } from '../vehicle-piece/vehicle-piece.model';
import { VehiclePieceService } from '../vehicle-piece/vehicle-piece.service';
import { Observable } from 'rxjs/Rx';
import { UserExtra, UserExtraService } from '../user-extra';

@Component({
    selector: 'jhi-quotation-piece-add',
    templateUrl: './quotation-piece-add.component.html'
})
export class QuotationPieceAddComponent implements OnInit, OnDestroy {

    @Input() vehiclePiece: VehiclePiece;
    vehiclePieceExt: VehiclePiece;
    textPattern = Global.textPattern;
    currentAccount: any;
    userExtra: UserExtra;
    error: any;
    success: any;
    isNewVP = false;
    pieceExist = false;
    isReparator = false;
    constructor(
        private vehiclePieceService: VehiclePieceService,
        private alertService: JhiAlertService,
        public activeModal: NgbActiveModal,
        private userExtraService: UserExtraService,
        private principal: Principal,
    ) {
    }

    /**
     * Init sinister list screen
     */
    ngOnInit() {
        console.log(this.isReparator);
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.userExtraService.finPersonneByUser(this.currentAccount.id).subscribe((res) => {
                this.userExtra = res;
                if (res !== null && res !== undefined && res.profileId === 28) {
                    this.isReparator = true;
                    this.vehiclePiece.id = null;
                }
            });
        });
    }

    trimLabel() {
        this.vehiclePiece.label = this.vehiclePiece.label.trim();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
    }

    save() {
        this.pieceExist = false;
        this.vehiclePieceExt = undefined;
        console.log('______________________________________________1');
        
        this.vehiclePieceService.getPieceByReference(this.vehiclePiece.reference, this.vehiclePiece.typeId).subscribe((res: VehiclePiece) => {
            if (res != null && res != undefined && res.id !== this.vehiclePiece.id) {
                this.pieceExist = true;
                this.vehiclePieceExt = res;
            } else {
                if (this.isReparator) {
                    if (this.vehiclePiece.id !== undefined && this.vehiclePiece.id !== null) {
                        this.activeModal.close(this.vehiclePiece);
                    } else {
                        if(this.vehiclePiece.code == null || this.vehiclePiece.code == undefined) {this.vehiclePiece.code = this.vehiclePiece.reference;}
                            this.isNewVP= true;
                        this.subscribeToSaveResponse(
                                this.vehiclePieceService.create(
                                    this.vehiclePiece
                                )
                            );
                    }
                } else {
                    if (this.vehiclePiece.id !== undefined && this.vehiclePiece.id !== null) {
                        if(this.vehiclePiece.code == null || this.vehiclePiece.code == undefined) {this.vehiclePiece.code = this.vehiclePiece.reference;}
                        this.subscribeToSaveResponse(
                            this.vehiclePieceService.update(
                                this.vehiclePiece
                            )
                        );
                    } else {
                        if(this.vehiclePiece.code == null || this.vehiclePiece.code == undefined) {this.vehiclePiece.code = this.vehiclePiece.reference;}
                        this.isNewVP= true;

                        this.subscribeToSaveResponse(
                            this.vehiclePieceService.create(
                                this.vehiclePiece
                            )
                        );
                    }
                }
            }
        }, (error) => {
            console.log('______________________________________________1-error');
            if (this.isReparator) {
                if (this.vehiclePiece.id !== undefined && this.vehiclePiece.id !== null) {
                    this.activeModal.close(this.vehiclePiece);
                } else {
                    if(this.vehiclePiece.code == null || this.vehiclePiece.code == undefined) {this.vehiclePiece.code = this.vehiclePiece.reference;}
                    this.isNewVP= true;

                    this.subscribeToSaveResponse(
                        this.vehiclePieceService.create(
                            this.vehiclePiece
                        )
                    );
                }
            } else {
                if (this.vehiclePiece.id !== undefined && this.vehiclePiece.id !== null) {
                    if(this.vehiclePiece.code == null || this.vehiclePiece.code == undefined) {this.vehiclePiece.code = this.vehiclePiece.reference;}
                    this.subscribeToSaveResponse(
                        this.vehiclePieceService.update(
                            this.vehiclePiece
                        )
                    );
                } else {
                    if(this.vehiclePiece.code == null || this.vehiclePiece.code == undefined) {this.vehiclePiece.code = this.vehiclePiece.reference;}
                    this.isNewVP= true;

                    this.subscribeToSaveResponse(
                        this.vehiclePieceService.create(
                            this.vehiclePiece
                        )
                    );
                }
            }
        });
    }

    useIt() {
        this.activeModal.close(this.vehiclePieceExt);
    }

    private subscribeToSaveResponse(result: Observable<VehiclePiece>) {
        result.subscribe(
            (res: VehiclePiece) => this.onSaveSuccess(res),
            (res: Response) => this.onSaveError(res)
        );
    }

    dateAsYYYYMMDDHHNNSS(date): string {
        return date.getFullYear() + this.leftpad(date.getMonth() + 1, 2)
            + this.leftpad(date.getDate(), 2)
            + this.leftpad(date.getHours(), 2)
            + this.leftpad(date.getMinutes(), 2)
            + this.leftpad(date.getSeconds(), 2);
    }

    leftpad(val, resultLength = 2, leftpadChar = '0'): string {
        return (String(leftpadChar).repeat(resultLength)
            + String(val)).slice(String(val).length);
    }

    private onSaveSuccess(result: VehiclePiece) {
        if(this.isNewVP == true){
            result.isNew= true;
        }
        this.activeModal.close(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }

}
