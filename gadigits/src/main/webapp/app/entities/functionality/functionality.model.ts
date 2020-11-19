import { BaseEntity } from './../../shared';

export class Functionality implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
    ) {
    }
}
