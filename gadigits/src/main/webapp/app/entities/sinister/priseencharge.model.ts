import { BaseEntity } from '../../shared';


export class PriseEnCharges implements BaseEntity {
    constructor(
	public id?: number,
	public sinisterId? : string,
	public refSinister?: String,
	public reference?: String,
	

	public incidentDate?: Date,

	public declarationDate?: Date,
 
	public companyName?: String,

	public raisonSocialeAssure?: String,
   
	public nomAgentAssurance?: String,

	public codeAgentAssurance?: String,
 
	public numeroContrat?: String,

	public marque?: String,
 
	public immatriculationVehicule?: String,

	public prenom?: String,
  
	public nom?: String,
  
	public isCompany?: boolean,

	public raisonSociale?: String,

	public nomPrenomRaison?: String,

	public immatriculationTiers?: String,

	public raisonSocialTiers?: String,

	public creationDate?: Date,

	public modeGestion?: String,

	public casbareme?: number,

	public toit?: boolean,

	public avant?: boolean,
 
	public arriere?: boolean,
   
	public arrieregauche?: boolean,

	public lateralegauche?: boolean,

	public lateraleGauchearriere?: boolean,
 
	public lateralegaucheavant?: boolean,
   
	public arriereroite?: boolean,
  
	public lateraledroite?: boolean,

	public lateraledroiteavant?: boolean,
 
	public lateraledroitearriere?: boolean,

	public avantgauche?: boolean,

	public avantdroite?: boolean,

	public retroviseurgauche?: boolean,
   
	public retroviseurdroite?: boolean,

	public lunettearriere?: boolean,

	public parebriseavant?: boolean,

	public vitreavantgauche?: boolean,

	public vitreavantdroit?: boolean,

	public vitrearrieregauche?: boolean,

	public vitrearrieredroit?: boolean,
 
	public trianglearrieregauche?: boolean,

	public trianglearrieredroit?: boolean,

	public pointChock?: String,

	public stateShock?: boolean,
	public lightShock?: String,

	public reparateur?: String,

	public expert?: String,
   
	public etatPrestation?: String,
   
	public motif?: String,

	public approvPec?: String,

	public etapePrestation?: String,
	public compagnieAdverse?: String,
	public montantTotalDevis?: number,
	
	public positionGa? : String,
	public positionGaId? : number,


	public chargeNom?: String,

	public chargePrenom?: String,

	public bossNom?: String,
	public bossPrenom?: String,
	public directeurNom?: String,
	public directeurPrenom?: String,
	
	public bonDeSortie? : Date,
	public imprimDate? : Date,
	public creation_date?: Date ,
	public modification_date?: Date,
	public responsableNom?: String,
	public responsablePrenom?: String,
	public adversePrenom?: String,
	public adverseNom?: String,
	public motifRefus?: String,
	public motifCancel?: String,
	public motifGeneral?: String,
	public numberTeleInsured?: String,
	public dateRDVReparation? : any,
	public vehicleReceiptDate? : any,
	public dateReprise? : any,
	public totalMo? : number,
	public signatureDate? : any,

	public dateEnvoiDemandePec? : any,

	public dateAcceptationDeForme? : any


    ) {
        
    }
}