import { BaseEntity } from '../../../shared';
export class DelaiMoyenImmobilisation implements BaseEntity {


    constructor(
        public id?: number,
        public referenceGa?: string,
        public compagnieId?: number,
        public compagnie?: string,
        public zoneId?: number,
        public zone?: string,
        public modeGestionId?: number,
        public modeGestion?: string,
        public chargeId?: number,
        public chargeNom?: string,
        public chargePrenom?: string,
        public dateBonDeSortie?: Date ,
        public dateOuverture?: Date ,
        public nbreDeJour?: string,
        public creationDate?: Date ,
        public reparateur?: string,
        public expert?: string,


        public typeChocBoolean?: boolean,
        public typeChoc?: string,
        public dateAccord?: any ,
        public vehicleReceiptDate?: any 


    
    ) {
    }

}