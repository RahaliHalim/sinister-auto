import { BaseEntity } from './../../shared';

const enum Civilite {
    'MONSIEUR',
    'MADAME'
}

export class Assure implements BaseEntity {
    constructor(
        public id?: number,
        public company?: boolean,
        public prenom?: string,
        public nom?: string,
        public raisonSociale?: string,
        public subscriber?: string,
        public premTelephone?: string,
        public deuxTelephone?: string,
        public premMail?: string,
        public deuxMail?: string,
        public adresse?: string,
        public latitude?: number,
        public longitude?: number,
        public registreCommerce?: string,
        public isUtilisateur?: boolean,
        public cin?: string,
        public civilite?: Civilite,
        public delegationId?: number,
        public delegationLabel?: string,
        public governorateId?: number,
        public governorateLabel?: string,
        public userId?: number,
        public isAssujettieTva?: boolean,
        public personnePhyisqueId?: number,
        public personneMoraleId?: number,
        public dossiers?: BaseEntity[],
        public fullName?: string
    ) {
        this.isUtilisateur = false;
        this.company = false;
        this.isAssujettieTva = false;
    }
}
