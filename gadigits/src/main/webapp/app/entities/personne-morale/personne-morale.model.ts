import { BaseEntity } from './../../shared';
import { Governorate } from '../governorate';
export class PersonneMorale implements BaseEntity {
    constructor(
        public id?: number,
        public raisonSociale?: string,
        public registreCommerce?: string,
        public numEtablissement?: string,
        public codeCategorie?: number,
        public codeTVA?: string,
        public matriculeFiscale?: string,
        public adresse?: string,
        public latitude?: number,
        public longitude?: number,
        public banque?: string,
        public agence?: string,
        public rib?: string,
        public villeId?: number,
        public reglementId?: number,
        public gouvernorat?: Governorate,
        public isAssujettieTva?: boolean,
        public tvaId?: number,
    ) {
        this.isAssujettieTva = false;
    }
}
