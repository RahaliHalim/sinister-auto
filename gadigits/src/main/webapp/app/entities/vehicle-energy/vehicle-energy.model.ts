import { BaseEntity } from './../../shared';

export class VehicleEnergy implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public active?: boolean,
    ) {
        this.active = true;
    }
}
