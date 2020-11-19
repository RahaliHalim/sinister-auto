import { BaseEntity } from './../../shared';

export class UserAccess implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
        public translationCode?: string,
        public url?: string,
        public leaf?: boolean,
        public businessEntityId?: number,
        public functionalityId?: number,
        public elementMenuId?: number,
        public elementMenuLabel?: string,
        public userId?: number,
    ) {
        this.leaf = false;
    }
}
