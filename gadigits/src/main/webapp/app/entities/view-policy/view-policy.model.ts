import { BaseEntity } from './../../shared';

export class ViewPolicy implements BaseEntity {
    constructor(
        public id?: number,
        public policyNumber?: string,
        public companyId?: number,
        public companyName?: string,
        public agencyId?: number,
        public agencyName?: string,
        public vehicleId?: number,
        public registrationNumber?: string,
        public policyHolderId?: number,
        public policyHolderName?: string,
        public startDate?: any,
        public endDate?: any,
    ) {
    }
}
