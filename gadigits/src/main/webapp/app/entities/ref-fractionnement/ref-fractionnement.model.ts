import { BaseEntity } from './../../shared';

export class RefFractionnement implements BaseEntity {
    constructor(
        public id?: number,
        public code?: number,
        public libelle?: string,
    ) {
    }
}
