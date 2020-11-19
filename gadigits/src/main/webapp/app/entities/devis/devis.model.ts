import { BaseEntity } from './../../shared';

export enum EtatDevis {
  'Accepte',
   'Valide',
   'ValideGestionnaire',
   'Accord',
   'GenereAccord',
   'En_cours',
   'Soumis',
   'Refuse',
   'Facture',
   'ValideFacturation',
   'Refuse_Apres_Facture',
   'Generation_bon_sortie_accepte',
   'Signature_Accord',
   'Signature_BS',
   'Demontage',
   'Expertise',
   'AccordCmp',
   'RDV_REPARATION',


 



}
export class Devis implements BaseEntity {
    constructor(
        public id?: number,
        public totalTtc?: number,
        public totHt?: number,
        public isComplementaire?: boolean,
        public isSupprime?: boolean,
        public dateGeneration?: any,
        public etatDevis?: EtatDevis,
        public timbre?: number,
        public version?: number,
        public commentaire?: string,
        public droitTimbre?: number,
        public prestationId?: number,
        public reparateurId?: number,
        public expertId?: number,
        public parentId?: number,
        public dateExpertise?: any,
        public valeurNeuf?: number,
        public valeurVenale?: number,
        public klm?: number,
        public etatVehicule?: string,
        public isVehiculeReparable?: boolean,
        public isCia?: boolean,
        public isConstatPre?: boolean,
        public quotationStatusId?: number,

    ) {
        this.isComplementaire = false;
        this.isSupprime = false;
        this.droitTimbre= 0;
    }
}
