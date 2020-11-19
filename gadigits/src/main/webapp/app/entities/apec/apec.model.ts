import { BaseEntity } from './../../shared';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';

export class Apec implements BaseEntity {
    constructor(
        public id?: number,
        public observationAssure?: string,
        public decriptionObservationAssure?: string,
        public dateGeneration?: any,
        public isComplementaire?: boolean,
        public participationGa?: number,
        public participationAssure?: number,
        public participationVetuste?: number,
        public participationRpc?: number,
        public participationTva?: number,
        public depacementPlafond?: number,
        public estimationFranchise?: number,
        public commentaire?: string,
        public sinisterPecId?: number,
        public sinisterPec?: SinisterPec,
        public fraisDossier?: number,
        public regleProportionnel?: number,
        public droitDeTimbre?: number,
        public avanceFacture?: number,
        public etat?: number,
        public decision?: number,
        public observation?: string,
        public soldeReparateur?: number,
        public surplusEncaisse?: number,
        public MO?: number,
        public PR?: number,
        public IPPF?: number,
        public decriptionObservation?: string,
        public decisionAssure?: number,
        public quotationId?: number,
        public accordRaisonId?: number,
        public apecEdit?: boolean,
        public toApprouvDate?: any,
        public approuvDate?: any,
        public modifDate?: any,
        public validationDate?: any,
        public assureValidationDate?: any,
        public imprimDate?: any,
        public ttc?: number,
        public isConfirme?: boolean,
        public testStep?: boolean,
        public testModifPrix?: boolean,
        public testDevis?: boolean,
        public testImprim?: boolean,
        public testAttAccord?: boolean,
        public etatApecString?: string,
        public typeApecString?: string,
        public signatureDate?: any,
        public decisionCompany?: string,



    ) {
        this.isComplementaire = false;
        this.apecEdit = false;
        this.isConfirme = true;
        this.testStep = false;

        //prestation = new SinisterPec();
    }
}
