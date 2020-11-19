package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Tarif.
 */
@Entity
@Table(name = "reparator_partner_mode")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartnerMode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "garantie_implique_id")
	@JsonBackReference
	private GarantieImplique garantieImplique;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Partner partner;

	@ManyToOne(fetch = FetchType.LAZY)
	private RefModeGestion mode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public GarantieImplique getGarantieImplique() {
		return garantieImplique;
	}

	public void setGarantieImplique(GarantieImplique garantieImplique) {
		this.garantieImplique = garantieImplique;
	}

	public RefModeGestion getMode() {
		return mode;
	}

	public void setMode(RefModeGestion mode) {
		this.mode = mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartnerMode other = (PartnerMode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
