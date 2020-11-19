import { BaseEntity } from '../../shared';

export class DetailsQuotation implements BaseEntity {
    constructor(
        public id?: number,
        public sinisterPecId?: number,
        public priceNewVehicle?: number,
        public marketValue?: number,
        public kilometer?: number,
        public estimateJour?: number,
        public heureMO?: number,
        public totalMo?: number,
        public conditionVehicle?: string,
        public repairableVehicle?: boolean,
        public preliminaryReport?: boolean,
        public immobilizedVehicle?: boolean,
        public concordanceReport?: boolean,
        public expertiseDate?: any,
        public expertDecision?: string,


    ) {
    }
}