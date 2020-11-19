import { BaseEntity } from '../../shared';
import { DetailsPieces } from '../details-pieces';
import { Attachment } from '../attachments/attachment.model';


export class PrimaryQuotation implements BaseEntity {
    constructor(
        public id?: number,
        public creationDate?: any,
        public stampDuty?: number,
        public ttcAmount?: number,
        public htAmount?: number,
        public repairableVehicle?: boolean,
        public concordanceReport?: boolean,
        public preliminaryReport?: boolean,
        public conditionVehicle?: string,
        public immobilizedVehicle?: boolean,
        public kilometer?: number,
        public priceNewVehicle?: number,
        public marketValue?: number,
        public expertiseDate?: any,
        public comment?: string,
        public detailsPieces?: DetailsPieces[],
        public attachments?: Attachment[],
        public prestationId?: number,
        public statusId?: number,
        public heureMO?: number,
        public totalMo?: number,
        public estimateJour?: number,
        public confirmationDecisionQuote?: boolean,
        public confirmationDecisionQuoteDate?: any,
    ) {
        this.confirmationDecisionQuote = false;
        this.stampDuty= 0;
        this.detailsPieces = [];
    
      
    }
}
