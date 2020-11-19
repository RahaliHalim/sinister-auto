import { BaseEntity } from './../../shared';

export class Produit implements BaseEntity {
    constructor(
        public id?: number,
        public code?: number,
        public libelle?: string,
        public clients?: BaseEntity[],
    ) {
    }
}
