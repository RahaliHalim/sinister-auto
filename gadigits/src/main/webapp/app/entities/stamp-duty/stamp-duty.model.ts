import { BaseEntity } from './../../shared';

export class StampDuty implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public startDate?: any,
        public endDate?: any,
        public active?: boolean,
    ) {
        this.active = false;
    }
}
