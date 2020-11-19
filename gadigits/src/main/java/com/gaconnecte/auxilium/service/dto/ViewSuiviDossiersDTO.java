package com.gaconnecte.auxilium.service.dto;
import java.io.Serializable;

import java.time.LocalDate;

import javax.persistence.Column;



public class ViewSuiviDossiersDTO implements Serializable {

	

    private Long id;
    private Long agentId;
    private String agent;
    
    private Long zoneId;
    
    
    private String zone;
    
    private Long compagnieId;
    
    private String compagnie;
    
    private LocalDate dateOuverture;
    
    private String etatDossier;
    
    private Long idEtatDossier;

    
    private Long modeGestionId;
    private String modeGestion;

    private LocalDate dateBonSortie;
    
    private Long chargeId;

    
    private String chargeNom;

    private String chargePrenom;
    private LocalDate creationDate;
    private String referencega;
    private String immatriculationVehicule;
    private String companyReference;
    

	public String getReferencega() {
		return referencega;
	}

	public void setReferencega(String referencega) {
		this.referencega = referencega;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Long getCompagnieId() {
		return compagnieId;
	}

	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}

	public String getCompagnie() {
		return compagnie;
	}

	public void setCompagnie(String compagnie) {
		this.compagnie = compagnie;
	}

	public LocalDate getDateOuverture() {
		return dateOuverture;
	}

	public void setDateOuverture(LocalDate dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	public String getEtatDossier() {
		return etatDossier;
	}

	public void setEtatDossier(String etatDossier) {
		this.etatDossier = etatDossier;
	}

	public Long getIdEtatDossier() {
		return idEtatDossier;
	}

	public void setIdEtatDossier(Long idEtatDossier) {
		this.idEtatDossier = idEtatDossier;
	}

	public Long getModeGestionId() {
		return modeGestionId;
	}

	public void setModeGestionId(Long modeGestionId) {
		this.modeGestionId = modeGestionId;
	}

	public String getModeGestion() {
		return modeGestion;
	}

	public void setModeGestion(String modeGestion) {
		this.modeGestion = modeGestion;
	}

	public LocalDate getDateBonSortie() {
		return dateBonSortie;
	}

	public void setDateBonSortie(LocalDate dateBonSortie) {
		this.dateBonSortie = dateBonSortie;
	}

	public Long getChargeId() {
		return chargeId;
	}

	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
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

	public String getImmatriculationVehicule() {
		return immatriculationVehicule;
	}

	public void setImmatriculationVehicule(String immatriculationVehicule) {
		this.immatriculationVehicule = immatriculationVehicule;
	}

	public String getCompanyReference() {
		return companyReference;
	}

	public void setCompanyReference(String companyReference) {
		this.companyReference = companyReference;
	}

	@Override
	public String toString() {
		return "ViewSuiviDossiersDTO [id=" + id + ", agentId=" + agentId + ", agent=" + agent + ", zoneId=" + zoneId
				+ ", zone=" + zone + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie + ", dateOuverture="
				+ dateOuverture + ", etatDossier=" + etatDossier + ", idEtatDossier=" + idEtatDossier
				+ ", modeGestionId=" + modeGestionId + ", modeGestion=" + modeGestion + ", dateBonSortie="
				+ dateBonSortie + ", chargeId=" + chargeId + ", chargeNom=" + chargeNom + ", chargePrenom="
				+ chargePrenom + ", creationDate=" + creationDate + ", referencega=" + referencega + "]";
	}
   
	
}
