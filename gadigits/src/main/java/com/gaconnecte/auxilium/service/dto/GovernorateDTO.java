package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.javers.core.metamodel.annotation.DiffIgnore;

/**
 * A DTO for the Governorate entity.
 */
public class GovernorateDTO implements Serializable {
	
    private Long id;
	@DiffIgnore
    private String code;

    private String label;
    @DiffIgnore
    private BigDecimal latitude;
    @DiffIgnore
    private BigDecimal longitude;
    @DiffIgnore
    private Long regionId;
    @DiffIgnore
    private String regionLabel;
    @DiffIgnore
    private Boolean addedGageo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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



    public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionLabel() {
		return regionLabel;
	}

	public void setRegionLabel(String regionLabel) {
		this.regionLabel = regionLabel;
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

        GovernorateDTO governorateDTO = (GovernorateDTO) o;
        if(governorateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), governorateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GovernorateDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            "}";
    }
}
