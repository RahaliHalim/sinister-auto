package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

import org.javers.core.metamodel.annotation.DiffIgnore;

/**
 * A DTO for the VehicleBrand entity.
 */
public class VehicleBrandDTO implements Serializable {
	
    private Long id;

    private String label;
    @DiffIgnore
    private Boolean active;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleBrandDTO vehicleBrandDTO = (VehicleBrandDTO) o;
        if(vehicleBrandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleBrandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleBrandDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
