package com.gaconnecte.auxilium.service.referential.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefHolidays entity.
 */
public class RefStatusSinisterDTO implements Serializable {

    private Long id;

    private String label;

    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefStatusSinisterDTO refHolidaysDTO = (RefStatusSinisterDTO) o;
        if(refHolidaysDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), refHolidaysDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "refHolidaysDTO {" +
            "id=" + getId() +
            ", libelle='" + getLabel() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
