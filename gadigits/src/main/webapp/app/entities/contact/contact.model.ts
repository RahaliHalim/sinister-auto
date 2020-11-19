import { BaseEntity } from './../../shared';
import { PersonnePhysique } from '../personne-physique';

export class Contact implements BaseEntity {
    constructor(
        public id?: number,
        public isGerant?: boolean,
        public personnePhysiqueId?: number,
        public personnePhysiqueNom?: string,
        public personnePhysiquePrenom?: string,
        public personnePhysiqueEmail?: string,
        public personnePhysiqueTel?: string,
        public personnePhysiqueFax?: string,
        public gouvernoratId?: number,
        public personneMoraleId?: number,
    ) {
        this.isGerant = false;
    }
}
