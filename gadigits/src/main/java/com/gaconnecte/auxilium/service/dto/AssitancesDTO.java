package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;



public class AssitancesDTO implements Serializable {

    private Long id;
    private Long assignedToId;

    private String reference;
       
    private LocalDate incidentDate;
     
    private String incidentNature;
    
    private String companyName;

    private String nomAgentAssurance;
    
    private String numeroContrat;
    
    private String marque;
    
    private String immatriculationVehicule;
    
    private LocalDateTime insuredArrivalDate;
    
    private LocalDateTime tugArrivalDate;
    
    private LocalDateTime tugAssignmentDate;
    
    private LocalDateTime creationDate;
    
    private String prenom;
   
    private String nom;
    
    private Boolean isCompany;
    
    private String raisonSociale;

    private String typeService;

    private String remorqueur;
    
    private Double price_ttc;

    private String etatPrestation;
    private Long etatPrestationId;
    
    private String motif;
    
    private Long statusId;
    
    private Long partnerId;
    
    private Long agenceId;
    
    private String charge;
    
    private String nomPrenomRaison;
    
    private Double kilometrage;
    
    private String villeDestination;
    
    private String villeSinister;
    
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

	public String getReference() {
		return reference;
	}

	public Long getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(Long assignedToId) {
		this.assignedToId = assignedToId;
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

	public String getEtatPrestation() {
		return etatPrestation;
	}

	public void setEtatPrestation(String etatPrestation) {
		this.etatPrestation = etatPrestation;
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
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        AssitancesDTO that = (AssitancesDTO) o;
	        return Objects.equals(id, that.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

		@Override
		public String toString() {
			return "AssitancesDTO [id=" + id + ", assignedToId=" + assignedToId + ", reference=" + reference
					+ ", incidentDate=" + incidentDate + ", incidentNature=" + incidentNature + ", companyName="
					+ companyName + ", nomAgentAssurance=" + nomAgentAssurance + ", numeroContrat=" + numeroContrat
					+ ", marque=" + marque + ", immatriculationVehicule=" + immatriculationVehicule + ", prenom="
					+ prenom + ", nom=" + nom + ", isCompany=" + isCompany + ", raisonSociale=" + raisonSociale
					+ ", typeService=" + typeService + ", remorqueur=" + remorqueur + ", price_ttc=" + price_ttc
					+ ", etatPrestation=" + etatPrestation + ", motif=" + motif + ", statusId=" + statusId
					+ ", creationDate=" + creationDate + ", partnerId=" + partnerId + ", agenceId=" + agenceId + "]";
		}
    
    
    
}
