import { BaseEntity } from './../../shared';

export class StatusPec implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public hasReason?: boolean,
        public active?: boolean,
    ) {
        this.hasReason = false;
        this.active = false;
    }
}
