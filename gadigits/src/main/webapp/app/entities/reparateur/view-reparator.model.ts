import { BaseEntity } from './../../shared';

export class ViewReparator implements BaseEntity {
    constructor(
        public id?: number,
        public raisonSociale?: String,
        public fullName?: String,
        public phone?: String,
        public email?: String,
        public governorateLabel?: String,
        public delegationLabel?: String,
        public activated?: boolean
    ) {
    }
}
