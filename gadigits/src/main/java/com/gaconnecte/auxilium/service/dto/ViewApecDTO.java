package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ViewApecDTO implements Serializable {
	
    private Long id;
	
	private Long sinisterPecId;
	
    private String gaReference;
	
    private String companyName;
	
    private Long companyId;
	
    private String agencyName;
	
    private Long agencyId;
	
    private String contractNumber;
	
    private Long contractId;
	
    private String immatriculation;
	
    private String modeLabel;
	
    private LocalDateTime declarationDate;
	
    private LocalDate incidentDate;
	
    private Long stepPecId;
	
    private Integer etatApec;
    
    private Long reparateurId;
    
    private Long modeId;
    
    private Long assignedToId;
    
    private Long sinisterId;
    
    private Long expertId;
    
    private Boolean modificationPrix;
    
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
