import { BaseEntity } from './../../shared';
export class Rules implements BaseEntity {
    constructor(
        public id?: number,
        public ruleNumber?: number,
        public code?: string,
        public label?: string,
    ) {
    }
}
