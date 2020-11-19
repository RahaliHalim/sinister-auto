/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hannibaal
 */
@Entity
@Table(name = "report_tug_performance")
public class ReportTugPerformance implements Serializable {
    
    @Id
    private Long id; 
    
    @Column(name = "affected_tug_id")
    private Long affectedTugId;

    @Column(name = "affected_tug_label")
    private String affectedTugLabel; 

    @Column(name = "reference")
    private String reference;
    
    @Column(name = "registration_number")
    private String registrationNumber;
    
    @Column(name = "partner_id")
    private Long partnerId;
    
    @Column(name = "partner_name")
    private String partnerName;
    
    @Column(name = "service_type_label")
    private String serviceTypeLabel;
    
    @Column(name = "usage_label")
    private String usageLabel;

  
    @Column(name = "intervention_time_avg")
    private String interventionTimeAvg;
    
    @Column(name = "delai_operation")
    private String delaiOperation;
    
    @Column(name = "intervention_time_avg_min")
    private Long interventionTimeAvgMin;
    
    
    @Column(name = "has_habillage")
    private Boolean hasHabillage;
    
    @Column(name = "incident_governorate_label")
    private String incidentGovernorateLabel;
    
    @Column(name = "price_ttc")
    private BigDecimal priceTtc;
    
    @Column(name = "creation_date")
    private LocalDate creationDate;
    
    @Column(name = "zone_id")
    private Long zoneId;
    
    @Column(name = "insured_arrival_date")
    private LocalDateTime insuredArrivalDate;
    
    @Column(name = "tug_arrival_date")
    private LocalDateTime tugArrivalDate;
    
    @Column(name = "tug_assignment_date")
    private LocalDateTime tugAssignmentDate;
    
    @Column(name = "cancel_grounds_description")
    private String cancelGroundsDescription;
    
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
    
    @Column(name = "closure_date")
	private LocalDateTime closureDate;

    @Column(name = "creation_user")
    private String creationUser;
    
    @Column(name = "closure_user")
    private String closureUser;
    

	@Override
	public String toString() {
		return "ReportTugPerformance [id=" + id + ", affectedTugId=" + affectedTugId + ", affectedTugLabel="
				+ affectedTugLabel + ", reference=" + reference + ", registrationNumber=" + registrationNumber
				+ ", partnerId=" + partnerId + ", partnerName=" + partnerName + ", serviceTypeLabel=" + serviceTypeLabel
				+ ", usageLabel=" + usageLabel + ", interventionTimeAvg=" + interventionTimeAvg + ", delaiOperation="
				+ delaiOperation + ", interventionTimeAvgMin=" + interventionTimeAvgMin + ", hasHabillage="
				+ hasHabillage + ", incidentGovernorateLabel=" + incidentGovernorateLabel + ", priceTtc=" + priceTtc
				+ ", creationDate=" + creationDate + ", zoneId=" + zoneId + ", insuredArrivalDate=" + insuredArrivalDate
				+ ", tugArrivalDate=" + tugArrivalDate + ", tugAssignmentDate=" + tugAssignmentDate
				+ ", cancelGroundsDescription=" + cancelGroundsDescription + ", cancelDate=" + cancelDate
				+ ", closureDate=" + closureDate + ", creationUser=" + creationUser + ", closureUser=" + closureUser
				+ "]";
	}
    
	public LocalDateTime getClosureDate() {
		return closureDate;
	}



	public void setClosureDate(LocalDateTime closureDate) {
		this.closureDate = closureDate;
	}



	public String getCreationUser() {
		return creationUser;
	}



	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}



	public String getClosureUser() {
		return closureUser;
	}



	public void setClosureUser(String closureUser) {
		this.closureUser = closureUser;
	}



	public Long getInterventionTimeAvgMin() {
		return interventionTimeAvgMin;
	}



	public void setInterventionTimeAvgMin(Long interventionTimeAvgMin) {
		this.interventionTimeAvgMin = interventionTimeAvgMin;
	}



	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAffectedTugId() {
        return affectedTugId;
    }

    public void setAffectedTugId(Long affectedTugId) {
        this.affectedTugId = affectedTugId;
    }

    public String getAffectedTugLabel() {
        return affectedTugLabel;
    }

    public void setAffectedTugLabel(String affectedTugLabel) {
        this.affectedTugLabel = affectedTugLabel;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getServiceTypeLabel() {
        return serviceTypeLabel;
    }

    public void setServiceTypeLabel(String serviceTypeLabel) {
        this.serviceTypeLabel = serviceTypeLabel;
    }

    public String getUsageLabel() {
        return usageLabel;
    }

    public void setUsageLabel(String usageLabel) {
        this.usageLabel = usageLabel;
    }


    public String getInterventionTimeAvg() {
        return interventionTimeAvg;
    }

    public void setInterventionTimeAvg(String interventionTimeAvg) {
        this.interventionTimeAvg = interventionTimeAvg;
    }

    public Boolean getHasHabillage() {
        return hasHabillage;
    }

    public void setHasHabillage(Boolean hasHabillage) {
        this.hasHabillage = hasHabillage;
    }

    public String getIncidentGovernorateLabel() {
        return incidentGovernorateLabel;
    }

    public void setIncidentGovernorateLabel(String incidentGovernorateLabel) {
        this.incidentGovernorateLabel = incidentGovernorateLabel;
    }

    public BigDecimal getPriceTtc() {
        return priceTtc;
    }

    public void setPriceTtc(BigDecimal priceTtc) {
        this.priceTtc = priceTtc;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

	public String getDelaiOperation() {
		return delaiOperation;
	}



	public void setDelaiOperation(String delaiOperation) {
		this.delaiOperation = delaiOperation;
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



	public String getCancelGroundsDescription() {
		return cancelGroundsDescription;
	}



	public void setCancelGroundsDescription(String cancelGroundsDescription) {
		this.cancelGroundsDescription = cancelGroundsDescription;
	}



	public LocalDateTime getCancelDate() {
		return cancelDate;
	}



	public void setCancelDate(LocalDateTime cancelDate) {
		this.cancelDate = cancelDate;
	}

}
