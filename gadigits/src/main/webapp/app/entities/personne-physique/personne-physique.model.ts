import { BaseEntity } from './../../shared';

import { Governorate } from '../governorate';
const enum Civilite {
    'MONSIEUR',
    'MADAME'
}

export class PersonnePhysique implements BaseEntity {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public premTelephone?: string,
        public deuxTelephone?: string,
        public fax?: string,
        public premMail?: string,
        public deuxMail?: string,
        public adresse?: string,
        public latitude?: number,
        public longitude?: number,
        public isUtilisateur?: boolean,
        public cin?: number,
        public civilite?: Civilite,
        public villeId?: number,
        public gouvernorat?: Governorate,
        public userId?: number,
    ) {
        this.isUtilisateur = false;
    }
}
    