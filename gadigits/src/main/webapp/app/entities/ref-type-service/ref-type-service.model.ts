import { BaseEntity } from './../../shared';
import { RefPack } from '../ref-pack/ref-pack.model';

export class RefTypeService implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
    ) {
    }
}

export class RefStatusSinister implements BaseEntity {
    constructor(
        public id?: number,
        public label?: string,
    ) {
    }
}

export class ServiceTypePacks implements BaseEntity {
    constructor(
        public id?: number,
        public refTypeService?: RefTypeService,
        public packs?: RefPack[],
    ) {
    }
}
