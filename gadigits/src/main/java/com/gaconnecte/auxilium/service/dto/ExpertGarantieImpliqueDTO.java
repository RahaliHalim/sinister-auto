package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.DiffInclude;

import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefModeGestion;
/**
 * A DTO for the Region entity.
 */
public class ExpertGarantieImpliqueDTO implements Serializable {
	 @DiffIgnore
	private Long id;
	 @DiffIgnore
	private Long expertId;
	 @DiffIgnore
	private Long partnerId;
	private String partnerName;
	private Set<RefModeGestionDTO> refModeGestions = new HashSet<>();

	private	String refModeGestionsString ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public Set<RefModeGestionDTO> getRefModeGestions() {
		return refModeGestions;
	}

	public void setRefModeGestions(Set<RefModeGestionDTO> refModeGestions) {
		this.refModeGestions = refModeGestions;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	

	

	public String getRefModeGestionsString() {
		return refModeGestionsString;
	}

	public void setRefModeGestionsString(String refModeGestionsString) {
		this.refModeGestionsString = refModeGestionsString;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ExpertGarantieImpliqueDTO regionDTO = (ExpertGarantieImpliqueDTO) o;
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
		return "ExpertGarantieImpliqueDTO [id=" + id + ", expertId=" + expertId + ", partnerId=" + partnerId
				+ ", partnerName=" + partnerName + ", refModeGestions=" + refModeGestions + "]";
	}

	

}
