package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import java.util.ArrayList;


public class RefActionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Long id;
	
    private Long oldStatus;
	
    private Long newStatus;
	
    private String entityName;
	
    private String label;
	
    private String translationCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Long oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Long getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Long newStatus) {
		this.newStatus = newStatus;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTranslationCode() {
		return translationCode;
	}

	public void setTranslationCode(String translationCode) {
		this.translationCode = translationCode;
	}

	@Override
	public String toString() {
		return "RefActionDTO [id=" + id + ", oldStatus=" + oldStatus + ", newStatus=" + newStatus + ", entityName="
				+ entityName + ", label=" + label + ", translationCode=" + translationCode + "]";
	}

	
    
 
    

}
