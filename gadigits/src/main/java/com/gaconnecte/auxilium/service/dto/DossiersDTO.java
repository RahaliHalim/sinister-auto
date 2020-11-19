package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

public class DossiersDTO implements Serializable{
	
    private Long id;
    private Long assignedToId;

    private String reference;
    
    private LocalDate incidentDate;
    
    private String incidentNature;
    
    private String companyName;
    
    private String nomAgentAssurance;
    
    private String numeroContrat;
    
    private String pack;
   
    private String immatriculationVehicule;
 
    private String marque;
   
    private Boolean isCompany;
    
    private String prenom;
    
    private String nom;

    private String raisonSociale;
    
    private String typeService;
    
    private int serviceCount;
	private Long statusId ;
	private Long compagnieId ;
	private Long agencyId ;

    
	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public Long getCompagnieId() {
		return compagnieId;
	}

	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "DossiersDTO [id=" + id + ", assignedToId=" + assignedToId + ", reference=" + reference
				+ ", incidentDate=" + incidentDate + ", incidentNature=" + incidentNature + ", companyName="
				+ companyName + ", nomAgentAssurance=" + nomAgentAssurance + ", numeroContrat=" + numeroContrat
				+ ", pack=" + pack + ", immatriculationVehicule=" + immatriculationVehicule + ", marque=" + marque
				+ ", isCompany=" + isCompany + ", prenom=" + prenom + ", nom=" + nom + ", raisonSociale="
				+ raisonSociale + ", typeService=" + typeService + ", serviceCount=" + serviceCount + ", statusId="
				+ statusId + ", compagnieId=" + compagnieId + ", agencyId=" + agencyId + "]";
	}

	public Long getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(Long assignedToId) {
		this.assignedToId = assignedToId;
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

	public void setReference(String reference) {
		this.reference = reference;
	}

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
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

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
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

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

    
	public Boolean isCompany() {
		return isCompany;
	}

	public void setCompany(Boolean isCompany) {
		this.isCompany = isCompany;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DossierDTO dossierDTO = (DossierDTO) o;
        if(dossierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dossierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
    
}
