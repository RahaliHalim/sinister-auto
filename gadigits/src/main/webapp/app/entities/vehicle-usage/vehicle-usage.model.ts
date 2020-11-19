import { BaseEntity } from './../../shared';

export class VehicleUsage implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public label?: string,
        public active?: boolean,
        public compagnieId?: number,
        public vatRateId?: number,
        public vatRate?: number,
        public compagnie?: string



    ) {
        this.active = false;
    }
}
