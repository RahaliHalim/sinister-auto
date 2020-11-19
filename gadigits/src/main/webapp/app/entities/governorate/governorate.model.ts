
import { BaseEntity } from './../../shared';

export class Governorate implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public latitude?: number,
        public longitude?: number,
        public regionId?: number,
        public addedGageo?: boolean,
    ) {
    }
}
