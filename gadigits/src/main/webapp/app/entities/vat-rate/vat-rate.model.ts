import { BaseEntity } from './../../shared';

export class VatRate implements BaseEntity {
    constructor(
        public id?: number,
        public vatRate?: number,
        public active?: boolean,
        public startDate?: any,
        public effectiveDate?: any,
    ) {
        this.active = true;
    }
}
