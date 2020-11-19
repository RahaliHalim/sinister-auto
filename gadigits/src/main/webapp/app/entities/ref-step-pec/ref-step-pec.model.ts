import { BaseEntity } from './../../shared';

export class RefStepPec implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
        public num?: number
    ) {
    }
}
