import { BaseEntity } from '../../shared';
import { DetailsPieces } from '../details-pieces';


export class Quotation implements BaseEntity {
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
        public motifNonConfirmeId?: number,
        public isComplementary?: boolean,
        public expertDecision?: string,
        public fromSignature?: boolean,
        public isConfirme?: boolean,
        public typeString?: string,
        public etatString?: string,
        public avisExpertDate?: any,
        public revueDate?: any,
        public verificationDevisValidationDate?: any,
        public verificationDevisRectificationDate?: any,
        public confirmationDevisDate?: any,
        public miseAJourDevisDate?: any,
        public confirmationDevisComplementaireDate?: any,
        public sinPecId?: number,
        public listMainO?: DetailsPieces[],
        public listIngredients?: DetailsPieces[],
        public listFourniture?: DetailsPieces[],
        public listItemsPieces?: DetailsPieces[],
        public pdfGenerationAvisTechnique?: boolean

    ) {
        this.confirmationDecisionQuote = true;
        this.stampDuty = 0;
        this.listPieces = [];
        this.listMainO = [];
        this.listIngredients = [];
        this.listFourniture = [];
        this.listItemsPieces = [];
        this.isComplementary = false;
        this.fromSignature = false;
        this.fromSignature = true;
    }
}
