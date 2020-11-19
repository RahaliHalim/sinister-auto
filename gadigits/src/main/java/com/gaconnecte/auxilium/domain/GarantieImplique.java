package com.gaconnecte.auxilium.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gaconnecte.auxilium.domain.prm.Convention;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A RefModeGestion.
 */
@Entity
@Table(name = "reparateur_garantie_implique")

public class GarantieImplique implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reparateur_id", nullable = false, updatable = false, insertable = true)
    @JsonBackReference
    private Reparateur reparateur;

	@ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Partner partner;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "reparateur_garantie_implique_ref_mode_gestion", joinColumns = { @JoinColumn(name = "garantie_implique_id") }, inverseJoinColumns = {
			@JoinColumn(name = "ref_mode_gestion_id") })
	private Set<RefModeGestion> refModeGestions = new HashSet<>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reparateur getReparateur() {
		return reparateur;
	}

	public void setReparateur(Reparateur reparateur) {
		this.reparateur = reparateur;
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
		GarantieImplique orientation = (GarantieImplique) o;
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
