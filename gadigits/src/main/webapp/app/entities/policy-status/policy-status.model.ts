import { BaseEntity } from './../../shared';

export class PolicyStatus implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
