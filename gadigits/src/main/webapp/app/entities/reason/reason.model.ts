import { BaseEntity } from './../../shared';

const enum ResponsibleEnum {
    'ga',
    'agent',
    'company'
}

export class Reason implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public description?: string,
        public responsible?: ResponsibleEnum,
        public active?: boolean,
        public stepId?: number,
    ) {
        this.active = true;
    }
}
