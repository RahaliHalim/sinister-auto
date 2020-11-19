package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_apec")
public class ViewApec implements Serializable{
	
	@Id
    private Long id;
	
	@Column(name = "sinister_pec_id")
	private Long sinisterPecId;
	
	@Column(name = "ga_reference")
    private String gaReference;
	
	@Column(name = "company_name")
    private String companyName;
	
	@Column(name = "company_id")
    private Long companyId;
	
	@Column(name = "agency_name")
    private String agencyName;
	
	@Column(name = "agency_id")
    private Long agencyId;
	
	@Column(name = "contract_number")
    private String contractNumber;
	
	@Column(name = "contract_id")
    private Long contractId;
	
	@Column(name = "immatriculation")
    private String immatriculation;
	
	@Column(name = "mode_label")
    private String modeLabel;
	
	@Column(name = "mode_id")
    private Long modeId;
	
	@Column(name = "declaration_date")
    private LocalDateTime declarationDate;
	
	@Column(name = "incident_date")
    private LocalDate incidentDate;
	
	@Column(name = "step_pec_id")
    private Long stepPecId;
	
	@Column(name = "etat_apec")
    private Integer etatApec;
	
	@Column(name = "reparateur_id")
    private Long reparateurId;
	
	@Column(name = "assigned_to_id")
    private Long assignedToId;
	
	@Column(name = "sinister_id")
    private Long sinisterId;
    
	@Column(name = "expert_id")
    private Long expertId;
	
	@Column(name = "modification_prix")
    private Boolean modificationPrix;
	
	@Column(name = "active_modification_prix")
    private Boolean activeModificationPrix;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSinisterPecId() {
		return sinisterPecId;
	}

	public void setSinisterPecId(Long sinisterPecId) {
		this.sinisterPecId = sinisterPecId;
	}

	public String getGaReference() {
		return gaReference;
	}

	public void setGaReference(String gaReference) {
		this.gaReference = gaReference;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getModeLabel() {
		return modeLabel;
	}

	public void setModeLabel(String modeLabel) {
		this.modeLabel = modeLabel;
	}

	public LocalDateTime getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(LocalDateTime declarationDate) {
		this.declarationDate = declarationDate;
	}

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public Long getStepPecId() {
		return stepPecId;
	}

	public void setStepPecId(Long stepPecId) {
		this.stepPecId = stepPecId;
	}

	public Integer getEtatApec() {
		return etatApec;
	}

	public void setEtatApec(Integer etatApec) {
		this.etatApec = etatApec;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	public Long getModeId() {
		return modeId;
	}

	public void setModeId(Long modeId) {
		this.modeId = modeId;
	}

	public Long getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(Long assignedToId) {
		this.assignedToId = assignedToId;
	}

	public Long getSinisterId() {
		return sinisterId;
	}

	public void setSinisterId(Long sinisterId) {
		this.sinisterId = sinisterId;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
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
	
	
}
