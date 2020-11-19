package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.mapstruct.Mapping;

import com.gaconnecte.auxilium.domain.Tiers;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.domain.enumeration.ApprovPec;
import com.gaconnecte.auxilium.domain.PrimaryQuotation;
import com.gaconnecte.auxilium.service.dto.PrimaryQuotationDTO;
import com.gaconnecte.auxilium.domain.ComplementaryQuotation;

import java.util.Objects;

/**
 * A DTO for the SinisterPec entity.
 */
public class SinisterPecDTO implements Serializable {

	private Long id;
	private String reference;
	private String referenceSinister;
	private LocalDateTime dateRDVReparation;
	private LocalDateTime dateUpdateExpert;
	private LocalDateTime dateAffectReparateur;	
	private LocalDateTime dateAffectExpert;
	private String companyReference;
	private LocalDateTime declarationDate;
	private Integer vehicleNumber;
	private Boolean responsabilityRate;
	private BigDecimal remainingCapital;
	private Double valeurNeuf;
	private Boolean driverOrInsured;
	private BigDecimal franchise;
	private String driverName;
	private String driverLicenseNumber;
	private Long governorateId;
	private String governorateLabel;
	private Long stepId;
	private Long reasonCancelAffectedRepId;
	private Long quoteHistoryId;
	private Long motifsReopenedId;
	private Long modeId;
	private Long posGaId;
	private String labelPosGa;
	private Long modeModifId;
	private String labelModeGestion;
	private Long baremeId;
	private Integer codeBareme;
	private Integer responsabiliteX;
	private Long sinisterPec;
	private Long delegationId;
	private Long primaryQuotationId;
	private Long sinisterId;
	private Long partnerId;
	private Long agencyId;
	private String companyName;
	private Boolean lightShock;
	private String agentName;
	private String agentLastName;
	private String agenceName;
	private String reparateurName;
	private String contractNumber;
	private String immatriculationVehicle;
	private Long expertId;
	private String expertName;
	private Long reparateurId;
	private Long villeRepId;
	private Long gouvernoratRepId;
	private LocalDate incidentDate;
	private Long reasonCancelExpertId;
	private Decision decision;
	private Long userId;
	private Boolean debloque;
	private LocalDate assignedDate;
	private LocalDate modifDecisionDate;
	private Boolean generatedLetter;
	private Long reasonRefusedId;
	private Long reasonCanceledId;
	private LocalDateTime vehicleReceiptDate;
	private Double marketValue;
	private Boolean isAssujettieTva;
	private Long insuredId;
	private String motifChangementStatus;
	private LocalDateTime dateCreation;
	private LocalDateTime dateModifAfterReserved;
	private LocalDateTime dateModification;
	private Long motifsDecisionId;
	private PointChocDTO pointChoc;
	private ApprovPec approvPec;
	private Long assignedToId;
	private String assignedTofirstName;
	private String assignedTolastName;
	@DiffIgnore
	private Set<TiersDTO> tiers = new HashSet<>();
	private Set<RefMotifDTO> motifs = new HashSet<>();
	private Boolean receptionVehicule;
	private Boolean disassemblyRequest;
	private String reparationStep;
	@DiffIgnore
	private String expertDecision;
	private QuotationDTO quotation;
	@DiffIgnore
	private PrimaryQuotationDTO primaryQuotation;
	@DiffIgnore
	private Set<ComplementaryQuotationDTO> listComplementaryQuotation = new HashSet<>();
	private String phone;
	private String insuredName;
	private String insuredSurName;
	private String historyAvisExpert;
	private Boolean modificationPrix;
	private Boolean activeModificationPrix;
	private Long oldStep;
	private Boolean changeModificationPrix;
	private LocalDateTime generationBonSortieDate;
	private Long oldStepModifSinPec;
	private Long motifNonConfirmeId;
	private String stepLabel;
	private Boolean fromDemandeOuverture;
	private Double dcCapitalFranchise;
	private String franchiseTypeDcCapital;
	private Double newValueVehicleFarnchise;
	private String franchiseTypeNewValue;
	private Double bgCapital;
	private Double bgCapitalFranchise;
	private String franchiseTypeBgCapital;
	private Double marketValueFranchise;
	private String franchiseTypeMarketValue;
	private Boolean assujettieTVA;
	private Double valeurAssure;
	private Double capitalRestantAfterComp;
	private Double totaleFranchise;
	private Boolean useMinFranchise;
	private Boolean pieceGenerique;
	private LocalDateTime dateCloture;
	private Double valeurVenaleExpert;
	private Double valeurANeufExpert;
	private Long oldStepNw;
	private Boolean testDevisCmpl;
	private Boolean testModifPrix;
	private BigDecimal insuredCapital;
	private String governorateRepLabel;
	private String motifsDecisionLabel;
	private String reasonCanceledLabel;
	private String reasonRefusedLabel;
	private String conditionVehicle;
	private Boolean repairableVehicle;
	private Boolean preliminaryReport;
	private Boolean concordanceReport;
	private Boolean immobilizedVehicle;
	private Long respControleTechnique;
	private Boolean quoteViaGt;
	private Long quoteId;
	private LocalDateTime annuleRefusDate;
	private Long repriseStep;
	private Long repriseEtat;
	private LocalDateTime dateReprise;
	private LocalDateTime dateValidationPremierAccord;
	private Long vehiculeId;
	
	
	
	public LocalDateTime getDateAffectReparateur() {
		return dateAffectReparateur;
	}

	public void setDateAffectReparateur(LocalDateTime dateAffectReparateur) {
		this.dateAffectReparateur = dateAffectReparateur;
	}

	public LocalDateTime getDateAffectExpert() {
		return dateAffectExpert;
	}

	public void setDateAffectExpert(LocalDateTime dateAffectExpert) {
		this.dateAffectExpert = dateAffectExpert;
	}

	public String getConditionVehicle() {
		return conditionVehicle;
	}

	public void setConditionVehicle(String conditionVehicle) {
		this.conditionVehicle = conditionVehicle;
	}

	public Boolean getRepairableVehicle() {
		return repairableVehicle;
	}

	public void setRepairableVehicle(Boolean repairableVehicle) {
		this.repairableVehicle = repairableVehicle;
	}

	public Boolean getConcordanceReport() {
		return concordanceReport;
	}

	public void setConcordanceReport(Boolean concordanceReport) {
		this.concordanceReport = concordanceReport;
	}

	public Boolean getImmobilizedVehicle() {
		return immobilizedVehicle;
	}

	public void setImmobilizedVehicle(Boolean immobilizedVehicle) {
		this.immobilizedVehicle = immobilizedVehicle;
	}

	public String getReasonCanceledLabel() {
		return reasonCanceledLabel;
	}

	public void setReasonCanceledLabel(String reasonCanceledLabel) {
		this.reasonCanceledLabel = reasonCanceledLabel;
	}

	public String getReasonRefusedLabel() {
		return reasonRefusedLabel;
	}

	public void setReasonRefusedLabel(String reasonRefusedLabel) {
		this.reasonRefusedLabel = reasonRefusedLabel;
	}

	public String getMotifsDecisionLabel() {
		return motifsDecisionLabel;
	}

	public void setMotifsDecisionLabel(String motifsDecisionLabel) {
		this.motifsDecisionLabel = motifsDecisionLabel;
	}

	private String motifsReopenedLabel;

	public String getMotifsReopenedLabel() {
		return motifsReopenedLabel;
	}

	public void setMotifsReopenedLabel(String motifsReopenedLabel) {
		this.motifsReopenedLabel = motifsReopenedLabel;
	}

	public String getGovernorateRepLabel() {
		return governorateRepLabel;
	}

	public void setGovernorateRepLabel(String governorateRepLabel) {
		this.governorateRepLabel = governorateRepLabel;
	}

	public Boolean getTestModifPrix() {
		return testModifPrix;
	}

	public void setTestModifPrix(Boolean testModifPrix) {
		this.testModifPrix = testModifPrix;
	}

	public Boolean getTestDevisCmpl() {
		return testDevisCmpl;
	}

	public void setTestDevisCmpl(Boolean testDevisCmpl) {
		this.testDevisCmpl = testDevisCmpl;
	}

	public String getAssignedTofirstName() {
		return assignedTofirstName;
	}

	public void setAssignedTofirstName(String assignedTofirstName) {
		this.assignedTofirstName = assignedTofirstName;
	}

	public String getAssignedTolastName() {
		return assignedTolastName;
	}

	public void setAssignedTolastName(String assignedTolastName) {
		this.assignedTolastName = assignedTolastName;
	}

	public Long getOldStepNw() {
		return oldStepNw;
	}

	public void setOldStepNw(Long oldStepNw) {
		this.oldStepNw = oldStepNw;
	}

	public LocalDateTime getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(LocalDateTime dateCloture) {
		this.dateCloture = dateCloture;
	}

	public Long getRespControleTechnique() {
		return respControleTechnique;
	}

	public void setRespControleTechnique(Long respControleTechnique) {
		this.respControleTechnique = respControleTechnique;
	}

	public String getStepLabel() {
		return stepLabel;
	}

	public void setStepLabel(String stepLabel) {
		this.stepLabel = stepLabel;
	}

	public LocalDate getModifDecisionDate() {
		return modifDecisionDate;
	}

	public void setModifDecisionDate(LocalDate modifDecisionDate) {
		this.modifDecisionDate = modifDecisionDate;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getReasonCancelAffectedRepId() {
		return reasonCancelAffectedRepId;
	}

	public void setReasonCancelAffectedRepId(Long reasonCancelAffectedRepId) {
		this.reasonCancelAffectedRepId = reasonCancelAffectedRepId;
	}

	public Long getQuoteHistoryId() {
		return quoteHistoryId;
	}

	public void setQuoteHistoryId(Long quoteHistoryId) {
		this.quoteHistoryId = quoteHistoryId;
	}

	public LocalDateTime getDateUpdateExpert() {
		return dateUpdateExpert;
	}

	public void setDateUpdateExpert(LocalDateTime dateUpdateExpert) {
		this.dateUpdateExpert = dateUpdateExpert;
	}

	public Long getPrimaryQuotationId() {
		return primaryQuotationId;
	}

	public void setPrimaryQuotationId(Long primaryQuotationId) {
		this.primaryQuotationId = primaryQuotationId;
	}

	public Boolean getResponsabilityRate() {
		return responsabilityRate;
	}

	/*
	 * public Set<ObservationDTO> getObservations() { return observations; }
	 * 
	 * public void setObservations(Set<ObservationDTO> observations) {
	 * this.observations = observations; }
	 */

	public LocalDateTime getVehicleReceiptDate() {
		return vehicleReceiptDate;
	}

	public void setVehicleReceiptDate(LocalDateTime vehicleReceiptDate) {
		this.vehicleReceiptDate = vehicleReceiptDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferenceSinister() {
		return referenceSinister;
	}

	public void setReferenceSinister(String referenceSinister) {
		this.referenceSinister = referenceSinister;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getMotifNonConfirmeId() {
		return motifNonConfirmeId;
	}

	public void setMotifNonConfirmeId(Long motifNonConfirmeId) {
		this.motifNonConfirmeId = motifNonConfirmeId;
	}

	public String getCompanyReference() {
		return companyReference;
	}

	public Long getReasonCancelExpertId() {
		return reasonCancelExpertId;
	}

	public void setReasonCancelExpertId(Long reasonCancelExpertId) {
		this.reasonCancelExpertId = reasonCancelExpertId;
	}

	public Boolean getLightShock() {
		return lightShock;
	}

	public void setLightShock(Boolean lightShock) {
		this.lightShock = lightShock;
	}

	public void setCompanyReference(String companyReference) {
		this.companyReference = companyReference;
	}

	public String getExpertDecision() {
		return expertDecision;
	}

	public void setExpertDecision(String expertDecision) {
		this.expertDecision = expertDecision;
	}

	public Long getMotifsDecisionId() {
		return motifsDecisionId;
	}

	public void setMotifsDecisionId(Long motifsDecisionId) {
		this.motifsDecisionId = motifsDecisionId;
	}

	public Boolean getReceptionVehicule() {
		return receptionVehicule;
	}

	public void setReceptionVehicule(Boolean receptionVehicule) {
		this.receptionVehicule = receptionVehicule;
	}

	public LocalDateTime getDateRDVReparation() {
		return dateRDVReparation;
	}

	public void setDateRDVReparation(LocalDateTime dateRDVReparation) {
		this.dateRDVReparation = dateRDVReparation;
	}

	public String getReparateurName() {
		return reparateurName;
	}

	public void setReparateurName(String reparateurName) {
		this.reparateurName = reparateurName;
	}

	public Long getBaremeId() {
		return baremeId;
	}

	public void setBaremeId(Long baremeId) {
		this.baremeId = baremeId;
	}

	public Boolean getDisassemblyRequest() {
		return disassemblyRequest;
	}

	public void setDisassemblyRequest(Boolean disassemblyRequest) {
		this.disassemblyRequest = disassemblyRequest;
	}

	public Integer getCodeBareme() {
		return codeBareme;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	public String getAgenceName() {
		return agenceName;
	}

	public void setAgenceName(String agenceName) {
		this.agenceName = agenceName;
	}

	public String getAgentLastName() {
		return agentLastName;
	}

	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}

	public Double getValeurNeuf() {
		return valeurNeuf;
	}

	public void setValeurNeuf(Double valeurNeuf) {
		this.valeurNeuf = valeurNeuf;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public void setCodeBareme(Integer codeBareme) {
		this.codeBareme = codeBareme;
	}

	public Integer getResponsabiliteX() {
		return responsabiliteX;
	}

	public void setResponsabiliteX(Integer responsabiliteX) {
		this.responsabiliteX = responsabiliteX;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public PointChocDTO getPointChoc() {
		return pointChoc;
	}

	public void setPointChoc(PointChocDTO pointChoc) {
		this.pointChoc = pointChoc;
	}

	public LocalDateTime getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(LocalDateTime declarationDate) {
		this.declarationDate = declarationDate;
	}

	public Integer getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(Integer vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Boolean isResponsabilityRate() {
		return responsabilityRate;
	}

	public void setResponsabilityRate(Boolean responsabilityRate) {
		this.responsabilityRate = responsabilityRate;
	}

	public BigDecimal getRemainingCapital() {
		return remainingCapital;
	}

	public void setRemainingCapital(BigDecimal remainingCapital) {
		this.remainingCapital = remainingCapital;
	}

	public BigDecimal getInsuredCapital() {
		return insuredCapital;
	}

	public void setInsuredCapital(BigDecimal insuredCapital) {
		this.insuredCapital = insuredCapital;
	}

	public Boolean isDriverOrInsured() {
		return driverOrInsured;
	}

	public void setDriverOrInsured(Boolean driverOrInsured) {
		this.driverOrInsured = driverOrInsured;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Long getModeId() {
		return modeId;
	}

	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public void setDriverLicenseNumber(String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public Long getGovernorateId() {
		return governorateId;
	}

	public void setGovernorateId(Long governorateId) {
		this.governorateId = governorateId;
	}

	public Long getDelegationId() {
		return delegationId;
	}

	public void setDelegationId(Long delegationId) {
		this.delegationId = delegationId;
	}

	public Long getSinisterId() {
		return sinisterId;
	}

	public void setSinisterId(Long sinisterId) {
		this.sinisterId = sinisterId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getImmatriculationVehicle() {
		return immatriculationVehicle;
	}

	public void setImmatriculationVehicle(String immatriculationVehicle) {
		this.immatriculationVehicle = immatriculationVehicle;
	}

	public String getLabelModeGestion() {
		return labelModeGestion;
	}

	public void setLabelModeGestion(String labelModeGestion) {
		this.labelModeGestion = labelModeGestion;
	}

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public Set<TiersDTO> getTiers() {
		return tiers;
	}

	public void setTiers(Set<TiersDTO> tiers) {
		this.tiers = tiers;
	}

	public Set<RefMotifDTO> getMotifs() {
		return motifs;
	}

	public BigDecimal getFranchise() {
		return franchise;
	}

	public void setFranchise(BigDecimal franchise) {
		this.franchise = franchise;
	}

	public void setMotifs(Set<RefMotifDTO> motifs) {
		this.motifs = motifs;
	}

	public Decision getDecision() {
		return decision;
	}

	public void setDecision(Decision decision) {
		this.decision = decision;
	}

	public ApprovPec getApprovPec() {
		return approvPec;
	}

	public void setApprovPec(ApprovPec approvPec) {
		this.approvPec = approvPec;
	}

	public Boolean getDriverOrInsured() {

		return driverOrInsured;
	}

	public Long getSinisterPec() {
		return sinisterPec;
	}

	public void setSinisterPec(Long sinisterPec) {
		this.sinisterPec = sinisterPec;
	}

	public Long getPosGaId() {
		return posGaId;
	}

	public void setPosGaId(Long posGaId) {
		this.posGaId = posGaId;
	}

	public String getLabelPosGa() {
		return labelPosGa;
	}

	public void setLabelPosGa(String labelPosGa) {
		this.labelPosGa = labelPosGa;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGouvernoratRepId() {
		return gouvernoratRepId;
	}

	public void setGouvernoratRepId(Long gouvernoratRepId) {
		this.gouvernoratRepId = gouvernoratRepId;
	}

	public Long getVilleRepId() {
		return villeRepId;
	}

	public void setVilleRepId(Long villeRepId) {
		this.villeRepId = villeRepId;
	}

	public String getReparationStep() {
		return reparationStep;
	}

	public void setReparationStep(String reparationStep) {
		this.reparationStep = reparationStep;
	}

	public Long getModeModifId() {
		return modeModifId;
	}

	public void setModeModifId(Long modeModifId) {
		this.modeModifId = modeModifId;
	}

	public Long getMotifsReopenedId() {
		return motifsReopenedId;
	}

	public void setMotifsReopenedId(Long motifsReopenedId) {
		this.motifsReopenedId = motifsReopenedId;

	}

	public PrimaryQuotationDTO getPrimaryQuotation() {
		return primaryQuotation;
	}

	public Long getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(Long assignedToId) {
		this.assignedToId = assignedToId;
	}

	public void setPrimaryQuotation(PrimaryQuotationDTO primaryQuotation) {
		this.primaryQuotation = primaryQuotation;
	}

	public QuotationDTO getQuotation() {
		return quotation;
	}

	public void setQuotation(QuotationDTO quotation) {
		this.quotation = quotation;
	}

	public Set<ComplementaryQuotationDTO> getListComplementaryQuotation() {
		return listComplementaryQuotation;
	}

	public void setListComplementaryQuotation(Set<ComplementaryQuotationDTO> listComplementaryQuotation) {
		this.listComplementaryQuotation = listComplementaryQuotation;
	}

	public Boolean getGeneratedLetter() {
		return generatedLetter;
	}

	public void setGeneratedLetter(Boolean generatedLetter) {
		this.generatedLetter = generatedLetter;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public Long getReasonRefusedId() {
		return reasonRefusedId;
	}

	public void setReasonRefusedId(Long reasonRefusedId) {
		this.reasonRefusedId = reasonRefusedId;
	}

	public Long getReasonCanceledId() {
		return reasonCanceledId;
	}

	public void setReasonCanceledId(Long reasonCanceledId) {
		this.reasonCanceledId = reasonCanceledId;
	}

	public Boolean getIsAssujettieTva() {
		return isAssujettieTva;
	}

	public void setIsAssujettieTva(Boolean isAssujettieTva) {
		this.isAssujettieTva = isAssujettieTva;
	}

	public Long getInsuredId() {
		return insuredId;
	}

	public void setInsuredId(Long insuredId) {
		this.insuredId = insuredId;
	}

	public String getMotifChangementStatus() {
		return motifChangementStatus;
	}

	public void setMotifChangementStatus(String motifChangementStatus) {
		this.motifChangementStatus = motifChangementStatus;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDateTime getDateModifAfterReserved() {
		return dateModifAfterReserved;
	}

	public void setDateModifAfterReserved(LocalDateTime dateModifAfterReserved) {
		this.dateModifAfterReserved = dateModifAfterReserved;
	}

	public LocalDateTime getDateModification() {
		return dateModification;
	}

	public void setDateModification(LocalDateTime dateModification) {
		this.dateModification = dateModification;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getInsuredSurName() {
		return insuredSurName;
	}

	public void setInsuredSurName(String insuredSurName) {
		this.insuredSurName = insuredSurName;
	}

	public String getHistoryAvisExpert() {
		return historyAvisExpert;
	}

	public void setHistoryAvisExpert(String historyAvisExpert) {
		this.historyAvisExpert = historyAvisExpert;
	}

	public Boolean getModificationPrix() {
		return modificationPrix;
	}

	public void setModificationPrix(Boolean modificationPrix) {
		this.modificationPrix = modificationPrix;
	}

	public Boolean getActiveModificationPrix() {
		return activeModificationPrix;
	}

	public void setActiveModificationPrix(Boolean activeModificationPrix) {
		this.activeModificationPrix = activeModificationPrix;
	}

	public Long getOldStep() {
		return oldStep;
	}

	public void setOldStep(Long oldStep) {
		this.oldStep = oldStep;
	}

	public Boolean getChangeModificationPrix() {
		return changeModificationPrix;
	}

	public void setChangeModificationPrix(Boolean changeModificationPrix) {
		this.changeModificationPrix = changeModificationPrix;
	}

	public LocalDateTime getGenerationBonSortieDate() {
		return generationBonSortieDate;
	}

	public void setGenerationBonSortieDate(LocalDateTime generationBonSortieDate) {
		this.generationBonSortieDate = generationBonSortieDate;
	}

	public Long getOldStepModifSinPec() {
		return oldStepModifSinPec;
	}

	public void setOldStepModifSinPec(Long oldStepModifSinPec) {
		this.oldStepModifSinPec = oldStepModifSinPec;
	}

	public Boolean getFromDemandeOuverture() {
		return fromDemandeOuverture;
	}

	public void setFromDemandeOuverture(Boolean fromDemandeOuverture) {
		this.fromDemandeOuverture = fromDemandeOuverture;
	}

	public Double getDcCapitalFranchise() {
		return dcCapitalFranchise;
	}

	public void setDcCapitalFranchise(Double dcCapitalFranchise) {
		this.dcCapitalFranchise = dcCapitalFranchise;
	}

	public String getFranchiseTypeDcCapital() {
		return franchiseTypeDcCapital;
	}

	public void setFranchiseTypeDcCapital(String franchiseTypeDcCapital) {
		this.franchiseTypeDcCapital = franchiseTypeDcCapital;
	}

	public Double getNewValueVehicleFarnchise() {
		return newValueVehicleFarnchise;
	}

	public void setNewValueVehicleFarnchise(Double newValueVehicleFarnchise) {
		this.newValueVehicleFarnchise = newValueVehicleFarnchise;
	}

	public String getFranchiseTypeNewValue() {
		return franchiseTypeNewValue;
	}

	public void setFranchiseTypeNewValue(String franchiseTypeNewValue) {
		this.franchiseTypeNewValue = franchiseTypeNewValue;
	}

	public Double getBgCapital() {
		return bgCapital;
	}

	public void setBgCapital(Double bgCapital) {
		this.bgCapital = bgCapital;
	}

	public Double getBgCapitalFranchise() {
		return bgCapitalFranchise;
	}

	public void setBgCapitalFranchise(Double bgCapitalFranchise) {
		this.bgCapitalFranchise = bgCapitalFranchise;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getFranchiseTypeBgCapital() {
		return franchiseTypeBgCapital;
	}

	public void setFranchiseTypeBgCapital(String franchiseTypeBgCapital) {
		this.franchiseTypeBgCapital = franchiseTypeBgCapital;
	}

	public Double getMarketValueFranchise() {
		return marketValueFranchise;
	}

	public void setMarketValueFranchise(Double marketValueFranchise) {
		this.marketValueFranchise = marketValueFranchise;
	}

	public String getFranchiseTypeMarketValue() {
		return franchiseTypeMarketValue;
	}

	public void setFranchiseTypeMarketValue(String franchiseTypeMarketValue) {
		this.franchiseTypeMarketValue = franchiseTypeMarketValue;
	}

	public Boolean getAssujettieTVA() {
		return assujettieTVA;
	}

	public void setAssujettieTVA(Boolean assujettieTVA) {
		this.assujettieTVA = assujettieTVA;
	}

	public Double getValeurAssure() {
		return valeurAssure;
	}

	public void setValeurAssure(Double valeurAssure) {
		this.valeurAssure = valeurAssure;
	}

	public Double getCapitalRestantAfterComp() {
		return capitalRestantAfterComp;
	}

	public void setCapitalRestantAfterComp(Double capitalRestantAfterComp) {
		this.capitalRestantAfterComp = capitalRestantAfterComp;
	}

	public Double getTotaleFranchise() {
		return totaleFranchise;
	}

	public void setTotaleFranchise(Double totaleFranchise) {
		this.totaleFranchise = totaleFranchise;
	}

	public Boolean getUseMinFranchise() {
		return useMinFranchise;
	}

	public void setUseMinFranchise(Boolean useMinFranchise) {
		this.useMinFranchise = useMinFranchise;
	}

	public Boolean getPieceGenerique() {
		return pieceGenerique;
	}

	public void setPieceGenerique(Boolean pieceGenerique) {
		this.pieceGenerique = pieceGenerique;
	}

	public Double getValeurVenaleExpert() {
		return valeurVenaleExpert;
	}

	public void setValeurVenaleExpert(Double valeurVenaleExpert) {
		this.valeurVenaleExpert = valeurVenaleExpert;
	}

	public Double getValeurANeufExpert() {
		return valeurANeufExpert;
	}

	public void setValeurANeufExpert(Double valeurANeufExpert) {
		this.valeurANeufExpert = valeurANeufExpert;
	}

	public Boolean getPreliminaryReport() {
		return preliminaryReport;
	}

	public void setPreliminaryReport(Boolean preliminaryReport) {
		this.preliminaryReport = preliminaryReport;
	}

	public Boolean getDebloque() {
		return debloque;
	}

	public void setDebloque(Boolean debloque) {
		this.debloque = debloque;
	}

	public Boolean getQuoteViaGt() {
		return quoteViaGt;
	}

	public void setQuoteViaGt(Boolean quoteViaGt) {
		this.quoteViaGt = quoteViaGt;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public LocalDateTime getAnnuleRefusDate() {
		return annuleRefusDate;
	}

	public void setAnnuleRefusDate(LocalDateTime annuleRefusDate) {
		this.annuleRefusDate = annuleRefusDate;
	}

	public Long getRepriseStep() {
		return repriseStep;
	}

	public void setRepriseStep(Long repriseStep) {
		this.repriseStep = repriseStep;
	}

	public Long getRepriseEtat() {
		return repriseEtat;
	}

	public void setRepriseEtat(Long repriseEtat) {
		this.repriseEtat = repriseEtat;
	}

	
	
	public LocalDateTime getDateReprise() {
		return dateReprise;
	}

	public void setDateReprise(LocalDateTime dateReprise) {
		this.dateReprise = dateReprise;
	}

	public LocalDateTime getDateValidationPremierAccord() {
		return dateValidationPremierAccord;
	}

	public void setDateValidationPremierAccord(LocalDateTime dateValidationPremierAccord) {
		this.dateValidationPremierAccord = dateValidationPremierAccord;
	}

	public Long getVehiculeId() {
		return vehiculeId;
	}

	public void setVehiculeId(Long vehiculeId) {
		this.vehiculeId = vehiculeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SinisterPecDTO sinisterPecDTO = (SinisterPecDTO) o;
		if (sinisterPecDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sinisterPecDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SinisterPecDTO [id=" + id  + "]";
	}

	public String getGovernorateLabel() {
		return governorateLabel;
	}

	public void setGovernorateLabel(String governorateLabel) {
		this.governorateLabel = governorateLabel;
	}
}
