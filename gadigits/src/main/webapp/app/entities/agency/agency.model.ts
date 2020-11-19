import { BaseEntity } from './../../shared';

export class Agency implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public nom?: string,
        public prenom?: string,
        public typeAgence?: number,
        public address?: string,
        public zipcode?: string,
        public phone?: string,
        public mobile?: string,
        public fax?: string,
        public email?: string,
        public deuxiemeMail?: string,
        public type?: number,
        public category?: number,
        public partnerId?: number,
        public partnerCompanyName?: string,
        public governorateId?: number,
        public genre?: number,
        public governorateLabel?: string,
        public delegationId?: number,
        public delegationLabel?: string,
        public regionId?: number,
        public regionLabel?: string,
    ) {
    }
}
