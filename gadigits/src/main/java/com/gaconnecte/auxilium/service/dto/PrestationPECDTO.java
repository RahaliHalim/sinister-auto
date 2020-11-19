package com.gaconnecte.auxilium.service.dto;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.gaconnecte.auxilium.domain.Expert;
import com.gaconnecte.auxilium.domain.RefBareme;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.domain.enumeration.PrestationStatus;

import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A DTO for the PrestationPEC entity.
 */
public class PrestationPECDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String referenceSinistre;

    @Size(max = 256)
    private String descPtsChoc;

    @NotNull
    @Max(value = 99999999)
    private Integer nbrVehicules;

    //private Long prestationId;

    private String dossierReference;

    private Long modeId;

    private String modeLibelle;

    private Long baremeId;

    private String baremeCode;

    private RefBareme bareme;

    private Long garantieId;

    private Long reparateurId;

    private Reparateur reparateur;

    private Expert expert;

    private Long expertId;

    private String garantieLibelle;

    private LocalDate dateReceptionVehicule;

    private Long posGaId;

    private String posGaLibelle;

    private Long pointChocId;

    private Decision decision;

    private Long userId;

    private String userLogin;

    private String userFirstName;

    private String userLastName;

    private Boolean reparatorFacturation;

    private LocalDate dateCreation;

    private LocalDate dateDerniereMaj;

    private Long dossierId;

    private Boolean isDelete;

    //private Long tiersId;

    @DecimalMax(value = "100000000000000000")
    private Double valeurNeuf;

    @DecimalMax(value = "100000000000000000")
    private Double franchise;

    @DecimalMax(value = "100000000000000000")
    private Double capitalDc;

    @DecimalMax(value = "100000000000000000")
    private Double valeurVenale;

    private String typeFranchise;

    private Set<AgentGeneralDTO> agentGenerales = new HashSet<>();

    private Set<TiersDTO> tiers = new HashSet<>();

    private PrestationStatus status;

    private Integer companyId;

    private Integer agencyId;
    
    private Long devisId;
    
    private Long quotationStatusId;
    
    private LocalDate dateRDVReparation;
    
    private String timeRDVReparation;
    
    private Boolean confirmationRDV;

    private Long primaryQuotationId;

    private Long activeComplementaryId;
    private Long  etatChoc;
    private Long  step;
 

	/**
	 * @return the step
	 */
	public Long getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(Long step) {
		this.step = step;
	}

	public Boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public void setDateDerniereMaj(LocalDate dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public Long getDossierId() {
        return dossierId;
    }

    public void setDossierId(Long dossierId) {
        this.dossierId = dossierId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceSinistre() {
        return referenceSinistre;
    }

    public void setReferenceSinistre(String referenceSinistre) {
        this.referenceSinistre = referenceSinistre;
    }


    public String getDescPtsChoc() {
        return descPtsChoc;
    }

    public void setDescPtsChoc(String descPtsChoc) {
        this.descPtsChoc = descPtsChoc;
    }

    public Integer getNbrVehicules() {
        return nbrVehicules;
    }

    public void setNbrVehicules(Integer nbrVehicules) {
        this.nbrVehicules = nbrVehicules;
    }

    /*

    public Long getPrestationId() {
        return prestationId;
    }

    public void setPrestationId(Long prestationId) {
        this.prestationId = prestationId;
    }
    */

    public String getDossierReference() {
        return dossierReference;
    }

    public void setDossierReference(String dossierReference) {
        this.dossierReference = dossierReference;
    }

    public Boolean getReparatorFacturation() {
		return reparatorFacturation;
	}

	public void setReparatorFacturation(Boolean reparatorFacturation) {
		this.reparatorFacturation = reparatorFacturation;
	}

	public Long getPointChocId() {
        return pointChocId;
    }

    public void setPointChocId(Long pointChocId) {
        this.pointChocId = pointChocId;
    }

    public LocalDate getDateReceptionVehicule() {
        return dateReceptionVehicule;
    }

    public void setDateReceptionVehicule(LocalDate dateReceptionVehicule) {
        this.dateReceptionVehicule = dateReceptionVehicule;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long refModeGestionId) {
        this.modeId = refModeGestionId;
    }

    public String getModeLibelle() {
        return modeLibelle;
    }

    public void setModeLibelle(String refModeGestionLibelle) {
        this.modeLibelle = refModeGestionLibelle;
    }

    public Long getGarantieId() {
        return garantieId;
    }

	public void setGarantieId(Long garantieId) {
        this.garantieId = garantieId;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }


    public String getGarantieLibelle() {
        return garantieLibelle;
    }

    public void setGarantieLibelle(String garantieLibelle) {
        this.garantieLibelle = garantieLibelle;
    }

    public Long getBaremeId() {
        return baremeId;
    }

    public void setBaremeId(Long baremeId) {
        this.baremeId = baremeId;
    }

    public String getBaremeCode() {
        return baremeCode;
    }

    public void setBaremeCode(String baremeCode) {
        this.baremeCode = baremeCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Long getPosGaId() {
        return posGaId;
    }

    public void setPosGaId(Long refPositionGaId) {
        this.posGaId = refPositionGaId;
    }

    public String getPosGaLibelle() {
        return posGaLibelle;
    }

    public void setPosGaLibelle(String posGaLibelle) {
        this.posGaLibelle = posGaLibelle;
    }

    public Set<AgentGeneralDTO> getAgentGenerales() {
        return agentGenerales;
    }

    public void setAgentGenerales(Set<AgentGeneralDTO> agentGenerals) {
        this.agentGenerales = agentGenerals;
    }

    public void setTiers(Set<TiersDTO> tiers) {
        this.tiers = tiers;
    }

    public Set<TiersDTO> getTiers() {
        return tiers;
    }


    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public RefBareme getBareme() {
		return bareme;
	}

	public void setBareme(RefBareme bareme) {
		this.bareme = bareme;
	}

	public Reparateur getReparateur() {
		return reparateur;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public void setReparateur(Reparateur reparateur) {
		this.reparateur = reparateur;
    }
    
    public Double getValeurNeuf() {
        return valeurNeuf;
    }

    public void setValeurNeuf(Double valeurNeuf) {
        this.valeurNeuf = valeurNeuf;
    }

    public Double getFranchise() {
        return franchise;
    }

    public void setFranchise(Double franchise) {
        this.franchise = franchise;
    }

    public Double getCapitalDc() {
        return capitalDc;
    }

    public void setCapitalDc(Double capitalDc) {
        this.capitalDc = capitalDc;
    }

    public Double getValeurVenale() {
        return valeurVenale;
    }

    public void setValeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
    }

    public String getTypeFranchise() {
		return typeFranchise;
	}

	public void setTypeFranchise(String typeFranchise) {
		this.typeFranchise = typeFranchise;
    }
    
    public Long getTiersId() {
        return 1L;
    }

    //public void setTiersId(Long tiersId) {
        //this.tiersId = tiersId;
    //}

    public PrestationStatus getStatus() {
        return status;
    }

    public void setStatus(PrestationStatus status) {
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }
     
	public Long getDevisId() {
		return devisId;
	}

	public void setDevisId(Long devisId) {
		this.devisId = devisId;
	}

	public Long getQuotationStatusId() {
		return quotationStatusId;
	}

	public void setQuotationStatusId(Long quotationStatusId) {
		this.quotationStatusId = quotationStatusId;
	}

	public LocalDate getDateRDVReparation() {
		return dateRDVReparation;
	}

	public void setDateRDVReparation(LocalDate dateRDVReparation) {
		this.dateRDVReparation = dateRDVReparation;
	}

	public String getTimeRDVReparation() {
		return timeRDVReparation;
	}

	public void setTimeRDVReparation(String timeRDVReparation) {
		this.timeRDVReparation = timeRDVReparation;
	}
	
	public Boolean getConfirmationRDV() {
		return confirmationRDV;
	}

	public void setConfirmationRDV(Boolean confirmationRDV) {
		this.confirmationRDV = confirmationRDV;
	}
    
	public Long getPrimaryQuotationId() {
		return primaryQuotationId;
	}

	public void setPrimaryQuotationId(Long primaryQuotationId) {
		this.primaryQuotationId = primaryQuotationId;
	}

	public Long getActiveComplementaryId() {
		return activeComplementaryId;
	}

	public void setActiveComplementaryId(Long activeComplementaryId) {
		this.activeComplementaryId = activeComplementaryId;
	}
	

	/**
	 * @return the etatChoc
	 */
	public Long getEtatChoc() {
		return etatChoc;
	}

	/**
	 * @param etatChoc the etatChoc to set
	 */
	public void setEtatChoc(Long etatChoc) {
		this.etatChoc = etatChoc;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrestationPECDTO prestationPECDTO = (PrestationPECDTO) o;
        if(prestationPECDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestationPECDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestationPECDTO{" +
            "id=" + getId() +
            ", descPtsChoc='" + getDescPtsChoc() + "'" +
            ", nbrVehicules='" + getNbrVehicules() + "'" +
            ", timeRDVReparation='" + getTimeRDVReparation() + "'" +
            ", dateRDVReparation='" + getDateRDVReparation() + "'" +
            "}";
    }
}
