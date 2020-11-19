import { BaseEntity } from './../../shared';

export class ViewDetailQuotation implements BaseEntity {
    constructor(
        public id?: number,
        public receptionVehicule?: boolean,
        public vehicleReceiptDate?: any,
        public lightShock?: boolean,
        public disassemblyRequest?: boolean,
        public marketValue?: number,
        public newValueVehicle?: number,
        public numeroChassis?: string,
        public puissance?: number,
        public immatriculationVehicule?: string,
        public usageId?: number,
        public marqueLibelle?: string,
        public modeleLibelle?: string,
        public fullName?: string,
        public raisonSociale?: string,
        public declarationDate?: any,
        public primaryQuotationId?: number,
        public modeId?: number,
        public packid?: number,
        public companyReference?: string,
        public vehicleNumber?: number,
        public posGaId?: number,
        public governorateId?: number,
        public delegationId?: number,
        public marqueId?: number,
        public isGaEstimate?: boolean
        //public dcCapital?: number,
        //public bgCapital?: number,
        //public franchiseTypeNewValue?: string,
        //public dcCapitalFranchise?: number
        //franchiseTypeDcCapital
        //marketValueFranchise,
        //franchiseTypeMarketValue,
        //bgCapitalFranchise,
        //franchiseTypeBgCapital

    ) {
        
    }
}