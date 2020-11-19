import { BaseEntity } from './../../shared';

export class Tiers implements BaseEntity {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public debutValidite?: any,
        public finValidite?: any,
        public compagnieId?: number,
        public compagnieNom?: String,
        public immatriculation?: string,
        public sinisterPecId?: number,
        public deleted?: boolean,
        public agenceId?: number,
        public agenceNom?: String,
        public agenceTier?: String,
        public optionalAgencyName?: String,
        public raisonSocial?: String,
        public numeroContrat?: String,
        public responsible?: boolean,
        public fullName?: String

    ) {
        this.deleted = false;
        this.responsible = false;
    }
}
