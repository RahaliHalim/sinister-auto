import { BaseEntity } from './../../shared';


export class VehiculeContrat implements BaseEntity {
    constructor(
        public id?: number,
        public immatriculation?: string,
        public assureId?: number,
        public contratId?: number,
        public assurePrenom?: string,
        public assureRaison?: string,
        public assureFullName?: string,
        public numeroContrat?: string,
        public debutValidite?: any,
        public finValidite?: any,
        public nomCompagnie?: string,
        public nomAgence?: string
    ) {
    }
}
