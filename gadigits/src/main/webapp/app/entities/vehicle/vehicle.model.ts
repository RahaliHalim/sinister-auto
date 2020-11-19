import { BaseEntity } from './../../shared';

export class Vehicle implements BaseEntity {
    constructor(
        public id?: number,
        public registrationNumber?: string,
        public chassisNumber?: string,
        public firstEntryIntoService?: any,
        public fiscalPower?: number,
        public brandId?: number,
        public modelId?: number,
        public energyId?: number,
        public usageId?: number,
    ) {
    }
}
