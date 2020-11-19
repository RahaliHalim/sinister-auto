import { BaseEntity } from './../../shared';

export class ViewExpert implements BaseEntity {
    constructor(
        public id?: number,
        public raisonSociale?: string,
        public fullName?: string,
        public email?: string,
        public activated?: boolean
    ) {
    }

}
