import { BaseEntity } from './../../shared';

export class RefTypeContrat implements BaseEntity {
    constructor(
        public id?: number,
        public code?: number,
        public libelle?: string,
        public isActif?: boolean,
        public compagnies?: BaseEntity[],
    ) {
        this.isActif = false;
    }
}
