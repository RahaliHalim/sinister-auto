import { BaseEntity } from './../../shared';
import { Authority } from '../authority/authority.model';

export class UserCellule implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public userLogin?: string,
        public userActivated?: boolean,
        public userLangKey?: string,
        public userCreatedBy?: string,
        public userCreatedDate?: any,
        public userLastModifiedBy?: string,
        public userLastModifiedDate?: any,
        public celluleId?: number,
        public celluleName?: string,
        public authorities?: Authority[],

    ) {
    }
}
