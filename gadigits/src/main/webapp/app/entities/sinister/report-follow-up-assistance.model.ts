import { BaseEntity } from '../../shared';

export class ReportFollowUpAssistance implements BaseEntity {

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
        public creationDate?: any,
        public insuredArrivalDate? : any,
        public tugArrivalDate? : any,
        public tugAssignmentDate? : any,
        public mileage?: number,

        public heavyWeights?: string,
        public holidays?: string,
        public night?: string,
        public halfPremium?: string,
        public fourPorteF?: string,
        public fourPorteK?: string,

        public creationUser?: string,
        public closureUser?: string,

        public tugGovernorateLabel?: string,


        ) {
    }
}