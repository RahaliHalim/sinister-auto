import { BaseEntity } from './../../shared';

export class Reinsurer implements BaseEntity {
    constructor(
        public id?: number,
        public companyName?: string,
        public taxIdentificationNumber?: string,
        public tradeRegister?: string,
        public address?: string,
        public phone?: string,
    ) {
    }
}
