import { BaseEntity } from './../../shared';

export class Step implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
