import { BaseEntity } from '../../shared';

export class ReportTugPerformance implements BaseEntity {

    constructor(
        public id?: number,
        public affectedTugId?: number,
        public affectedTugLabel?: string,
        public reference?: string,
        public registrationNumber?: string,
        public partnerId?: number,
        public partnerName?: string,
        public serviceTypeLabel?: string,
        public usageLabel?: string,
        public interventionTimeAvgMin?: number,
        public interventionTimeAvg?: string,
        public hasHabillage?: boolean,
        public habillage?: string,
        public incidentGovernorateLabel?: string,
        public priceTtc?: number,
        public creationDate?: any,
        public zoneId?: number,
        public delaiOperation?: string,
        public insuredArrivalDate?: any,
        public tugArrivalDate?: any,
        public tugAssignmentDate?: any,
        public cancelGroundsDescription?: string,
        public cancelDate?: any,
        public closureDate?: any,
        public creationUser?: string,
        public closureUser?: string,

    ) {
        this.hasHabillage = false;
    }
}
