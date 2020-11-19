import { BaseEntity } from "../../shared";

export class Contrat implements BaseEntity {
    constructor(
        public id?: number,
        public nomAgentAssurance?: String,
        public usageLabel?: String,
        public zone?: String,
        public zoneId? : number,
        public packId?: number,
        public naturePack? : String,
        public companyName?: String,
        public nbrePack? : String,
        public companyId? : number



    ) { }
}