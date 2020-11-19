package com.gaconnecte.auxilium.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AgentGeneral entity.
 */
public class AgentGeneralDTO implements Serializable {

    private Long id;

    private Boolean isBloque;

    private Long personnePhysiqueId;

    private String personnePhysiqueNom;

    //private Long agenceId;

    //private String agenceNom;

    private Long serviceAssuranceId;

    private String serviceAssuranceLibelle;
    
    private Set<Long> agences = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsBloque() {
        return isBloque;
    }

    public void setIsBloque(Boolean isBloque) {
        this.isBloque = isBloque;
    }

    public Long getPersonnePhysiqueId() {
        return personnePhysiqueId;
    }

    public void setPersonnePhysiqueId(Long personnePhysiqueId) {
        this.personnePhysiqueId = personnePhysiqueId;
    }

    public String getPersonnePhysiqueNom() {
        return personnePhysiqueNom;
    }

    public void setPersonnePhysiqueNom(String personnePhysiqueNom) {
        this.personnePhysiqueNom = personnePhysiqueNom;
    }

    public Long getServiceAssuranceId() {
        return serviceAssuranceId;
    }

    public void setServiceAssuranceId(Long serviceAssuranceId) {
        this.serviceAssuranceId = serviceAssuranceId;
    }

    public String getServiceAssuranceLibelle() {
        return serviceAssuranceLibelle;
    }

    public void setServiceAssuranceLibelle(String serviceAssuranceLibelle) {
        this.serviceAssuranceLibelle = serviceAssuranceLibelle;
    }
    
	public Set<Long> getAgences() {
		return agences;
	}

	public void setAgences(Set<Long> agences) {
		this.agences = agences;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentGeneralDTO agentGeneralDTO = (AgentGeneralDTO) o;
        if(agentGeneralDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agentGeneralDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgentGeneralDTO{" +
            "id=" + getId() +
            ", isBloque='" + isIsBloque() + "'" +
            "}";
    }
}
