package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

/**
 * A DTO for the PointChoc entity.
 */
public class PermissionAccessDTO implements Serializable {

	private Long id;
	private Boolean canCreate;
	private Boolean canUpdate;
	private Boolean canLister;
	private Boolean canDelete;
	private Boolean canConsult;
	private Boolean canActive;
	private Boolean canDesactive;
	private Boolean canTrait;
	private Boolean canModifStatus;
	private Boolean canModifReserveLifted;
	private Boolean canAprouveSinPec;
	private Boolean canReopenPecRefus;
	private Boolean canReopenPecCanceled;
	private Boolean canCancelSinPec;
	private Boolean canConfirmCancelSinPec;
	private Boolean canRefusSinPec;
	private Boolean canConfirmRefusSinPec;
	private Boolean canGenereBonSortie;
	private Boolean canVerifOriginalsPrinted;
	private Boolean canSignatureBonSortie;
	private Boolean canAssignRepairer;
	private Boolean canCancellationAssignmentRepair;
	private Boolean canMissionAnExpert;
	private Boolean canMissionExpertCancellation;
	private Boolean canEditVehicleReception;
	private Boolean canEditQuoteQuote;
	private Boolean canEditConfirmationQuote;
	private Boolean canUpdateQuotation;
	private Boolean canValidationQueryrectify;
	private Boolean canExpertOpinion;
	private Boolean canSignatureAgreement;
	private Boolean canConfirmationOfTheSupplementaryQuote;
	private Boolean canBilling;
	private Boolean canDecide;
	private Boolean canRepriseSinPec;
	private Boolean canDismantling;
	private Boolean canConfirmModifPrix;
	private Boolean canModifPrix;
	private Boolean canApprouvApec;
	private Boolean canModifApec;
	private Boolean canValidApec;
	private Boolean canValidAssurApec;
	private Boolean canImprimApec;
	private Boolean canConsultDemandPec;
	private Boolean canAjoutDemandPecExpertise;
	private Boolean canConsultDossier;
	private Boolean canAjoutDossier;
	private Boolean canModifDossier;
	private Boolean canModifWithDerogation;
	private Boolean canGenerate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getCanConsultDossier() {
		return canConsultDossier;
	}

	public void setCanConsultDossier(Boolean canConsultDossier) {
		this.canConsultDossier = canConsultDossier;
	}

	public Boolean getCanModifWithDerogation() {
		return canModifWithDerogation;
	}

	public void setCanModifWithDerogation(Boolean canModifWithDerogation) {
		this.canModifWithDerogation = canModifWithDerogation;
	}

	public Boolean getCanAjoutDossier() {
		return canAjoutDossier;
	}

	public void setCanAjoutDossier(Boolean canAjoutDossier) {
		this.canAjoutDossier = canAjoutDossier;
	}

	public Boolean getCanModifDossier() {
		return canModifDossier;
	}

	public void setCanModifDossier(Boolean canModifDossier) {
		this.canModifDossier = canModifDossier;
	}

	public Boolean getCanCreate() {
		return canCreate;
	}

	public void setCanCreate(Boolean canCreate) {
		this.canCreate = canCreate;
	}

	public Boolean getCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(Boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public Boolean getCanLister() {
		return canLister;
	}

	public void setCanLister(Boolean canLister) {
		this.canLister = canLister;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public Boolean getCanConsult() {
		return canConsult;
	}

	public void setCanConsult(Boolean canConsult) {
		this.canConsult = canConsult;
	}

	public Boolean getCanActive() {
		return canActive;
	}

	public void setCanActive(Boolean canActive) {
		this.canActive = canActive;
	}

	public Boolean getCanDesactive() {
		return canDesactive;
	}

	public void setCanDesactive(Boolean canDesactive) {
		this.canDesactive = canDesactive;
	}

	public Boolean getCanAssignRepairer() {
		return canAssignRepairer;
	}

	public void setCanAssignRepairer(Boolean canAssignRepairer) {
		this.canAssignRepairer = canAssignRepairer;
	}

	public Boolean getCanCancellationAssignmentRepair() {
		return canCancellationAssignmentRepair;
	}

	public void setCanCancellationAssignmentRepair(Boolean canCancellationAssignmentRepair) {
		this.canCancellationAssignmentRepair = canCancellationAssignmentRepair;
	}

	public Boolean getCanMissionAnExpert() {
		return canMissionAnExpert;
	}

	public void setCanMissionAnExpert(Boolean canMissionAnExpert) {
		this.canMissionAnExpert = canMissionAnExpert;
	}

	public Boolean getCanMissionExpertCancellation() {
		return canMissionExpertCancellation;
	}

	public void setCanMissionExpertCancellation(Boolean canMissionExpertCancellation) {
		this.canMissionExpertCancellation = canMissionExpertCancellation;
	}

	public Boolean getCanEditVehicleReception() {
		return canEditVehicleReception;
	}

	public void setCanEditVehicleReception(Boolean canEditVehicleReception) {
		this.canEditVehicleReception = canEditVehicleReception;
	}

	public Boolean getCanEditQuoteQuote() {
		return canEditQuoteQuote;
	}

	public void setCanEditQuoteQuote(Boolean canEditQuoteQuote) {
		this.canEditQuoteQuote = canEditQuoteQuote;
	}

	public Boolean getCanEditConfirmationQuote() {
		return canEditConfirmationQuote;
	}

	public void setCanEditConfirmationQuote(Boolean canEditConfirmationQuote) {
		this.canEditConfirmationQuote = canEditConfirmationQuote;
	}

	public Boolean getCanUpdateQuotation() {
		return canUpdateQuotation;
	}

	public void setCanUpdateQuotation(Boolean canUpdateQuotation) {
		this.canUpdateQuotation = canUpdateQuotation;
	}

	public Boolean getCanValidationQueryrectify() {
		return canValidationQueryrectify;
	}

	public void setCanValidationQueryrectify(Boolean canValidationQueryrectify) {
		this.canValidationQueryrectify = canValidationQueryrectify;
	}

	public Boolean getCanExpertOpinion() {
		return canExpertOpinion;
	}

	public void setCanExpertOpinion(Boolean canExpertOpinion) {
		this.canExpertOpinion = canExpertOpinion;
	}

	public Boolean getCanDismantling() {
		return canDismantling;
	}

	public void setCanDismantling(Boolean canDismantling) {
		this.canDismantling = canDismantling;
	}

	public Boolean getCanSignatureAgreement() {
		return canSignatureAgreement;
	}

	public void setCanSignatureAgreement(Boolean canSignatureAgreement) {
		this.canSignatureAgreement = canSignatureAgreement;
	}

	public Boolean getCanConfirmationOfTheSupplementaryQuote() {
		return canConfirmationOfTheSupplementaryQuote;
	}

	public void setCanConfirmationOfTheSupplementaryQuote(Boolean canConfirmationOfTheSupplementaryQuote) {
		this.canConfirmationOfTheSupplementaryQuote = canConfirmationOfTheSupplementaryQuote;
	}

	public Boolean getCanBilling() {
		return canBilling;
	}

	public void setCanBilling(Boolean canBilling) {
		this.canBilling = canBilling;
	}

	public Boolean getCanTrait() {
		return canTrait;
	}

	public void setCanTrait(Boolean canTrait) {
		this.canTrait = canTrait;
	}

	public Boolean getCanModifStatus() {
		return canModifStatus;
	}

	public void setCanModifStatus(Boolean canModifStatus) {
		this.canModifStatus = canModifStatus;
	}

	public Boolean getCanModifReserveLifted() {
		return canModifReserveLifted;
	}

	public void setCanModifReserveLifted(Boolean canModifReserveLifted) {
		this.canModifReserveLifted = canModifReserveLifted;
	}

	public Boolean getCanAprouveSinPec() {
		return canAprouveSinPec;
	}

	public void setCanAprouveSinPec(Boolean canAprouveSinPec) {
		this.canAprouveSinPec = canAprouveSinPec;
	}

	public Boolean getCanReopenPecRefus() {
		return canReopenPecRefus;
	}

	public void setCanReopenPecRefus(Boolean canReopenPecRefus) {
		this.canReopenPecRefus = canReopenPecRefus;
	}

	public Boolean getCanReopenPecCanceled() {
		return canReopenPecCanceled;
	}

	public Boolean getCanCancelSinPec() {
		return canCancelSinPec;
	}

	public void setCanCancelSinPec(Boolean canCancelSinPec) {
		this.canCancelSinPec = canCancelSinPec;
	}

	public Boolean getCanConfirmCancelSinPec() {
		return canConfirmCancelSinPec;
	}

	public void setCanConfirmCancelSinPec(Boolean canConfirmCancelSinPec) {
		this.canConfirmCancelSinPec = canConfirmCancelSinPec;
	}

	public Boolean getCanRefusSinPec() {
		return canRefusSinPec;
	}

	public void setCanRefusSinPec(Boolean canRefusSinPec) {
		this.canRefusSinPec = canRefusSinPec;
	}

	public Boolean getCanConfirmRefusSinPec() {
		return canConfirmRefusSinPec;
	}

	public void setCanConfirmRefusSinPec(Boolean canConfirmRefusSinPec) {
		this.canConfirmRefusSinPec = canConfirmRefusSinPec;
	}

	public void setCanReopenPecCanceled(Boolean canReopenPecCanceled) {
		this.canReopenPecCanceled = canReopenPecCanceled;
	}

	public Boolean getCanGenereBonSortie() {
		return canGenereBonSortie;
	}

	public void setCanGenereBonSortie(Boolean canGenereBonSortie) {
		this.canGenereBonSortie = canGenereBonSortie;
	}

	public Boolean getCanVerifOriginalsPrinted() {
		return canVerifOriginalsPrinted;
	}

	public void setCanVerifOriginalsPrinted(Boolean canVerifOriginalsPrinted) {
		this.canVerifOriginalsPrinted = canVerifOriginalsPrinted;
	}

	public Boolean getCanSignatureBonSortie() {
		return canSignatureBonSortie;
	}

	public void setCanSignatureBonSortie(Boolean canSignatureBonSortie) {
		this.canSignatureBonSortie = canSignatureBonSortie;
	}

	public Boolean getCanDecide() {
		return canDecide;
	}

	public void setCanDecide(Boolean canDecide) {
		this.canDecide = canDecide;
	}

	public Boolean getCanRepriseSinPec() {
		return canRepriseSinPec;
	}

	public void setCanRepriseSinPec(Boolean canRepriseSinPec) {
		this.canRepriseSinPec = canRepriseSinPec;
	}

	public Boolean getCanConfirmModifPrix() {
		return canConfirmModifPrix;
	}

	public void setCanConfirmModifPrix(Boolean canConfirmModifPrix) {
		this.canConfirmModifPrix = canConfirmModifPrix;
	}

	public Boolean getCanModifPrix() {
		return canModifPrix;
	}

	public void setCanModifPrix(Boolean canModifPrix) {
		this.canModifPrix = canModifPrix;
	}

	public Boolean getCanApprouvApec() {
		return canApprouvApec;
	}

	public void setCanApprouvApec(Boolean canApprouvApec) {
		this.canApprouvApec = canApprouvApec;
	}

	public Boolean getCanModifApec() {
		return canModifApec;
	}

	public void setCanModifApec(Boolean canModifApec) {
		this.canModifApec = canModifApec;
	}

	public Boolean getCanValidApec() {
		return canValidApec;
	}

	public void setCanValidApec(Boolean canValidApec) {
		this.canValidApec = canValidApec;
	}

	public Boolean getCanValidAssurApec() {
		return canValidAssurApec;
	}

	public void setCanValidAssurApec(Boolean canValidAssurApec) {
		this.canValidAssurApec = canValidAssurApec;
	}

	public Boolean getCanImprimApec() {
		return canImprimApec;
	}

	public void setCanImprimApec(Boolean canImprimApec) {
		this.canImprimApec = canImprimApec;
	}

	public Boolean getCanConsultDemandPec() {
		return canConsultDemandPec;
	}

	public void setCanConsultDemandPec(Boolean canConsultDemandPec) {
		this.canConsultDemandPec = canConsultDemandPec;
	}

	public Boolean getCanAjoutDemandPecExpertise() {
		return canAjoutDemandPecExpertise;
	}

	public void setCanAjoutDemandPecExpertise(Boolean canAjoutDemandPecExpertise) {
		this.canAjoutDemandPecExpertise = canAjoutDemandPecExpertise;
	}

	public Boolean getCanGenerate() {
		return canGenerate;
	}

	public void setCanGenerate(Boolean canGenerate) {
		this.canGenerate = canGenerate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermissionAccessDTO other = (PermissionAccessDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "PermissionAccessDTO [id=" + id + ", canCreate=" + canCreate + ", canUpdate=" + canUpdate
				+ ", canLister=" + canLister + ", canDelete=" + canDelete + ", canConsult=" + canConsult
				+ ", canActive=" + canActive + ", canDesactive=" + canDesactive + ", canTrait=" + canTrait
				+ ", canModifStatus=" + canModifStatus + ", canModifReserveLifted=" + canModifReserveLifted
				+ ", canAprouveSinPec=" + canAprouveSinPec + ", canReopenPecRefus=" + canReopenPecRefus
				+ ", canReopenPecCanceled=" + canReopenPecCanceled + ", canCancelSinPec=" + canCancelSinPec
				+ ", canConfirmCancelSinPec=" + canConfirmCancelSinPec + ", canRefusSinPec=" + canRefusSinPec
				+ ", canConfirmRefusSinPec=" + canConfirmRefusSinPec + ", canGenereBonSortie=" + canGenereBonSortie
				+ ", canVerifOriginalsPrinted=" + canVerifOriginalsPrinted + ", canSignatureBonSortie="
				+ canSignatureBonSortie + ", canAssignRepairer=" + canAssignRepairer
				+ ", canCancellationAssignmentRepair=" + canCancellationAssignmentRepair + ", canMissionAnExpert="
				+ canMissionAnExpert + ", canMissionExpertCancellation=" + canMissionExpertCancellation
				+ ", canEditVehicleReception=" + canEditVehicleReception + ", canEditQuoteQuote=" + canEditQuoteQuote
				+ ", canEditConfirmationQuote=" + canEditConfirmationQuote + ", canUpdateQuotation="
				+ canUpdateQuotation + ", canValidationQueryrectify=" + canValidationQueryrectify
				+ ", canExpertOpinion=" + canExpertOpinion + ", canSignatureAgreement=" + canSignatureAgreement
				+ ", canConfirmationOfTheSupplementaryQuote=" + canConfirmationOfTheSupplementaryQuote + ", canBilling="
				+ canBilling + ", canDecide=" + canDecide + ", canRepriseSinPec=" + canRepriseSinPec
				+ ", canDismantling=" + canDismantling + ", canConfirmModifPrix=" + canConfirmModifPrix
				+ ", canModifPrix=" + canModifPrix + ", canApprouvApec=" + canApprouvApec + ", canModifApec="
				+ canModifApec + ", canValidApec=" + canValidApec + ", canValidAssurApec=" + canValidAssurApec
				+ ", canImprimApec=" + canImprimApec + ", canConsultDemandPec=" + canConsultDemandPec
				+ ", canAjoutDemandPecExpertise=" + canAjoutDemandPecExpertise + ", canConsultDossier="
				+ canConsultDossier + ", canAjoutDossier=" + canAjoutDossier + ", canModifDossier=" + canModifDossier
				+ "]";
	}

}
