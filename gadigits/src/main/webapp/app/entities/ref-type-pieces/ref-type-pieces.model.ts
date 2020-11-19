import { BaseEntity } from './../../shared';

export class RefTypePieces implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
    ) {
    }
}
