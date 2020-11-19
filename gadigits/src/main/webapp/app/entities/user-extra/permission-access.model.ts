import { BaseEntity } from '../../shared';

export class PermissionAccess implements BaseEntity {
  constructor(
    public id?: number,
    public canCreate?: boolean,
    public canUpdate?: boolean,
    public canLister?: boolean,
    public canDelete?: boolean,
    public canConsult?: boolean,
    public canActive?: boolean,
    public canDesactive?: boolean,
    public canTrait?: boolean,
    public canModifStatus?: boolean,
    public canModifReserveLifted?: boolean,
    public canAprouveSinPec?: boolean,
    public canReopenPecRefus?: boolean,
    public canReopenPecCanceled?: boolean,
    public canCancelSinPec?: boolean,
    public canConfirmCancelSinPec?: boolean,
    public canRefusSinPec?: boolean,
    public canConfirmRefusSinPec?: boolean,
    public canGenereBonSortie?: boolean,
    public canVerifOriginalsPrinted?: boolean,
    public canSignatureBonSortie?: boolean,
    public canAssignRepairer?: boolean,
    public canCancellationAssignmentRepair?: boolean,
    public canMissionAnExpert?: boolean,
    public canMissionExpertCancellation?: boolean,
    public canEditVehicleReception?: boolean,
    public canEditQuoteQuote?: boolean,
    public canEditConfirmationQuote?: boolean,
    public canUpdateQuotation?: boolean,
    public canValidationQueryrectify?: boolean,
    public canExpertOpinion?: boolean,
    public canSignatureAgreement?: boolean,
    public canConfirmationOfTheSupplementaryQuote?: boolean,
    public canBilling?: boolean,
    public canDecide?: boolean,
    public canRepriseSinPec?: boolean,
    public canDismantling?: boolean,
    public canConfirmModifPrix?: boolean,
    public canModifPrix?: boolean,
    public canApprouvApec?: boolean,
    public canModifApec?: boolean,
    public canValidApec?: boolean,
    public canValidAssurApec?: boolean,
    public canImprimApec?: boolean,
    public canConsultDemandPec?: boolean,
    public canAjoutDemandPecExpertise?: boolean,
    public canModifWithDerogation?: boolean,
    public canGenerate?: boolean
    //  public canConsultDossier?: boolean,
    //   public canAjoutDossier?: boolean,
    //   public canModifDossier?: boolean

  ) {
    this.canCreate = false;
    this.canDelete = false;
    this.canLister = false;
    this.canUpdate = false;
    this.canConsult = false;
    this.canActive = false;
    this.canDesactive = false;
    this.canTrait = false;
    this.canModifStatus = false;
    this.canModifReserveLifted = false;
    this.canAprouveSinPec = false;
    this.canReopenPecRefus = false;
    this.canReopenPecCanceled = false;
    this.canCancelSinPec = false;
    this.canConfirmCancelSinPec = false;
    this.canRefusSinPec = false;
    this.canConfirmRefusSinPec = false;
    this.canGenereBonSortie = false;
    this.canVerifOriginalsPrinted = false;
    this.canSignatureBonSortie = false;
    this.canAssignRepairer = false;
    this.canCancellationAssignmentRepair = false;
    this.canMissionAnExpert = false;
    this.canMissionExpertCancellation = false;
    this.canEditVehicleReception = false;
    this.canEditQuoteQuote = false;
    this.canEditConfirmationQuote = false;
    this.canUpdateQuotation = false;
    this.canValidationQueryrectify = false;
    this.canExpertOpinion = false;
    this.canSignatureAgreement = false;
    this.canConfirmationOfTheSupplementaryQuote = false;
    this.canBilling = false;
    this.canDecide = false;
    this.canRepriseSinPec = false;
    this.canDismantling = false;
    this.canConfirmModifPrix = false;
    this.canModifPrix = false;
    this.canApprouvApec = false;
    this.canModifApec = false;
    this.canValidApec = false;
    this.canValidAssurApec = false;
    this.canImprimApec = false;
    this.canConsultDemandPec = false;
    this.canAjoutDemandPecExpertise = false;
    this.canModifWithDerogation = false;
    this.canGenerate = false;
    //  this.canConsultDossier = false;
    //  this.canAjoutDossier = false ; 
    //  this.canModifDossier = false ; 
  }

}
