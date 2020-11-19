import { BaseEntity } from '../../shared';

export class AssistanceMonitoringCompany implements BaseEntity {
    
    constructor(
        public id?: number,
        public reference?: string,
        public registrationNumber?: string,
        public fullName?: string,
        public partnerId?: number,
        public partnerName?: string,
        public packLabel?: string,
        public usageLabel?: string,
        public serviceType?: string,
        public incidentDate?: any,
        public incidentNature?: string,
        public incidentMonth?: string,
        public affectedTugId?: number,
        public affectedTugLabel?: string,
        public interventionTimeAvgMin?: number,
        public interventionTimeAvg?: string,
        public incidentLocationLabel?: string,
        public destinationLocationLabel?: string,
        public prestationCount?: number,
        public priceTtc?: number,
        public statusLabel?: string,
        public creationDate?: any
    ) {
    }
}