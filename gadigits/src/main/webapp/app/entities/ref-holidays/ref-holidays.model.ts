import { BaseEntity } from '../../shared';

export class RefHolidays implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public date?: any,
    ) {
    }
}
