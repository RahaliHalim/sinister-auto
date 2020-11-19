import { BaseEntity } from './../../shared';

export class RefPricing implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public priceUrbanPlanService?: number,
        public priceKlmShortDistance?: number,
        public priceKlmLongDistance?: number,
        public priceExtraction?: number,
        public priceReparation?: number,
        public priceUrbanMobility?: number,
        public priceIncrease?: number,
        public priceUrbanPlanServicePl?: number,
        public priceKlmShortDistancePl?: number,
        public priceKlmLongDistancePl?: number

    ) {
    }
}
