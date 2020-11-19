import { BaseEntity } from '../../shared';
import { Observation } from '../observation';

export class SinisterPrestation implements BaseEntity {
    constructor(
        public id?: number,
        public sinisterId?: number,
        public reference?: string,
        public incidentDate?: any,
        public vehicleRegistration?: string,
        public insuredName?: string,
        public serviceTypeId?: number,
        public serviceTypeLabel?: string,
        public statusId?: number,
        public statusLabel?: string,
        public incidentGovernorateId?: number,
        public incidentGovernorateLabel?: string,
        public incidentLocationId?: number,
        public incidentLocationLabel?: string,
        public destinationGovernorateId?: number,
        public destinationGovernorateLabel?: string,
        public destinationLocationId?: number,
        public destinationLocationLabel?: string,

        public mileage?: number,

        public heavyWeights?: boolean,
        public holidays?: boolean,
        public night?: boolean,
        public halfPremium?: boolean,
        public fourPorteF?: boolean,
        public fourPorteK?: boolean,

        public isU?: boolean,
        public isR?: boolean,


        public tugArrivalDate?: any,
        public insuredArrivalDate?: any,
        public cancelDate?: any,

        public affectedTugId?: number,
        public affectedTugLabel?: string,
        public affectedTruckId?: number,
        public affectedTruckLabel?: string,

        public vatRate?: number,
        public priceHt?: number,
        public priceTtc?: number,

        public cancelGroundsId?: number,
        public cancelGroundsDescription?: string,
        public reopenGroundsId?: number,
        public reopenGroundsDescription?: string,
        public notEligibleGroundsId?: number,
        public notEligibleGroundsDescription?: string,

        public partnerName?: string, // TODO
        public pack?: string, // TODO
        public usage?: string, // TODO
        public incidentNature?: string, // TODO
        public incidentMonth?: string, // TODO
        public dmi?: number, // TODO
        public dmiStr?: string, // TODO
        public prestationCount?: number, // TODO

        public creationDate?: any,
        public closureDate?: any,
        public updateDate?: any,
        public assignmentDate?: any,

        public creationUserId?: number,
        public closureUserId?: number,
        public updateUserId?: number,
        public assignedToId?: number,
        public locationGovernorate?: string,
        public locationDelegation?: string,
        public gageo?: boolean,
        public observations?: Observation[],

        public deliveryGovernorateId?: number,
        public deliveryGovernorateLabel?: string,
        public deliveryLocationId?: number,
        public deliveryLocationLabel?: string,

        public days?: number,
        public longDuration?: boolean,
        public shortDuration?: boolean,
        public pricePerDay?: number,
        public deliveryDate?: any,
        public expectedReturnDate?: any,
        public loueurId?: number,
        public loueurLabel?: string,
        public refusedDate?: any,
        public refusedUserId?: number,
        public loueurAffectedDate?: any,
        public motifRefusId?: any,
        public immatriculationVr?: string,
        public motifRefusLabel?: string,
        public returnDate?: any,
        public acquisitionDate?: any,

        public acquisitionTime?:any,
        public returnTime?: any,
        public expectedReturnTime?: any,
        public deliveryTime?: any,
    
        public firstDriver?: string,
        public secondDriver?: string,
        public isGenerated?: boolean,


    ) {
        shortDuration=false;
        longDuration=false;
    }
}
