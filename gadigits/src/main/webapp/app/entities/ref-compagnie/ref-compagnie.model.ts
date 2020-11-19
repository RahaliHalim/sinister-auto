import { BaseEntity } from './../../shared';
import { PersonneMorale } from '../personne-morale/index';

export class RefCompagnie implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public reference?: number,
        public isEtrangere?: boolean,
        public dateCreation?: any,
        public logoContentType?: string,
        public logo?: any,
        public isBloque?: boolean,
        public isClient?: boolean,
        public personneMoraleId?: number,
        public personneMorale?: PersonneMorale,
        public natures?: BaseEntity[],
        public types?: BaseEntity[],
        public usages?: BaseEntity[],
    ) {
        this.isEtrangere = false;
        this.isBloque = false;
        this.isClient = false;
    }
}
