package com.gaconnecte.auxilium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AgentGeneral.
 */
@Entity
@Table(name = "agent_general")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agentgeneral")
public class AgentGeneral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "is_bloque")
    private Boolean isBloque;

    /**
     * Relations concernant l'agent general
     */
    @ApiModelProperty(value = "Relations concernant l'agent general")
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private PersonnePhysique personnePhysique;
    
    @ManyToOne
    private ServiceAssurance serviceAssurance;

    @ManyToMany(mappedBy = "agentGenerales")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PrestationPEC> dossiers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsBloque() {
        return isBloque;
    }

    public AgentGeneral isBloque(Boolean isBloque) {
        this.isBloque = isBloque;
        return this;
    }

    public void setIsBloque(Boolean isBloque) {
        this.isBloque = isBloque;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public AgentGeneral personnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
        return this;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    /*public AgentGeneral agence(RefAgence refAgence) {
        this.agence = refAgence;
        return this;
    }*/
    
   
	public ServiceAssurance getServiceAssurance() {
        return serviceAssurance;
    }

    public void setServiceAssurance(ServiceAssurance serviceAssurance) {
        this.serviceAssurance = serviceAssurance;
    }

    public Set<PrestationPEC> getDossiers() {
        return dossiers;
    }

    public AgentGeneral dossiers(Set<PrestationPEC> prestationPECS) {
        this.dossiers = prestationPECS;
        return this;
    }

    public AgentGeneral addDossier(PrestationPEC prestationPEC) {
        this.dossiers.add(prestationPEC);
        prestationPEC.getAgentGenerales().add(this);
        return this;
    }

    public AgentGeneral removeDossier(PrestationPEC prestationPEC) {
        this.dossiers.remove(prestationPEC);
        prestationPEC.getAgentGenerales().remove(this);
        return this;
    }

    public void setDossiers(Set<PrestationPEC> prestationPECS) {
        this.dossiers = prestationPECS;
    }
 
    
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AgentGeneral agentGeneral = (AgentGeneral) o;
        if (agentGeneral.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agentGeneral.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgentGeneral{" +
            "id=" + getId() +
            ", isBloque='" + isIsBloque() + "'" +
            "}";
    }
}
