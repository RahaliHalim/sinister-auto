import { BaseEntity } from '../../shared';

export class UserPartnerMode implements BaseEntity{
    constructor(
        public id?: number,
        public partnerId?: number,
        public modeId?: number,
        public userExtraId?: number,
        public courtierId?: number
    ) { }
}