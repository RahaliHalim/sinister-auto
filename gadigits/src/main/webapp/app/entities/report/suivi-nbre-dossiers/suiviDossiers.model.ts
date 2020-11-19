import { BaseEntity } from '../../../shared';
export class SuiviDossiers implements BaseEntity {
    constructor(
        public id?: number,
        public agentId?: number,
        public agent?: string,
        public zoneId?: number,
        public zone?: string,
        public compagnieId?: number,
        public compagnie?: string,
        public dateOuverture?: Date,
        public etatDossier?: string,
        public idEtatDossier?: number,
        public modeGestionId?: number,
        public modeGestion?: string,
        public dateBonSortie?: Date,
        public chargeId?: number,
        public chargeNom?: string,
        public chargePrenom?: string,
        public referencega?: string,
        public immatriculationVehicule?: string,
        public companyReference?: string


    ) {
    }
}

    
    
    