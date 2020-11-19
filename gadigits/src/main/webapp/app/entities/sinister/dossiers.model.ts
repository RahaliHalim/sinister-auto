import { BaseEntity } from '../../shared';


export class Dossiers implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: String,
        public incidentDate?: Date,
        public incidentMois?: String,
        public incidentNature?: String,
        public companyName?: String,
        public nomAgentAssurance?: String,
        public numeroContrat?: String,
        public pack?: String,
        public immatriculationVehicule?: String,
        public marque?: String,
        public raisonSociale?: String,
        public typeService?: String,
        public serviceCount?: number,
        public isCompany?: boolean,
        public prenom?: String,
        public nom?: String,
        public nomPrenomRaison?: String,
        public statusId? : number
    ) {
        
    }
}
