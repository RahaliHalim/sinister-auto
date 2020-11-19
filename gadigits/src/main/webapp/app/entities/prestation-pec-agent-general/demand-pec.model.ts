import { BaseEntity } from './../../shared';
import { SysGouvernorat } from '../sys-gouvernorat/sys-gouvernorat.model';

export enum EtatDossier {
    'Encours',
    'Cloturé',
    'Annulé'
}
const enum NatureIncident {
    'PANNE',
    'ACCIDENT'
}

export class DemandPec implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public referenceSinistre?: string,
        public isDelete?: boolean,
        public etat?: EtatDossier,
        public dateCreation?: any,
        public dateCloture?: any,
        public dateDerniereMaj?: any,
        public telephone?: string,
        public contractNumber?: number,
        public dateAccident?: any,
        public lieuId?: number,
        public lieuNom?: string,
        public insuredId?: number,
        public insuredName?: string,
        public vehiculeId?: number,
        public immatriculation?: string,
        public managmentMode?: number,
        public gouvernorat?: SysGouvernorat,
        public quotationId?: number
    ) {
        this.isDelete = false;
    }
}
