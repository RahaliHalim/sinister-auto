package com.gaconnecte.auxilium.service.referential.dto;

import com.gaconnecte.auxilium.service.dto.RefModeGestionDTO;
import com.gaconnecte.auxilium.service.dto.RefExclusionDTO;
import com.gaconnecte.auxilium.service.dto.RefTypeServiceDTO;
import com.gaconnecte.auxilium.service.dto.VehicleUsageDTO;
import com.gaconnecte.auxilium.service.prm.dto.ApecSettingsDTO;
import com.gaconnecte.auxilium.service.prm.dto.SinisterTypeSettingDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the RefPack entity.
 */
public class RefPackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    private Double mileage;
    private Double price;
    private Double interventionNumber;
    private Double vrDaysNumber;
    private Double ceilingToConsume;

    private Long reinsurerId;
    private Long conventionId;
    private Long ConventionAmendmentId;
    private String reinsurerName;
    
    private boolean combinable;
    private boolean homeService;
    private String grantTimingList;

    private Set<RefTypeServiceDTO> serviceTypes;

    private boolean blocked;

    private Set<VehicleUsageDTO> usages;
    private Set<RefModeGestionDTO> modeGestions;
    private Set<RefExclusionDTO> exclusions;

    private Double companyPart;
    private Double reinsurerPart;
    private Double partnerPart;

    private List<SinisterTypeSettingDTO> sinisterTypeSettings;
    private List<ApecSettingsDTO> apecSettings;
    
    public String getGrantTimingList() {
        return grantTimingList;
    }

    public void setGrantTimingList(String grantTimingList) {
        this.grantTimingList = grantTimingList;
    }

    private boolean apecValidation;
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    public Long getConventionAmendmentId() {
		return ConventionAmendmentId;
	}

	public void setConventionAmendmentId(Long conventionAmendmentId) {
		ConventionAmendmentId = conventionAmendmentId;
	}

	/**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the mileage
     */
    public Double getMileage() {
        return mileage;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the interventionNumber
     */
    public Double getInterventionNumber() {
        return interventionNumber;
    }

    /**
     * @param interventionNumber the interventionNumber to set
     */
    public void setInterventionNumber(Double interventionNumber) {
        this.interventionNumber = interventionNumber;
    }

    /**
     * @return the vrDaysNumber
     */
    public Double getVrDaysNumber() {
        return vrDaysNumber;
    }

    /**
     * @param vrDaysNumber the vrDaysNumber to set
     */
    public void setVrDaysNumber(Double vrDaysNumber) {
        this.vrDaysNumber = vrDaysNumber;
    }

    /**
     * @return the ceilingToConsume
     */
    public Double getCeilingToConsume() {
        return ceilingToConsume;
    }

    /**
     * @param ceilingToConsume the ceilingToConsume to set
     */
    public void setCeilingToConsume(Double ceilingToConsume) {
        this.ceilingToConsume = ceilingToConsume;
    }

    public Long getReinsurerId() {
        return reinsurerId;
    }

    public void setReinsurerId(Long reinsurerId) {
        this.reinsurerId = reinsurerId;
    }

    public String getReinsurerName() {
        return reinsurerName;
    }

    public void setReinsurerName(String reinsurerName) {
        this.reinsurerName = reinsurerName;
    }

    /**
     * @return the combinable
     */
    public boolean isCombinable() {
        return combinable;
    }

    /**
     * @param combinable the combinable to set
     */
    public void setCombinable(boolean combinable) {
        this.combinable = combinable;
    }

    /**
     * @return the homeService
     */
    public boolean isHomeService() {
        return homeService;
    }

    /**
     * @param homeService the homeService to set
     */
    public void setHomeService(boolean homeService) {
        this.homeService = homeService;
    }

    public Set<RefTypeServiceDTO> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<RefTypeServiceDTO> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    /**
     * @return the blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * @param blocked the blocked to set
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Long getConventionId() {
        return conventionId;
    }

    public void setConventionId(Long conventionId) {
        this.conventionId = conventionId;
    }

    public Set<RefExclusionDTO> getExclusions() {
        return exclusions;
    }

    public void setExclusions(Set<RefExclusionDTO> exclusions) {
        this.exclusions = exclusions;
    }

    public Set<VehicleUsageDTO> getUsages() {
        return usages;
    }

    public void setUsages(Set<VehicleUsageDTO> usages) {
        this.usages = usages;
    }

    public Set<RefModeGestionDTO> getModeGestions() {
        return modeGestions;
    }

    public void setModeGestions(Set<RefModeGestionDTO> modeGestions) {
        this.modeGestions = modeGestions;
    }

    public Double getCompanyPart() {
        return companyPart;
    }

    public void setCompanyPart(Double companyPart) {
        this.companyPart = companyPart;
    }

    public Double getReinsurerPart() {
        return reinsurerPart;
    }

    public void setReinsurerPart(Double reinsurerPart) {
        this.reinsurerPart = reinsurerPart;
    }

    public Double getPartnerPart() {
        return partnerPart;
    }

    public void setPartnerPart(Double partnerPart) {
        this.partnerPart = partnerPart;
    }

    public List<SinisterTypeSettingDTO> getSinisterTypeSettings() {
        return sinisterTypeSettings;
    }

    public void setSinisterTypeSettings(List<SinisterTypeSettingDTO> sinisterTypeSettings) {
        this.sinisterTypeSettings = sinisterTypeSettings;
    }

    public List<ApecSettingsDTO> getApecSettings() {
        return apecSettings;
    }

    public void setApecSettings(List<ApecSettingsDTO> apecSettings) {
        this.apecSettings = apecSettings;
    }

    public boolean isApecValidation() {
        return apecValidation;
    }

    public void setApecValidation(boolean apecValidation) {
        this.apecValidation = apecValidation;
    }

    
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
     */
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
        RefPackDTO other = (RefPackDTO) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RefPackDTO{" + "id=" + id + ", label=" + label + ", mileage=" + mileage + ", price=" + price + ", interventionNumber=" + interventionNumber + ", vrDaysNumber=" + vrDaysNumber + ", ceilingToConsume=" + ceilingToConsume + ", combinable=" + combinable + ", homeService=" + homeService + ", grantTimingList=" + grantTimingList + ", serviceTypes=" + serviceTypes + ", blocked=" + blocked + ", usages=" + usages + ", modeGestions=" + modeGestions + ", exclusions=" + exclusions + ", companyPart=" + companyPart + ", reinsurerPart=" + reinsurerPart + ", partnerPart=" + partnerPart + ", sinisterTypeSettings=" + sinisterTypeSettings + '}';
    }
}
