import { BaseEntity } from './../../shared';
import { UserProfile } from '../user-profile/user-profile.model';

export class ProfileAccess implements BaseEntity {
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
        public profileId?: number,
        public profileIds?: number[],
        public active?: boolean
    ) {
        this.leaf = false;
        this.active = true;
        this.profileIds = [];
    }
}

export class EntityProfileAccess implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public profileAccesss?: ProfileAccess[],
    ) {
        this.profileAccesss = [];
    }
} 

export class UserAccessWork implements BaseEntity {
    constructor(
        public id?: number,
        public addFlag?: boolean,
        public profiles?: UserProfile[],
        public entityProfileAccesss?: EntityProfileAccess[],
    ) {
        this.addFlag = true;
        this.profiles = [];
        this.entityProfileAccesss = [];
    }
}
