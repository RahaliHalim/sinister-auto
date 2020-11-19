package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;

/**
 * A DTO for the ViewSinisterPec entity.
 */
public class ViewSinisterPecDTO implements Serializable {

    private Long id;

    private String gaReference;

    private String companyReference;

    private String companyName;

    private String agencyName;

    private String contractNumber;

    private String immatriculation;

    private String modeLabel;

    private String posGaLabel;

    private String decisionIda;

    private LocalDateTime declarationDate;

    private LocalDate incidentDate;

    private String decision;

    private Integer step;

    private String approvPec;

    private Long expertId;

    private Long reparateurId;

    private Long userId;

    private Long assignedToId;
    
    private Long primaryQuotationId;
    
    private Boolean changeModificationPrix ;

    private Boolean generatedLetter ;
    
    private String immatriculationTier;
    
    private Integer codeBareme;
    
    private Integer responsabiliteX;
    
    private Integer oldStep;
    
    private Long contractId;

    private LocalDateTime creationDate;
    
    private LocalDateTime clotureDate;
    
    private String stepPec;
    
    private Long vehiculeId;
    
    private Long clientId;
    
    private Long agencyId;
    
    private String charge;
    
    private String expertRaisonSociale;
    
    private String reparateurRaisonSociale;

    
    
    public String getStepPec() {
		return stepPec;
	}

	public void setStepPec(String stepPec) {
		this.stepPec = stepPec;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getClotureDate() {
		return clotureDate;
	}

	public void setClotureDate(LocalDateTime clotureDate) {
		this.clotureDate = clotureDate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGaReference() {
        return gaReference;
    }

    public void setGaReference(String gaReference) {
        this.gaReference = gaReference;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public void setCompanyReference(String companyReference) {
        this.companyReference = companyReference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
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

    public String getPosGaLabel() {
        return posGaLabel;
    }

    public void setPosGaLabel(String posGaLabel) {
        this.posGaLabel = posGaLabel;
    }

    public String getDecisionIda() {
        return decisionIda;
    }

    public void setDecisionIda(String decisionIda) {
        this.decisionIda = decisionIda;
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

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getApprovPec() {
        return approvPec;
    }

    public void setApprovPec(String approvPec) {
        this.approvPec = approvPec;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Long getPrimaryQuotationId() {
		return primaryQuotationId;
	}

	public void setPrimaryQuotationId(Long primaryQuotationId) {
		this.primaryQuotationId = primaryQuotationId;
	}

	public Boolean getChangeModificationPrix() {
		return changeModificationPrix;
	}

	public void setChangeModificationPrix(Boolean changeModificationPrix) {
		this.changeModificationPrix = changeModificationPrix;
    }
    
    public Boolean getGeneratedLetter() {
		return generatedLetter;
	}

	public void setGeneratedLetter(Boolean generatedLetter) {
		this.generatedLetter = generatedLetter;
	}

	public String getImmatriculationTier() {
		return immatriculationTier;
	}

	public void setImmatriculationTier(String immatriculationTier) {
		this.immatriculationTier = immatriculationTier;
	}

	public Integer getCodeBareme() {
		return codeBareme;
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

	public Integer getOldStep() {
		return oldStep;
	}

	public void setOldStep(Integer oldStep) {
		this.oldStep = oldStep;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	

	public Long getVehiculeId() {
		return vehiculeId;
	}

	public void setVehiculeId(Long vehiculeId) {
		this.vehiculeId = vehiculeId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getExpertRaisonSociale() {
		return expertRaisonSociale;
	}

	public void setExpertRaisonSociale(String expertRaisonSociale) {
		this.expertRaisonSociale = expertRaisonSociale;
	}

	public String getReparateurRaisonSociale() {
		return reparateurRaisonSociale;
	}

	public void setReparateurRaisonSociale(String reparateurRaisonSociale) {
		this.reparateurRaisonSociale = reparateurRaisonSociale;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViewSinisterPecDTO viewSinisterPecDTO = (ViewSinisterPecDTO) o;
        if(viewSinisterPecDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), viewSinisterPecDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "ViewSinisterPecDTO [id=" + id + ", gaReference=" + gaReference + ", companyReference="
				+ companyReference + ", companyName=" + companyName + ", agencyName=" + agencyName + ", contractNumber="
				+ contractNumber + ", immatriculation=" + immatriculation + ", modeLabel=" + modeLabel
				+ ", declarationDate=" + declarationDate + ", incidentDate=" + incidentDate + ", decision=" + decision
				+ ", step=" + step + ", approvPec=" + approvPec + ", expertId=" + expertId + ", reparateurId="
				+ reparateurId + ", userId=" + userId + ", assignedToId=" + assignedToId + ", primaryQuotationId="
				+ primaryQuotationId + ", changeModificationPrix=" + changeModificationPrix + ", immatriculationTier="
				+ immatriculationTier + ", codeBareme=" + codeBareme + ", responsabiliteX=" + responsabiliteX
				+ ", oldStep=" + oldStep + ", contractId=" + contractId + ", creationDate=" + creationDate
				+ ", clotureDate=" + clotureDate + ", stepPec=" + stepPec + "]";
	}
}
