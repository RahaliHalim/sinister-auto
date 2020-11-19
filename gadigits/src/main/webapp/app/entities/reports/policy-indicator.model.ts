import { BaseEntity } from '../../shared';

export class PolicyIndicator implements BaseEntity {
    
    constructor(
    	public id?: number,
        public agencyName?: string,
        public usageLabel?: string,
        public packNumber?: number,
        public zoneLabel?: number
    ) {
    }
}