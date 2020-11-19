package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Sinister entity.
 */
public class SinisterDTO implements Serializable {

    private Long id;
    private String reference;
    private Boolean deleted;
    private String phone;
    private Integer passengerCount;
    private String conductorFirstName;
    private String conductorLastName;

    private Long governorateId;
    private String governorateLabel;

    private Long locationId;
    private String locationLabel;
    private Boolean isAssure;
    private String nature;
    private LocalDate incidentDate;

    private Long contractId;
    private Long vehicleId;
    private Long insuredId;    
    private String insuredFullName;
    private String insuredName;
    private String insuredSurName;
    private String tel;
    private String vehicleRegistration;

    private Long statusId;
    private String statusLabel;
    
    private Long partnerId;
    private String partnerLabel;

    private Long agencyId;
    private String agencyLabel;

    private String usageLabel;
    
    private String motif; 
    
    private double frequencyRate;
    
    private SinisterPecDTO sinisterPec;
    
    private List<SinisterPrestationDTO> prestations = new ArrayList<>();
    
    private LocalDateTime creationDate;
    private LocalDate creationD ;
    private LocalDateTime closureDate;
    private LocalDate updateDate;
    private LocalDate assignmentDate;

    private Long creationUserId;
    private Long closureUserId;
    private Long updateUserId;
    private Long assignedToId;
    private Boolean generatedBonSortie;
    private Long naturePanneId;

    public LocalDate getCreationD() {
		return creationD;
	}

	public void setCreationD(LocalDate creationD) {
		this.creationD = creationD;
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

    public Long getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(Long governorateId) {
        this.governorateId = governorateId;
    }

    public String getGovernorateLabel() {
        return governorateLabel;
    }

    public void setGovernorateLabel(String governorateLabel) {
        this.governorateLabel = governorateLabel;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getInsuredId() {
        return insuredId;
    }

    public void setInsuredId(Long insuredId) {
        this.insuredId = insuredId;
    }

    public String getInsuredFullName() {
        return insuredFullName;
    }

    public void setInsuredFullName(String insuredFullName) {
        this.insuredFullName = insuredFullName;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getInsuredSurName() {
        return insuredSurName;
    }

    public void setInsuredSurName(String insuredSurName) {
        this.insuredSurName = insuredSurName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Boolean getIsAssure() {
		return isAssure;
	}

	public void setIsAssure(Boolean isAssure) {
		this.isAssure = isAssure;
	}

	public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }
    
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerLabel() {
        return partnerLabel;
    }

    public void setPartnerLabel(String partnerLabel) {
        this.partnerLabel = partnerLabel;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyLabel() {
        return agencyLabel;
    }

    public void setAgencyLabel(String agencyLabel) {
        this.agencyLabel = agencyLabel;
    }

    public String getUsageLabel() {
        return usageLabel;
    }

    public void setUsageLabel(String usageLabel) {
        this.usageLabel = usageLabel;
    }

    public double getFrequencyRate() {
        return frequencyRate;
    }

    public void setFrequencyRate(double frequencyRate) {
        this.frequencyRate = frequencyRate;
    }

    public SinisterPecDTO getSinisterPec() {
        return sinisterPec;
    }

    public void setSinisterPec(SinisterPecDTO sinisterPec) {
        this.sinisterPec = sinisterPec;
    }
    public List<SinisterPrestationDTO> getPrestations() {
        return prestations;
    }

    public void setPrestations(List<SinisterPrestationDTO> prestations) {
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

    public Long getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
    }

    public Long getClosureUserId() {
        return closureUserId;
    }

    public void setClosureUserId(Long closureUserId) {
        this.closureUserId = closureUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Boolean getGeneratedBonSortie() {
		return generatedBonSortie;
	}

	public void setGeneratedBonSortie(Boolean generatedBonSortie) {
		this.generatedBonSortie = generatedBonSortie;
	}


	public Long getNaturePanneId() {
		return naturePanneId;
	}

	public void setNaturePanneId(Long naturePanneId) {
		this.naturePanneId = naturePanneId;
	}


	@Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final SinisterDTO other = (SinisterDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
	public String toString() {
		return "SinisterDTO [id=" + id + ", reference=" + reference + ", deleted=" + deleted + ", phone=" + phone
				+ ", passengerCount=" + passengerCount + ", conductorFirstName=" + conductorFirstName
				+ ", conductorLastName=" + conductorLastName + ", governorateId=" + governorateId
				+ ", governorateLabel=" + governorateLabel + ", locationId=" + locationId + ", locationLabel="
				+ locationLabel + ", isAssure=" + isAssure + ", nature=" + nature + ", incidentDate=" + incidentDate
				+ ", contractId=" + contractId + ", vehicleId=" + vehicleId + ", insuredId=" + insuredId
				+ ", insuredFullName=" + insuredFullName + ", insuredName=" + insuredName + ", insuredSurName="
				+ insuredSurName + ", tel=" + tel + ", vehicleRegistration=" + vehicleRegistration + ", statusId="
				+ statusId + ", statusLabel=" + statusLabel + ", partnerId=" + partnerId + ", partnerLabel="
				+ partnerLabel + ", agencyId=" + agencyId + ", agencyLabel=" + agencyLabel + ", usageLabel="
				+ usageLabel + ", motif=" + motif + ", frequencyRate=" + frequencyRate + ", sinisterPec=" + sinisterPec
				+ ", prestations=" + prestations + ", creationDate=" + creationDate + ", creationD=" + creationD
				+ ", closureDate=" + closureDate + ", updateDate=" + updateDate + ", assignmentDate=" + assignmentDate
				+ ", creationUserId=" + creationUserId + ", closureUserId=" + closureUserId + ", updateUserId="
				+ updateUserId + ", assignedToId=" + assignedToId + ", generatedBonSortie=" + generatedBonSortie + "]";
	}
    
}
