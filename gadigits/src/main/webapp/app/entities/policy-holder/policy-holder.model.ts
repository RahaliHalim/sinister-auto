import { BaseEntity } from './../../shared';

export class PolicyHolder implements BaseEntity {
    constructor(
        public id?: number,
        public company?: boolean,
        public title?: string,
        public firstName?: string,
        public lastName?: string,
        public companyName?: string,
        public firstPhone?: string,
        public secondPhone?: string,
        public fax?: string,
        public firstEmail?: string,
        public secondEmail?: string,
        public address?: string,
        public identifier?: number,
        public vatRegistered?: boolean,
        public tradeRegister?: string,
        public creationDate?: any,
        public updateDate?: any,
        public governorateId?: number,
        public delegationId?: number,
        public creationUserId?: number,
        public updateUserId?: number,
    ) {
        this.company = false;
        this.vatRegistered = false;
    }
}
