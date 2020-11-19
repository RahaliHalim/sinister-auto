package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehicleBrandModel entity.
 */
public class VehicleBrandModelDTO implements Serializable {

    private Long id;

    private String label;

    private Boolean active;

    private Long brandId;

    private String brandLabel;

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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long vehicleBrandId) {
        this.brandId = vehicleBrandId;
    }

    public String getBrandLabel() {
        return brandLabel;
    }

    public void setBrandLabel(String vehicleBrandLabel) {
        this.brandLabel = vehicleBrandLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleBrandModelDTO vehicleBrandModelDTO = (VehicleBrandModelDTO) o;
        if(vehicleBrandModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleBrandModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleBrandModelDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
