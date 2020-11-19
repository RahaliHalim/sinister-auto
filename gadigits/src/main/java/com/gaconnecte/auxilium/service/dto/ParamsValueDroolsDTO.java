package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

import com.gaconnecte.auxilium.domain.enumeration.TypeParamDrools;


/**
 * A DTO for the ParamsValueDrools entity.
 */

public class ParamsValueDroolsDTO implements Serializable {

	
	private Long id;
	private Integer numAccord;
	private Long compagnieId;
	private Long paramDroolsId;
	private String typeParamDrools;
	private Long minValue;
	private Long fixValue;
	private Long paramValue;
	private Long paramParentId;
	private Long garantieId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumAccord() {
		return numAccord;
	}
	public void setNumAccord(Integer numAccord) {
		this.numAccord = numAccord;
	}
	public Long getCompagnieId() {
		return compagnieId;
	}
	public void setCompagnieId(Long compagnieId) {
		this.compagnieId = compagnieId;
	}
	public Long getParamDroolsId() {
		return paramDroolsId;
	}
	public void setParamDroolsId(Long paramDroolsId) {
		this.paramDroolsId = paramDroolsId;
	}
	public String getTypeParamDrools() {
		return typeParamDrools;
	}
	public void setTypeParamDrools(String typeParamDrools) {
		this.typeParamDrools = typeParamDrools;
	}
	public Long getMinValue() {
		return minValue;
	}
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	public Long getFixValue() {
		return fixValue;
	}
	public void setFixValue(Long fixValue) {
		this.fixValue = fixValue;
	}
	public Long getParamValue() {
		return paramValue;
	}
	public void setParamValue(Long paramValue) {
		this.paramValue = paramValue;
	}
	public Long getParamParentId() {
		return paramParentId;
	}
	public void setParamParentId(Long paramParentId) {
		this.paramParentId = paramParentId;
	}
	public Long getGarantieId() {
		return garantieId;
	}
	public void setGarantieId(Long garantieId) {
		this.garantieId = garantieId;
	}
	
	
}
