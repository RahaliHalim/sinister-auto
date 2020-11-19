package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A ViewSinisterPec.
 */
@Entity
@Table(name = "view_sinister_pec")
public class ViewSinisterPec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "ga_reference")
    private String gaReference;

    @Column(name = "company_reference")
    private String companyReference;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "immatriculation")
    private String immatriculation;

    @Column(name = "mode_label")
    private String modeLabel;

    @Column(name = "pos_ga_label")
    private String posGaLabel;

    @Column(name = "declaration_date")
    private LocalDateTime declarationDate;

    @Column(name = "incident_date")
    private LocalDate incidentDate;

    @Column(name = "decision")
    private String decision;

    @Column(name = "step_pec_id")
    private Integer step;
    
    @Column(name = "step_pec")
    private String stepPec;

    @Column(name = "approv_pec")
    private String approvPec;

    @Column(name = "expert_id")
    private Long expertId;

    @Column(name = "reparateur_id")
    private Long reparateurId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "assigned_to_id")
    private Long assignedToId;
    
    @Column(name = "primary_quotation_id")
    private Long primaryQuotationId;
    
    @Column(name = "change_modification_prix")	
    private Boolean changeModificationPrix ;
    
    @Column(name = "generated_letter")	
	private Boolean generatedLetter ;
    
    @Column(name = "immatriculation_tier")
    private String immatriculationTier;

    @Column(name = "decision_ida")
    private String decisionIda;
    
    @Column(name = "bareme_code")
    private Integer codeBareme;
    
    @Column(name = "bareme_responsabilite")
    private Integer responsabiliteX;
    
    @Column(name = "old_step")
    private Integer oldStep;
    
    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "date_creation")
    private LocalDateTime creationDate;
    
    @Column(name = "date_cloture")
    private LocalDateTime clotureDate;
    
    @Column(name = "vehicule_id")
    private Long vehiculeId;
    
    @Column(name = "client_id")
    private Long clientId;
    
    @Column(name = "agency_id")
    private Long agencyId;
    
    @Column(name = "charge")
    private String charge;
    
    @Column(name = "expert_raison_sociale")
    private String expertRaisonSociale;
    
    @Column(name = "reparateur_raison_sociale")
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

    public ViewSinisterPec gaReference(String gaReference) {
        this.gaReference = gaReference;
        return this;
    }

    public void setGaReference(String gaReference) {
        this.gaReference = gaReference;
    }

    public String getCompanyReference() {
        return companyReference;
    }

    public ViewSinisterPec companyReference(String companyReference) {
        this.companyReference = companyReference;
        return this;
    }

    public void setCompanyReference(String companyReference) {
        this.companyReference = companyReference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ViewSinisterPec companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public ViewSinisterPec agencyName(String agencyName) {
        this.agencyName = agencyName;
        return this;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public ViewSinisterPec contractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public ViewSinisterPec immatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
        return this;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getModeLabel() {
        return modeLabel;
    }

    public ViewSinisterPec modeLabel(String modeLabel) {
        this.modeLabel = modeLabel;
        return this;
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

    public LocalDateTime getDeclarationDate() {
        return declarationDate;
    }

    public ViewSinisterPec declarationDate(LocalDateTime declarationDate) {
        this.declarationDate = declarationDate;
        return this;
    }

    public void setDeclarationDate(LocalDateTime declarationDate) {
        this.declarationDate = declarationDate;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public ViewSinisterPec incidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
        return this;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getDecision() {
        return decision;
    }

    public ViewSinisterPec decision(String decision) {
        this.decision = decision;
        return this;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getDecisionIda() {
        return decisionIda;
    }

    public void setDecisionIda(String decisionIda) {
        this.decisionIda = decisionIda;
    }

    public Integer getStep() {
        return step;
    }

    public ViewSinisterPec step(Integer step) {
        this.step = step;
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getApprovPec() {
        return approvPec;
    }

    public ViewSinisterPec approvPec(String approvPec) {
        this.approvPec = approvPec;
        return this;
    }

    public void setApprovPec(String approvPec) {
        this.approvPec = approvPec;
    }

    public Long getExpertId() {
        return expertId;
    }

    public ViewSinisterPec expertId(Long expertId) {
        this.expertId = expertId;
        return this;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public ViewSinisterPec reparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
        return this;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getUserId() {
        return userId;
    }

    public ViewSinisterPec userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public ViewSinisterPec assignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
        return this;
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
        ViewSinisterPec viewSinisterPec = (ViewSinisterPec) o;
        if (viewSinisterPec.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), viewSinisterPec.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
	public String toString() {
		return "ViewSinisterPec [id=" + id + ", gaReference=" + gaReference + ", companyReference=" + companyReference
				+ ", companyName=" + companyName + ", agencyName=" + agencyName + ", contractNumber=" + contractNumber
				+ ", immatriculation=" + immatriculation + ", modeLabel=" + modeLabel + ", declarationDate="
				+ declarationDate + ", incidentDate=" + incidentDate + ", decision=" + decision + ", step=" + step
				+ ", stepPec=" + stepPec + ", approvPec=" + approvPec + ", expertId=" + expertId + ", reparateurId="
				+ reparateurId + ", userId=" + userId + ", assignedToId=" + assignedToId + ", primaryQuotationId="
				+ primaryQuotationId + ", changeModificationPrix=" + changeModificationPrix + ", immatriculationTier="
				+ immatriculationTier + ", codeBareme=" + codeBareme + ", responsabiliteX=" + responsabiliteX
				+ ", oldStep=" + oldStep + ", contractId=" + contractId + ", creationDate=" + creationDate
				+ ", clotureDate=" + clotureDate + "]";
	}
}
