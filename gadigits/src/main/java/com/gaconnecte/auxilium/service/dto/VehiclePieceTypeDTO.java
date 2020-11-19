package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehiclePieceType entity.
 */
public class VehiclePieceTypeDTO implements Serializable {

    private Long id;

    private String code;

    private String label;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehiclePieceTypeDTO vehiclePieceTypeDTO = (VehiclePieceTypeDTO) o;
        if(vehiclePieceTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiclePieceTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiclePieceTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
