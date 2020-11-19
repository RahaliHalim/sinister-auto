import { BaseEntity } from './../../shared';

export class VehiclePieceType implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
    ) {
    }
}
