import { BaseEntity } from './../../shared';

export class Fournisseur implements BaseEntity {
    constructor(
        public id?: number,
        public remise?: number,
        public isBloque?: boolean,
        public personneMoraleId?: number,
    ) {
        this.isBloque = false;
    }
}
