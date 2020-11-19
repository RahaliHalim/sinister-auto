import { BaseEntity } from './../../shared';

export class Tarif implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public majorationFerier?: number,
        public majorationNuit?: number,
        public tauxTarif?: number,
    ) {
    }
}
