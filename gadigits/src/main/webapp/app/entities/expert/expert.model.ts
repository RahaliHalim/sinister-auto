import { BaseEntity } from './../../shared';
import { Partner } from '../partner';
import { RefModeGestion } from './../ref-mode-gestion/ref-mode-gestion.model';
import { Governorate } from './../governorate/governorate.model';
import { Delegation } from './../delegation/delegation.model';
export class GarantieImplique implements BaseEntity {
    constructor(
        public id?: number,
        public ExpertId?: number,
        public refModeGestions?: RefModeGestion[],
        public partnerId?: number,
        public partnerName?: string,
        public listRefCompagnie?: string,
        public listRefModeGestion?: string,
    ) {
        this.listRefCompagnie='';
        this.listRefModeGestion = '';
    }
}
export class Expert implements BaseEntity {
    constructor(
        public id?: number,
        public raisonSociale?: string,
        public nom?: string,
        public prenom?: string,
        public email?: string,
        public telephone?: string,
        public fax?: string,
        public mobile?: string,
        public mobile2?: string,
        public numeroFTUSA?: string,
        public centreExpertise?: boolean,
        public blocage?: boolean,
        public eng?: boolean,
        public isActive?: boolean,
        public debutBlocage?: any,
        public finBlocage?: any,
        public dateEffetConvention?: any,
        public dateFinConvention?: any,
        public delegationId?: number,
        public gouvernoratId?: number,
        public delegationLabel?: string,
        public governorateLabel?: string,
        public adresse?: string,
        public listeVilles?: Delegation[],
        public governorate?: Governorate,
        public garantieImpliques?: GarantieImplique[],
        public gouvernorats?: Governorate[]
    ) {
        this.governorate = new Governorate();
        this.blocage = false;
        this.isActive = true;
        this.eng = false;
        this.centreExpertise = false;
        this.garantieImpliques = [];
        this.listeVilles = [];
        this.gouvernorats = [];
        this.dateEffetConvention = null;
        this.dateFinConvention = null;
    }

}
