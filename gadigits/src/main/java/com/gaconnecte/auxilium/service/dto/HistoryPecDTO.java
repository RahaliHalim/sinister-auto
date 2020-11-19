package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class HistoryPecDTO implements Serializable{
	
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
	
	private List<ChangeValueDTO> changeValues;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

	public String getDescriptionOfHistorization() {
		return descriptionOfHistorization;
	}

	public void setDescriptionOfHistorization(String descriptionOfHistorization) {
		this.descriptionOfHistorization = descriptionOfHistorization;
	}

	public String getTypeDevis() {
		return typeDevis;
	}

	public void setTypeDevis(String typeDevis) {
		this.typeDevis = typeDevis;
	}

	public String getTypeAccord() {
		return typeAccord;
	}

	public void setTypeAccord(String typeAccord) {
		this.typeAccord = typeAccord;
	}

	public Long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}
	
	public List<ChangeValueDTO> getChangeValues() {
		return changeValues;
	}

	public void setChangeValues(List<ChangeValueDTO> changeValues) {
		this.changeValues = changeValues;
	}
	

}
