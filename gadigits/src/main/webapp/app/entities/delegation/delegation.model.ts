import { BaseEntity } from './../../shared';

export class Delegation implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public latitude?: number,
        public longitude?: number,
        public governorateId?: number,
        public addedGageo?: boolean,
    ) {
    }
}
