import { BaseEntity } from './../../shared';

export class SysActionUtilisateur implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public journals?: BaseEntity[],
        public motifs?: BaseEntity[],
    ) {
    }
}
