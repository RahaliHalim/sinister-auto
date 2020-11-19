package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;

/**
 * A DTO for the Reason entity.
 */
public class ReasonDTO implements Serializable {

    private Long id;

    private String code;

    private String label;

    private String description;

    private ResponsibleEnum responsible;

    private Boolean active;

    private Long stepId;
    
    private String stepLabel;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponsibleEnum getResponsible() {
        return responsible;
    }

    public void setResponsible(ResponsibleEnum responsible) {
        this.responsible = responsible;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public String getStepLabel() {
        return stepLabel;
    }

    public void setStepLabel(String stepLabel) {
        this.stepLabel = stepLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReasonDTO reasonDTO = (ReasonDTO) o;
        if(reasonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reasonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReasonDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
