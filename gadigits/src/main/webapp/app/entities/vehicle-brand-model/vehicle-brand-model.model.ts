import { BaseEntity } from './../../shared';

export class VehicleBrandModel implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public active?: boolean,
        public brandId?: number,
        public brandLabel?: string,
    ) {
        this.active = false;
    }
}
