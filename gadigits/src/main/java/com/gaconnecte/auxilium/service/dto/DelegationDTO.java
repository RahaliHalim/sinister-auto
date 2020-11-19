package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.javers.core.metamodel.annotation.DiffIgnore;

import java.util.Objects;

/**
 * A DTO for the Delegation entity.
 */
public class DelegationDTO implements Serializable {
	
    private Long id;

    private String label;
    @DiffIgnore
    private BigDecimal latitude;
    @DiffIgnore
    private BigDecimal longitude;
    @DiffIgnore
    private Long governorateId;
    @DiffIgnore
    private String governorateLabel;
    @DiffIgnore
    private Boolean addedGageo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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

    public Boolean getAddedGageo() {
		return addedGageo;
	}

	public void setAddedGageo(Boolean addedGageo) {
		this.addedGageo = addedGageo;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DelegationDTO delegationDTO = (DelegationDTO) o;
        if(delegationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), delegationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DelegationDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            "}";
    }
}
