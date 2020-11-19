import { BaseEntity } from './../../shared';
import { RefPack } from '../ref-pack/ref-pack.model';
export class ConventionAmendment implements BaseEntity {
    constructor(
        public id?: number,
        public conventionId?: number,
        public serial?: number,
        public startDate?: any,
        public endDate?: any,
        public signedAgreement?: string|any,
        public refPack?: RefPack,
        public deleted?: boolean,
        public oldRefPackId?: number
    ) {
        deleted = false;
    
    }
}
