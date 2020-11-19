import { BaseEntity } from '../../shared';
import { PersonnePhysique } from '../personne-physique';

export class VisAVis implements BaseEntity {
    constructor(
        public id?: number,
        public reparateurId?: number,
        public partnerId?: number,
        public loueurId?: number,
        public entityName?: string,
        public isGerant?: boolean,
        public nom?: string,
        public prenom?: string,
        public premTelephone?: string,
        public deuxTelephone?: string,
        public fax?: string,
        public premMail?: string,
        public deuxMail?: string,
        public email?: string,
        public email2?: string,
    ) {
        this.isGerant = false;
    }
}
