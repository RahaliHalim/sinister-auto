package com.gaconnecte.auxilium.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;


@Entity
@Table(name = "observation_apec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "observation_apec")
public class ObservationApec implements Serializable {
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "observation_assure")
    private String observationAssure;

    @Column(name = "description_observation_assure")
    private String decriptionObservationAssure;
    
    @Column(name = "apec_id")
	private Long apecId;
	
	@Column(name = "approuv_date")
    private LocalDateTime approuvDate;

    @Column(name = "modif_date")
    private LocalDateTime modifDate;

    @Column(name = "validation_date")
    private LocalDateTime validationDate;

    @Column(name = "assure_validation_date")
    private LocalDateTime assureValidationDate;

    @Column(name = "imprim_date")
	private LocalDateTime imprimDate;
	
	@Column(name = "decision_approbation_compagnie")
	private Long decisionApprobationCompagnie;
	
	@Column(name = "decision_validation_apec")
	private Long decisionValidationApec;
	
	@Column(name = "decision_validation_part_assure")
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
		ObservationApec other = (ObservationApec) obj;
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
