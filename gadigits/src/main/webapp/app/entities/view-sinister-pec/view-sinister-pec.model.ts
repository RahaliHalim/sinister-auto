import { BaseEntity } from './../../shared';

export class ViewSinisterPec implements BaseEntity {
    constructor(
        public id?: number,
        public gaReference?: string,
        public companyReference?: string,
        public companyName?: string,
        public agencyName?: string,
        public contractNumber?: string,
        public immatriculation?: string,
        public modeLabel?: string,
        public declarationDate?: any,
        public incidentDate?: any,
        public decision?: string,
        public step?: number,
        public approvPec?: string,
        public expertId?: number,
        public reparateurId?: number,
        public userId?: number,
        public assignedToId?: number,
        public primaryQuotationId?: number,
        public immatriculationTier?: string,
        public changeModificationPrix?: boolean,
        public codeBareme?: number,
        public responsabiliteX?: number,
        public isSelected?: Boolean,
        public oldStep?: number,
        public contractId?: number,
        public creationDate?: any,
        public clotureDate?: any,
        public stepPec?: string,
        public posGaLabel?: string,
        public decisionIda?: string,
        public vehiculeId?: number,
        public agencyId?: number,
        public clientId?: number,
        public charge?: string,
        public expertRaisonSociale?: string,
        public reparateurRaisonSociale?: string

        
    ) {
    }
}
