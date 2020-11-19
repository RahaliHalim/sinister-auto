import { BaseEntity } from './../../shared';

export class ServiceAssurance implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
    ) {
    }
}
