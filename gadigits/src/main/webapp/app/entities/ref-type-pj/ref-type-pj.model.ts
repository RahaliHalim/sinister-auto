import { BaseEntity } from './../../shared';

export class RefTypePj implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
    ) {
    }
}
