package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;

/**
 * A DTO for the ViewSinisterPrestation entity.
 */
public class ViewSinisterPrestationDTO implements Serializable {

    private Long id;

    private String reference;

    private LocalDate incidentDate;

    private String registrationNumber;

    private String fullName;

    private String serviceType;
    
    private String serviceTypeId;

    private String incidentLocationLabel;

    private String destinationLocationLabel;

    private String affectedTugId;
    
    private String affectedTugLabel;

    private Double priceTtc;

    private LocalDateTime insuredArrivalDate;

    private String cancelGroundsDescription;

    private String notEligibleGroundsDescription;

    private Long statusId;
    
    private LocalDateTime creationDate;
    
    private LocalDateTime tugArrivalDate;
    
    private LocalDateTime tugAssignmentDate;
    
    private LocalDateTime closureDate;
    
    private LocalDate updateDate;
    
    private String statusLabel;
    
    private String charge;
    
    private Long vehiculeId;
    
   // private String deliveryGovernorateLabel;
    		 
   // private Double days;	
    
   // private String loueurLabel;	
    
   // private LocalDateTime loueurAffectedDate;	
    
   // private LocalDateTime returnDate;
    
   // private String motifRefusLabel;
    
    //private LocalDateTime acquisitionDate;
    
   // private String incidentGovernorateLabel;
	//private String firstDriver;
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

    public String getAffectedTugId() {
		return affectedTugId;
	}

	public void setAffectedTugId(String affectedTugId) {
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final ViewSinisterPrestationDTO other = (ViewSinisterPrestationDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "ViewSinisterPrestationDTO [id=" + id + ", reference=" + reference + ", incidentDate=" + incidentDate
				+ ", registrationNumber=" + registrationNumber + ", fullName=" + fullName + ", serviceType="
				+ serviceType + ", incidentLocationLabel=" + incidentLocationLabel + ", destinationLocationLabel="
				+ destinationLocationLabel + ", affectedTugId=" + affectedTugId + ", affectedTugLabel="
				+ affectedTugLabel + ", priceTtc=" + priceTtc + ", insuredArrivalDate=" + insuredArrivalDate
				+ ", cancelGroundsDescription=" + cancelGroundsDescription + ", notEligibleGroundsDescription="
				+ notEligibleGroundsDescription + ", statusId=" + statusId + "]";
	}

    

}
