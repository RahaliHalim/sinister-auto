import { BaseEntity } from './../../shared';

export class Grille implements BaseEntity {
    constructor(
        public id?: number,
        public th?: number,
        public remise?: number,
        public tva?: number,
        public reparateurs?: BaseEntity[],
        public typeInters?: BaseEntity[],
    ) {
    }
}
