import { BaseEntity } from './../../shared';
import { SysGouvernorat } from '../sys-gouvernorat/index';

export enum Decision {
    'Accepté',
    'Annulé',
    'Refusé',
    'Accepté_avec_réserves'
}
export class PrestationPEC implements BaseEntity {
    constructor(
        public id?: number,
        public referenceSinistre?: string,
        public descPtsChoc?: string,
        public nbrVehicules?: number,
        public prestationId?: number,
        public prestationReference?: string,
        public modeId?: number,
        public reparateurId?: number,
        public reparateurNom?: String,
        public expertId?: number,
        public expertNom?: String,
        public modeLibelle?: string,
        public garantieId?: number,
        public garantieLibelle?: string,
        public baremeId?: number,
        public gouvernorat?: SysGouvernorat,
        public baremeCode?: string,
        public posGaId?: number,
        public posGaLibelle?: string,
        public agentGenerales?: BaseEntity[],
        public affetctExpertId?: number,
        public affectReparateurId?: number,
        public pointChocId?: number,
        public dateReceptionVehicule?: any,
        public userId?: number,
        public userLogin?: string,
        public userFirstName?: string,
        public userLastName?: string,
        public decision?: Decision,
        public reparatorFacturation?: boolean,
        public valeurNeuf?: number,
        public franchise?: number,
        public capitalDc?: number,
        public valeurVenale?: number,
        public typeFranchise?: String,
        public tiersId?: number,
        public quotationStatusId?: number,
        public devisId?: number,
        public primaryQuotationId?: number,
        public activeComplementaryId?: number

    ) {
    }
}

