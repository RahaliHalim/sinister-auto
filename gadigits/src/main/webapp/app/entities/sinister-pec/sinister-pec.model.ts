import { BaseEntity } from './../../shared';
import { DecisionPec, ApprovPec } from '../../constants/app.constants';
import { Tiers } from '../tiers/tiers.model';
import { RefMotif } from '../ref-motif/ref-motif.model';
import { Governorate } from '../governorate/governorate.model';
import { PointChoc } from './point-choc.model';
import { Quotation } from '../quotation/quotation.model';
import { Observation } from '../observation';
export enum SinisterStatus {
    'ST_EXT_NEW',
    'ST_INT_NEW',
    'ST_SUBMITTED',
    'ST_QUALIFIED',
    'ST_QUOTATION',
    'ST_RDVON'
}
export enum Decision {
    'In_progress',
    'Refused',
    'Accepté',
    'Annulé',
    'Refusé',
    'Accepted_With_Reserv'

}
export class SinisterPec implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public referenceSinister?: string,
        public companyReference?: string,
        public declarationDate?: any,
        public dateAffectReparateur?: any,
        public dateAffectExpert?: any,
        public vehicleNumber?: number,
        public responsabilityRate?: boolean,
        public remainingCapital?: number, // capital restant
        public driverOrInsured?: boolean,
        public driverName?: string,
        public franchise?: number,
        public driverFirstName?: string,
        public driverLicenseNumber?: string,
        public governorateId?: number,
        public delegationId?: number,
        public modeId?: number,
        public villeLibelle?: String,
        public libelleGouvernorat?: String,
        public labelModeGestion?: string,
        public baremeId?: number,
        public codeBareme?: number,
        public responsabiliteX?: number,
        public valeurNeuf?: number,
        public sinisterId?: number,
        public partnerId?: number,
        public userId?: number,
        public agencyId?: number,
        public dateUpdateExpert?: any,
        public reasonCancelExpertId?: number,
        public dateDerniereMaj?: any,
        public dateRDVReparation?: any,
        public vehicleReceiptDate?: any,
        public status?: SinisterStatus,
        public decision?: DecisionPec,
        public approvPec?: ApprovPec,
        public pointChoc?: PointChoc,
        public dateCreation?: any,
        public tiers?: Tiers[],
        public motifs?: RefMotif[],
        public companyName?: string,
        public agentName?: string,
        public contractNumber?: string,
        public gouvernorat?: Governorate,
        public gouvernoratRep?: Governorate,
        public villeId?: number,
        public villeRepId?: number,
        public gouvernoratRepId?: number,
        public immatriculationVehicle?: string,
        public incidentDate?: any,
        public expertId?: number,
        public immatriculationTier?: string,
        public motifsDecisionId?: number,
        public isDecidedFromInProgress?: boolean,
        public stepId?: number,
        public stepDecisionId?: number,
        public agentLastName?: string,
        public reparateurId?: number,
        public apecId?: number,
        public lightShock?: boolean,
        public fromDemandeOuverture?: boolean,
        public primaryQuotationId?: number,
        public receptionVehicule?: boolean,
        public thirdPhotos?: boolean,
        public disassemblyRequest?: boolean,
        public reparationStep?: string,
        public agenceName?: string,
        public modeModifId?: number,
        public motifsReopenedId?: number,
        public reasonCancelAffectedRepId?: number,
        public assignedToId?: number,
        public expertDecision?: string,
        public quotation?: Quotation,
        public primaryQuotation?: Quotation,
        public listComplementaryQuotation?: Quotation[],
        public observations?: Observation[],
        public assignedDate?: any,
        public isSelected?: Boolean,
        public rfCmp?: boolean,
        public decisionIda?: string,
        public posGaId?: number,
        public labelPosGa?: string,
        public generatedLetter?: boolean,
        public reparateurName?: string,
        public expertName?: string,
        public reasonRefusedId?: number,
        public reasonCanceledId?: number,
        public isDecidedFromCreateSinister?: boolean,
        public marketValue?: number,
        public isAssujettieTva?: boolean,
        public regleModeGestion?: boolean,
        public prestAApprouv?: boolean,
        public modificationCharge?: boolean,
        public phone?: string,
        public insuredName?: string,
        public insuredSurname?: string,
        public motifChangementStatus?: string,
        public historyAvisExpert?: string,
        public dateModifAfterReserved?: any,
        public dateModification?: any,
        public modificationPrix?: boolean,
        public activeModificationPrix?: boolean,
        public oldStep?: number,
        public changeModificationPrix?: boolean,
        public generationBonSortieDate?: any,
        public oldStepModifSinPec?: number,
        public motifNonConfirmeId?: number,
        public assujettieTVA?: boolean,
        public valeurAssure?: number,
        public capitalRestantAfterComp?: number,
        public franchiseTypeDcCapital?: string,
        public dcCapitalFranchise?: number,
        public franchiseTypeBgCapital?: string,
        public bgCapitalFranchise?: number,
        public totaleFranchise?: number,
        public useMinFranchise?: boolean,
        public pieceGenerique?: boolean,
        public dateCloture?: any,
        public valeurANeufExpert?: number,
        public valeurVenaleExpert?: number,
        public oldStepNw?: number,
        public preliminaryReport?: boolean,
        public debloque?: boolean,
        public assignedTofirstName?: string,
        public assignedTolastName?: string,
        public testDevisCmpl?: boolean,
        public testModifPrix?: boolean,
        public insuredCapital?: number,
        public governorateLabel?: string,
        public governorateRepLabel?: string,
        public motifsReopenedLabel?: string,
        public motifsDecisionLabel?: string,
        public reasonCanceledLabel?: string,
        public reasonRefusedLabel?: string,
        public conditionVehicle?: string,
        public repairableVehicle?: boolean,
        public concordanceReport?: boolean,
        public immobilizedVehicle?: boolean,
        public respControleTechnique?: number,
        public quoteViaGt?: boolean,
        public quoteId?: number,
        public annuleRefusDate?: any,
        public repriseStep?: number,
        public repriseEtat?: number,
        public dateReprise?: any,
        public dateValidationPremierAccord?: any,
        public vehiculeId?: number

        


    ) {
        this.tiers = [];
        this.responsabilityRate = false;
        this.driverOrInsured = false;
        this.isDecidedFromInProgress = false;
        this.receptionVehicule = false;
        this.rfCmp = false;
        this.thirdPhotos = false;
        this.listComplementaryQuotation = [];
        this.observations = [];
        this.isDecidedFromCreateSinister = false;
        this.regleModeGestion = false;
        this.prestAApprouv = false;
        this.modificationPrix = false;
        this.activeModificationPrix = false;
        this.changeModificationPrix = false;
        //this.assujettieTVA = false;

    }
}
