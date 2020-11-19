package com.gaconnecte.auxilium.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;

public class PriseEnChargeDTO {

    private Long id;

    private Long sinisterId;

    private Long assignedToId;

    private String reference;

    private String refSinister;

    private LocalDate incidentDate;

    private ZonedDateTime declarationDate;

    private String companyName;

    private String raisonSocialeAssure;

    private String nomAgentAssurance;
    
    private String codeAgentAssurance;

    private String numeroContrat;

    private String marque;

    private String pointChock;

    private String immatriculationVehicule;

    private String immatriculationTiers;

    private String raisonSocialTiers;

    private ZonedDateTime creationDate;

    private String modeGestion;

    private Integer casbareme;

    private Boolean stateShock;
    private String lightShock;

    private String reparateur;

    private String expert;

    private Long positionGaId;

    private String positionGa;

    private String etatPrestation;

    private String approvPec;

    private String etapePrestation;
    private Long idEtapePrestation;

    private String compagnieAdverse;

    private Double montantTotalDevis;

    private LocalDateTime dateRDVReparation;
    
    private LocalDateTime vehicleReceiptDate;
    
    private String numberTeleInsured;

    private String chargeNom;
    private String chargePrenom;
    private String bossNom;
    private String bossPrenom;
    private String directeurNom;
    private String directeurPrenom;
    private LocalDate bonDeSortie;
    private LocalDate imprimDate;
    private String responsableNom;
    private String responsablePrenom;
    private Long modeId;
    private Long partnerId;
    private Long agencyId;

    private Long expertId;
    private Long reparateurId;
    private LocalDateTime dateReprise;	

    private String motifGeneral;
    
    private Double totalMo;
    
    private LocalDateTime signatureDate;
    
    private LocalDateTime dateEnvoiDemandePec;
    
    private LocalDateTime dateAcceptationDeForme;


    public Double getTotalMo() {
		return totalMo;
	}

	public void setTotalMo(Double totalMo) {
		this.totalMo = totalMo;
	}

	public String getMotifGeneral() {
        return motifGeneral;
    }

    public void setMotifGeneral(String motifGeneral) {
        this.motifGeneral = motifGeneral;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public LocalDateTime getDateRDVReparation() {
		return dateRDVReparation;
	}

	public void setDateRDVReparation(LocalDateTime dateRDVReparation) {
		this.dateRDVReparation = dateRDVReparation;
    }
    
    public LocalDateTime getVehicleReceiptDate() {
		return vehicleReceiptDate;
	}

	public void setVehicleReceiptDate(LocalDateTime vehicleReceiptDate) {
		this.vehicleReceiptDate = vehicleReceiptDate;
    }
    
    public String getNumberTeleInsured() {
        return numberTeleInsured;
    }

    public void setNumberTeleInsured(String numberTeleInsured) {
        this.numberTeleInsured = numberTeleInsured;
    }

    public String getPointChock() {
        return pointChock;
    }

    public void setPointChock(String pointChock) {
        this.pointChock = pointChock;
    }

    public Long getReparateurId() {
        return reparateurId;
    }

    public void setReparateurId(Long reparateurId) {
        this.reparateurId = reparateurId;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getResponsableNom() {
        return responsableNom;
    }

    public void setResponsableNom(String responsableNom) {
        this.responsableNom = responsableNom;
    }

    public String getResponsablePrenom() {
        return responsablePrenom;
    }

    public void setResponsablePrenom(String responsablePrenom) {
        this.responsablePrenom = responsablePrenom;
    }

    public String getChargeNom() {
        return chargeNom;
    }

    public void setChargeNom(String chargeNom) {
        this.chargeNom = chargeNom;
    }

    public String getChargePrenom() {
        return chargePrenom;
    }

    public void setChargePrenom(String chargePrenom) {
        this.chargePrenom = chargePrenom;
    }

    public String getBossNom() {
        return bossNom;
    }

    public void setBossNom(String bossNom) {
        this.bossNom = bossNom;
    }

    public String getBossPrenom() {
        return bossPrenom;
    }

    public void setBossPrenom(String bossPrenom) {
        this.bossPrenom = bossPrenom;
    }

    public String getDirecteurNom() {
        return directeurNom;
    }

    public void setDirecteurNom(String directeurNom) {
        this.directeurNom = directeurNom;
    }

    public String getDirecteurPrenom() {
        return directeurPrenom;
    }

    public void setDirecteurPrenom(String directeurPrenom) {
        this.directeurPrenom = directeurPrenom;
    }

    public LocalDate getBonDeSortie() {
        return bonDeSortie;
    }

    public void setBonDeSortie(LocalDate bonDeSortie) {
        this.bonDeSortie = bonDeSortie;
    }

    public LocalDate getImprimDate() {
        return imprimDate;
    }

    public void setImprimDate(LocalDate imprimDate) {
        this.imprimDate = imprimDate;
    }

    public Long getIdEtapePrestation() {
        return idEtapePrestation;
    }

    public void setIdEtapePrestation(Long idEtapePrestation) {
        this.idEtapePrestation = idEtapePrestation;
    }

    public Long getSinisterId() {
        return sinisterId;
    }

    public void setSinisterId(Long sinisterId) {
        this.sinisterId = sinisterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public ZonedDateTime getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(ZonedDateTime declarationDate) {
        this.declarationDate = declarationDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNomAgentAssurance() {
        return nomAgentAssurance;
    }

    public void setNomAgentAssurance(String nomAgentAssurance) {
        this.nomAgentAssurance = nomAgentAssurance;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getImmatriculationVehicule() {
        return immatriculationVehicule;
    }

    public void setImmatriculationVehicule(String immatriculationVehicule) {
        this.immatriculationVehicule = immatriculationVehicule;
    }

    public String getRaisonSocialTiers() {
        return raisonSocialTiers;
    }

    public void setRaisonSocialTiers(String raisonSocialTiers) {
        this.raisonSocialTiers = raisonSocialTiers;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getModeGestion() {
        return modeGestion;
    }

    public void setModeGestion(String modeGestion) {
        this.modeGestion = modeGestion;
    }

    public Integer getCasbareme() {
        return casbareme;
    }

    public void setCasbareme(Integer casbareme) {
        this.casbareme = casbareme;
    }

    public Boolean getStateShock() {
        return stateShock;
    }

    public void setStateShock(Boolean stateShock) {
        this.stateShock = stateShock;
    }

    public String getReparateur() {
        return reparateur;
    }

    public void setReparateur(String reparateur) {
        this.reparateur = reparateur;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getEtatPrestation() {
        return etatPrestation;
    }

    public void setEtatPrestation(String etatPrestation) {
        this.etatPrestation = etatPrestation;
    }

    public String getApprovPec() {
        return approvPec;
    }

    public void setApprovPec(String approvPec) {
        this.approvPec = approvPec;
    }

    public String getEtapePrestation() {
        return etapePrestation;
    }

    public void setEtapePrestation(String etapePrestation) {
        this.etapePrestation = etapePrestation;
    }

    public String getImmatriculationTiers() {
        return immatriculationTiers;
    }

    public void setImmatriculationTiers(String immatriculationTiers) {
        this.immatriculationTiers = immatriculationTiers;
    }

    public String getCompagnieAdverse() {
        return compagnieAdverse;
    }

    public void setCompagnieAdverse(String compagnieAdverse) {
        this.compagnieAdverse = compagnieAdverse;
    }

    public Double getMontantTotalDevis() {
        return montantTotalDevis;
    }

    public void setMontantTotalDevis(Double montantTotalDevis) {
        this.montantTotalDevis = montantTotalDevis;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRefSinister() {
        return refSinister;
    }

    public void setRefSinister(String refSinister) {
        this.refSinister = refSinister;
    }

    public String getRaisonSocialeAssure() {
        return raisonSocialeAssure;
    }

    public void setRaisonSocialeAssure(String raisonSocialeAssure) {
        this.raisonSocialeAssure = raisonSocialeAssure;
    }

    public String getCodeAgentAssurance() {
		return codeAgentAssurance;
	}

	public void setCodeAgentAssurance(String codeAgentAssurance) {
		this.codeAgentAssurance = codeAgentAssurance;
	}
	
	

	public LocalDateTime getDateReprise() {
		return dateReprise;
	}

	public void setDateReprise(LocalDateTime dateReprise) {
		this.dateReprise = dateReprise;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriseEnChargeDTO priseEnChargeDTO = (PriseEnChargeDTO) o;
        if (priseEnChargeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priseEnChargeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public String getLightShock() {
        return lightShock;
    }

    public void setLightShock(String lightShock) {
        this.lightShock = lightShock;
    }

    public Long getPositionGaId() {
        return positionGaId;
    }

    public void setPositionGaId(Long positionGaId) {
        this.positionGaId = positionGaId;
    }

    public String getPositionGa() {
        return positionGa;
    }

    public void setPositionGa(String positionGa) {
        this.positionGa = positionGa;
    }

	public LocalDateTime getSignatureDate() {
		return signatureDate;
	}

	public void setSignatureDate(LocalDateTime signatureDate) {
		this.signatureDate = signatureDate;
	}

	public LocalDateTime getDateEnvoiDemandePec() {
		return dateEnvoiDemandePec;
	}

	public void setDateEnvoiDemandePec(LocalDateTime dateEnvoiDemandePec) {
		this.dateEnvoiDemandePec = dateEnvoiDemandePec;
	}

	public LocalDateTime getDateAcceptationDeForme() {
		return dateAcceptationDeForme;
	}

	public void setDateAcceptationDeForme(LocalDateTime dateAcceptationDeForme) {
		this.dateAcceptationDeForme = dateAcceptationDeForme;
	}

}
