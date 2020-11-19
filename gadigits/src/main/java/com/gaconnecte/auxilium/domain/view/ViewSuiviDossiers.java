package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "view_suivi_nbre_dossiers")
public class ViewSuiviDossiers implements Serializable{  
	private static final long serialVersionUID = 1L;

    
    @Id
    private Long id;

    
    @Column(name = "agency_name_id")
    private Long agentId;
    
    
    @Column(name = "agency_name")
    private String agent;
    
    @Column(name = "zone_id")
    private Long zoneId;
    
    
    @Column(name = "zone")
    private String zone;
    
    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    @Column(name = "compagnie")
    private String compagnie;
    
    @Column(name = "referencega")
    private String referencega;
    
    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;
    
    @Column(name = "etat_dossier")
    private String etatDossier;
    
    @Column(name = "id_etat_dossier")
    private Long idEtatDossier;

    
    @Column(name = "mode_gestion_id")
    private Long modeGestionId;

    @Column(name = "mode_gestion")
    private String modeGestion;

    @Column(name = "date_bon_sortie")
    private LocalDate dateBonSortie;
    
    @Column(name = "charge_id")
    private Long chargeId;

    
    @Column(name = "charge_nom")
    private String chargeNom;

    @Column(name = "charge_prenom")
    private String chargePrenom;
    
    @Column(name = "date_creation")
    private LocalDate creationDate;


    @Column(name = "immatriculation_vehicule")
    private String immatriculationVehicule;
    
    @Column(name = "company_reference")
    private String companyReference;
    
    
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

	
	
	public String getReferencega() {
		return referencega;
	}

	public void setReferencega(String referencega) {
		this.referencega = referencega;
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
		return "ViewSuiviDossiers [id=" + id + ", agentId=" + agentId + ", agent=" + agent + ", zoneId=" + zoneId
				+ ", zone=" + zone + ", compagnieId=" + compagnieId + ", compagnie=" + compagnie + ", referencega="
				+ referencega + ", dateOuverture=" + dateOuverture + ", etatDossier=" + etatDossier + ", idEtatDossier="
				+ idEtatDossier + ", modeGestionId=" + modeGestionId + ", modeGestion=" + modeGestion
				+ ", dateBonSortie=" + dateBonSortie + ", chargeId=" + chargeId + ", chargeNom=" + chargeNom
				+ ", chargePrenom=" + chargePrenom + ", creationDate=" + creationDate + "]";
	}
   
}
