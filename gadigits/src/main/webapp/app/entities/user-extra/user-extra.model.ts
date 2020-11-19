import { BaseEntity } from './../../shared';
import { UserAccess } from './../user-access/user-access.model';
import { UserAccessWork } from './../profile-access';
import { UserPartnerMode } from '../user-partner-mode/user-partner-mode.model';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public firstName?: string,
        public lastName?: string,
        public personId?: number,
        public userId?: number,
        public userBossId?: number,
        public profileId?: number,
        public profileName?: string,
        public email?: string,
        public activated?: any,
        public accesses?: UserAccess[],
        public userAccessWork?: UserAccessWork,
        public userPartnerModes?: UserPartnerMode[]
    ) {
    }
}
