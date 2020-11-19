import { BaseEntity } from '../../shared';
import { DetailsPieces } from '../details-pieces';


export class QuotationMP implements BaseEntity {
    constructor(   
        public id?: number,
        public creationDate?: any,
        public stampDuty?: number,
        public ttcAmount?: number,
        public htAmount?: number,
        public repairableVehicle?: boolean,
        public concordanceReport?: boolean,
        public preliminaryReport?: boolean,
        public immobilizedVehicle?: boolean,
        public conditionVehicle?: string,
        public kilometer?: number,
      
        public priceNewVehicle?: number,
        public marketValue?: number,
        public expertiseDate?: any,
        public comment?: string,
        public sinisterPecId?: number,
        public statusId?: number,
        public listPieces?: DetailsPieces[],
       
        public totalMo?: number,
        public estimateJour?: number,
        public heureMO?: number,
        public confirmationDecisionQuote?: boolean,
        public confirmationDecisionQuoteDate?: any,
        public originalQuotationId?: number,
        public expertDecision?: string,
        public avisExpertDate?: any,
        public revueDate?: any,
        public verificationDevisValidationDate?: any,
        public verificationDevisRectificationDate?: any,
        public confirmationDevisDate?: any,
        public miseAJourDevisDate?: any,
        public confirmationDevisComplementaireDate?: any,

    ) {
        this.confirmationDecisionQuote = false;
        this.stampDuty= 0;
        this.listPieces = [];
    }
}
