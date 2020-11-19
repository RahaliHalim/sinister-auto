import { BaseEntity } from './../../shared';

export class Policy implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public startDate?: any,
        public endDate?: any,
        public newVehiclePrice?: number,
        public newVehiclePriceIsAmount?: boolean,
        public dcCapital?: number,
        public dcCapitalIsAmount?: boolean,
        public bgCapital?: number,
        public bgCapitalIsAmount?: boolean,
        public marketValue?: number,
        public marketValueIsAmount?: boolean,
        public active?: boolean,
        public deleted?: boolean,
        public typeId?: number,
        public natureId?: number,
        public periodicityId?: number,
        public packId?: number,
        public partnerId?: number,
        public agencyId?: number,
        public policyHolderId?: number,
        public vehicles?: BaseEntity[],
    ) {
        this.newVehiclePriceIsAmount = false;
        this.dcCapitalIsAmount = false;
        this.bgCapitalIsAmount = false;
        this.marketValueIsAmount = false;
        this.active = false;
        this.deleted = false;
    }
}
