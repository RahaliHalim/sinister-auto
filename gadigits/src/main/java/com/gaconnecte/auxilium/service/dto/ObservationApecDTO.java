package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ObservationApecDTO implements Serializable {

	private Long id;

	private String observationAssure;

	private String decriptionObservationAssure;

	private Long apecId;

	private LocalDateTime approuvDate;

	private LocalDateTime modifDate;

	private LocalDateTime validationDate;

	private LocalDateTime assureValidationDate;

	private LocalDateTime imprimDate;

	private Long decisionApprobationCompagnie;

	private Long decisionValidationApec;

	private Long decisionValidationPartAssure;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservationAssure() {
		return observationAssure;
	}

	public void setObservationAssure(String observationAssure) {
		this.observationAssure = observationAssure;
	}

	public String getDecriptionObservationAssure() {
		return decriptionObservationAssure;
	}

	public void setDecriptionObservationAssure(String decriptionObservationAssure) {
		this.decriptionObservationAssure = decriptionObservationAssure;
	}

	public Long getApecId() {
		return apecId;
	}

	public void setApecId(Long apecId) {
		this.apecId = apecId;
	}

	public LocalDateTime getApprouvDate() {
		return approuvDate;
	}

	public void setApprouvDate(LocalDateTime approuvDate) {
		this.approuvDate = approuvDate;
	}

	public LocalDateTime getModifDate() {
		return modifDate;
	}

	public void setModifDate(LocalDateTime modifDate) {
		this.modifDate = modifDate;
	}

	public LocalDateTime getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(LocalDateTime validationDate) {
		this.validationDate = validationDate;
	}

	public LocalDateTime getAssureValidationDate() {
		return assureValidationDate;
	}

	public void setAssureValidationDate(LocalDateTime assureValidationDate) {
		this.assureValidationDate = assureValidationDate;
	}

	public LocalDateTime getImprimDate() {
		return imprimDate;
	}

	public void setImprimDate(LocalDateTime imprimDate) {
		this.imprimDate = imprimDate;
	}

	public Long getDecisionApprobationCompagnie() {
		return decisionApprobationCompagnie;
	}

	public void setDecisionApprobationCompagnie(Long decisionApprobationCompagnie) {
		this.decisionApprobationCompagnie = decisionApprobationCompagnie;
	}

	public Long getDecisionValidationApec() {
		return decisionValidationApec;
	}

	public void setDecisionValidationApec(Long decisionValidationApec) {
		this.decisionValidationApec = decisionValidationApec;
	}

	public Long getDecisionValidationPartAssure() {
		return decisionValidationPartAssure;
	}

	public void setDecisionValidationPartAssure(Long decisionValidationPartAssure) {
		this.decisionValidationPartAssure = decisionValidationPartAssure;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((decriptionObservationAssure == null) ? 0 : decriptionObservationAssure.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observationAssure == null) ? 0 : observationAssure.hashCode());
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
		ObservationApecDTO other = (ObservationApecDTO) obj;
		if (decriptionObservationAssure == null) {
			if (other.decriptionObservationAssure != null)
				return false;
		} else if (!decriptionObservationAssure.equals(other.decriptionObservationAssure))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (observationAssure == null) {
			if (other.observationAssure != null)
				return false;
		} else if (!observationAssure.equals(other.observationAssure))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObservationApec [id=" + id + ", observationAssure=" + observationAssure
				+ ", decriptionObservationAssure=" + decriptionObservationAssure + "]";
	}

}
