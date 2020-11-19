import { BaseEntity } from './../../shared';
import { RefPack } from '../ref-pack/ref-pack.model';
import { ConventionAmendment } from '../convention/convention-amendment.model';
export class Convention implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
        public clientId?: number,
        public clientLabel?: string,
        public startDate?: any,
        public endDate?: any,
        public signedAgreement?: string|any,
        public packs?: RefPack[],
        public conventionAmendments?: ConventionAmendment[],
        public packsString?: string,
        public deleted?: boolean
    ) {
        deleted = false;
 
    }
}
