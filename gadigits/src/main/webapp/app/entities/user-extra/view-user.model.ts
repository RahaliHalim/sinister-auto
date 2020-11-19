import { BaseEntity } from './../../shared';

export class ViewUser implements BaseEntity {
    constructor(
        public id?: number,
        public lastName?: string,
        public firstName?: string,
        public profileName?: string,
        public email?: string,
        public activated?: any
    ) {
    }
}
