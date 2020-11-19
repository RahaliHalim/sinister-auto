package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;


public class ViewSuiviAnnulationDTO implements Serializable  {

    private Long id;
    private String referenceGa;
    private Long compagnieId;
    private String compagnie;
    private Long compagnieAdvId;
    private String compagnieAdv;
    private Long agenceId;
    private String agence;
    private Long zoneId;
    private String zone;
    private Long modeGestionId; 
    private String modeGestion; 
    private Long assureId;
    private String assure;
    private Long vehiculeId;
    private String immatriculationVehicule;
    private String marque;
    private Long reparateurId;
    private String reparateur;
    private Long chargeId;
    private String nomCharge;
    private String prenomCharge;
    private Long expertId;
    private String expert;
    private Long villeId;
    private String zoneReparateur;
    private LocalDate ouvertureDate;
    private LocalDate annulationDate;
    private Long motifAnnulationId;
    private String motifAnnulation;
    private Double montantDevis;
    private String participationType;
    private Double montantParticipation;
    private Long etatDossierId;
    private String etatDossier;
    private LocalDate creationDate;
    private LocalDate dateIncident;
    private String decision;
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
	public String getReferenceGa() {
		return referenceGa;
	}
	public void setReferenceGa(String referenceGa) {
		this.referenceGa = referenceGa;
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
	public Long getCompagnieAdvId() {
		return compagnieAdvId;
	}
	public void setCompagnieAdvId(Long compagnieAdvId) {
		this.compagnieAdvId = compagnieAdvId;
	}
	public String getCompagnieAdv() {
		return compagnieAdv;
	}
	public void setCompagnieAdv(String compagnieAdv) {
		this.compagnieAdv = compagnieAdv;
	}
	public Long getAgenceId() {
		return agenceId;
	}
	public void setAgenceId(Long agenceId) {
		this.agenceId = agenceId;
	}
	public String getAgence() {
		return agence;
	}
	public void setAgence(String agence) {
		this.agence = agence;
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
	public Long getAssureId() {
		return assureId;
	}
	public void setAssureId(Long assureId) {
		this.assureId = assureId;
	}
	public String getAssure() {
		return assure;
	}
	public void setAssure(String assure) {
		this.assure = assure;
	}
	public Long getVehiculeId() {
		return vehiculeId;
	}
	public void setVehiculeId(Long vehiculeId) {
		this.vehiculeId = vehiculeId;
	}
	public String getImmatriculationVehicule() {
		return immatriculationVehicule;
	}
	public void setImmatriculationVehicule(String immatriculationVehicule) {
		this.immatriculationVehicule = immatriculationVehicule;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public Long getReparateurId() {
		return reparateurId;
	}
	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}
	public String getReparateur() {
		return reparateur;
	}
	public void setReparateur(String reparateur) {
		this.reparateur = reparateur;
	}
	public Long getChargeId() {
		return chargeId;
	}
	public void setChargeId(Long chargeId) {
		this.chargeId = chargeId;
	}
	public String getNomCharge() {
		return nomCharge;
	}
	public void setNomCharge(String nomCharge) {
		this.nomCharge = nomCharge;
	}
	public String getPrenomCharge() {
		return prenomCharge;
	}
	public void setPrenomCharge(String prenomCharge) {
		this.prenomCharge = prenomCharge;
	}
	public Long getExpertId() {
		return expertId;
	}
	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}
	public String getExpert() {
		return expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	public Long getVilleId() {
		return villeId;
	}
	public void setVilleId(Long villeId) {
		this.villeId = villeId;
	}
	public String getZoneReparateur() {
		return zoneReparateur;
	}
	public void setZoneReparateur(String zoneReparateur) {
		this.zoneReparateur = zoneReparateur;
	}
	public LocalDate getOuvertureDate() {
		return ouvertureDate;
	}
	public void setOuvertureDate(LocalDate ouvertureDate) {
		this.ouvertureDate = ouvertureDate;
	}
	public LocalDate getAnnulationDate() {
		return annulationDate;
	}
	public void setAnnulationDate(LocalDate annulationDate) {
		this.annulationDate = annulationDate;
	}
	public Long getMotifAnnulationId() {
		return motifAnnulationId;
	}
	public void setMotifAnnulationId(Long motifAnnulationId) {
		this.motifAnnulationId = motifAnnulationId;
	}
	public String getMotifAnnulation() {
		return motifAnnulation;
	}
	public void setMotifAnnulation(String motifAnnulation) {
		this.motifAnnulation = motifAnnulation;
	}
	public Double getMontantDevis() {
		return montantDevis;
	}
	public void setMontantDevis(Double montantDevis) {
		this.montantDevis = montantDevis;
	}
	public String getParticipationType() {
		return participationType;
	}
	public void setParticipationType(String participationType) {
		this.participationType = participationType;
	}
	public Double getMontantParticipation() {
		return montantParticipation;
	}
	public void setMontantParticipation(Double montantParticipation) {
		this.montantParticipation = montantParticipation;
	}
	public Long getEtatDossierId() {
		return etatDossierId;
	}
	public void setEtatDossierId(Long etatDossierId) {
		this.etatDossierId = etatDossierId;
	}
	public String getEtatDossier() {
		return etatDossier;
	}
	public void setEtatDossier(String etatDossier) {
		this.etatDossier = etatDossier;
	}
	public LocalDate getDateIncident() {
		return dateIncident;
	}
	public void setDateIncident(LocalDate dateIncident) {
		this.dateIncident = dateIncident;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	
	
	public String getCompanyReference() {
		return companyReference;
	}
	public void setCompanyReference(String companyReference) {
		this.companyReference = companyReference;
	}
	@Override
	public String toString() {
		return "ViewSuiviAnnulationDTO [id=" + id + ", referenceGa=" + referenceGa + ", compagnieId=" + compagnieId
				+ ", compagnie=" + compagnie + ", compagnieAdvId=" + compagnieAdvId + ", compagnieAdv=" + compagnieAdv
				+ ", agenceId=" + agenceId + ", agence=" + agence + ", zoneId=" + zoneId + ", zone=" + zone
				+ ", modeGestionId=" + modeGestionId + ", modeGestion=" + modeGestion + ", assureId=" + assureId
				+ ", assure=" + assure + ", vehiculeId=" + vehiculeId + ", immatriculationVehicule="
				+ immatriculationVehicule + ", marque=" + marque + ", reparateurId=" + reparateurId + ", reparateur="
				+ reparateur + ", chargeId=" + chargeId + ", nomCharge=" + nomCharge + ", prenomCharge=" + prenomCharge
				+ ", expertId=" + expertId + ", expert=" + expert + ", villeId=" + villeId + ", zoneReparateur="
				+ zoneReparateur + ", ouvertureDate=" + ouvertureDate + ", annulationDate=" + annulationDate
				+ ", motifAnnulationId=" + motifAnnulationId + ", motifAnnulation=" + motifAnnulation
				+ ", montantDevis=" + montantDevis + ", participationType=" + participationType
				+ ", montantParticipation=" + montantParticipation + ", etatDossierId=" + etatDossierId
				+ ", etatDossier=" + etatDossier + ", creationDate=" + creationDate + "]";
	}
    
}
