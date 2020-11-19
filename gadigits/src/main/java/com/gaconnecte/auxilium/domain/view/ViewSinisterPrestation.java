package com.gaconnecte.auxilium.domain.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Sinister Prestation.
 */
@Entity
@Table(name = "view_sinister_prestation")
public class ViewSinisterPrestation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "service_type")
    private String serviceType;
    
    @Column(name = "service_type_id")
    private String serviceTypeId;

    @Column(name = "incident_location_label")
    private String incidentLocationLabel;

    @Column(name = "destination_location_label")
    private String destinationLocationLabel;

    @Column(name = "affected_tug_label")
    private String affectedTugLabel;
    
    @Column(name = "affected_tug_id")
    private Long affectedTugId;

    @Column(name = "price_ttc")
    private Double priceTtc;

    @Column(name = "insured_arrival_date")
    private LocalDateTime insuredArrivalDate;
    
    @Column(name = "tug_arrival_date")
    private LocalDateTime tugArrivalDate;
    
    @Column(name = "tug_assignment_date")
    private LocalDateTime tugAssignmentDate;

    @Column(name = "cancel_grounds_description")
    private String cancelGroundsDescription;

    @Column(name = "not_eligible_grounds_description")
    private String notEligibleGroundsDescription;

    @Column(name = "status_id")
    private Long statusId;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    @Column(name = "closure_date")
    private LocalDateTime closureDate;

    @Column(name = "update_date")
    private LocalDate updateDate;
    
    @Column(name = "status_label")
    private String statusLabel;
    
    @Column(name = "nom_charge")
    private String charge;
    
    @Column(name = "vehicule_id")
    private Long vehiculeId;
    
  
 //   @Column(name = "delivery_governorate_label")
  //  private String deliveryGovernorateLabel;
    		 
   // @Column(name = "days")
  //  private Double days;	
    
  //  @Column(name = "loueur_label")
  //  private String loueurLabel;	
    
   // @Column(name = "loueur_affected_date")
   // private LocalDateTime loueurAffectedDate;	
    
  //  @Column(name = "return_date")
  //  private LocalDateTime returnDate;
    
  //  @Column(name = "motif_refus_label")
   // private String motifRefusLabel;
    
  //  @Column(name = "acquisition_date")
  //  private LocalDateTime acquisitionDate;
    
    //@Column(name = "incident_governorate_label")
  //  private String incidentGovernorateLabel;
    
	//@Column(name = "first_driver")
	//private String firstDriver;

	//@Column(name = "second_driver")
	//private String secondDriver;
    
    public String getServiceTypeId() {
  		return serviceTypeId;
  	}
	
	public void setServiceTypeId(String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getIncidentLocationLabel() {
        return incidentLocationLabel;
    }

    public void setIncidentLocationLabel(String incidentLocationLabel) {
        this.incidentLocationLabel = incidentLocationLabel;
    }

    public String getDestinationLocationLabel() {
        return destinationLocationLabel;
    }

    public void setDestinationLocationLabel(String destinationLocationLabel) {
        this.destinationLocationLabel = destinationLocationLabel;
    }

    public String getAffectedTugLabel() {
        return affectedTugLabel;
    }

    public void setAffectedTugLabel(String affectedTugLabel) {
        this.affectedTugLabel = affectedTugLabel;
    }

    public Double getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(Double priceTtc) {
        this.priceTtc = priceTtc;
    }

    public LocalDateTime getInsuredArrivalDate() {
        return insuredArrivalDate;
    }

    public void setInsuredArrivalDate(LocalDateTime insuredArrivalDate) {
        this.insuredArrivalDate = insuredArrivalDate;
    }

    public String getCancelGroundsDescription() {
        return cancelGroundsDescription;
    }

    public void setCancelGroundsDescription(String cancelGroundsDescription) {
        this.cancelGroundsDescription = cancelGroundsDescription;
    }

    public String getNotEligibleGroundsDescription() {
        return notEligibleGroundsDescription;
    }

    public void setNotEligibleGroundsDescription(String notEligibleGroundsDescription) {
        this.notEligibleGroundsDescription = notEligibleGroundsDescription;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getAffectedTugId() {
		return affectedTugId;
	}

	public void setAffectedTugId(Long affectedTugId) {
		this.affectedTugId = affectedTugId;
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

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public Long getVehiculeId() {
		return vehiculeId;
	}

	public void setVehiculeId(Long vehiculeId) {
		this.vehiculeId = vehiculeId;
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

	
	@Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final ViewSinisterPrestation other = (ViewSinisterPrestation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ViewSinisterPrestation{" + "id=" + id + ", reference=" + reference + ", incidentDate=" + incidentDate + ", registrationNumber=" + registrationNumber + ", fullName=" + fullName + ", serviceType=" + serviceType + ", incidentLocationLabel=" + incidentLocationLabel + ", destinationLocationLabel=" + destinationLocationLabel + ", affectedTugLabel=" + affectedTugLabel + ", priceTtc=" + priceTtc + ", insuredArrivalDate=" + insuredArrivalDate + ", cancelGroundsDescription=" + cancelGroundsDescription + ", notEligibleGroundsDescription=" + notEligibleGroundsDescription + ", statusId=" + statusId + '}';
    }
    
}
