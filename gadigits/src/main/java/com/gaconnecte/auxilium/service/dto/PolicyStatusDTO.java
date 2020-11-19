package com.gaconnecte.auxilium.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PolicyStatus entity.
 */
public class PolicyStatusDTO implements Serializable {

    private Long id;

    private String code;

    private String label;

    private Boolean active;

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

        PolicyStatusDTO policyStatusDTO = (PolicyStatusDTO) o;
        if(policyStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policyStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolicyStatusDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
