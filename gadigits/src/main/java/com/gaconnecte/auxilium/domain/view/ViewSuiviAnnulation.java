package com.gaconnecte.auxilium.domain.view;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "view_suivi_annulation")
public class ViewSuiviAnnulation implements Serializable{
    private static final long serialVersionUID = 1L;

	 
    @Id
    private Long id;

    
    @Column(name = "referencega")
    private String referenceGa;
    
   
    @Column(name = "compagnie_id")
    private Long compagnieId;
    
    
    @Column(name = "compagnie")
    private String compagnie;
    
    @Column(name = "compagnie_adv_id")
    private Long compagnieAdvId;
    
    
    @Column(name = "compagnie_adv")
    private String compagnieAdv;
    
    @Column(name = "decision")
    private String decision;
    
    @Column(name = "agency_name_id")
    private Long agenceId;
    
    
    @Column(name = "agency_name")
    private String agence;
    
    
    @Column(name = "governorate_id")
    private Long zoneId;
    
    @Column(name = "zoneagent")
    private String zone;
    
    @Column(name = "mode_gestion_id")
    private Long modeGestionId; 
    
    @Column(name = "mode_gestion")
    private String modeGestion; 
    
  
    @Column(name = "assure_id")
    private Long assureId;
    
    @Column(name = "insured_name")
    private String assure;
    
    @Column(name = "vehicle_id")
    private Long vehiculeId;
    
    @Column(name = "immatriculation_vehicule")
    private String immatriculationVehicule;
    
    @Column(name = "marque")
    private String marque;
    
    @Column(name = "reparateur_id")
    private Long reparateurId;
    
    
    @Column(name = "nom_reparateur")
    private String reparateur;
    
    
    @Column(name = "charge_id")
    private Long chargeId;
    
    
    @Column(name = "charge_nom")
    private String nomCharge;
    
    @Column(name = "charge_prenom")
    private String prenomCharge;
    
    
   
    @Column(name = "expert_id")
    private Long expertId;
    
    @Column(name = "expert")
    private String expert;

    
    @Column(name = "ville_id")
    private Long villeId;
    
    @Column(name = "zone_reparateur")
    private String zoneReparateur;

    
    
    @Column(name = "date_ouverture")
    private LocalDate ouvertureDate;
    
    
    @Column(name = "date_annulation")
    private LocalDate annulationDate;
    
    
    @Column(name = "motif_annulation_id")
    private Long motifAnnulationId;
    
    @Column(name = "motif_annulation")
    private String motifAnnulation;

    
   

	@Column(name = "montant_devis")
    private Double montantDevis;
    
    @Column(name = "type_participation")
    private String participationType;
   
    @Column(name = "montant_participation")
    private Double montantParticipation;
    
    @Column(name = "etat_dossier_id")
    private Long etatDossierId;
    
    @Column(name = "etat_dossier")
    private String etatDossier;
    
    @Column(name = "date_creation")
    private LocalDate creationDate;
    
    @Column(name = "date_incident")
    private LocalDate dateIncident;

    @Column(name = "company_reference" )
    private String companyReference;
    
    
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

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
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
		return "ViewSuiviAnnulation [id=" + id + ", referenceGa=" + referenceGa + ", compagnieId=" + compagnieId
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
