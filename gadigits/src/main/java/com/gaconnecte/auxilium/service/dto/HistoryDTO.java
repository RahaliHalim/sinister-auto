package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * A DTO for the Journal entity.
 */
public class HistoryDTO implements Serializable {

	private Long id;

	private String entityName;

	private Long entityId;

	private String operationName;

	private Long actionId;

	private String actionLabel;

	private LocalDateTime operationDate;

	private Long userId;

	private String firstName;

	private String lastName;

	private String descriptionOfHistorization;

	private String typeDevis;

	private String typeAccord;

	private Long quotationId;

	public String getTypeAccord() {
		return typeAccord;
	}

	public void setTypeAccord(String typeAccord) {
		this.typeAccord = typeAccord;
	}

	private List<ChangeValueDTO> changeValues;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public LocalDateTime getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(LocalDateTime operationDate) {
		this.operationDate = operationDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDescriptionOfHistorization() {
		return descriptionOfHistorization;
	}

	public void setDescriptionOfHistorization(String descriptionOfHistorization) {
		this.descriptionOfHistorization = descriptionOfHistorization;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getActionLabel() {
		return actionLabel;
	}

	public void setActionLabel(String actionLabel) {
		this.actionLabel = actionLabel;
	}

	public List<ChangeValueDTO> getChangeValues() {
		return changeValues;
	}

	public void setChangeValues(List<ChangeValueDTO> changeValues) {
		this.changeValues = changeValues;
	}

	public String getTypeDevis() {
		return typeDevis;
	}

	public void setTypeDevis(String typeDevis) {
		this.typeDevis = typeDevis;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoryDTO other = (HistoryDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "HistoryDTO [id=" + id + ", entityName=" + entityName + ", entityId=" + entityId + ", operationName="
				+ operationName + ", operationDate=" + operationDate + ", userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

}
