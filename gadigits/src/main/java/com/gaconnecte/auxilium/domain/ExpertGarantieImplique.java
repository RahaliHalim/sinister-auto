package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//import org.javers.core.metamodel.annotation.DiffIgnore;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * A RefModeGestion.
 */
@Entity
@Table(name = "expert_garantie_implique")

public class ExpertGarantieImplique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false, updatable = false, insertable = true)
    @JsonBackReference
    private Expert expert;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Partner partner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "expert_garantie_implique_ref_mode_gestion", joinColumns = {
        @JoinColumn(name = "expert_garantie_implique_id")}, inverseJoinColumns = {
        @JoinColumn(name = "ref_mode_gestion_id")})
    private Set<RefModeGestion> refModeGestions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Set<RefModeGestion> getRefModeGestions() {
        return refModeGestions;
    }

    public void setRefModeGestions(Set<RefModeGestion> refModeGestions) {
        this.refModeGestions = refModeGestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExpertGarantieImplique orientation = (ExpertGarantieImplique) o;
        if (orientation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orientation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
