import { BaseEntity } from './../../shared';

export class Lien implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public url?: string,
        public cellules?: BaseEntity[],
    ) {
    }
}
