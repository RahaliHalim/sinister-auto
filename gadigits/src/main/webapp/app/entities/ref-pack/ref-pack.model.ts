import { RefModeGestion } from './../ref-mode-gestion/ref-mode-gestion.model';
import { BaseEntity } from './../../shared';
import { VehicleUsage } from '../vehicle-usage';
import { RefExclusion } from './ref-exclusion.model';
import { Convention } from '../convention/convention.model';
import { RefTypeService } from '../ref-type-service/ref-type-service.model';

export class SinisterTypeSetting implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public sinisterTypeId?: number,
        public sinisterTypeLabel?: string,
        public ceiling?: number
    ) {
    }
}

export const enum Operator {
    
    'GREATER' = "GREATER",
    'LOWER' = "LOWER"
}

export class ApecSettings implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public mgntModeId?: number,
        public mgntModeLabel?: string,
        public operator?: Operator,
        public ceiling?: number
    ) {
    }
}

export class RefPack implements BaseEntity {
    constructor(
        public clientLabel?: string,
        public startDate?: any,
        public endDate?: any,
        public id?: number,
        public idConvention?: number,
        public label?: string,
        public reinsurerId?: number,
        public reinsurerName?: string,
        public mileage?: number,
        public price?: number,
        public interventionNumber?: number,
        public vrDaysNumber?: number,
        public ceilingToConsume?: number,
        public combinable?: boolean,
        public homeService?: boolean,
        public conventionId?: number,
        public serviceTypes?: RefTypeService[],
        public blocked?: boolean,
        public usages?: VehicleUsage[],
        public modeGestions?: RefModeGestion[],
        public exclusions?: RefExclusion[],
        public grantTimingList?: string,
        public companyPart?: number,
        public reinsurerPart?: number,
        public partnerPart?: number,
        public ConventionAmendment?: number,
        public sinisterTypeSettings?: SinisterTypeSetting[],
        public apecSettings?: ApecSettings[],
        public listPack?: string,
        public serviceTypesStr?: string,
        public apecValidation?: boolean,
    ) {

        listPack = '';
        apecValidation = false;
    }
}
