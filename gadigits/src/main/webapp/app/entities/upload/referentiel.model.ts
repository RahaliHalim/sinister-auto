import { BaseEntity } from './../../shared';
export class Referentiel implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        
    ) {
    }
}