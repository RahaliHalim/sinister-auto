package com.gaconnecte.auxilium.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.gaconnecte.auxilium.domain.enumeration.ResponsibleEnum;

/**
 * A DTO for the RaisonPec entity.
 */
public class RaisonPecDTO implements Serializable {

    private Long id;

    private String code;

    private String label;

    private Boolean active;

    private LocalDate creationDate;

    private Long creationUserId;

    private ResponsibleEnum responsible;

    private Long operationId;

    private String operationLabel;

    private Long pecStatusChangeMatrixId;

    private String pecStatusChangeMatrixLabel;
    
    private Long statusPecId;
    
    private String stepLabel;		

    private String statusPecLabel;		


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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(Long creationUserId) {
        this.creationUserId = creationUserId;
    }

    public ResponsibleEnum getResponsible() {
        return responsible;
    }

    public void setResponsible(ResponsibleEnum responsible) {
        this.responsible = responsible;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getOperationLabel() {
        return operationLabel;
    }

    public void setOperationLabel(String operationLabel) {
        this.operationLabel = operationLabel;
    }

    public Long getPecStatusChangeMatrixId() {
        return pecStatusChangeMatrixId;
    }

    public void setPecStatusChangeMatrixId(Long pecStatusChangeMatrixId) {
        this.pecStatusChangeMatrixId = pecStatusChangeMatrixId;
    }

    public String getPecStatusChangeMatrixLabel() {
        return pecStatusChangeMatrixLabel;
    }

    public void setPecStatusChangeMatrixLabel(String pecStatusChangeMatrixLabel) {
        this.pecStatusChangeMatrixLabel = pecStatusChangeMatrixLabel;
    }

    public Long getStatusPecId() {
        return statusPecId;
    }

    public void setStatusPecId(Long statusPecId) {
        this.statusPecId = statusPecId;
    }

    public String getStatusPecLabel() {
        return statusPecLabel;
    }

    public void setStatusPecLabel(String statusPecLabel) {
        this.statusPecLabel = statusPecLabel;
    }

    public String getStepLabel() {
		return stepLabel;
	}

	public void setStepLabel(String stepLabel) {
		this.stepLabel = stepLabel;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RaisonPecDTO raisonPecDTO = (RaisonPecDTO) o;
        if(raisonPecDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), raisonPecDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RaisonPecDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", active='" + isActive() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", creationUserId='" + getCreationUserId() + "'" +
            ", responsible='" + getResponsible() + "'" +
            "}";
    }
}
