package com.gaconnecte.auxilium.domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "assistance")

public class Assitances implements Serializable {

    @Id
    private Long id;
    
    @Column(name = "assigned_to_id")
    private Long assignedToId;
    
    @Column(name = "reference")
    private String reference;
    
   
    @Column(name = "incident_date")
    private LocalDate incidentDate;
    
  
    @Column(name = "incident_nature")
    private String incidentNature;
    
   
    @Column(name = "company_name")
    private String companyName;
    
    
    @Column(name = "nom_agent_assurance")
    private String nomAgentAssurance;
    
    @Column(name = "numero_contrat")
    private String numeroContrat;
    
    @Column(name = "marque")
    private String marque;
    
    @Column(name = "immatriculation_vehicule")
    private String immatriculationVehicule;
    
    @Column(name = "prenom")
    private String prenom;
    
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "insured_arrival_date")
    private LocalDateTime insuredArrivalDate;
    
    @Column(name = "tug_arrival_date")
    private LocalDateTime tugArrivalDate;
    
    @Column(name = "tug_assignment_date")
    private LocalDateTime tugAssignmentDate;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    
    @Column(name = "is_company")
    private Boolean isCompany;
    

    @Column(name = "raison_sociale")
    private String raisonSociale;
    
    @Column(name = "type_service")
    private String typeService;
    
    @Column(name = "remorqueur")
    private String remorqueur;
    
   @Column(name = "price_ttc")
    private Double price_ttc;
    
   
    @Column(name = "etat_prestation")
    private String etatPrestation;
    
    @Column(name = "etat_prestation_id")
    private Long etatPrestationId;

    //@NotNull
    @Column(name = "motif", length=512)//, nullable = false
    private String motif;
    
    

    @Column(name = "status_id")
    private Long statusId;
    
    
    @Column(name = "partner_id")
    private Long partnerId;
    
    @Column(name = "agence_id")
    private Long agenceId;
    
    @Column(name = "nom_charge")
    private String charge;
    
    @Column(name = "nom_prenom_raison")
    private String nomPrenomRaison;
    
    @Column(name = "kilometrage")
    private Double kilometrage;
    
    @Column(name = "ville_destination")
    private String villeDestination;
    
    @Column(name = "ville_sinister")
    private String villeSinister;
  
    
    @Column(name = "incident_governorate_label")
    private String incidentGovernorateLabel;
    
    
    
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getAgenceId() {
		return agenceId;
	}

	public void setAgenceId(Long agenceId) {
		this.agenceId = agenceId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	

	public String getIncidentNature() {
		return incidentNature;
	}

	public void setIncidentNature(String incidentNature) {
		this.incidentNature = incidentNature;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Boolean getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(Boolean isCompany) {
		this.isCompany = isCompany;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getTypeService() {
		return typeService;
	}

	public void setTypeService(String typeService) {
		this.typeService = typeService;
	}

	public String getRemorqueur() {
		return remorqueur;
	}

	public void setRemorqueur(String remorqueur) {
		this.remorqueur = remorqueur;
	}


	public String getEtatPrestation() {
		return etatPrestation;
	}

	public void setEtatPrestation(String etatPrestation) {
		this.etatPrestation = etatPrestation;
	}
	
	
	
	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public Double getPrice_ttc() {
		
		return price_ttc;
	}

	public void setPrice_ttc(Double price_ttc) {
		if(price_ttc != null)
		{this.price_ttc = price_ttc;}
		else price_ttc = 0.0;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public LocalDateTime getInsuredArrivalDate() {
		return insuredArrivalDate;
	}

	public void setInsuredArrivalDate(LocalDateTime insuredArrivalDate) {
		this.insuredArrivalDate = insuredArrivalDate;
	}

	public LocalDateTime getTugArrivalDate() {
		return tugArrivalDate;
	}

	public void setTugArrivalDate(LocalDateTime tugArrivalDate) {
		this.tugArrivalDate = tugArrivalDate;
	}

	public LocalDateTime getTugAssignmentDate() {
		return tugAssignmentDate;
	}

	public void setTugAssignmentDate(LocalDateTime tugAssignmentDate) {
		this.tugAssignmentDate = tugAssignmentDate;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	

	public Long getEtatPrestationId() {
		return etatPrestationId;
	}

	public void setEtatPrestationId(Long etatPrestationId) {
		this.etatPrestationId = etatPrestationId;
	}

	public String getNomPrenomRaison() {
		return nomPrenomRaison;
	}

	public void setNomPrenomRaison(String nomPrenomRaison) {
		this.nomPrenomRaison = nomPrenomRaison;
	}

	
	
	public Double getKilometrage() {
		return kilometrage;
	}

	public void setKilometrage(Double kilometrage) {
		this.kilometrage = kilometrage;
	}

	public String getVilleDestination() {
		return villeDestination;
	}

	public void setVilleDestination(String villeDestination) {
		this.villeDestination = villeDestination;
	}

	public String getVilleSinister() {
		return villeSinister;
	}

	public void setVilleSinister(String villeSinister) {
		this.villeSinister = villeSinister;
	}

	public String getIncidentGovernorateLabel() {
		return incidentGovernorateLabel;
	}

	public void setIncidentGovernorateLabel(String incidentGovernorateLabel) {
		this.incidentGovernorateLabel = incidentGovernorateLabel;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assitances a = (Assitances) o;
        if (a.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), a.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "Assitances [id=" + id + ", assignedToId=" + assignedToId + ", reference=" + reference
				+ ", incidentDate=" + incidentDate + ", incidentNature=" + incidentNature + ", companyName="
				+ companyName + ", nomAgentAssurance=" + nomAgentAssurance + ", numeroContrat=" + numeroContrat
				+ ", marque=" + marque + ", immatriculationVehicule=" + immatriculationVehicule + ", prenom=" + prenom
				+ ", nom=" + nom + ", isCompany=" + isCompany + ", raisonSociale=" + raisonSociale + ", typeService="
				+ typeService + ", remorqueur=" + remorqueur + ", price_ttc=" + price_ttc + ", etatPrestation="
				+ etatPrestation + ", motif=" + motif + ", statusId=" + statusId + ", creationDate=" + creationDate
				+ ", partnerId=" + partnerId + ", agenceId=" + agenceId + "]";
	}
    
    

}

