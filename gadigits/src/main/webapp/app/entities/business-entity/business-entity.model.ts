import { BaseEntity } from './../../shared';

export class BusinessEntity implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public code?: string,
        public functionalities?: BaseEntity[],
    ) {
    }
}
