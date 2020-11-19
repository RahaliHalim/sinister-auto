package com.gaconnecte.auxilium.service.referential.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RefHolidays entity.
 */
public class RefHolidaysDTO implements Serializable {

    private Long id;

    private String label;

    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RefHolidaysDTO refHolidaysDTO = (RefHolidaysDTO) o;
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
            ", date='" + getDate() + "'" +
            "}";
    }
}
