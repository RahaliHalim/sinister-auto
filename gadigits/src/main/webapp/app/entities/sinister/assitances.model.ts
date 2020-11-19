import { BaseEntity } from '../../shared';


export class Assitances implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: String,
        public incidentDate?: Date,         
        public incidentNature?: String,       
        public companyName?: String,  
        public nomAgentAssurance?: String,      
        public numeroContrat?: String,     
        public marque?: String,     
        public immatriculationVehicule?: String,     
        public prenom?: String,    
        public nom?: String,      
        public isCompany?: String,
        public raisonSociale?: String, 
        public typeService?: String,
        public remorqueur?: String,    
        public price_ttc?: number,  
        public etatPrestation?: String,
        public nomPrenomRaison?: String,
        public motif?:String,
        public statusId? : number,
        public charge?: String,
        public insuredArrivalDate? : any,
        public tugArrivalDate? : any,
        public tugAssignmentDate? : any,
        public creationDate? : any,
        public kilometrage?: number,  
        public villeDestination?: String,
        public villeSinister?: String,
        public  deliveryGovernorateLabel?: String,  
        public  loueurLabel?: String,	
        public  days? : number,	
        public  loueurAffectedDate? : any,	   
        public  returnDate ? : any,   
        public  motifRefusLabel?: String, 
        public  acquisitionDate? : any,
        public  incidentGovernorateLabel?: String,

    ) {
        
    }
}