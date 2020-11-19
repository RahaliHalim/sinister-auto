import { BaseEntity } from './../../shared';

export class ElementMenu implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
