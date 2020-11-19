package com.gaconnecte.auxilium.domain;

import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.domain.enumeration.ApprovPec;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A SinisterPec.
 */
@Entity
@Table(name = "app_sinister_pec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sinisterpec")
public class SinisterPec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "reference")
	private String reference;

	@Column(name = "company_reference")
	private String companyReference;

	@Column(name = "declaration_date")
	private LocalDateTime declarationDate;
	
	@Column(name = "date_affect_reparateur")
	private LocalDateTime dateAffectReparateur;
	
	@Column(name = "date_affect_expert")
	private LocalDateTime dateAffectExpert;

	@Column(name = "vehicle_number")
	private Integer vehicleNumber;

	@Column(name = "responsability_rate")
	private Boolean responsabilityRate;

	@Column(name = "remaining_capital", precision = 10, scale = 2)
	private BigDecimal remainingCapital;

	@Column(name = "driver_or_insured")
	private Boolean driverOrInsured;

	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "driver_license_number")
	private String driverLicenseNumber;

	@Column(name = "vehicle_receipt_date")
	private LocalDateTime vehicleReceiptDate;

	@Column(name = "generation_bon_sortie_date")
	private LocalDateTime generationBonSortieDate;

	@Column(name = "assigned_date")
	private LocalDate assignedDate;

	@Column(name = "modif_decision_date")
	private LocalDate modifDecisionDate;

	@Column(name = "motif_changement_status")
	private String motifChangementStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_decision_id")
	private RaisonPec reasonDecision;

	@ManyToOne
	@JoinColumn(name = "reason_refused_id")
	private RaisonPec reasonRefused;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_canceled_id")
	private RaisonPec reasonCanceled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_cancel_affected_rep_id")
	private RaisonPec reasonCancelAffectedRep;

	@ManyToOne(fetch = FetchType.LAZY)
	private Governorate governorate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Delegation delegation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gouvernorat_rep_id")
	private Governorate governorateRep;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ville_rep_id")
	private Delegation delegationRep;

	@ManyToOne
	private RefPositionGa posGa;
	@Column(name = "motif_non_confirme_id")
	private Long motifNonConfirmeId;
	@ManyToOne
	private User user;

	@OneToOne
	@JoinColumn(name = "sinister_id")
	private Sinister sinister;

	@Transient
	private Long quotationStatusId;

	@Transient
	private Long devisId;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "expert_id")
	private Expert expert;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_cancel_expert_id")
	private RaisonPec reasonCancelExpert;

	@ManyToOne
	@JoinColumn(name = "reason_reopened_id")
	private RaisonPec reasonReopened;

	@Column(name = "generated_letter")
	private Boolean generatedLetter;

	@Column(name = "reception_vehicule")
	private Boolean receptionVehicule;

	@Column(name = "light_shock")
	private Boolean lightShock;

	@Column(name = "disassembly_request")
	private Boolean disassemblyRequest;

	@Column(name = "from_demande_ouverture")
	private Boolean fromDemandeOuverture;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "step_pec_id")
	private RefStepPec step;

	@Enumerated(EnumType.STRING)
	@Column(name = "decision", nullable = false)
	private Decision decision;

	@Enumerated(EnumType.STRING)
	@Column(name = "approv_pec")
	private ApprovPec approvPec;

	@ManyToOne(fetch = FetchType.LAZY)
	private RefModeGestion mode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mode_gestion_to_modif")
	private RefModeGestion modeModif;

	@OneToOne(mappedBy = "sinisterPec", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private PointChoc pointChoc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reparateur_id")
	private Reparateur reparateur;

	@Column(name = "appointment_repair_date")
	private LocalDateTime dateRDVReparation;

	@Column(name = "date_update_expert")
	private LocalDateTime dateUpdateExpert;

	@Column(name = "date_creation")
	private LocalDateTime dateCreation;

	@Column(name = "date_modification_after_reserved")
	private LocalDateTime dateModifAfterReserved;

	@Column(name = "date_modification")
	private LocalDateTime dateModification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_to_id")
	private User assignedTo;

	@ManyToOne(fetch = FetchType.LAZY)
	private RefBareme bareme;
	@OneToMany(mappedBy = "sinisterPec", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Tiers> tiers = new HashSet<>();

	@Column(name = "preliminary_report")
	private Boolean preliminaryReport;

	@Column(name = "debloque")
	private Boolean debloque;

	@Column(name = "quote_via_gt")
	private Boolean quoteViaGt;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_quotation_id")
	private PrimaryQuotation primaryQuotation;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Quotation.class, mappedBy = "sinisterPec", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ComplementaryQuotation> listComplementaryQuotation = new HashSet<>();

	@Column(name = "expert_decision")
	private String expertDecision;
	@Column(name = "modification_prix")
	private Boolean modificationPrix;
	@Column(name = "active_modification_prix")
	private Boolean activeModificationPrix;

	@Column(name = "old_step")
	private Long oldStep;
	@Column(name = "change_modification_prix")
	private Boolean changeModificationPrix;
	@Column(name = "old_step_modif_sin_pec")
	private Long oldStepModifSinPec;
	@Column(name = "assujettie_tva")
	private Boolean assujettieTVA;

	@Column(name = "valeur_assure")
	private Double valeurAssure;

	@Column(name = "capital_restant_after_comp")
	private Double capitalRestantAfterComp;

	@Column(name = "totale_franchise")
	private Double totaleFranchise;

	@Column(name = "use_min_franchise")
	private Boolean useMinFranchise;

	@Column(name = "piece_generique")
	private Boolean pieceGenerique;

	@Column(name = "date_cloture")
	private LocalDateTime dateCloture;

	@Column(name = "valeur_neuf_expert")
	private Double valeurANeufExpert;

	@Column(name = "valeur_venale_expert")
	private Double valeurVenaleExpert;

	@Column(name = "old_stepnw")
	private Long oldStepNw;

	@Column(name = "resp_controle_technique_id")
	private Long respControleTechnique;
	
	@Column(name = "quote_id")
	private Long quoteId;
	
	@Column(name = "reprise_step")
	private Long repriseStep;
	
	@Column(name = "reprise_etat")
	private Long repriseEtat;
	
	@Column(name = "annule_refus_date")
	private LocalDateTime annuleRefusDate;
	
	@Column(name = "date_reprise")
	private LocalDateTime dateReprise;

	
	
	@Column(name = "date_validation_premier_accord")
	private LocalDateTime dateValidationPremierAccord;
	
	
	public LocalDateTime getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(LocalDateTime dateCloture) {
		this.dateCloture = dateCloture;
	}

	public LocalDateTime getVehicleReceiptDate() {
		return vehicleReceiptDate;
	}

	public void setVehicleReceiptDate(LocalDateTime vehicleReceiptDate) {
		this.vehicleReceiptDate = vehicleReceiptDate;
	}

	public LocalDate getModifDecisionDate() {
		return modifDecisionDate;
	}

	public void setModifDecisionDate(LocalDate modifDecisionDate) {
		this.modifDecisionDate = modifDecisionDate;
	}

	public RefModeGestion getMode() {
		return mode;
	}

	public Long getRespControleTechnique() {
		return respControleTechnique;
	}

	public void setRespControleTechnique(Long respControleTechnique) {
		this.respControleTechnique = respControleTechnique;
	}

	public void setMode(RefModeGestion mode) {
		this.mode = mode;
	}

	public RaisonPec getReasonCancelExpert() {
		return reasonCancelExpert;
	}

	public RaisonPec getReasonCancelAffectedRep() {
		return reasonCancelAffectedRep;
	}

	public void setReasonCancelAffectedRep(RaisonPec reasonCancelAffectedRep) {
		this.reasonCancelAffectedRep = reasonCancelAffectedRep;
	}

	public LocalDateTime getDateUpdateExpert() {
		return dateUpdateExpert;
	}

	public void setDateUpdateExpert(LocalDateTime dateUpdateExpert) {
		this.dateUpdateExpert = dateUpdateExpert;
	}

	public Long getMotifNonConfirmeId() {
		return motifNonConfirmeId;
	}

	public void setMotifNonConfirmeId(Long motifNonConfirmeId) {
		this.motifNonConfirmeId = motifNonConfirmeId;
	}

	public RaisonPec getReasonDecision() {
		return reasonDecision;
	}

	public void setReasonDecision(RaisonPec reasonDecision) {
		this.reasonDecision = reasonDecision;
	}

	public void setReasonCancelExpert(RaisonPec reasonCancelExpert) {
		this.reasonCancelExpert = reasonCancelExpert;
	}

	public RefModeGestion getModeModif() {
		return modeModif;
	}

	public String getExpertDecision() {
		return expertDecision;
	}

	public void setExpertDecision(String expertDecision) {
		this.expertDecision = expertDecision;
	}

	public void setModeModif(RefModeGestion modeModif) {
		this.modeModif = modeModif;
	}

	public Reparateur getReparateur() {
		return reparateur;
	}

	public void setReparateur(Reparateur reparateur) {
		this.reparateur = reparateur;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public RefBareme getBareme() {
		return bareme;
	}

	public void setBareme(RefBareme bareme) {
		this.bareme = bareme;
	}

	public Long getQuotationStatusId() {
		return quotationStatusId;
	}

	public void setQuotationStatusId(Long quotationStatusId) {
		this.quotationStatusId = quotationStatusId;
	}

	public Long getDevisId() {
		return devisId;
	}

	public LocalDateTime getDateRDVReparation() {
		return dateRDVReparation;
	}

	public void setDateRDVReparation(LocalDateTime dateRDVReparation) {
		this.dateRDVReparation = dateRDVReparation;
	}

	public void setDevisId(Long devisId) {
		this.devisId = devisId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public SinisterPec reference(String reference) {
		this.reference = reference;
		return this;
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

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCompanyReference() {
		return companyReference;
	}

	public SinisterPec companyReference(String companyReference) {
		this.companyReference = companyReference;
		return this;
	}

	public PointChoc getPointChoc() {
		return pointChoc;
	}

	public Boolean getDisassemblyRequest() {
		return disassemblyRequest;
	}

	public void setDisassemblyRequest(Boolean disassemblyRequest) {
		this.disassemblyRequest = disassemblyRequest;
	}

	public Boolean getReceptionVehicule() {
		return receptionVehicule;
	}

	public void setReceptionVehicule(Boolean receptionVehicule) {
		this.receptionVehicule = receptionVehicule;
	}

	public void setPointChoc(PointChoc pointChoc) {
		this.pointChoc = pointChoc;
	}

	public void setCompanyReference(String companyReference) {
		this.companyReference = companyReference;
	}

	public LocalDateTime getDeclarationDate() {
		return declarationDate;
	}

	public SinisterPec declarationDate(LocalDateTime declarationDate) {
		this.declarationDate = declarationDate;
		return this;
	}

	public void setDeclarationDate(LocalDateTime declarationDate) {
		this.declarationDate = declarationDate;
	}

	public Integer getVehicleNumber() {
		return vehicleNumber;
	}

	public SinisterPec vehicleNumber(Integer vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
		return this;
	}

	public void setVehicleNumber(Integer vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Boolean isResponsabilityRate() {
		return responsabilityRate;
	}

	public SinisterPec responsabilityRate(Boolean responsabilityRate) {
		this.responsabilityRate = responsabilityRate;
		return this;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public SinisterPec expert(Expert expert) {
		this.expert = expert;
		return this;
	}

	public void setResponsabilityRate(Boolean responsabilityRate) {
		this.responsabilityRate = responsabilityRate;
	}

	public BigDecimal getRemainingCapital() {
		return remainingCapital;
	}

	public SinisterPec remainingCapital(BigDecimal remainingCapital) {
		this.remainingCapital = remainingCapital;
		return this;
	}

	public void setRemainingCapital(BigDecimal remainingCapital) {
		this.remainingCapital = remainingCapital;
	}

	public Boolean isDriverOrInsured() {
		return driverOrInsured;
	}

	public SinisterPec driverOrInsured(Boolean driverOrInsured) {
		this.driverOrInsured = driverOrInsured;
		return this;
	}

	public void setDriverOrInsured(Boolean driverOrInsured) {
		this.driverOrInsured = driverOrInsured;
	}

	public String getDriverName() {
		return driverName;
	}

	public Boolean getLightShock() {
		return lightShock;
	}

	public void setLightShock(Boolean lightShock) {
		this.lightShock = lightShock;
	}

	public RefStepPec getStep() {
		return step;
	}

	public void setStep(RefStepPec step) {
		this.step = step;
	}

	public SinisterPec driverName(String driverName) {
		this.driverName = driverName;
		return this;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverLicenseNumber() {
		return driverLicenseNumber;
	}

	public SinisterPec driverLicenseNumber(String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
		return this;
	}

	public void setDriverLicenseNumber(String driverLicenseNumber) {
		this.driverLicenseNumber = driverLicenseNumber;
	}

	public Governorate getGovernorate() {
		return governorate;
	}

	public SinisterPec governorate(Governorate governorate) {
		this.governorate = governorate;
		return this;
	}

	public void setGovernorate(Governorate governorate) {
		this.governorate = governorate;
	}

	public Delegation getDelegation() {
		return delegation;
	}

	public SinisterPec delegation(Delegation delegation) {
		this.delegation = delegation;
		return this;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	public Governorate getGovernorateRep() {
		return governorateRep;
	}

	public SinisterPec governorateRep(Governorate governorateRep) {
		this.governorateRep = governorateRep;
		return this;
	}

	public void setGovernorateRep(Governorate governorateRep) {
		this.governorateRep = governorateRep;
	}

	public Delegation getDelegationRep() {
		return delegationRep;
	}

	public SinisterPec delegationRep(Delegation delegationRep) {
		this.delegationRep = delegationRep;
		return this;
	}

	public void setDelegationRep(Delegation delegationRep) {
		this.delegationRep = delegationRep;
	}

	public Sinister getSinister() {
		return sinister;
	}

	public void setSinister(Sinister sinister) {
		this.sinister = sinister;
	}

	public Boolean getResponsabilityRate() {
		return responsabilityRate;
	}

	public Boolean getDriverOrInsured() {
		return driverOrInsured;
	}

	public RefPositionGa getPosGa() {
		return posGa;
	}

	public void setPosGa(RefPositionGa posGa) {
		this.posGa = posGa;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Tiers> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Tiers> tiers) {
		this.tiers = tiers;
	}

	public RaisonPec getReasonReopened() {
		return reasonReopened;
	}

	public PrimaryQuotation getPrimaryQuotation() {
		return primaryQuotation;
	}

	public void setPrimaryQuotation(PrimaryQuotation primaryQuotation) {
		this.primaryQuotation = primaryQuotation;
	}

	public Set<ComplementaryQuotation> getListComplementaryQuotation() {
		return listComplementaryQuotation;
	}

	public void setListComplementaryQuotation(Set<ComplementaryQuotation> listComplementaryQuotation) {
		this.listComplementaryQuotation = listComplementaryQuotation;
	}

	public void setReasonReopened(RaisonPec reasonReopened) {
		this.reasonReopened = reasonReopened;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Boolean getGeneratedLetter() {
		return generatedLetter;
	}

	public void setGeneratedLetter(Boolean generatedLetter) {
		this.generatedLetter = generatedLetter;
	}

	public RaisonPec getReasonRefused() {
		return reasonRefused;
	}

	public void setReasonRefused(RaisonPec reasonRefused) {
		this.reasonRefused = reasonRefused;
	}

	public RaisonPec getReasonCanceled() {
		return reasonCanceled;
	}

	public void setReasonCanceled(RaisonPec reasonCanceled) {
		this.reasonCanceled = reasonCanceled;
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

	public Double getValeurANeufExpert() {
		return valeurANeufExpert;
	}

	public void setValeurANeufExpert(Double valeurANeufExpert) {
		this.valeurANeufExpert = valeurANeufExpert;
	}

	public Double getValeurVenaleExpert() {
		return valeurVenaleExpert;
	}

	public void setValeurVenaleExpert(Double valeurVenaleExpert) {
		this.valeurVenaleExpert = valeurVenaleExpert;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SinisterPec sinisterPec = (SinisterPec) o;
		if (sinisterPec.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sinisterPec.getId());
	}

	
	
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

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SinisterPec [id=" + id + ", reference=" + reference + ", companyReference=" + companyReference
				+ ", declarationDate=" + declarationDate + ", vehicleNumber=" + vehicleNumber + ", responsabilityRate="
				+ responsabilityRate + ", remainingCapital=" + remainingCapital + ", driverOrInsured=" + driverOrInsured
				+ ", driverName=" + driverName + ", driverLicenseNumber=" + driverLicenseNumber
				+ ", vehicleReceiptDate=" + vehicleReceiptDate + ", generationBonSortieDate=" + generationBonSortieDate
				+ ", assignedDate=" + assignedDate + ", modifDecisionDate=" + modifDecisionDate
				+ ", motifChangementStatus=" + motifChangementStatus + ", reasonDecision=" + reasonDecision
				+ ", reasonRefused=" + reasonRefused + ", reasonCanceled=" + reasonCanceled
				+ ", reasonCancelAffectedRep=" + reasonCancelAffectedRep + ", governorate=" + governorate
				+ ", delegation=" + delegation + ", governorateRep=" + governorateRep + ", delegationRep="
				+ delegationRep + ", posGa=" + posGa + ", motifNonConfirmeId=" + motifNonConfirmeId + ", user=" + user
				+ ", sinister=" + sinister + ", quotationStatusId=" + quotationStatusId + ", devisId=" + devisId
				+ ", expert=" + expert + ", reasonCancelExpert=" + reasonCancelExpert + ", reasonReopened="
				+ reasonReopened + ", generatedLetter=" + generatedLetter + ", receptionVehicule=" + receptionVehicule
				+ ", lightShock=" + lightShock + ", disassemblyRequest=" + disassemblyRequest
				+ ", fromDemandeOuverture=" + fromDemandeOuverture + ", step=" + step + ", decision=" + decision
				+ ", approvPec=" + approvPec + ", mode=" + mode + ", modeModif=" + modeModif + ", pointChoc="
				+ pointChoc + ", reparateur=" + reparateur + ", dateRDVReparation=" + dateRDVReparation
				+ ", dateUpdateExpert=" + dateUpdateExpert + ", dateCreation=" + dateCreation
				+ ", dateModifAfterReserved=" + dateModifAfterReserved + ", dateModification=" + dateModification
				+ ", assignedTo=" + assignedTo + ", bareme=" + bareme + ", tiers=" + tiers + ", primaryQuotation="
				+ primaryQuotation + ", listComplementaryQuotation=" + listComplementaryQuotation + ", expertDecision="
				+ expertDecision + ", modificationPrix=" + modificationPrix + ", activeModificationPrix="
				+ activeModificationPrix + ", oldStep=" + oldStep + ", changeModificationPrix=" + changeModificationPrix
				+ ", oldStepModifSinPec=" + oldStepModifSinPec + ", assujettieTVA=" + assujettieTVA + ", valeurAssure="
				+ valeurAssure + ", capitalRestantAfterComp=" + capitalRestantAfterComp + ", totaleFranchise="
				+ totaleFranchise + ", useMinFranchise=" + useMinFranchise + ", pieceGenerique=" + pieceGenerique
				+ ", dateCloture=" + dateCloture + ", valeurANeufExpert=" + valeurANeufExpert + ", valeurVenaleExpert="
				+ valeurVenaleExpert + ", oldStepNw=" + oldStepNw + "]";
	}

	public Long getOldStepNw() {
		return oldStepNw;
	}

	public void setOldStepNw(Long oldStepNw) {
		this.oldStepNw = oldStepNw;
	}
}
