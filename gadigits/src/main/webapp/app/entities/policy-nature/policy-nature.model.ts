import { BaseEntity } from './../../shared';

export class PolicyNature implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
