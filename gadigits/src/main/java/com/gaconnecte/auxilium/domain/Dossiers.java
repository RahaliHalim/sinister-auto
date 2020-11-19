package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;



	@Entity
	@Table(name = "dossiers")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@Document(indexName = "dossiers")
	public class Dossiers implements Serializable {

	    private static final long serialVersionUID = 1L;
	    @Id
	    private Long id;
	    @Column(name = "assigned_to_id")
	    private Long assignedToId;

	    
	    @NotNull
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
	    @Column(name = "pack")
	    private String pack;
	    @Column(name = "immatriculation_vehicule")
	    private String immatriculationVehicule;
	    @Column(name = "marque")
	    private String marque;
	    @Column(name = "is_company")
	    private Boolean isCompany;
	    @Column(name = "prenom")
	    private String prenom;
	    @Column(name = "nom")
	    private String nom;
	    @Column(name = "raison_sociale")
	    private String raisonSociale;
	    @Column(name = "type_service")
	    private String typeService;
	    @Column(name = "service_count")
	    private int serviceCount;
	    
	    @Column(name = "status_id")
		private Long statusId ;

	    @Column(name = "compagnie_id")
		private Long compagnieId ;
	    

	    @Column(name = "agency_id")
		private Long agencyId ;

		public Long getAgencyId() {
			return agencyId;
		}


		public void setAgencyId(Long agencyId) {
			this.agencyId = agencyId;
		}


		public Long getStatusId() {
			return statusId;
		}


		public Long getCompagnieId() {
			return compagnieId;
		}


		public void setCompagnieId(Long compagnieId) {
			this.compagnieId = compagnieId;
		}


		public void setStatusId(Long statusId) {
			this.statusId = statusId;
		}


		public Long getId() {
			return id;
		}
		

		public Long getAssignedToId() {
			return assignedToId;
		}


		public void setAssignedToId(Long assignedToId) {
			this.assignedToId = assignedToId;
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
	        Dossier dossier = (Dossier) o;
	        if (dossier.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), dossier.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

		@Override
		public String toString() {
			return "Dossiers [id=" + id + ", assignedToId=" + assignedToId + ", reference=" + reference
					+ ", incidentDate=" + incidentDate + ", incidentNature=" + incidentNature + ", companyName="
					+ companyName + ", nomAgentAssurance=" + nomAgentAssurance + ", numeroContrat=" + numeroContrat
					+ ", pack=" + pack + ", immatriculationVehicule=" + immatriculationVehicule + ", marque=" + marque
					+ ", isCompany=" + isCompany + ", prenom=" + prenom + ", nom=" + nom + ", raisonSociale="
					+ raisonSociale + ", typeService=" + typeService + ", serviceCount=" + serviceCount + ", statusId="
					+ statusId + ", compagnieId=" + compagnieId + ", agencyId=" + agencyId + "]";
		}

	
}
