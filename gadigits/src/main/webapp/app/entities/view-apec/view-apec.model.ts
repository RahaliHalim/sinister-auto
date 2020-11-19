import { BaseEntity } from './../../shared';

export class ViewApec implements BaseEntity {
    constructor(
        public id?: number,
        public sinisterPecId?: number,
        public gaReference?: string,
        public companyName?: string,
        public companyId?: number,
        public agencyName?: string,
        public agencyId?: number,
        public contractNumber?: string,
        public contractId?: number,
        public immatriculation?: string,
        public modeLabel?: string,
        public declarationDate?: any,
        public incidentDate?: any,
        public stepPecId?: number,
        public etatApec?: number,
        public reparateurId?: number,
        public modeId?: number,
        public assignedToId?: number,
        public sinisterId?: number,
        public expertId?: number,
        public modificationPrix?: boolean,
        public activeModificationPrix?: boolean

        
    ) {
    }
}