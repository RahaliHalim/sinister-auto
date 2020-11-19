import { BaseEntity } from './../../shared';

export class UserProfile implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
