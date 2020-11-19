import { BaseEntity } from '../../../shared';
export class SuiviAnnulation implements BaseEntity {


    constructor(
        public id?: number,
        public referenceGa?: string,
        public compagnieId?: number,
        public compagnie?: string,
        public compagnieAdvId?: number,
        public compagnieAdv?: string,
        public agenceId?: number,
        public agence?: string,
        public zoneId?: string,
        public zone?: string,
        public modeGestionId?: number,
        public modeGestion?: string,
        public assureId?: number,
        public assure?: string,
        public vehiculeId?: number,
        public immatriculationVehicule?: string,
        public marque?: string,
        public reparateurId?: number,
        public reparateur?: string,
        public chargeId?: number,
        public nomCharge?: string,
        public prenomCharge?: string,
        public expertId?: number,
        public expert?: string,
        public villeId?: number,
        public zoneReparateur?: string,
        public ouvertureDate?: Date,
        public annulationDate?: Date,
        public motifAnnulationId?: number,
        public motifAnnulation?: string,
        public montantDevis?: number,
        public participationType?: string,
        public montantParticipation?: number,
        public etatDossierId?: number,
        public etatDossier?: string,
        public dateIncident?: any,
        public decision?: string,
        public companyReference?: string,


    
    ) {
    }

}