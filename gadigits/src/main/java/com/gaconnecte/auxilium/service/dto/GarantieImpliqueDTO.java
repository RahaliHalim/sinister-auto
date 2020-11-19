package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.javers.core.metamodel.annotation.DiffIgnore;

import com.gaconnecte.auxilium.domain.RefModeGestion;
import com.gaconnecte.auxilium.domain.Partner;
/**
 * A DTO for the Region entity.
 */
public class GarantieImpliqueDTO implements Serializable {
	@DiffIgnore
	private Long id;
	@DiffIgnore
	private Long reparateurId;
	@DiffIgnore
	private Set<RefModeGestionDTO> refModeGestions = new HashSet<>();
	@DiffIgnore
	private Long partnerId;
	private String partnerName;
	private String listModeGestions;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReparateurId() {
		return reparateurId;
	}

	public void setReparateurId(Long reparateurId) {
		this.reparateurId = reparateurId;
	}

	

	public Set<RefModeGestionDTO> getRefModeGestions() {
		return refModeGestions;
	}

	public void setRefModeGestions(Set<RefModeGestionDTO> refModeGestions) {
		this.refModeGestions = refModeGestions;
	}


	



	public String getListModeGestions() {
		return listModeGestions;
	}

	public void setListModeGestions(String listModeGestions) {
		this.listModeGestions = listModeGestions;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		GarantieImpliqueDTO regionDTO = (GarantieImpliqueDTO) o;
		if (regionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), regionDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "GarantieImpliqueDTO [id=" + id + ", reparateurId=" + reparateurId + ", refModeGestions="
				+ refModeGestions + ", partnerId=" + partnerId + "]";
	}



}
