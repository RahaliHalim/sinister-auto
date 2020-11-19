package com.gaconnecte.auxilium.domain.app;

import com.gaconnecte.auxilium.domain.Agency;
import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.domain.ContratAssurance;
import com.gaconnecte.auxilium.domain.Delegation;
import com.gaconnecte.auxilium.domain.Governorate;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefNaturePanne;
import com.gaconnecte.auxilium.domain.SinisterPec;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import org.springframework.data.elasticsearch.annotations.Document;
import com.gaconnecte.auxilium.domain.referential.RefStatusSinister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Sinister.
 */
@Entity
@Table(name = "app_sinister")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "app_sinister")
public class Sinister implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "reference", nullable = false)
	private String reference;

	@Column(name = "deleted")
	private Boolean deleted;

	@Column(name = "phone")
	private String phone;

	@Column(name = "is_assure")
	private Boolean isAssure;
	@Column(name = "passenger_count")
	private Integer passengerCount;

	@Column(name = "conductor_first_name", length = 100)
	private String conductorFirstName;

	@Column(name = "conductor_last_name", length = 100)
	private String conductorLastName;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "incident_governorate_id")
	private Governorate governorate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "incident_location_id")
	private Delegation location;

	@Column(name = "incident_nature")
	private String nature;


	@Column(name = "incident_date", nullable = false)
	private LocalDate incidentDate;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "contract_id")
	private ContratAssurance contract;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "vehicle_id")
	private VehiculeAssure vehicle;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "insured_id")
	private Assure insured;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private RefStatusSinister status;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	private Partner partner;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@OneToOne(mappedBy = "sinister", cascade = CascadeType.ALL)
	@JoinColumn(name = "sinister_pec_id")
	private SinisterPec sinisterPec;

	@OneToMany(mappedBy = "sinister", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SinisterPrestation> prestations = new ArrayList<>();

	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;

	@Column(name = "closure_date")
	private LocalDateTime closureDate;

	@Column(name = "update_date")
	private LocalDate updateDate;

	@Column(name = "assignment_date")
	private LocalDate assignmentDate;

	public Boolean getIsAssure() {
		return isAssure;
	}

	public void setIsAssure(Boolean isAssure) {
		this.isAssure = isAssure;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "creation_user_id")
	private User creationUser;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "closure_user_id")
	private User closureUser;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "update_user_id")
	private User updateUser;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "assigned_to_id")
	private User assignedTo;
	
	@Column(name = "motif")
	private String motif;
	
	@ManyToOne
    @JoinColumn(name = "nature_panne_id")
    private RefNaturePanne naturePanne; 
	
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(Integer passengerCount) {
		this.passengerCount = passengerCount;
	}

	public String getConductorFirstName() {
		return conductorFirstName;
	}

	public void setConductorFirstName(String conductorFirstName) {
		this.conductorFirstName = conductorFirstName;
	}

	public String getConductorLastName() {
		return conductorLastName;
	}

	public void setConductorLastName(String conductorLastName) {
		this.conductorLastName = conductorLastName;
	}

	public Governorate getGovernorate() {
		return governorate;
	}

	public void setGovernorate(Governorate governorate) {
		this.governorate = governorate;
	}

	public Delegation getLocation() {
		return location;
	}



	public void setLocation(Delegation location) {
		this.location = location;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public ContratAssurance getContract() {
		return contract;
	}

	public void setContract(ContratAssurance contract) {
		this.contract = contract;
	}

	public VehiculeAssure getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehiculeAssure vehicle) {
		this.vehicle = vehicle;
	}

	public Assure getInsured() {
		return insured;
	}

	public void setInsured(Assure insured) {
		this.insured = insured;
	}

	public RefStatusSinister getStatus() {
		return status;
	}

	public void setStatus(RefStatusSinister status) {
		this.status = status;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public SinisterPec getSinisterPec() {
		return sinisterPec;
	}

	public void setSinisterPec(SinisterPec sinisterPec) {
		this.sinisterPec = sinisterPec;
	}

	public List<SinisterPrestation> getPrestations() {
		return prestations;
	}

	public void setPrestations(List<SinisterPrestation> prestations) {
		this.prestations = prestations;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(LocalDateTime closureDate) {
		this.closureDate = closureDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public User getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(User creationUser) {
		this.creationUser = creationUser;
	}

	public User getClosureUser() {
		return closureUser;
	}

	public void setClosureUser(User closureUser) {
		this.closureUser = closureUser;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public RefNaturePanne getNaturePanne() {
		return naturePanne;
	}

	public void setNaturePanne(RefNaturePanne naturePanne) {
		this.naturePanne = naturePanne;
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Sinister other = (Sinister) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Dossier{" + "id=" + id + ", reference=" + reference + ", governorate=" + governorate + ", location="
				+ location + ", nature=" + nature + ", incidentDate=" + incidentDate + ", status=" + status + '}';
	}

}
