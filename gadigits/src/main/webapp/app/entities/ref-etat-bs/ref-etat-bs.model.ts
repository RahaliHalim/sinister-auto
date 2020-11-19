import { BaseEntity } from './../../shared';

export class RefEtatBs implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public bonSorties?: BaseEntity[],
    ) {
    }
}
