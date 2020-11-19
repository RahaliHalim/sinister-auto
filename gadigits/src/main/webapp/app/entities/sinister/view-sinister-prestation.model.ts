import { BaseEntity } from '../../shared';

export class ViewSinisterPrestation implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public incidentDate?: any,
        public registrationNumber?: string,
        public fullName?: string,
        public serviceType?: string,
        public incidentLocationLabel?: string,
        public destinationLocationLabel?: string,
        public affectedTugLabel?: string,
        public priceTtc?: number,
        public insuredArrivalDate?: any,
        public cancelGroundsDescription?: string,
        public notEligibleGroundsDescription?: string,
        public statusId?: number,
        public creationDate?: any,
        public closureDate?: any,
        public updateDate?: any,
        public statusLabel?: string,
        public charge?: string,
        public vehiculeId?: number,
        public tugAssignmentDate?: any,
        public tugArrivalDate?: any,


        public incidentGovernorateLabel?: string,
        public deliveryGovernorateLabel?: string,
        public days?: number,
        public loueurLabel?: string,
        public loueurAffectedDate?: any,
        public motifRefusLabel?: string,
        public returnDate?: any,
        public acquisitionDate?: any,
        public firstDriver?: string,
        public secondDriver?: string,
        public isGenerated?: boolean,


    ) {
    }
}
