import { BaseEntity } from './../../shared';

export class NaturePanne implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
