import { BaseEntity } from '../../shared';

export class TugPricing implements BaseEntity {
    constructor(
        public id?: number,
        public tugId?: number,
        public priceUrbanPlanService?: number,
        public priceKlmShortDistance?: number,
        public priceKlmLongDistance?: number,
        public priceExtraction?: number,
        public priceReparation?: number,
        public priceUrbanMobility?: number,
        public priceUrbanPlanServicePl?: number,
        public priceKlmShortDistancePl?: number,
        public priceKlmLongDistancePl?: number,
        public dateEffectiveAgreement?: any,
        public dateEndAgreement?: any

    ) {
    }
}
