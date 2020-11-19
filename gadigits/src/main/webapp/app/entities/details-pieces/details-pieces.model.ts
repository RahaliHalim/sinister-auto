import { BaseEntity } from './../../shared';
import { Quotation } from '../quotation/quotation.model';

const enum NaturePiece {
    'ORIGINE',
    'GENERIQUE',
    'CASSE'
}
const enum ObservationRepairer {
    'COMMANDE_FERME',
    'PIECE_NON_DISPONIBLE'
}

export class DetailsPieces implements BaseEntity {
    constructor(
        public id?: number,
        public quantite?: number,
        public nombreHeures?: number,
        public observationExpert?: number,
        public nombreMOEstime?: number,
        public reference?: number,
        public designationId?: number,
        public designationReference?: string,
        public designationSource?: string,

        public designation?: string,
        public designationCode?: string,
        public prixUnit?: number,
        public tva?: number,
        public vetuste?: number,
        public listobservationExpert?: any[],
        public fournisseurId?: number,
        public quotationId?: number,
        public totalHt?: number,
        public discount?: number,
        public amountDiscount?: number,
        public insuredParticipation ?: number,
        public HTVetuste?: number,
        public TTCVetuste?: number,
        public amountAfterDiscount?: number,
        public totalTtc?: number,
        public observation?: string,
        public naturePiece?: NaturePiece,
        public observationRepairer?: ObservationRepairer,
        public typeInterventionId?: number,
        public typeInterventionLibelle?: string,
        public isMo?: boolean,
        public modifiedLine?: number,
        public isModified?: boolean,
        public vatRateId?: number,
        public amountVat?: number,
        public quotation?: Quotation,
        public quotationMPId?: number,
        public scaledDown ?: boolean,
        public typePA?: string,
        public typeDesignationId?: number,
        public isComplementary?: boolean,
        public isNew?: boolean,

    ) {
        this.isMo = false;
        this.isModified = false;
        this.isNew = false;
        this.scaledDown = false;
        this.discount = 0;
        this.tva = 0;
        this.insuredParticipation = 0;
        this.prixUnit = 0;
        this.nombreHeures = 0;
        this.totalHt = 0;
        this.totalTtc = 0;
        this.amountVat = 0;
        this.amountDiscount = 0;
        this.amountAfterDiscount = 0;
        this.nombreMOEstime = 0;
        this.vetuste = 0;
        listobservationExpert = [];
    }
}
