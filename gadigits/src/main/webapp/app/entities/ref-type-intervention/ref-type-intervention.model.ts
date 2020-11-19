import { BaseEntity } from './../../shared';

export class RefTypeIntervention implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public grilles?: BaseEntity[],
        public detailMos?: BaseEntity[],
    ) {
    }
}
